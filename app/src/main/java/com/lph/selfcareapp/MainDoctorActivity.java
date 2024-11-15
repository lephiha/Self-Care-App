package com.lph.selfcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.Utils.BottomNavigationViewHelper;
import com.lph.selfcareapp.menu.DiagnoseActivity;
import com.lph.selfcareapp.menu.MedicalDoctorTicketActivity;
import com.lph.selfcareapp.menu.MedicalTicketActivity;
import com.lph.selfcareapp.menu.account.InfoUserActivity;
import com.lph.selfcareapp.stringee.activity.CallActivity;
import com.lph.selfcareapp.stringee.common.Constant;
import com.lph.selfcareapp.stringee.common.PermissionsUtils;
import com.lph.selfcareapp.stringee.common.Utils;
import com.lph.selfcareapp.stringee.manager.ClientManager;

public class MainDoctorActivity extends AppCompatActivity {
    Button calendarBtn;
    Button diagnosisBtn;
    TextView fullnameTextView;
    BottomNavigationViewEx bottomNavigationView;
    ImageView avatar;
    ClientManager clientManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);
        avatar = findViewById(R.id.avatar);
        fullnameTextView = findViewById(R.id.fullnameTextView);
        //anh xa
        calendarBtn = findViewById(R.id.calendarBtn);
        diagnosisBtn = findViewById(R.id.diagnoseBtn);
        setupNavigationView();
        // get db SharedPre
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String fullname = sharedPreferences.getString("fullname", "Tên người dùng");

        // show textview
        fullnameTextView.setText(fullname);

        // Xử lý sự kiện khi nhấn vào avatar (chuyển đến trang tài khoản)
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainDoctorActivity.this, InfoUserActivity.class));
            }
        });
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainDoctorActivity.this, MedicalDoctorTicketActivity.class));
            }
        });
        diagnosisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainDoctorActivity.this, DiagnoseActivity.class));
            }
        });

        setupVideoCall();

    };


    private void setupVideoCall(){
        clientManager = ClientManager.getInstance(this);

        initAndConnectStringee();

        requestPermission();
//        makeCall(true,true,callId);
    }

    private void requestPermission() {
        if (!PermissionsUtils.getInstance().checkSelfPermission(this)) {
            PermissionsUtils.getInstance().requestPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGranted = PermissionsUtils.getInstance().verifyPermissions(grantResults);
        if (requestCode == PermissionsUtils.REQUEST_PERMISSION) {
            clientManager.isPermissionGranted = isGranted;
            if (!isGranted) {
                if (PermissionsUtils.getInstance().shouldRequestPermissionRationale(this)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Permissions must be granted for the call");
                    builder.setPositiveButton("Ok", (dialogInterface, id) -> dialogInterface.cancel());
                    builder.setNegativeButton("Settings", (dialogInterface, id) -> {
                        dialogInterface.cancel();
                        // open app setting
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    });
                    builder.create().show();
                }
            }
        }
    }

    public void initAndConnectStringee() {
//        clientManager.addOnConnectionListener(status -> runOnUiThread(() -> binding.tvStatus.setText(status)));
        clientManager.connect();
    }

    private void makeCall(boolean isStringeeCall, boolean isVideoCall, String callId) {
        if (Utils.isStringEmpty(callId) || !clientManager.getStringeeClient().isConnected()) {
            return;
        }
        if (!clientManager.isPermissionGranted) {
            PermissionsUtils.getInstance().requestPermissions(this);
            return;
        }
        Intent intent = new Intent(this, CallActivity.class);
        intent.putExtra(Constant.PARAM_TO, callId);
        intent.putExtra(Constant.PARAM_IS_VIDEO_CALL, isVideoCall);
        intent.putExtra(Constant.PARAM_IS_INCOMING_CALL, false);
        intent.putExtra(Constant.PARAM_IS_STRINGEE_CALL, isStringeeCall);
        startActivity(intent);
    }





    private void setupNavigationView(){
        Log.d("Main", "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.doctorBottomNavBar);
        BottomNavigationViewHelper.setupTopNavigationView(tvEx);
        BottomNavigationViewHelper.enableNavigation2(MainDoctorActivity.this, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }
}
