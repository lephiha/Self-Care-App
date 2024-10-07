package com.lph.selfcareapp.tuvanOnline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.SearchView; // Sử dụng SearchView của androidx

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.R;

import java.util.ArrayList;
import java.util.List;

public class TuvanActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView questionsRecyclerView;
    private Button buttonAskQuestion;
    private List<String> questionList;
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuvan);

        // Ánh xạ các phần tử trong layout
        searchView = findViewById(R.id.searchView);
        questionsRecyclerView = findViewById(R.id.questionsRecyclerView);
        buttonAskQuestion = findViewById(R.id.buttonAskQuestion);

        // Tạo danh sách câu hỏi giả lập (bạn có thể thay bằng dữ liệu từ server)
        questionList = new ArrayList<>();
        questionList.add("Câu hỏi về chuyên khoa Tim mạch?");
        questionList.add("Câu hỏi về chuyên khoa Nhi?");
        questionList.add("Làm sao để tư vấn online với bác sĩ?");
        questionList.add("Tôi nên làm gì khi có triệu chứng đau đầu?");

        // Thiết lập RecyclerView
        adapter = new QuestionAdapter(questionList);
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionsRecyclerView.setAdapter(adapter);

        // Xử lý khi người dùng nhấn vào nút "Đặt câu hỏi"
        buttonAskQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình đặt câu hỏi
                Intent intent = new Intent(TuvanActivity.this, AskQuestionActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý tìm kiếm trong SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Lọc danh sách câu hỏi dựa trên nội dung tìm kiếm
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
