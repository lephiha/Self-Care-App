package com.lph.selfcareapp.menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.Utils.BottomNavigationViewHelper;
import com.lph.selfcareapp.model.Appointment;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;
import com.lph.selfcareapp.view.TicketAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicalDoctorTicketActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Appointment> appointmentList;
    List<Appointment> temp;
    TicketAdapter ticketAdapter;
    TextView navText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_doctor_activity);
        recyclerView = findViewById(R.id.ticketRecyclerView);
        navText = findViewById(R.id.navText);
        navText.setText("Lịch khám");
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
        temp = appointmentList.stream().filter(s->s.getHasDone()==0).collect(Collectors.toList());
        ticketAdapter = new TicketAdapter(this,temp);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ticketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ticketAdapter.notifyDataSetChanged();
    }
}