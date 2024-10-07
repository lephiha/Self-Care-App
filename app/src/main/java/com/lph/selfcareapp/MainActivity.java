package com.lph.selfcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
<<<<<<< HEAD
import com.lph.selfcareapp.tuvanOnline.TuvanActivity;
=======
>>>>>>> 251833204d89412ac7f67bf0437642619ba19cf3


public class MainActivity extends AppCompatActivity {
    Button bookingBtn;
    TextView fullnameTextView;
    BottomNavigationViewEx bottomNavigationView;
<<<<<<< HEAD
    ImageView avatar, tuvanOnline;
=======
    ImageView avatar;
>>>>>>> 251833204d89412ac7f67bf0437642619ba19cf3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //anh xa
        fullnameTextView = findViewById(R.id.fullnameTextView);
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        bookingBtn = findViewById(R.id.bookingBtn);
        avatar = findViewById(R.id.avatar);
<<<<<<< HEAD
        tuvanOnline = findViewById(R.id.tuvanOnline);
=======
>>>>>>> 251833204d89412ac7f67bf0437642619ba19cf3
        setupNavigationView();
        // get db SharedPre
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String fullname = sharedPreferences.getString("fullname", "Tên người dùng");

        // show textview
        fullnameTextView.setText(fullname);
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
<<<<<<< HEAD
        // click tu van online
        tuvanOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TuvanActivity.class));
            }
        });
=======
>>>>>>> 251833204d89412ac7f67bf0437642619ba19cf3
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_ticket) {
                    // Xử lý khi nhấn vào Tìm kiếm
                    startActivity(new Intent(MainActivity.this, MedicalTicketActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_file) {
                    // Xử lý khi nhấn vào Hồ sơ (thay Fragment)
                    loadFragment(new MedicalFragment()); // Gọi loadFragment
                    return true;
                } else if (item.getItemId() == R.id.nav_chat) {
                    // Xử lý khi nhấn vào Live Chat
                    startActivity(new Intent(MainActivity.this, ChatActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_account) {
                    // Xử lý khi nhấn vào Tài khoản
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



    private void setupNavigationView(){
        Log.d("Main", "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupTopNavigationView(tvEx);
        BottomNavigationViewHelper.enableNavigation(MainActivity.this, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(0 );
        menuItem.setChecked(true);
    }

}
