package com.lph.selfcareapp.lockscreen;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;

public class LockScreenHelper {

    private Context context;

    public LockScreenHelper(Context context) {
        this.context = context;
    }

    // Kiểm tra xem thiết bị có màn hình khóa hay không
    public boolean isDeviceSecure() {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        // Kiểm tra nếu màn hình khóa đã được thiết lập
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return keyguardManager.isDeviceSecure();
        } else {
            return keyguardManager.isKeyguardSecure();
        }
    }

    // Hiển thị cảnh báo nếu thiết bị chưa có màn hình khóa
    public void showLockScreenAlert() {
        if (!isDeviceSecure()) {
            new AlertDialog.Builder(context)
                    .setTitle("Bảo mật thiết bị")
                    .setMessage("Để bảo vệ thông tin, vui lòng thiết lập màn hình khóa.")
                    .setPositiveButton("Cài đặt", (dialog, which) -> {
                        // Chuyển hướng người dùng đến cài đặt bảo mật
                        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                        context.startActivity(intent);
                    })
                    .setNegativeButton("Thoát", (dialog, which) -> {
                        // Đóng ứng dụng nếu không thiết lập màn hình khóa
                        System.exit(0);
                    })
                    .setCancelable(false)
                    .show();
        }
    }
}

