package com.lph.selfcareapp.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.Login.LoginActivity;
import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.Utils.BottomNavigationViewHelper;

public class AccountActivity extends AppCompatActivity {
    private Button logout_btn;
    private ImageView backButton;
    private ShapeableImageView avatar;
    private TextView usernameText;
    private ListView account_listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account);
        // Ánh xạ các phần tử giao diện
        backButton = findViewById(R.id.backButton);
        avatar = findViewById(R.id.avatar);
        logout_btn = findViewById(R.id.logout_btn);
        usernameText = findViewById(R.id.username);
        account_listview = findViewById(R.id.account_listview);
        setupNavigationView();
        // Thiết lập tên người dùng
        String username = "laphihe";
        usernameText.setText(username);

        // Thiết lập sự kiện cho nút quay lại
        backButton.setOnClickListener(v -> onBackPress());

        // Thiết lập sự kiện cho ảnh đại diện
        avatar.setOnClickListener(v -> openImagePicker());

        // Thiết lập sự kiện cho nút đăng xuất
        logout_btn.setOnClickListener(v -> logout());
    }



    private void onBackPress() {
        getOnBackPressedDispatcher().onBackPressed();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1); // Mã yêu cầu là 1
    }

    private void logout() {
        getApplication().getSharedPreferences("MyPrefs", getApplication().MODE_PRIVATE).edit().clear().apply();

        Intent intent = new Intent(getApplication(), LoginActivity.class);
        startActivity(intent);
        finish();// Kết thúc Activity hiện tại
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Xử lý ảnh được chọn
            Uri selectedImageUri = data.getData();
            if (avatar != null) {
                avatar.setImageURI(selectedImageUri);
            }
        }
    }

    private void setupNavigationView(){
        Log.d("Main", "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupTopNavigationView(tvEx);
        BottomNavigationViewHelper.enableNavigation(AccountActivity.this, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
    }
}
