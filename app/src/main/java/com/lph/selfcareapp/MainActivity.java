package com.lph.selfcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.Utils.BottomNavigationViewHelper;
import com.lph.selfcareapp.menu.Chat.ChatActivity;
import com.lph.selfcareapp.menu.MedicalFragment;
import com.lph.selfcareapp.menu.MedicalTicketActivity;
import com.lph.selfcareapp.menu.account.AccountActivity;
import com.lph.selfcareapp.menu.account.InfoUserActivity;
import com.lph.selfcareapp.tuvanOnline.TuvanActivity;

public class MainActivity extends AppCompatActivity {
    Button bookingBtn;
    TextView fullnameTextView;
    BottomNavigationViewEx bottomNavigationView;
    ImageView avatar, tuvanOnline;
    ViewFlipper viewFlipper;
    Animation in, out;

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

        //viewflipper chuyển ảnh
        in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);


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
                    loadFragment(new MedicalFragment());
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

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // Cho phép quay lại fragment trước đó
        transaction.commit();
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
