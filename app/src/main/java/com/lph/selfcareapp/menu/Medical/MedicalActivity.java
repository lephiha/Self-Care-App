package com.lph.selfcareapp.menu.Medical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lph.selfcareapp.R;

public class MedicalActivity extends AppCompatActivity {

    private TextView textViewProfileInfo;
    private Button buttonCreateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicalfrag); // Đảm bảo rằng layout này đã tồn tại

        // Ánh xạ các thành phần giao diện
        textViewProfileInfo = findViewById(R.id.textViewProfileInfo);
        buttonCreateProfile = findViewById(R.id.buttonCreateProfile);

        // Thiết lập sự kiện click cho nút tạo hồ sơ
        buttonCreateProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MedicalActivity.this, CreateProfileActivity.class);
            startActivity(intent);
        });

        // Hiển thị thông tin hồ sơ
        displayProfileInfo();
    }

    // Phương thức để hiển thị thông tin hồ sơ bệnh nhân
    private void displayProfileInfo() {
        Intent intent = getIntent();

        // Nhận dữ liệu từ Intent
        String name = intent.getStringExtra("name");
        String birthDate = intent.getStringExtra("birthDate");
        String gender = intent.getStringExtra("gender");
        String id = intent.getStringExtra("id");
        String insurance = intent.getStringExtra("insurance");
        String occupation = intent.getStringExtra("occupation");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        String address = intent.getStringExtra("address");

        // Tạo chuỗi thông tin để hiển thị
        String profileInfo = "Họ và tên: " + (name != null ? name : "Chưa có thông tin") + "\n" +
                "Ngày sinh: " + (birthDate != null ? birthDate : "Chưa có thông tin") + "\n" +
                "Giới tính: " + (gender != null ? gender : "Chưa có thông tin") + "\n" +
                "Số ID: " + (id != null ? id : "Chưa có thông tin") + "\n" +
                "Số thẻ bảo hiểm: " + (insurance != null ? insurance : "Chưa có thông tin") + "\n" +
                "Nghề nghiệp: " + (occupation != null ? occupation : "Chưa có thông tin") + "\n" +
                "Số điện thoại: " + (phone != null ? phone : "Chưa có thông tin") + "\n" +
                "Email: " + (email != null ? email : "Chưa có thông tin") + "\n" +
                "Địa chỉ: " + (address != null ? address : "Chưa có thông tin");

        // Hiển thị thông tin hồ sơ lên TextView
        textViewProfileInfo.setText(profileInfo);
    }
}
