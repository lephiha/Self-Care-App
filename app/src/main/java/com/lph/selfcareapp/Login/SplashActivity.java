package com.lph.selfcareapp.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.MainDoctorActivity;
import com.lph.selfcareapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);

        Boolean isLogedin =sp1.getBoolean("isLogedin", false);
        if(isLogedin){
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            if(sharedPreferences.getString("utype","").equals("doctor"))
                intent = new Intent(SplashActivity.this, MainDoctorActivity.class);
            startActivity(intent);
        }
        // anh xa btn
        Button btnLogin = findViewById(R.id.loginBtn);
        Button btnSignUP = findViewById(R.id.signUpBtn);

        // click login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        // click sign up
        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
