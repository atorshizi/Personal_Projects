#include <Arduino.h>
#include <TFT_eSPI.h>
#include <driver/i2s.h>
#include <complex>
#include <WiFi.h>
#include <PubSubClient.h>

using namespace std;

#define I2S_SCK  26
#define I2S_WS   12
#define I2S_SD   13
#define I2S_PORT I2S_NUM_0

#define SAMPLES 128 
#define SAMPLE_RATE 12000

TFT_eSPI tft = TFT_eSPI();

double vReal[SAMPLES];
double vImag[SAMPLES];

enum FFTWindow {
  FFT_WIN_TYP_HAMMING,
  FFT_WIN_TYP_HANN,
};

template<typename T> 
class FFT {
  private:
  T* vReal; 
  T* vImag; 
  uint32_t numSamples;
  uint32_t sampleRate;
  void recursiveFFT(complex<T>* array, uint32_t n) {
    if (n <= 1) return;

    complex<T>* even = new complex<T>[n / 2];
    complex<T>* odd = new complex<T>[n / 2];
    for (uint32_t i = 0; i < n / 2; i++) {
      even[i] = array[i * 2];
      odd[i] = array[i * 2 + 1];
    }

    recursiveFFT(even, n / 2);
    recursiveFFT(odd, n / 2);

    for (uint32_t k = 0; k < n / 2; k++) {
        T angle = -2.0 * M_PI * k / n;
        complex<T> twiddle(cos(angle), sin(angle));
        complex<T> t = twiddle * odd[k];
        array[k] = even[k] + t;
        array[k + n / 2] = even[k] - t;
    }
    delete[] even;
    delete[] odd;
  }

  public: 
  FFT(T* vReal, T* vImag, uint32_t numSamples, uint32_t sampleRate){
    this->vReal = vReal;
    this->vImag = vImag;
    this->numSamples = numSamples;
    this->sampleRate = sampleRate;
  }

  void windowing(FFTWindow windowType){
    switch(windowType){
      case FFT_WIN_TYP_HAMMING:
        for (uint32_t i = 0; i < numSamples; i++) {
          vReal[i] *= 0.54 - 0.46 * cos(2 * PI * i / (numSamples - 1));
        }
        break;
      case FFT_WIN_TYP_HANN:
        for (uint32_t i = 0; i < numSamples; i++) {
          vReal[i] *= 0.5 * (1 - cos(2 * PI * i / (numSamples - 1)));
        }
        break;
      default:
        throw "Window type not yet supported";
        break;
    }
  }
  void compute(){
    complex<T>* data = new complex<T>[numSamples];
    for (uint32_t i = 0; i < numSamples; i++) {
      data[i] = complex<T>(this->vReal[i], this->vImag[i]);
    }
    recursiveFFT(data, numSamples);
    for (uint32_t i = 0; i < numSamples; i++) {
      this->vReal[i] = data[i].real();
      this->vImag[i] = data[i].imag();
    }
    delete[] data;
  }
  void complexToMagnitude(){
    for (uint32_t i = 0; i < numSamples / 2; i++) {
      this->vReal[i] = sqrt(this->vReal[i] * this->vReal[i] + this->vImag[i] * this->vImag[i]);
    }
  }
};

FFT<double> fft(vReal, vImag, SAMPLES, SAMPLE_RATE);

#define SCREEN_WIDTH  320
#define SCREEN_HEIGHT 240

const char* ssid     = "NetworkName";
const char* password = "NetworkPassword";

WiFiClient espClient;
PubSubClient mqtt(espClient);
const char* mqttServer = "broker.hivemq.com";
const int mqttPort = 1883;
const char* mqttTopic = "spectrum/arian-esp32"; 

void reconnectMQTT() {
  while (!mqtt.connected()) {
    Serial.print("Connecting to MQTT...");
    if (mqtt.connect("esp32-spectrum-client")) {
      Serial.println("connected");
    } else {
      Serial.print("failed, rc=");
      Serial.println(mqtt.state());
      delay(2000);
    }
  }
}


void setup() {
  Serial.begin(115200);

  WiFi.begin(ssid, password);
  
  Serial.print("Connecting to WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  
  Serial.println();
  Serial.print("Connected! IP address: ");
  Serial.println(WiFi.localIP());

  mqtt.setBufferSize(512);
  mqtt.setServer(mqttServer, mqttPort);

  tft.init();
  tft.setRotation(1);
  tft.fillScreen(TFT_BLACK);
  
  i2s_config_t i2s_config = {
    .mode = (i2s_mode_t)(I2S_MODE_MASTER | I2S_MODE_RX),
    .sample_rate = SAMPLE_RATE,
    .bits_per_sample = I2S_BITS_PER_SAMPLE_32BIT,
    .channel_format = I2S_CHANNEL_FMT_ONLY_LEFT,
    .communication_format = I2S_COMM_FORMAT_STAND_I2S,
    .intr_alloc_flags = ESP_INTR_FLAG_LEVEL1,
    .dma_buf_count = 4,
    .dma_buf_len = 1024,
    .use_apll = false,
    .tx_desc_auto_clear = false,
    .fixed_mclk = 0
  };
  
  i2s_pin_config_t pin_config = {
    .bck_io_num = I2S_SCK,
    .ws_io_num = I2S_WS,
    .data_out_num = I2S_PIN_NO_CHANGE,
    .data_in_num = I2S_SD
  };
  
  i2s_driver_install(I2S_PORT, &i2s_config, 0, NULL);
  i2s_set_pin(I2S_PORT, &pin_config);
}

void loop() {
  if (!mqtt.connected()) {
    reconnectMQTT();
  }
  mqtt.loop();

  int32_t sample_buffer[SAMPLES];
  size_t bytes_read;
  
  i2s_read(I2S_PORT, sample_buffer, sizeof(sample_buffer), &bytes_read, portMAX_DELAY);
  
  for (int i = 0; i < SAMPLES; i++) {
    vReal[i] = (double)(sample_buffer[i] >> 8);
    vImag[i] = 0.0;
  }
  
  fft.windowing(FFT_WIN_TYP_HAMMING);
  fft.compute();
  fft.complexToMagnitude();
  
  double maxMag = 0;
  for (int i = 2; i < SAMPLES / 2; i++) { // skips DC 
    if (vReal[i] > maxMag) maxMag = vReal[i];
  }
  if (maxMag < 1) maxMag = 1;
    
  int barWidth = SCREEN_WIDTH / (SAMPLES / 2);
  if (barWidth < 1) barWidth = 1;

  int barHeights[SAMPLES / 2 - 2];
  
  for (int i = 2; i < SAMPLES / 2; i++) {
    int barHeight = map(vReal[i], 0, maxMag, 0, SCREEN_HEIGHT - 20);
    barHeight = constrain(barHeight, 0, SCREEN_HEIGHT - 20);
    barHeights[i - 2] = barHeight;
  }

  // for (int i = 2; i < SAMPLES / 2; i++) {
  //   double magnitude = vReal[i];
  //   double dB = 10 * log10(magnitude + 1e-6); 
  //   int barHeight = map(dB, -10, 110, 0, SCREEN_HEIGHT - 20);
  //   barHeight = constrain(barHeight, 0, SCREEN_HEIGHT - 20);
  //   barHeights[i - 2] = barHeight;
  // }

  static int frameCount = 0;
  frameCount++;

  if (frameCount % 5 == 0) {
    tft.fillScreen(TFT_BLACK);
    for (int i = 2; i < SAMPLES / 2; i++) {
      int x = (i - 2) * barWidth;
      tft.fillRect(x, SCREEN_HEIGHT - barHeights[i-2], barWidth - 1, barHeights[i-2], TFT_ORANGE);
    }
  }

  mqtt.publish(mqttTopic, (uint8_t*)barHeights, sizeof(barHeights));
}