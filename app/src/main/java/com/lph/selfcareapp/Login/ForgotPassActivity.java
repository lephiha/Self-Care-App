package com.lph.selfcareapp.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.lph.selfcareapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassActivity extends AppCompatActivity {

    TextInputEditText emailEditText;
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        emailEditText = findViewById(R.id.emailEditText);

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();

                // Kiểm tra email không được để trống và hợp lệ
                if (email.isEmpty()) {
                    Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else {
                    sendForgotPasswordRequest(email);
                }
            }
        });

        findViewById(R.id.signUpRedirect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendForgotPasswordRequest(final String email) {
        String url = "http://192.168.56.1/book-doctor/forgot_password.php"; // Thay bằng URL của API

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Xử lý phản hồi từ API
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");
                            String message = jsonResponse.getString("message");

                            if (success.equals("1")) {
                                Toast.makeText(context, "OTP sent to your email", Toast.LENGTH_SHORT).show();

                                // Chuyển đến màn hình xác nhận mã OTP
                                Intent intent = new Intent(ForgotPassActivity.this, ResetPasswordActivity.class);
                                intent.putExtra("email", email); // Gửi email sang màn hình tiếp theo
                                startActivity(intent);
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email); // Gửi email qua API
                return params;
            }
        };

        // Thêm yêu cầu vào hàng đợi
        requestQueue.add(stringRequest);
    }
}
