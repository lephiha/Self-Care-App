package com.lph.selfcareapp.lockscreen;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;

public class LockScreenHelper {

    private Context context;
    private static final int LOCK_REQUEST_CODE = 100;

    public LockScreenHelper(Context context) {
        this.context = context;
    }

    // Kiểm tra xem thiết bị có mã PIN được thiết lập hay không
    public boolean isPinLockEnabled() {
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int passwordQuality = dpm.getPasswordQuality(null);

            // Kiểm tra nếu phương pháp bảo mật là PIN
            return passwordQuality == DevicePolicyManager.PASSWORD_QUALITY_NUMERIC;
        }
        return false;
    }

    // Kiểm tra nếu thiết bị có bất kỳ phương pháp bảo mật nào
    public boolean isDeviceSecure() {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return keyguardManager.isDeviceSecure();
        } else {
            return keyguardManager.isKeyguardSecure();
        }
    }

    // Hiển thị cảnh báo yêu cầu thiết lập mã PIN
    public void promptSetPinLock(Activity activity) {
        if (!isDeviceSecure() || !isPinLockEnabled()) {
            new AlertDialog.Builder(context)
                    .setTitle("Yêu cầu bảo mật")
                    .setMessage("Để sử dụng ứng dụng, vui lòng thiết lập mã PIN làm phương pháp bảo mật.")
                    .setPositiveButton("Cài đặt", (dialog, which) -> {
                        // Chuyển đến cài đặt bảo mật
                        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                        activity.startActivity(intent);
                    })
                    .setNegativeButton("Thoát", (dialog, which) -> {
                        activity.finish();
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    // Yêu cầu xác thực màn hình khóa
    public void authenticateUser(Activity activity) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = keyguardManager.createConfirmDeviceCredentialIntent(
                    "Xác thực",
                    "Vui lòng nhập mã PIN để tiếp tục"
            );

            if (intent != null) {
                activity.startActivityForResult(intent, LOCK_REQUEST_CODE);
            }
        }
    }

    // Xử lý kết quả xác thực
    public void handleAuthenticationResult(int requestCode, int resultCode) {
        if (requestCode == LOCK_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                System.out.println("Xác thực thành công!");
            } else {
                System.out.println("Xác thực thất bại hoặc bị hủy.");
                ((Activity) context).finish();
            }
        }
    }
}

