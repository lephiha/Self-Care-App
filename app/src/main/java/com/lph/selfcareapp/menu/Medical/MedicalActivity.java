package com.lph.selfcareapp.menu.Medical;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.R;

import java.util.concurrent.Executor;

public class MedicalActivity extends AppCompatActivity {

    private TextView textViewProfileInfo;
    private Button buttonCreateProfile;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

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
        // Kiểm tra xem thiết bị có hỗ trợ sinh trắc học không
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                // Thiết bị hỗ trợ sinh trắc học
                showBiometricPrompt();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Thiết bị không hỗ trợ sinh trắc học", Toast.LENGTH_LONG).show();
                finish();
                return;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Sinh trắc học hiện không khả dụng", Toast.LENGTH_LONG).show();
                finish();
                return;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "Vui lòng thiết lập sinh trắc học trên thiết bị của bạn", Toast.LENGTH_LONG).show();
                Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                startActivity(enrollIntent);
                finish();
                return;
            default:
                Toast.makeText(this, "Không thể sử dụng sinh trắc học", Toast.LENGTH_LONG).show();
                finish();
                return;
        }
    }
    private void showBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(MedicalActivity.this, "Lỗi xác thực: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MedicalActivity.this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MedicalActivity.this, "Xác thực không thành công, thử lại!", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Xác thực sinh trắc học")
                .setSubtitle("Sử dụng vân tay hoặc khuôn mặt để xác thực")
                .setNegativeButtonText("Hủy")
                .build();

        biometricPrompt.authenticate(promptInfo);
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
