package com.lph.selfcareapp.menu.account;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.Login.LoginActivity;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.Utils.BottomNavigationViewHelper;
import com.lph.selfcareapp.menu.*;
import com.lph.selfcareapp.menu.account.CustomAdapter;

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

        // Ánh xạ các thành phần giao diện
        backButton = findViewById(R.id.backButton);
        avatar = findViewById(R.id.avatar);
        logout_btn = findViewById(R.id.logout_btn);
        usernameText = findViewById(R.id.username);
        account_listview = findViewById(R.id.account_listview);

        setupNavigationView();

        // Lấy username từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "lephiha");
        usernameText.setText(username);

        // Thiết lập sự kiện cho nút quay lại
        backButton.setOnClickListener(v -> onBackPressed());

        // Thiết lập sự kiện cho ảnh đại diện
        avatar.setOnClickListener(v -> checkPermissionAndOpenImagePicker());

        // Thiết lập sự kiện cho nút đăng xuất
        logout_btn.setOnClickListener(v -> logout());

        // Thiết lập ListView với các mục và sự kiện click
        setupListView();
    }

    private void setupListView() {
        // Các mục trong ListView
        String[] itemNames = {
                "Quy định sử dụng",
                "Chính sách bảo mật",
                "Điều khoản dịch vụ",
                "Tổng đài CSKH 19002115",
                "Đánh giá ứng dụng",
                "Chia sẻ ứng dụng",
                "Một số câu hỏi thường gặp",
                "Đăng Xuất"
        };

        // Các biểu tượng tương ứng
        int[] itemIcons = {
                R.drawable.quy_dinh_sd,
                R.drawable.chinh_sach_bm,
                R.drawable.dieukhoan,
                R.drawable.baseline_local_phone_24,
                R.drawable.danhgia,
                R.drawable.share,
                R.drawable.faq,
                R.drawable.baseline_logout_24
        };

        // Thiết lập adapter tùy chỉnh cho ListView
        CustomAdapter adapter = new CustomAdapter(this, itemNames, itemIcons);
        account_listview.setAdapter(adapter);

        // Xử lý sự kiện khi click vào một mục trong ListView
        account_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(AccountActivity.this, UsePolicyActivity.class);
                        break;
                    case 1:
                        intent = new Intent(AccountActivity.this, PrivacyPolicyActivity.class);
                        break;
                    case 2:
                        intent = new Intent(AccountActivity.this, TermServiceActivity.class);
                        break;
                    case 3:
                        intent = new Intent(AccountActivity.this, CallActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(AccountActivity.this, RateApp.class);
                        startActivity(intent);
                        break;
                    case 5:
                        // Tạo Intent để chia sẻ
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Chia sẻ ứng dụng");

                        // Nội dung chia sẻ
                        String shareMessage = "Tải ứng dụng SelfCare tại: https://www.selfcare.com";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                        // Hiển thị menu chia sẻ với các ứng dụng có thể xử lý
                        startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
                        break;

                    case 6:
                        intent = new Intent(AccountActivity.this, FAQ.class);
                        break;

                    case 7:
                        // Xóa thông tin
                        getApplication().getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().clear().apply();

                        intent = new Intent(AccountActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        finish();
                        break;

                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void checkPermissionAndOpenImagePicker() {
        // Kiểm tra quyền truy cập bộ nhớ
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            openImagePicker();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1); // Mã yêu cầu là 1
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

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.remove("username"); // Chỉ xóa dữ liệu đăng nhập
        editor.apply();

        Intent intent = new Intent(getApplication(), LoginActivity.class);
        startActivity(intent);
        finish(); // Kết thúc Activity hiện tại
    }

    private void setupNavigationView(){
        Log.d("Main", "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupTopNavigationView(tvEx);
        BottomNavigationViewHelper.enableNavigation(AccountActivity.this, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(3); // Đặt mục "Tài khoản" được chọn
        menuItem.setChecked(true);
    }
}
