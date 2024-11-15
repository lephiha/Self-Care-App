package com.lph.selfcareapp;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.Utils.BottomNavigationViewHelper;
import com.lph.selfcareapp.menu.Chat.ChatActivity;
import com.lph.selfcareapp.menu.MedicalTicketActivity;
import com.lph.selfcareapp.menu.account.AccountActivity;
import com.lph.selfcareapp.menu.account.InfoUserActivity;
import com.lph.selfcareapp.stringee.activity.StringeeActivity;
import com.lph.selfcareapp.tuvanOnline.TuvanActivity;

public class MainActivity extends AppCompatActivity {
    Button bookingBtn;
    TextView fullnameTextView;
    BottomNavigationViewEx bottomNavigationView;
    ImageView avatar, tuvanOnline;
    ViewFlipper viewFlipper;
    Animation in, out;
    Button examBtn, viewResults, reschedule;
    private FusedLocationProviderClient fusedLocationClient;
    public static final int REQUEST_LOCATION_PERMISSION = 100;
    public static final int REQUEST_BACKGROUND_LOCATION_PERMISSION = 123;
    public static final int REQUEST_CHECK_SETTINGS = 999;
    LocationSettingsRequest.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Ánh xạ
        fullnameTextView = findViewById(R.id.fullnameTextView);
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        bookingBtn = findViewById(R.id.bookingBtn);
        avatar = findViewById(R.id.avatar);
        tuvanOnline = findViewById(R.id.tuvanOnline);
        viewFlipper = findViewById(R.id.viewFlipper);
        examBtn = findViewById(R.id.examBtn);
        viewResults = findViewById(R.id.viewResultsBtn);
        reschedule = findViewById(R.id.reschedule);
        //viewflipper chuyển ảnh
        in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        checkLocationSettings();
        askNotificationPermission();



        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String fullname = sharedPreferences.getString("fullname", "Tên người dùng");
        requirePermission();

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

        viewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SeeResultActivity.class);
                startActivity(i);
            }
        });


        examBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseCallDoctorActivity.class);
                startActivity(intent);
            }
        });

        reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RescheduleActivity.class);
                startActivity(i);
            }
        });



    }
    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });


    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
    private void checkLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000); // 10 seconds
        locationRequest.setFastestInterval(5000); // 5 seconds
        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); // Show the dialog even if the settings are already satisfied

        setupNavigationView();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. You can initialize location requests here.
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                // User agreed to make required location settings changes.
                // You can initialize location requests here.
            } else {
                checkLocationSettings();
                // User chose not to make required location settings changes.
                Toast.makeText(this, "Location services are required for this app.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void requirePermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);

        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("latitude", String.valueOf(location.getLatitude()));
                        editor.putString("longitude", String.valueOf(location.getLongitude()));
                        editor.apply();
                    }
                }
            });
            // already permission granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(requestCode ==  REQUEST_LOCATION_PERMISSION) {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

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
