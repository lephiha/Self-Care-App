package com.lph.selfcareapp.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.lph.selfcareapp.Login.LoginActivity;
import com.lph.selfcareapp.R;

public class AccountFragment extends Fragment {
    private Button logout_btn;
    private ImageView backButton;
    private ShapeableImageView avatar;
    private TextView usernameText;
    private ListView account_listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Ánh xạ các phần tử giao diện
        backButton = view.findViewById(R.id.backButton);
        avatar = view.findViewById(R.id.avatar);
        logout_btn = view.findViewById(R.id.logout_btn);
        usernameText = view.findViewById(R.id.username);
        account_listview = view.findViewById(R.id.account_listview);

        // Thiết lập tên người dùng
        String username = "laphihe";
        usernameText.setText(username);

        // Thiết lập sự kiện cho nút quay lại
        backButton.setOnClickListener(v -> onBackPress());

        // Thiết lập sự kiện cho ảnh đại diện
        avatar.setOnClickListener(v -> openImagePicker());

        // Thiết lập sự kiện cho nút đăng xuất
        logout_btn.setOnClickListener(v -> logout());

        return view;
    }

    private void onBackPress() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1); // Mã yêu cầu là 1
    }

    private void logout() {
        getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE).edit().clear().apply();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish(); // Kết thúc Activity hiện tại
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            // Xử lý ảnh được chọn
            Uri selectedImageUri = data.getData();
            if (avatar != null) {
                avatar.setImageURI(selectedImageUri);
            }
        }
    }
}
