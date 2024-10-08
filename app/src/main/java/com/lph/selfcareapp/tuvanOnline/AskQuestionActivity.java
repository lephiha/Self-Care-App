package com.lph.selfcareapp.tuvanOnline;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lph.selfcareapp.MainActivity;
import com.lph.selfcareapp.R;

public class AskQuestionActivity extends AppCompatActivity {

    private Spinner spinnerChuyenKhoa, spinnerGioiTinh, spinnerDoTuoi;
    private EditText edtCauHoi;
    private Button btnDatCauHoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        // Ánh xạ các phần tử trong layout
        spinnerChuyenKhoa = findViewById(R.id.spinnerChuyenKhoa);
        spinnerGioiTinh = findViewById(R.id.spinnerGioiTinh);
        spinnerDoTuoi = findViewById(R.id.spinnerDoTuoi);
        edtCauHoi = findViewById(R.id.edtCauHoi);
        btnDatCauHoi = findViewById(R.id.btnDatCauHoi);

        // Thiết lập Spinner cho chuyên khoa, giới tính và độ tuổi
        setupSpinners();

        // Xử lý khi nhấn nút "Đặt câu hỏi"
        btnDatCauHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chuyenKhoa = spinnerChuyenKhoa.getSelectedItem().toString();
                String gioiTinh = spinnerGioiTinh.getSelectedItem().toString();
                String doTuoi = spinnerDoTuoi.getSelectedItem().toString();
                String cauHoi = edtCauHoi.getText().toString().trim();

                if (cauHoi.isEmpty()) {
                    Toast.makeText(AskQuestionActivity.this, "Vui lòng nhập câu hỏi", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Bạn có thể gửi câu hỏi này lên server hoặc xử lý theo logic của bạn
                Toast.makeText(AskQuestionActivity.this, "Câu hỏi đã được gửi thành công!", Toast.LENGTH_SHORT).show();

                // Quay lại trang tư vấn online sau khi đặt câu hỏi
                finish();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AskQuestionActivity.this, TuvanActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Hàm thiết lập các giá trị cho Spinner
    private void setupSpinners() {
        // Chuyên khoa
        ArrayAdapter<CharSequence> adapterChuyenKhoa = ArrayAdapter.createFromResource(this,
                R.array.chuyen_khoa_array, android.R.layout.simple_spinner_item);
        adapterChuyenKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChuyenKhoa.setAdapter(adapterChuyenKhoa);

        // Giới tính
        ArrayAdapter<CharSequence> adapterGioiTinh = ArrayAdapter.createFromResource(this,
                R.array.gioi_tinh_array, android.R.layout.simple_spinner_item);
        adapterGioiTinh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGioiTinh.setAdapter(adapterGioiTinh);

        // Độ tuổi
        ArrayAdapter<CharSequence> adapterDoTuoi = ArrayAdapter.createFromResource(this,
                R.array.do_tuoi_array, android.R.layout.simple_spinner_item);
        adapterDoTuoi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoTuoi.setAdapter(adapterDoTuoi);
    }
}

