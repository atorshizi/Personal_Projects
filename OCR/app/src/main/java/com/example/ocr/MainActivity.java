package com.example.ocr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Object>[] Formats = new ArrayList[13];
    File image = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Window statBar = getWindow();
        statBar.setStatusBarColor(0x00000000);
        TextView text = findViewById(R.id.textView2);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        if ((ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        Formats[0] = new ArrayList<>();Formats[0].add(Barcode.FORMAT_CODE_128);Formats[0].add("CODE_128");
        Formats[1] = new ArrayList<>();Formats[1].add(Barcode.FORMAT_CODE_39);Formats[1].add("CODE_39");
        Formats[2] = new ArrayList<>();Formats[2].add(Barcode.FORMAT_CODE_93);Formats[2].add("CODE_93");
        Formats[3] = new ArrayList<>();Formats[3].add(Barcode.FORMAT_CODABAR);Formats[3].add("CODABAR");
        Formats[4] = new ArrayList<>();Formats[4].add(Barcode.FORMAT_EAN_13);Formats[4].add("EAN_13");
        Formats[5] = new ArrayList<>();Formats[5].add(Barcode.FORMAT_EAN_8);Formats[5].add("EAN_8");
        Formats[6] = new ArrayList<>();Formats[6].add(Barcode.FORMAT_ITF);Formats[6].add("ITF");
        Formats[7] = new ArrayList<>();Formats[7].add(Barcode.FORMAT_UPC_A);Formats[7].add("UPC_A");
        Formats[8] = new ArrayList<>();Formats[8].add(Barcode.FORMAT_UPC_E);Formats[8].add("UPC_E");
        Formats[9] = new ArrayList<>();Formats[9].add(Barcode.FORMAT_QR_CODE);Formats[9].add("QR_CODE");
        Formats[10] = new ArrayList<>();Formats[10].add(Barcode.FORMAT_PDF417);Formats[10].add("PDF417");
        Formats[11] = new ArrayList<>();Formats[11].add(Barcode.FORMAT_AZTEC);Formats[11].add("AZTEC");
        Formats[12] = new ArrayList<>();Formats[12].add(Barcode.FORMAT_DATA_MATRIX);Formats[12].add("DATA_MATRIX");

        Button CFF = findViewById(R.id.button3);
        Button TF = findViewById(R.id.button2);
        Button BQR = findViewById(R.id.button);
        //set on click listeners for the three buttons
        CFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int OLDIMG = 0;
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Pics"), OLDIMG);
            }
        });
        TF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int NEWIMG = 1;
                capture(v,NEWIMG);
            }
        });
        BQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int NEWIMGQR = 2;
                capture(v, NEWIMGQR);
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        long MAX_FILE_SIZE = 0;

        if ((resultCode != RESULT_OK)){
            return;
        }
        if (requestCode == 0) {
            Uri image = data.getData();
            // check size
            MAX_FILE_SIZE = 10000000;
            long size = 0;
            try {
                AssetFileDescriptor afd = getApplicationContext().getContentResolver().openAssetFileDescriptor(image, "r");
                size = afd.getLength();
            } catch (Exception e){
                //do something
                return;
            }
            if (size > MAX_FILE_SIZE){
                Toast.makeText(MainActivity.this, "File Size too Large", Toast.LENGTH_SHORT).show();
                return;
            }

            ImageView pic = findViewById(R.id.imageView3);
            pic.setImageURI(image);

            TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
            InputImage img = null;
            try {
                img = InputImage.fromFilePath(MainActivity.this, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Task<Text> result = recognizer.process(img).addOnSuccessListener(new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(Text visionText) {
                    // Task completed successfully
                    TextView output = findViewById(R.id.textView2);
                    output.setTextSize(15);
                    output.setText(visionText.getText());
                    output.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Task failed with an exception
                    Toast.makeText(MainActivity.this, "Could not recognize text, try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        }
        else if (requestCode == 1){
            MAX_FILE_SIZE = 50000000;
            Bitmap bitmp = BitmapFactory.decodeFile(image.getAbsolutePath());
            // check size
            long size = bitmp.getHeight() * bitmp.getWidth();
            if (size > MAX_FILE_SIZE){
                Toast.makeText(MainActivity.this, "File Size too Large", Toast.LENGTH_SHORT).show();
                return;
            }

            Matrix mat = new Matrix();
            mat.postRotate(90);
            bitmp = Bitmap.createBitmap(bitmp, 0,0, bitmp.getWidth(), bitmp.getHeight(), mat, true);

            ImageView imgView = findViewById(R.id.imageView3);
            imgView.setImageBitmap(bitmp);

            TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
            InputImage img = InputImage.fromBitmap(bitmp,0);

            Task<Text> result = recognizer.process(img).addOnSuccessListener(new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(Text visionText) {
                    // Task completed successfully
                    TextView output = findViewById(R.id.textView2);
                    output.setText(visionText.getText());
                    output.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    output.setTextSize(15);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Could not recognize text, try again.", Toast.LENGTH_SHORT).show();                }
            });
        }
        else if (requestCode == 2){
            MAX_FILE_SIZE = 50000000;
            Bitmap bitmp = BitmapFactory.decodeFile(image.getAbsolutePath());
            // check size
            long size = bitmp.getHeight() * bitmp.getWidth();
            if (size > MAX_FILE_SIZE){
                Toast.makeText(MainActivity.this, "File Size too Large", Toast.LENGTH_SHORT).show();
                return;
            }

            Matrix mat = new Matrix();
            mat.postRotate(90);
            bitmp = Bitmap.createBitmap(bitmp, 0,0, bitmp.getWidth(), bitmp.getHeight(), mat, true);

            ImageView imgView = findViewById(R.id.imageView3);
            imgView.setImageBitmap(bitmp);
            InputImage img = InputImage.fromBitmap(bitmp,0);
            BarcodeScanner scanner = BarcodeScanning.getClient();
            Task<List<Barcode>> result = scanner.process(img)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            TextView text = findViewById(R.id.textView2);
                            if (barcodes.size() > 0){
                                String rawData = "";
                                for (Barcode bar : barcodes){
                                    for (int i = 0; i < 13 ; i++){
                                        if (bar.getFormat() == (int) Formats[i].get(0)){
                                            rawData = rawData + "Type: " + Formats[i].get(1) + "\nRaw Data: " + bar.getRawValue() + "\n\n";
                                            break;
                                        }
                                    }
                                }
                                text.setText(barcodes.size() + " FOUND:\n\n" + rawData);
                            }
                            else{
                                text.setText("None Found!");
                            }
                            // task success
                            return;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //task failed prompt and then return
                            Toast.makeText(MainActivity.this, "Processing Failed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
        }
    }

    private void capture(View v, int NEWIMG){
        if ((ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(".jpg", imageFileName, storageDir);
        } catch (IOException e){
            Toast.makeText(MainActivity.this, "Could not create file, try again.", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            if (image != null){
                Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.example.OCR.fileprovider", image);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, NEWIMG);
            }
            else{
                Toast.makeText(MainActivity.this, "Could not create image, try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex){
            Toast.makeText(MainActivity.this, "Could not launch activity, try again.", Toast.LENGTH_SHORT).show();
        }
    }
}