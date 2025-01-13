package com.lph.selfcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.model.Appointment2;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;
import com.lph.selfcareapp.view.ClinicAdapter;
import com.lph.selfcareapp.view.ResultAdapter;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeResultActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Appointment2> appointment2List;
    ResultAdapter resultAdapter;
    TextView navText;
    ImageButton back;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private static final String KEY = "your-secret-key"; // Khóa bí mật (phải trùng với API)
    private static final String IV = "your-secret-iv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_see_result);
        recyclerView = findViewById(R.id.seeResView);
        navText = findViewById(R.id.navText);
        navText.setText("Kết quả khám bệnh ");
        back = findViewById(R.id.back);
        back.setOnClickListener(v->getOnBackPressedDispatcher().onBackPressed());
        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        int pid = sp.getInt("id",0);
        RetrofitInstance.getService().getResult(pid).enqueue(new Callback<List<Appointment2>>() {
            @Override
            public void onResponse(Call<List<Appointment2>> call, Response<List<Appointment2>> response) {
                appointment2List = response.body();
                resultAdapter = new ResultAdapter(SeeResultActivity.this, appointment2List);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(resultAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(SeeResultActivity.this));
                resultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Appointment2>> call, Throwable throwable) {

            }
        });
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
                Toast.makeText(SeeResultActivity.this, "Lỗi xác thực: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(SeeResultActivity.this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(SeeResultActivity.this, "Xác thực không thành công, thử lại!", Toast.LENGTH_SHORT).show();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Xác thực sinh trắc học")
                .setSubtitle("Sử dụng vân tay hoặc khuôn mặt để xác thực")
                .setNegativeButtonText("Hủy")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}