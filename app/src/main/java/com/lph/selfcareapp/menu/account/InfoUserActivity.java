package com.lph.selfcareapp.menu.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.Utils.AESUtils;

public class InfoUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_user);

        ImageView backButton = findViewById(R.id.backButton);

        // Thêm sự kiện nhấn cho backButton
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi nhấn vào nút quay lại, trở về MainActivity
                Intent intent = new Intent(InfoUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String encryptionKey = sharedPreferences.getString("encryptionKey", null);

        // Kiểm tra và giải mã các trường thông tin
        String fullname = sharedPreferences.getString("fullname", null);
        String email = sharedPreferences.getString("email", null);
        String phone = sharedPreferences.getString("phone", null);
        String dob = sharedPreferences.getString("dob", null);
        String nic = sharedPreferences.getString("nic", null);
        String role = sharedPreferences.getString("utype", null);

        if (encryptionKey != null) {
            try {
                // Giải mã các giá trị, kiểm tra nếu giá trị null
                if (fullname != null) fullname = AESUtils.decrypt(fullname, encryptionKey);
                if (email != null) email = AESUtils.decrypt(email, encryptionKey);
                if (phone != null) phone = AESUtils.decrypt(phone, encryptionKey);
                if (dob != null) dob = AESUtils.decrypt(dob, encryptionKey);
                if (nic != null) nic = AESUtils.decrypt(nic, encryptionKey);
                if (role != null) role = AESUtils.decrypt(role, encryptionKey);
            } catch (Exception e) {
                Log.e("InfoUserActivity", "Giải mã dữ liệu thất bại", e);
                Toast.makeText(InfoUserActivity.this, "Giải mã dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        }

        // Ghi log thông tin sau khi giải mã
        Log.d("InfoUserActivity", "Fullname: " + fullname);
        Log.d("InfoUserActivity", "Email: " + email);
        Log.d("InfoUserActivity", "Phone: " + phone);
        Log.d("InfoUserActivity", "DOB: " + dob);
        Log.d("InfoUserActivity", "NIC: " + nic);
        Log.d("InfoUserActivity", "Role: " + role);

        // Tìm các TextView và cập nhật thông tin
        TextView fullnameTextView = findViewById(R.id.fullnameTextView);
        TextView fullnameDetailTextView = findViewById(R.id.fullNameDetailTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView dobTextView = findViewById(R.id.dobTextView);
        TextView nicTextView = findViewById(R.id.nicTextView);
        TextView roleTextView = findViewById(R.id.roleTextView);

        // Kiểm tra nếu dữ liệu hợp lệ mới set vào TextView
        if (fullname != null) fullnameTextView.setText(fullname);
        if (email != null) emailTextView.setText(email);
        if (phone != null) phoneTextView.setText(phone);
        if (dob != null) dobTextView.setText(dob);
        if (nic != null) nicTextView.setText(nic);
        if (role != null) roleTextView.setText(role);
    }
}
