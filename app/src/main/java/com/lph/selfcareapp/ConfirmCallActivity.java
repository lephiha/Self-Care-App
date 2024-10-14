package com.lph.selfcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lph.selfcareapp.databinding.ActivityConfirmCallBinding;
import com.lph.selfcareapp.model.CallDoctor;
import com.lph.selfcareapp.model.ReturnData;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmCallActivity extends AppCompatActivity {
    TextView navText;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityConfirmCallBinding binding = ActivityConfirmCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navText = findViewById(R.id.navText);
        back = findViewById(R.id.back);
        navText.setText("Xác nhận thanh toán");
        back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        Intent intent = getIntent();
        CallDoctor callDoctor = (CallDoctor) intent.getSerializableExtra("callDoctor");
        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        String name = sp.getString("fullname","");
        int id = sp.getInt("id",0);
        String phone = sp.getString("phone","");
        binding.patientName.setText(name);
        binding.patientNumber.setText(phone);
        binding.doctorName.setText(callDoctor.getDocname());
        binding.doctorSpe.setText(callDoctor.getSname());
        String priceText = String.format("%,d", callDoctor.getPrice());
        priceText = priceText +"đ";
        binding.price.setText(priceText);
        String orderInfo = "call " + id + " "+ callDoctor.getDocid();
        binding.payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitInstance.getService().createPayment(callDoctor.getPrice(),System.currentTimeMillis()/1000,orderInfo).enqueue(new Callback<ReturnData>() {
                    @Override
                    public void onResponse(Call<ReturnData> call, Response<ReturnData> response) {
                        ReturnData returnData = response.body();
                        Intent i = new Intent(ConfirmCallActivity.this, ChooseCallDoctorActivity.class);
                        startActivity(i);
                        Intent i2 = new Intent(Intent.ACTION_VIEW);
                        i2.setData(Uri.parse(returnData.getData()));
                        startActivity(i2);
                    }

                    @Override
                    public void onFailure(Call<ReturnData> call, Throwable throwable) {

                    }
                });
            }
        });
    }
}