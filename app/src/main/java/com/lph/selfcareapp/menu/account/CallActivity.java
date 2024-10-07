package com.lph.selfcareapp.menu.account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CallActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final String HOTLINE_NUMBER = "19002115";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra quyền CALL_PHONE
        if (ContextCompat.checkSelfPermission(CallActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền CALL_PHONE nếu chưa được cấp
            ActivityCompat.requestPermissions(CallActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            // Nếu đã có quyền, thực hiện cuộc gọi
            makePhoneCall();
        }
    }

    // Phương thức thực hiện cuộc gọi
    private void makePhoneCall() {
        String dial = "tel:" + HOTLINE_NUMBER;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(dial));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(CallActivity.this, "Không có quyền thực hiện cuộc gọi", Toast.LENGTH_SHORT).show();
            return;
        }


        startActivity(intent);
    }

    // Xử lý khi người dùng phản hồi yêu cầu cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Nếu người dùng cấp quyền, thực hiện cuộc gọi
                makePhoneCall();
            } else {
                // Nếu từ chối quyền, hiện thông báo lỗi
                Toast.makeText(this, "Quyền gọi điện bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
