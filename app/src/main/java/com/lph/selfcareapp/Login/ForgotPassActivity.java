package com.lph.selfcareapp.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.lph.selfcareapp.R;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassActivity extends AppCompatActivity {

    Button submitButton;
    EditText usernameEditText, phoneEditText;
    String phone, username;
    StringRequest stringRequest;
    String URL = "http://192.168.0.107/selfcare/forgotpass.php";  // url api

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        // anh xa
        submitButton = (Button) findViewById(R.id.submitButton);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);

        submit();
    }

    private void submit() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = phoneEditText.getText().toString();
                username = usernameEditText.getText().toString();

                stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("SUCCESS")) {
                            Toast.makeText(getApplicationContext(), "Email đã được gửi, vui lòng kiểm tra hộp thư", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgotPassActivity.this, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("phone", phone);
                        params.put("username", username);
                        return params;
                    }
                };

                // Thêm yêu cầu vào hàng đợi để thực thi
                RequestQueue requestQueue = Volley.newRequestQueue(ForgotPassActivity.this);
                requestQueue.add(stringRequest);
            }
        });
    }
}
