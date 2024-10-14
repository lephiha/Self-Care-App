package com.lph.selfcareapp.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.R;
import com.lph.selfcareapp.UploadDiagnose;
import com.lph.selfcareapp.model.Appointment;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;
import com.lph.selfcareapp.view.DiagnoseAdapter;
import com.lph.selfcareapp.view.TicketAdapter;
import com.lph.selfcareapp.viewmodel.DiagnoseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiagnoseActivity extends AppCompatActivity implements DiagnoseListener {
    RecyclerView recyclerView;
    List<Appointment> appointmentList;
    List<Appointment> temp;
    DiagnoseAdapter ticketAdapter;
    TextView navText;
    private int SELECT_FILE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_activity);
        recyclerView = findViewById(R.id.ticketRecyclerView);
        navText = findViewById(R.id.navText);
        navText.setText("Gửi chẩn đoán");
        getTicket();

    }



    private void getTicket(){
        SharedPreferences sp = getSharedPreferences("UserData",MODE_PRIVATE);
        int id = sp.getInt("id",0);
        new RetrofitInstance().getService().getAppointment2(id).enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                appointmentList = response.body();
                temp = new ArrayList<>(appointmentList);
                displayAppointmentRecyclerview();

            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable throwable) {

            }
        });
    }

    private void displayAppointmentRecyclerview(){
        temp = appointmentList.stream().filter(s->s.getHasDone()==1).collect(Collectors.toList());
        ticketAdapter = new DiagnoseAdapter(this,temp,DiagnoseActivity.this::onButtonClicked);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ticketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ticketAdapter.notifyDataSetChanged();
    }

    @Override
    public void onButtonClicked(Appointment appointment) {
        Intent intent = new Intent(DiagnoseActivity.this, UploadDiagnose.class);
        intent.putExtra("appoid",appointment.getAppoid());
        startActivity(intent);
    }


}