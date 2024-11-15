package com.lph.selfcareapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.model.Reschedule;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;
import com.lph.selfcareapp.view.RescheduleAdapter;
import com.lph.selfcareapp.view.TicketAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RescheduleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView navText;
    ImageButton back;
    int id;
    List<Reschedule> rescheduleList;
    RescheduleAdapter rescheduleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reschedule);
        recyclerView = findViewById(R.id.recyclerView);
        navText = findViewById(R.id.navText);
        back = findViewById(R.id.back);
        navText.setText("Lịch tái khám ");
        back.setOnClickListener(v -> finish());
        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        id = sp.getInt("id", 0);
        getReschedule();
    }

    private void getReschedule() {
        RetrofitInstance.getService().showreschedule(id).enqueue(new Callback<List<Reschedule>>() {
            @Override
            public void onResponse(Call<List<Reschedule>> call, Response<List<Reschedule>> response) {
                rescheduleList = response.body();
                rescheduleAdapter = new RescheduleAdapter(RescheduleActivity.this,rescheduleList);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(rescheduleAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(RescheduleActivity.this));
                rescheduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Reschedule>> call, Throwable throwable) {

            }
        });
    }
}