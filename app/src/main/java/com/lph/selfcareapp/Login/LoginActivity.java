package com.lph.selfcareapp.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    String url_login = "http://192.168.0.107/selfcare/login.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các view
        Anhxa();

        // click sign up
        signUpRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // click login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoLogin();
            }
        });
        // click forgot pass
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
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
    }

    private void GoLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(username.isEmpty()) {
            tv_error.setText("Please Enter Your Email");
        } else if (password.isEmpty()) {
            tv_error.setText("Please Enter Your Password");
        } else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.e("RESPONSE", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("login");
                                if (success.equals("1")) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        String ojbusername = object.getString("username").trim();
                                        String objfullname  = object.getString("fullname").trim();
                                        String objemail = object.getString("email").trim();

                                        // lưu vào sharedpreference
                                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putString("username", ojbusername);
                                        editor.putString("fullname", objfullname);
                                        editor.putString("email", objemail);
                                        editor.apply();

                                        Toast.makeText(LoginActivity.this, "Welcome " + objfullname, Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                        // Chuyển đến MainActivity khi đăng nhập thành công
                                        Log.d("LOGIN", "Đăng nhập thành công, chuyển đến MainActivity");
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();


                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(LoginActivity.this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(LoginActivity.this, "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
