const functions = require('../../scripts.js');
// basic test to make sure readings array is saved properly onto localStorage
test('readings are saved on localStorage', () => {
  const readings = [];
  const reading = functions.generateReading('How can I create more balance in my friendships?');
  readings[0] = reading;
  functions.saveReadings(readings);
  const retrieved = JSON.parse(localStorage.getItem('readings'));
  expect(retrieved).toEqual(readings);
});

// basic test to make sure that the saved array of readings is being retrieved correctly
test('readings are retrieved correctly from localStorage', () => {
  const readings = [];
  const reading = functions.generateReading('How can I create more balance in my friendships?');
  readings[0] = reading;
  functions.saveReadings(readings);
  const retrieved = functions.getReadings();
  expect(retrieved).toEqual(readings);
});

//basic test to make sure that a reading is being saved correctly
test('reading is saved on localStorage', () => {
  const readings = [];
  functions.saveReadings(readings);
  const reading = functions.generateReading('How can I create more balance in my friendships?');
  functions.saveReading(reading);
  let retrieved = JSON.parse(localStorage.getItem('readings'));
  retrieved = retrieved[0];
  expect(retrieved).toEqual(reading);
});

// basic test to make sure that a reading is being retrieved correctly
test('reading is saved on localStorage', () => {
  const readings = [];
  functions.saveReadings(readings);
  const reading = functions.generateReading('How can I create more balance in my friendships?');
  const key = reading.id;
  functions.saveReading(reading);
  const retrieved = functions.getReading(key);
  expect(retrieved).toEqual(reading);
});

// basic test to make sure that a reading is removed properly
test('reading is deleted on localStorage', () => {
  const readings = [];
  functions.saveReadings(readings);
  const reading = functions.generateReading('How can I create more balance in my friendships?');
  const key = reading.id;
  functions.saveReading(reading);
  functions.deleteReading(key);
  const retrieved = JSON.parse(localStorage.getItem('readings'));
  expect(retrieved).toEqual([]);
});

// basic test to make sure that all readings are removed properly
test('readings are deleted on localStorage', () => {
  const readings = [];
  functions.saveReadings(readings);
  const reading = functions.generateReading('How can I create more balance in my friendships?');
  functions.saveReading(reading);
  functions.deleteAllReadings();
  const retrieved = localStorage.getItem('readings');
  expect(retrieved).toEqual('');
});

// basic test to make sure that a reading is renamed properly
test('reading is renamed on localStorage', () => {
  const readings = [];
  functions.saveReadings(readings);
  const reading = functions.generateReading('How can I create more balance in my friendships?');
  const key = reading.id;
  functions.saveReading(reading);
  functions.renameReading('test1',key);
  const retrieved = functions.getReading(key);
  expect(retrieved.name).toEqual('test1');
});

//test to save multiple readings to localStorage
test('add multiple readings to localStorage', () => {
  let reading = functions.generateReading('How can I create more balance in my friendships?');
  let reading2 = functions.generateReading('What do I need to focus on at my current workplace?');
  let reading3 = functions.generateReading('How is my past affecting my present?');
  let reading4 = functions.generateReading('How can I better strengthen my current relationship?');
  let readings = [reading,reading2,reading3,reading4];
  functions.saveReadings(readings);
  let retrieved = JSON.parse(localStorage.getItem('readings'));
  expect(retrieved).toEqual(readings);
});

//test to get readings from localStorage
test('add multiple readings to localStorage', () => {
  let questions = [
    'How can I create more balance in my friendships?',
    'What do I need to focus on at my current workplace?',
    'How is my past affecting my present?',
    'How can I better strengthen my current relationship?'
  ];
  let readings = [];
  for (let i = 0; i < questions.length; i++) {
    readings.push(functions.generateReading(questions[i]));
  }
  functions.saveReadings(readings);
  let retrieved = functions.getReadings();
  expect(retrieved).toEqual(readings);
});