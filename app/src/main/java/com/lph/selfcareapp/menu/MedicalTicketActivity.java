package com.lph.selfcareapp.menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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


public class MedicalTicketActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Appointment> appointmentList;
    List<Appointment> temp;
    TicketAdapter ticketAdapter;
    TextView navText;
    MaterialButton bookedBtn;
    MaterialButton seenBtn;
    ImageButton back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_activity);
        recyclerView = findViewById(R.id.ticketRecyclerView);
        navText = findViewById(R.id.navText);
        navText.setText("Lịch khám");
        bookedBtn = findViewById(R.id.bookedBtn);
        seenBtn = findViewById(R.id.seenBtn);
        back = findViewById(R.id.back);
        back.setOnClickListener(v->getOnBackPressedDispatcher().onBackPressed());
        getTicket();
        bookedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookedBtn.setBackgroundColor(getColor(R.color.link_blue));
                bookedBtn.setTextColor(getColor(R.color.white));
                seenBtn.setBackgroundColor(getColor(R.color.grey));
                seenBtn.setTextColor(getColor(R.color.black));
                temp = appointmentList.stream().filter(s->s.getHasDone()==0).collect(Collectors.toList());
                ticketAdapter = new TicketAdapter(MedicalTicketActivity.this,temp);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(ticketAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MedicalTicketActivity.this));
                ticketAdapter.notifyDataSetChanged();
            }
        });
        seenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seenBtn.setBackgroundColor(getColor(R.color.link_blue));
                seenBtn.setTextColor(getColor(R.color.white));
                bookedBtn.setBackgroundColor(getColor(R.color.grey));
                bookedBtn.setTextColor(getColor(R.color.black));
                temp = appointmentList.stream().filter(s->s.getHasDone()==1).collect(Collectors.toList());
                ticketAdapter = new TicketAdapter(MedicalTicketActivity.this,temp);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(ticketAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MedicalTicketActivity.this));

                ticketAdapter.notifyDataSetChanged();
            }
        });
        setupNavigationView();
    }

    private void setupNavigationView(){
        Log.d("Main", "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupTopNavigationView(tvEx);
        BottomNavigationViewHelper.enableNavigation(MedicalTicketActivity.this, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }

    private void getTicket(){
        SharedPreferences sp = getSharedPreferences("UserData",MODE_PRIVATE);
        int id = sp.getInt("id",0);
        new RetrofitInstance().getService().getAppointment(id).enqueue(new Callback<List<Appointment>>() {
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