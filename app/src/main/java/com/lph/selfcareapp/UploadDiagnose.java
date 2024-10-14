package com.lph.selfcareapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lph.selfcareapp.serviceAPI.RetrofitInstance;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDiagnose extends AppCompatActivity {
    Button sendBtn;
    Button pickImage;
    ImageView imageView;
    private int SELECT_FILE =1;
    String imageUrl;
    int appoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.upload_diagnose);
        sendBtn = findViewById(R.id.pickImageBtn);
        pickImage = findViewById(R.id.sendBtn);
        imageView = findViewById(R.id.diagImage);

        Intent intent = getIntent();
        appoid = intent.getIntExtra("appoid",0);
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                String img= Base64.encodeToString(imageBytes, Base64.DEFAULT);
                sendBtn.setOnClickListener(v -> RetrofitInstance.getImage().getImageUrl(getString(R.string.api_key),img,"txt").enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        imageUrl = response.body();
                        Log.d("Retrofit",imageUrl);
                        uploadImage();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        Log.d("Retrofit",throwable.toString());
                    }
                }));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImage(){
        RetrofitInstance.getImage().uploadImage(imageUrl,appoid).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    Toast.makeText(getApplicationContext(),"Gửi thành công",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }
}