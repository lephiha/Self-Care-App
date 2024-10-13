package com.lph.selfcareapp;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.Utils.BottomNavigationViewHelper;
import com.lph.selfcareapp.menu.Chat.ChatActivity;
import com.lph.selfcareapp.menu.Medical.MedicalActivity;
import com.lph.selfcareapp.menu.MedicalTicketActivity;
import com.lph.selfcareapp.menu.account.AccountActivity;
import com.lph.selfcareapp.menu.account.InfoUserActivity;
import com.lph.selfcareapp.tuvanOnline.TuvanActivity;

public class MainActivity extends AppCompatActivity {
    Button bookingBtn, callHotline;
    TextView fullnameTextView;
    BottomNavigationViewEx bottomNavigationView;
    ImageView avatar, tuvanOnline;
    ViewFlipper viewFlipper;
    Animation in, out;
    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final String HOTLINE_NUMBER = "19001234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        fullnameTextView = findViewById(R.id.fullnameTextView);
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        bookingBtn = findViewById(R.id.bookingBtn);
        avatar = findViewById(R.id.avatar);
        tuvanOnline = findViewById(R.id.tuvanOnline);
        viewFlipper = findViewById(R.id.viewFlipper);
        callHotline = findViewById(R.id.callHotline);

        //viewflipper chuyển ảnh
        in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        // Thiết lập sự kiện cho nút gọi
        callHotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra quyền CALL_PHONE
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Yêu cầu quyền nếu chưa được cấp
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
                } else {
                    // Nếu đã có quyền, thực hiện cuộc gọi
                    makePhoneCall();
                }
            }
        });
        setupNavigationView();

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String fullname = sharedPreferences.getString("fullname", "Tên người dùng");

        // Hiển thị tên người dùng
        fullnameTextView.setText(fullname);

        // Xử lý sự kiện nút "Đặt lịch"
        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), ChooseHospitalActivity.class));
            }
        });

        // Xử lý sự kiện khi nhấn vào avatar (chuyển đến trang tài khoản)
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InfoUserActivity.class));
            }
        });

        // Xử lý sự kiện khi nhấn vào tư vấn online
        tuvanOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TuvanActivity.class));
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_ticket) {
                    startActivity(new Intent(MainActivity.this, MedicalTicketActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_file) {
                    startActivity(new Intent(MainActivity.this, MedicalActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_chat) {
                    startActivity(new Intent(MainActivity.this, ChatActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_account) {
                    startActivity(new Intent(MainActivity.this, AccountActivity.class));
                    return true;
                }
                return false;
            }
        });
    }


    // Phương thức thực hiện cuộc gọi
    private void makePhoneCall() {
        String dial = "tel:" + HOTLINE_NUMBER;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(dial));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Không có quyền thực hiện cuộc gọi", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(intent);
    }

    // Xử lý phản hồi quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Quyền gọi điện bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupNavigationView() {
        Log.d("Main", "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupTopNavigationView(tvEx);
        BottomNavigationViewHelper.enableNavigation(MainActivity.this, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }
}
