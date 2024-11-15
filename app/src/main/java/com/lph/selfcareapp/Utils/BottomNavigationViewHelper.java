package com.lph.selfcareapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.MainDoctorActivity;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.menu.Chat.ChatActivity;
import com.lph.selfcareapp.menu.account.AccountActivity;
import com.lph.selfcareapp.menu.Medical.MedicalActivity;
import com.lph.selfcareapp.menu.MedicalTicketActivity;
import com.lph.selfcareapp.menu.account.AccountDoctorActivity;

public class BottomNavigationViewHelper {
    private static final String TAG = "TopNavigationViewHelper";

    public static void setupTopNavigationView(BottomNavigationViewEx tv) {
        Log.d(TAG, "setupTopNavigationView: setting up navigationview");


    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    Intent intent2 = new Intent(context, MainActivity.class);
                    context.startActivity(intent2);
                } else if (itemId == R.id.nav_file) {
                    Intent intent1 = new Intent(context, MedicalActivity.class);
                    context.startActivity(intent1);
                } else if (itemId == R.id.nav_ticket) {
                    Intent intent3 = new Intent(context, MedicalTicketActivity.class);
                    context.startActivity(intent3);
                } else if (itemId == R.id.nav_chat) {
                    Intent intent4 = new Intent(context, ChatActivity.class);
                    context.startActivity(intent4);
                }
                else if (itemId == R.id.nav_account) {
                    Intent intent5 = new Intent(context, AccountActivity.class);
                    context.startActivity(intent5);
                }

                return false;
            }
        });
    }
    public static void enableNavigation2(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    Intent intent2 = new Intent(context, MainDoctorActivity.class);
                    context.startActivity(intent2);}
                else if (itemId == R.id.nav_account) {
                    Intent intent5 = new Intent(context, AccountDoctorActivity.class);
                    context.startActivity(intent5);
                }

                return false;
            }
        });
    }
}
