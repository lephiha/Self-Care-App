package com.lph.selfcareapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.lph.selfcareapp.Utils.AESUtils;
import com.lph.selfcareapp.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText fullnameEditText, emailEditText, phoneEditText, addressEditText,dobEditText,nicEditText, passwordEditText, confirmPasswordEditText;
    TextView loginRedirect;
    Button signUpButton;
    Spinner roleSpinner;

    String url_register = "http://192.168.56.1/book-doctor/register.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Ánh xạ các view
        Anhxa();

        // Xử lý sự kiện khi nhấn vào nút login redirect
        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn vào nút sign up
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoRegister();
            }
        });
    }

    private void Anhxa() {
        fullnameEditText = findViewById(R.id.fullnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);
        dobEditText = findViewById(R.id.dobEditText);
        nicEditText = findViewById(R.id.nicEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        loginRedirect = findViewById(R.id.loginRedirect);
        signUpButton = findViewById(R.id.signUpButton);
        roleSpinner = findViewById(R.id.roleSpinner);
    }

    private void GoRegister() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        String fullname = fullnameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String nic = nicEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString();
        String extraInfo = "Default info";

        // Kiểm tra dữ liệu bắt buộc
        if (fullname.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || dob.isEmpty() || nic.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.show();

        String hashedPassword = PasswordHelper.hashPassword(password);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");

                            if (success.equals("1")) {
                                progressDialog.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                try {
                                    String key = "MySecretKey12345"; // Hoặc tạo một khóa riêng cho từng người dùng
                                    editor.putString("fullname", AESUtils.encrypt(fullname, key));
                                    editor.putString("dob", AESUtils.encrypt(dob, key));
                                    editor.putString("nic", AESUtils.encrypt(nic, key));
                                    editor.putString("address", AESUtils.encrypt(address, key));
                                    editor.putString("phone", AESUtils.encrypt(phone, key));
                                    editor.putString("email", AESUtils.encrypt(email, key));
                                    editor.putString("role", AESUtils.encrypt(role, key));
                                    editor.apply();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(SignUpActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (success.equals("2")) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Error: " + message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignUpActivity.this, "Response Error: " + e.toString(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this, "Volley Error: " + error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", fullname);
                params.put("email", email);
                params.put("phone", phone);
                params.put("address", address);
                params.put("dob", dob);
                params.put("nic", nic);
                params.put("password", hashedPassword);
                params.put("utype", role); // Gửi vai trò
                params.put("extra_info", extraInfo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
