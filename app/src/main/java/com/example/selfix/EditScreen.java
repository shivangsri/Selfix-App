package com.example.selfix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.selfix.databinding.ActivityEditScreenBinding;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EditScreen extends AppCompatActivity {
    ActivityEditScreenBinding binding;
    static float r = 90;
    Uri resultUri;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Displaying image on edit screen from gallery or camera//
        binding.result.setImageURI(getIntent().getData());
        final Uri uri = getIntent().getData();




        //Performing crop function on image
        binding.crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCropActivity();

            }
        });


        //Performing rotate//
        binding.rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.result.setRotation((r));
                r = r + 90;

            }

        });

        //Undo button function
        binding.undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.result.setImageURI(uri);
            }
        });


        //Saving cropped image to gallery
        binding.saveb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1) {
                    Intent intent = new Intent(EditScreen.this, MainActivity.class);
                    intent.setData(resultUri);
                    startActivity(intent);


                }
                if (i == 0) {
                    Intent intent = new Intent(EditScreen.this, MainActivity.class);
                    intent.setData(uri);
                    startActivity(intent);


                }
            }
        });

        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditScreen.this, MainActivity.class);

                startActivity(intent);

            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditScreen.this, MainActivity.class);

                startActivity(intent);


            }
        });


    }

    private void startCropActivity() {
        CropImage.activity(getIntent().getData())
                .start(this);


    }

    private void saveToGallery() {
        Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_SHORT).show();
        BitmapDrawable drawable = (BitmapDrawable) binding.result.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        FileOutputStream outputStream = null;
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()
                + "/WhatSappIMG/");
        dir.mkdirs();
        String filename = String.format("%d.png", System.currentTimeMillis());
        File file = new File(dir, filename);

        try {

            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                i = 1;
                resultUri = result.getUri();
                binding.result.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
    }

}