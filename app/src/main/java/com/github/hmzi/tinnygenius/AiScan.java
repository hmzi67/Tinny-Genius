package com.github.hmzi.tinnygenius;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.hmzi.tinnygenius.Classes.ProgressStatus;
import com.github.hmzi.tinnygenius.Classes.SpeakClass;
import com.github.hmzi.tinnygenius.databinding.ActivityAiScanBinding;
import com.github.hmzi.tinnygenius.databinding.ActivityAnimalBinding;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AiScan extends AppCompatActivity {

    private ActivityAiScanBinding binding;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    ProgressStatus progressStatus;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAiScanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressStatus = new ProgressStatus(this);
        init();
    }

    private void init() {
        // goBack
        binding.back.setOnClickListener(view -> onBackPressed());

        // on image select
        binding.imgSelectBtn.setOnClickListener(view -> SelectImage());
        binding.nextBtn.setOnClickListener(view -> {
            if (filePath != null) {
                try {
                    progressStatus.show();
                    progressStatus.setTitle("Please wait ...");
                    progressStatus.setCanceledOnTouchOutside(false);
                    sendGeminaiPhoto();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        binding.cameraBtn.setOnClickListener(view -> {
            // Check for camera permission
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission not granted, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                // Permission already granted, proceed with camera operations
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            // Image captured successfully, you can retrieve the image from the Intent data
//            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
//            // Now you can do something with the image
//        }
//    }

    private void sendGeminaiPhoto() throws IOException {
        String apiKey = getResources().getString(R.string.api_key);
        GenerativeModel gm = new GenerativeModel("gemini-pro-vision", apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);
        Content content = new Content.Builder()
                .addText("What's in this picture?")
                .addImage(MediaStore.Images.Media.getBitmap(getContentResolver(), filePath))
                .build();

        Executor executor =  Executors.newFixedThreadPool(10);

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                runOnUiThread(() -> progressStatus.dismiss());
                String resultText = result.getText();
                runOnUiThread(() -> binding.generatedText.setVisibility(View.VISIBLE));
                runOnUiThread(() -> binding.generatedText.setText(resultText));
//                binding.generatedText.setVisibility(View.VISIBLE);
//                binding.generatedText.setText(resultText);
                runOnUiThread(() -> Toast.makeText(AiScan.this, "Generated Successfully", Toast.LENGTH_SHORT).show());
                Log.d("RES", "onSuccess: " + resultText);
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> Toast.makeText(AiScan.this, "Something went wrong", Toast.LENGTH_SHORT).show());
                Log.d("RES", "onFail: " + t.getMessage());
            }
        }, executor);
    }
    // Select Image method
    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                setImage(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            filePath = data.getData();
            // Image captured successfully, you can retrieve the image from the Intent data
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            setImage(imageBitmap);
        }
    }

    private void setImage(Bitmap bitmap) {
        binding.userSelectedImage.setImageBitmap(bitmap);
    }
}