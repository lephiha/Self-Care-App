package com.lph.selfcareapp.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.Utils.BottomNavigationViewHelper;


public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        setupNavigationView();
    }

    private void setupNavigationView(){
        Log.d("Main", "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupTopNavigationView(tvEx);
        BottomNavigationViewHelper.enableNavigation(SearchActivity.this, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }
}