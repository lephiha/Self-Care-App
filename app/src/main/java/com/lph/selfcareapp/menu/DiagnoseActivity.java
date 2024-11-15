package com.lph.selfcareapp.menu;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener;
import com.lph.selfcareapp.R;
import com.lph.selfcareapp.UploadDiagnose;
import com.lph.selfcareapp.model.Appointment;
import com.lph.selfcareapp.model.Result;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;
import com.lph.selfcareapp.view.DiagnoseAdapter;
import com.lph.selfcareapp.view.TicketAdapter;
import com.lph.selfcareapp.viewmodel.DiagnoseListener;

import java.util.ArrayList;
import java.util.Calendar;
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
    int id;
    ImageButton back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_activity);
        recyclerView = findViewById(R.id.ticketRecyclerView);
        navText = findViewById(R.id.navText);
        back = findViewById(R.id.back);
        back.setOnClickListener(v->finish());
        navText.setText("Gửi chẩn đoán");
        getTicket();

    }



    private void getTicket(){
        SharedPreferences sp = getSharedPreferences("UserData",MODE_PRIVATE);
        id = sp.getInt("id",0);
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
        ticketAdapter = new DiagnoseAdapter(this,temp,DiagnoseActivity.this);
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

    @Override
    public void onButtonClicked2(Appointment appointment) {
        getSchedulteDate(appointment);
    }

    public void getSchedulteDate(Appointment appointment){
        Dialog dateDialog = new Dialog(DiagnoseActivity.this);
        dateDialog.setContentView(R.layout.dialog_calendar_view);

        //set custom width and height
        dateDialog.getWindow().setLayout(1000,1500);
        // set transparent background
        dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dateDialog.show();
        CalendarView calendarView = dateDialog.findViewById(R.id.calendarView);
        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_MONTH, -1);


        calendarView.setMinimumDate(min);
        calendarView.setOnCalendarDayClickListener(new OnCalendarDayClickListener() {
            @Override
            public void onClick(@NonNull CalendarDay calendarDay) {
                Calendar clickedDayCalendar = calendarDay.getCalendar();
                if (clickedDayCalendar.getTime().compareTo(min.getTime())>=0
                       ){
                    String day = String.valueOf(clickedDayCalendar.get(Calendar.DAY_OF_MONTH));
                    String month = String.valueOf(clickedDayCalendar.get(Calendar.MONTH)+1);
                    String year = String.valueOf(clickedDayCalendar.get(Calendar.YEAR));
                    dateDialog.dismiss();

                    String date = year + "-"+month+"-"+day;
                    RetrofitInstance.getService().insertDate(appointment.getPid(),id,date).enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(DiagnoseActivity.this,"Thêm lịch thành công ",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable throwable) {
                            Log.d("Retrofit",throwable.toString());
                        }
                    });
                }

            }
        });
    }

}