package com.lph.selfcareapp.menu.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lph.selfcareapp.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_service);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PrivacyPolicyActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        WebView webViewPolicy = findViewById(R.id.webViewPolicy);
        WebSettings webSettings = webViewPolicy.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Tải tệp HTML từ thư mục assets
        webViewPolicy.loadUrl("file:///android_asset/chinh_sach_bm.html");
    }
}