package com.lph.selfcareapp.menu.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.R;

public class InfoUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_user); // Thay bằng layout info_user của bạn


        ImageView backButton = findViewById(R.id.backButton);

        // Thêm sự kiện nhấn cho backButton
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi nhấn vào nút quay lại, trở về MainActivity
                Intent intent = new Intent(InfoUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Kết thúc Activity hiện tại
            }
        });
        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "username");
        String fullname = sharedPreferences.getString("fullname", "Không có tên");
        String email = sharedPreferences.getString("email", "Không có email");
        String phone = sharedPreferences.getString("phone", "0392405600");
        String role = sharedPreferences.getString("utype", "doctor");
        Log.d("InfoUserActivity", "Username: " + username);
        Log.d("InfoUserActivity", "Fullname: " + fullname);
        Log.d("InfoUserActivity", "Email: " + email);
        Log.d("InfoUserActivity", "Phone: " + phone);


        // Tìm các TextView
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        TextView fullnameTextView = findViewById(R.id.fullNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView roleTextView = findViewById(R.id.roleTextView);

        // Cập nhật TextView với dữ liệu lấy được
        usernameTextView.setText(username);
        fullnameTextView.setText(fullname);
        emailTextView.setText(email);
        phoneTextView.setText(phone);
        roleTextView.setText(role);
    }
}
