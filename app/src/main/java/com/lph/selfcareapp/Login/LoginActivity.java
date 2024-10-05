package com.lph.selfcareapp.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView signUpRedirect, tv_error, forgotPassword;
    TextInputEditText usernameEditText, passwordEditText;
    Button loginBtn;
    Spinner roleSpiner;

    String url_login = "http://192.168.0.107/selfcare/login.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các view
        Anhxa();

        // click sign up
        signUpRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // click login
        loginBtn.setOnClickListener(view -> GoLogin());

        // click forgot pass
        forgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("WrongViewCast")
    private void Anhxa() {
        signUpRedirect = findViewById(R.id.signUpRedirect);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        tv_error = findViewById(R.id.tv_error);
        forgotPassword = findViewById(R.id.forgotPassword);
        roleSpiner = findViewById(R.id.roleSpinner);
    }

    private void GoLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String selectedRole = roleSpiner.getSelectedItem().toString().toLowerCase(); // Chuyển về chữ thường

        if (username.isEmpty()) {
            tv_error.setText("Please Enter Your Username");
        } else if (password.isEmpty()) {
            tv_error.setText("Please Enter Your Password");
        } else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                    response -> {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("login");
                                JSONObject object = jsonArray.getJSONObject(0);

                                String objusername = object.getString("username").trim();
                                String objfullname = object.getString("fullname").trim();
                                String objemail = object.getString("email").trim();
                                String objphone = object.getString("phone").trim();
                                String objrole = object.getString("utype").trim();

                                // Log vai trò từ server
                                Log.d("ServerRole", "Role from server: " + objrole);

                                // Lưu thông tin vào SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", objusername);
                                editor.putString("fullname", objfullname);
                                editor.putString("email", objemail);
                                editor.putString("phone", objphone);
                                editor.putString("utype", objrole);  // Lưu role
                                editor.apply();

                                // Chuyển hướng dựa vào vai trò
                                Intent intent;
                                switch (objrole.toLowerCase()) {  // So sánh không phân biệt hoa thường
                                    case "doctor":
                                        intent = new Intent(LoginActivity.this, MainActivity.class);
                                        break;
                                    case "patient":
                                        intent = new Intent(LoginActivity.this, MainActivity.class);
                                        break;
                                    case "admin":
                                        intent = new Intent(LoginActivity.this, MainActivity.class);
                                        break;
                                    default:
                                        Toast.makeText(LoginActivity.this, "Invalid role", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        return;
                                }

                                Toast.makeText(LoginActivity.this, "Welcome " + objfullname, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                startActivity(intent);
                                finish();
                            } else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    },
                    error -> {
                        Toast.makeText(LoginActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("utype", selectedRole); // Gửi vai trò người dùng chọn
                    Log.d("LoginParams", "Username: " + username + ", Password: " + password + ", Role: " + selectedRole);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
