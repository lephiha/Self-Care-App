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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.MainDoctorActivity;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.Utils.AESUtils;
import com.lph.selfcareapp.Utils.Utils;
import com.lph.selfcareapp.lockscreen.LockScreenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;


public class LoginActivity extends AppCompatActivity {
    TextView signUpRedirect, tv_error, forgotPassword;
    TextInputEditText emailEditText, passwordEditText;
    Button loginBtn;
    Spinner roleSpiner;

    BiometricPrompt biometricPrompt;
    Executor executor;



    //    String url_login = "edoc.clbook-doctor/login.php";
    String url_login = "http://192.168.56.1/book-doctor/login.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các view
        Anhxa();

        // Kiểm tra và yêu cầu thiết lập màn hình khóa nếu chưa có
        LockScreenHelper lockScreenHelper = new LockScreenHelper(this);

        lockScreenHelper.promptSetPinLock(this); // Yêu cầu thiết lập mã PIN
        lockScreenHelper.authenticateUser(this);
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
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        tv_error = findViewById(R.id.tv_error);
        forgotPassword = findViewById(R.id.forgotPassword);
        roleSpiner = findViewById(R.id.roleSpinner);
    }


    private void GoLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String selectedRole = roleSpiner.getSelectedItem().toString().toLowerCase();

        if (email.isEmpty()) {
            tv_error.setText("Please Enter Your Email");
        } else if (password.isEmpty()) {
            tv_error.setText("Please Enter Your Password");
        } else {

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();



            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                    response -> {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);

                            // Kiểm tra trường success tồn tại hay không
                            if (jsonObject.has("success")) {
                                String success = jsonObject.getString("success");
                                if (success.equals("1")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                                    JSONObject object = jsonArray.getJSONObject(0);

                                    String objemail = object.getString("email").trim();
                                    String objfullname = object.getString("fullname").trim();
                                    String objrole = object.getString("utype").trim();
                                    String token = object.optString("token", "");
                                    int objid = object.getInt("id");

                                    // Lưu thông tin vào SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", objemail);
                                    editor.putString("fullname", objfullname);
                                    editor.putString("utype", objrole);
                                    editor.putInt("id", objid);
                                    editor.putString("token", token);
                                    editor.apply();



                                    Intent intent;
                                    switch (objrole.toLowerCase()) {
                                        case "doctor":
                                            intent = new Intent(LoginActivity.this, MainDoctorActivity.class);
                                            break;
                                        case "patient":
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
                            } else {
                                // Xử lý khi trường success không tồn tại
                                String message = jsonObject.optString("message", "Unknown error");
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
                    params.put("email", email);
                    params.put("password", password);
                    params.put("utype", selectedRole);
                    Log.d("LoginParams", "Email: " + email + ", Password: " + password + ", Role: " + selectedRole);
                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

}
