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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
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
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode != RESULT_OK)){
            return;
        }
        if (requestCode == 0) {
            // make sure to check the exit codes later on
            Uri image = data.getData();
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
            Bitmap bitmp = BitmapFactory.decodeFile(image.getAbsolutePath());
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
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Could not recognize text, try again.", Toast.LENGTH_SHORT).show();                }
            });
        }
    }

    public void func(View v){
        int CONST = 0;
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pics"), CONST);
    }

    File image = null;
    public void cam(View v){
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
                startActivityForResult(takePictureIntent, 1);
            }
            else{
                Toast.makeText(MainActivity.this, "Could not create image, try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex){
            Toast.makeText(MainActivity.this, "Could not launch activity, try again.", Toast.LENGTH_SHORT).show();
        }
    }
}