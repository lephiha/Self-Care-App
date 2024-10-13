package com.lph.selfcareapp.menu.Medical;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lph.selfcareapp.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateProfileActivity extends AppCompatActivity {

    private EditText editTextName, editTextBirthDate, editTextID, editTextInsurance, editTextOccupation;
    private EditText editTextPhone, editTextEmail, editTextAddress;
    private Spinner spinnerGender;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical); // Đảm bảo rằng layout này đã được tạo

        // Ánh xạ các thành phần giao diện
        editTextName = findViewById(R.id.editTextName);
        editTextBirthDate = findViewById(R.id.editTextBirthDate);
        editTextID = findViewById(R.id.editTextID);
        editTextInsurance = findViewById(R.id.editTextInsurance);
        editTextOccupation = findViewById(R.id.editTextOccupation);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editAddress);
        spinnerGender = findViewById(R.id.spinnerGender);
        buttonSave = findViewById(R.id.buttonSave);

        // Thiết lập giá trị cho Spinner Giới Tính
        ArrayAdapter<CharSequence> adapterGioiTinh = ArrayAdapter.createFromResource(this,
                R.array.gioi_tinh_array, android.R.layout.simple_spinner_item);
        adapterGioiTinh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGioiTinh);

        // Thêm DatePicker cho trường Ngày sinh
        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Xử lý khi nhấn nút Tạo mới hồ sơ
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    // Hiển thị DatePickerDialog để chọn ngày sinh
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextBirthDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    // Kiểm tra và lưu thông tin hồ sơ bệnh nhân
    private void saveProfile() {
        String name = editTextName.getText().toString().trim();
        String birthDate = editTextBirthDate.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();
        String id = editTextID.getText().toString().trim();
        String insurance = editTextInsurance.getText().toString().trim();
        String occupation = editTextOccupation.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        // Kiểm tra các trường thông tin bắt buộc
        if (name.isEmpty() || birthDate.isEmpty() || gender.isEmpty() || phone.isEmpty() || occupation.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ các trường bắt buộc", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra định dạng số điện thoại
        if (!Patterns.PHONE.matcher(phone).matches()) {
            editTextPhone.setError("Số điện thoại không hợp lệ");
            return;
        }

        // Kiểm tra định dạng email
        if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email không hợp lệ");
            return;
        }

        // Gửi dữ liệu lên server thông qua API
        sendDataToServer(name, birthDate, gender, id, insurance, occupation, phone, email, address);
    }

    // Phương thức gửi dữ liệu lên server bằng phương thức POST
    private void sendDataToServer(final String name, final String birthDate, final String gender,
                                  final String id, final String insurance, final String occupation,
                                  final String phone, final String email, final String address) {

        String url = "http://192.168.0.107/selfcare/create_patient_profile.php"; // Thay bằng URL API

        // Sử dụng Volley để gửi yêu cầu POST
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Xử lý phản hồi từ server
                        Toast.makeText(CreateProfileActivity.this, "Hồ sơ đã được tạo thành công", Toast.LENGTH_SHORT).show();

                        // Chuyển đến màn hình xem hồ sơ
                        Intent intent = new Intent(CreateProfileActivity.this, MedicalActivity.class); // Đảm bảo rằng ViewProfileActivity đã tồn tại
                        intent.putExtra("phone", phone); // Gửi dữ liệu cần thiết đến màn hình xem hồ sơ
                        startActivity(intent);
                        finish(); // Kết thúc activity hiện tại
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi
                Toast.makeText(CreateProfileActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Gửi dữ liệu dưới dạng Map key-value
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("birthDate", birthDate);
                params.put("gender", gender);
                params.put("id", id);
                params.put("insurance", insurance);
                params.put("occupation", occupation);
                params.put("phone", phone);
                params.put("email", email);
                params.put("address", address);
                return params;
            }
        };

        // Thêm yêu cầu vào hàng đợi
        queue.add(stringRequest);
    }
}
