package com.example.ocr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void func(View v){
        // Create Tesseract instance
        TessBaseAPI tess = new TessBaseAPI();
        // Given path must contain subdirectory `tessdata` where are `*.traineddata` language files
        String dataPath = new File(Environment.getExternalStorageDirectory(), "tesseract").getAbsolutePath();

        // Initialize API for specified language (can be called multiple times during Tesseract lifetime)
//        if (!tess.init(dataPath, "eng")) {
//            // Error initializing Tesseract (wrong data path or language)
//            tess.recycle();
//            return;
//        }
        // Specify image and then recognize it and get result (can be called multiple times during Tesseract lifetime)
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + "/sdcard/test.jpg"), "image/*");
        startActivity(intent);

//        tess.setImage(image);
//        String text = tess.getUTF8Text();
//
//
//        // Release Tesseract when you don't want to use it anymore
//        tess.recycle();
        Button but = (Button) v;
        but.setText("hello");

    }
}