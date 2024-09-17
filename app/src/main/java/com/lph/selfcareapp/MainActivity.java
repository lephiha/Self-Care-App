package com.lph.selfcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.Login.LoginActivity;
import com.lph.selfcareapp.menu.HomeFragment;
import com.lph.selfcareapp.menu.AccountFragment;
import com.lph.selfcareapp.menu.MedicalFragment;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.menu.SearchFragment;


public class MainActivity extends AppCompatActivity {
    Button bookingBtn;
    TextView fullnameTextView;
    BottomNavigationViewEx bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //anh xa
        fullnameTextView = findViewById(R.id.fullnameTextView);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // get db SharedPre
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String fullname = sharedPreferences.getString("fullname", "Tên người dùng");

        // show textview
        fullnameTextView.setText(fullname);

        // xet home là mặc định
        loadFragment (new HomeFragment());
        // click chọn bottom Nav
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.nav_search) {
                    selectedFragment = new SearchFragment();
                } else if (itemId == R.id.nav_file) {
                    selectedFragment = new MedicalFragment();
                } else if (itemId == R.id.nav_account) {
                    selectedFragment = new AccountFragment();
                }

                if(selectedFragment != null) {
                    loadFragment(selectedFragment);
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
