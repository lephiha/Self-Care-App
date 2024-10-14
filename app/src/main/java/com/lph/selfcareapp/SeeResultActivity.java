package com.lph.selfcareapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.model.Appointment2;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;
import com.lph.selfcareapp.view.ClinicAdapter;
import com.lph.selfcareapp.view.ResultAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeResultActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Appointment2> appointment2List;
    ResultAdapter resultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_see_result);
        recyclerView = findViewById(R.id.seeResView);
        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        int pid = sp.getInt("id",0);
        RetrofitInstance.getService().getResult(pid).enqueue(new Callback<List<Appointment2>>() {
            @Override
            public void onResponse(Call<List<Appointment2>> call, Response<List<Appointment2>> response) {
                appointment2List = response.body();
                resultAdapter = new ResultAdapter(SeeResultActivity.this, appointment2List);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(resultAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(SeeResultActivity.this));
                resultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Appointment2>> call, Throwable throwable) {

            }
        });
    }
}