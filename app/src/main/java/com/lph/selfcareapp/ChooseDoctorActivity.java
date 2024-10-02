package com.lph.selfcareapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener;
import com.lph.selfcareapp.model.Doctor;
import com.lph.selfcareapp.model.DoctorList;
import com.lph.selfcareapp.model.ScheduleTime;
import com.lph.selfcareapp.model.Specialty;
import com.lph.selfcareapp.model.SpecialtyList;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;
import com.lph.selfcareapp.view.DoctorAdapter;
import com.lph.selfcareapp.view.TimeAdapter;
import com.lph.selfcareapp.viewmodel.ChooseDoctorListener;
import com.lph.selfcareapp.viewmodel.ChooseTImeListener;
import com.lph.selfcareapp.viewmodel.SpecialtyViewModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseDoctorActivity extends AppCompatActivity implements ChooseDoctorListener, ChooseTImeListener {


    SearchableSpinner spinner;
    private SpecialtyList specialtyList;
    private List<Doctor> doctorList;
    private List<Doctor> temp;
    private List<ScheduleTime> scheduleTimeList;
    private SpecialtyViewModel specialtyViewModel;
    DoctorAdapter doctorAdapter;
    ArrayList<String> specialtyArrayList = new ArrayList<>();
    private RecyclerView doctorRecyclerView;
    private RecyclerView timeRecyclerView;
    TextView chooseDoctor;
    int chiefId;
    EditText editText;
    Dialog dialog;
    TextView chooseDate;
    TextView chooseTime;
    TimeAdapter timeAdapter;
    int docId;
    Dialog timeDialog;
    Button continueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor);
        spinner = findViewById(R.id.speSpinner);
        specialtyViewModel = new ViewModelProvider(this)
                    .get(SpecialtyViewModel.class);
        chiefId = getIntent().getIntExtra("chief id",0);
        chooseDoctor = findViewById(R.id.chooseDoctor);
        chooseDate = findViewById(R.id.chooseDate);
        chooseTime = findViewById(R.id.chooseTime);
        continueBtn= findViewById(R.id.continueBtn);
        dialog = new Dialog(ChooseDoctorActivity.this);
        dialog.setContentView(R.layout.dialog_searchable_doctor);
        //set custom width and height
        dialog.getWindow().setLayout(800,1500);
        // set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        Log.e("Intent", String.valueOf(getIntent().getIntExtra("chief id",0)));
//        chiefId = Integer.parseInt(getIntent().getStringExtra("chief id"));
        getAllSpecialties();

    }



    public void getAllSpecialties(){
        specialtyViewModel.getSpecialtyListLiveData().observe(this, new Observer<SpecialtyList>() {
            @Override
            public void onChanged(SpecialtyList specialties) {
                specialtyList = specialties;
                for(Specialty specialty: specialtyList)
                    specialtyArrayList.add(specialty.getSname());
                spinner.setAdapter(new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item,specialtyArrayList));
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int speId = specialties.get(position).getId();
                            chooseDoctor.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getAllDoctor(chiefId, speId);
                                }
                            });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        });
    }

    public void getAllDoctor(int chiefId, int speId){

        dialog.show();

        // Initialize and assign variable
        editText=dialog.findViewById(R.id.edit_text);
        doctorRecyclerView =dialog.findViewById(R.id.doctorRecyclerview);


        RetrofitInstance.getService().getAllDoctor(chiefId, speId).enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                temp = response.body();
                doctorList = new ArrayList<>(temp);
                doctorAdapter = new DoctorAdapter(ChooseDoctorActivity.this, doctorList,ChooseDoctorActivity.this::onItemClicked);
                doctorRecyclerView.setAdapter(doctorAdapter);
                doctorRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                doctorAdapter.notifyDataSetChanged();
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        doctorAdapter = new DoctorAdapter(getApplicationContext(), doctorList,ChooseDoctorActivity.this::onItemClicked);
                        doctorRecyclerView.setAdapter(doctorAdapter);
                        doctorList = temp.stream().filter(e->e.getDocname().toLowerCase().contains(s)).collect(Collectors.toList());
                        doctorAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                });

            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable throwable) {
                Log.d("Retrofit", throwable.toString());
            }
        });
    }

    @Override
    public void onItemClicked(Doctor doctor) {
        docId = doctor.getDocId();
        chooseDoctor.setText(doctor.getAcademicRank() + " " + doctor.getDocname());
        chooseTime.setText("");
        chooseDate.setText("");

        dialog.dismiss();
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSchedulteDate();
            }
        });
    }

    public void getSchedulteDate(){
        Dialog dateDialog = new Dialog(ChooseDoctorActivity.this);
        dateDialog.setContentView(R.layout.dialog_calendar_view);

        //set custom width and height
        dateDialog.getWindow().setLayout(1000,1500);
        // set transparent background
        dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dateDialog.show();
        CalendarView calendarView = dateDialog.findViewById(R.id.calendarView);
        Calendar min = Calendar.getInstance();
                min.add(Calendar.DAY_OF_MONTH, -1);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.DAY_OF_MONTH, +30);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);
        calendarView.setOnCalendarDayClickListener(new OnCalendarDayClickListener() {
            @Override
            public void onClick(@NonNull CalendarDay calendarDay) {
                Calendar clickedDayCalendar = calendarDay.getCalendar();
                if (clickedDayCalendar.getTime().compareTo(min.getTime())>=0
                        &&clickedDayCalendar.getTime().compareTo(max.getTime())<=0){
                    String day = String.valueOf(clickedDayCalendar.get(Calendar.DAY_OF_MONTH));
                    String month = String.valueOf(clickedDayCalendar.get(Calendar.MONTH)+1);
                    String year = String.valueOf(clickedDayCalendar.get(Calendar.YEAR));
                    dateDialog.dismiss();
                    chooseDate.setText(day+"/"+month+"/"+year);
                    String date = year + "-"+month+"-"+day;
                    RetrofitInstance.getService().getScheduleTime(docId,date).enqueue(new Callback<List<ScheduleTime>>() {
                        @Override
                        public void onResponse(Call<List<ScheduleTime>> call, Response<List<ScheduleTime>> response) {
                            scheduleTimeList = response.body();
                            getScheduleTime();
                        }

                        @Override
                        public void onFailure(Call<List<ScheduleTime>> call, Throwable throwable) {
                            Log.e("retrofit", throwable.toString());
                        }
                    });
                }

            }
        });
    }

    public void getScheduleTime(){
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog = new Dialog(ChooseDoctorActivity.this);
                timeDialog.setContentView(R.layout.dialog_choose_time);
                timeDialog.getWindow().setLayout(1000,1500);
                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();
                timeRecyclerView = timeDialog.findViewById(R.id.timeRecyclerView);
                timeAdapter = new TimeAdapter(scheduleTimeList, ChooseDoctorActivity.this,ChooseDoctorActivity.this::onButtonClicked);
                timeRecyclerView.setAdapter(timeAdapter);
                timeRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                timeAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onButtonClicked(ScheduleTime scheduleTime) {
        String starttime = scheduleTime.getStartTime();
        starttime = starttime.substring(0, starttime.length()-3);
        String endtime = scheduleTime.getEndTime();
        endtime = endtime.substring(0, endtime.length()-3);
        chooseTime.setText(starttime + " - " + endtime);
        timeDialog.dismiss();
        continueBtn.setBackgroundColor(getColor(R.color.link_blue));
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseDoctorActivity.this, ConfirmActivity.class);
                startActivity(intent);
            }
        });
    }
}