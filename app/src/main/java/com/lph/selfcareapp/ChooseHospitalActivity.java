package com.lph.selfcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.databinding.ActivityChooseHospitalBinding;
import com.lph.selfcareapp.model.Clinic;
import com.lph.selfcareapp.model.ClinicList;
import com.lph.selfcareapp.view.ClinicAdapter;
import com.lph.selfcareapp.viewmodel.ChooseClinicListener;
import com.lph.selfcareapp.viewmodel.ClinicViewModel;

public class ChooseHospitalActivity extends AppCompatActivity implements ChooseClinicListener {
    private ClinicList clinics;
    private ActivityChooseHospitalBinding binding;
    private RecyclerView recyclerView;
    private ClinicAdapter clinicAdapter;
    private ClinicViewModel viewModel;
    Button bookDoctorBtn;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_hospital);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_choose_hospital);
        recyclerView = binding.clinicRecyclerview;
        viewModel = new ViewModelProvider(this)
                .get(ClinicViewModel.class);
        back = findViewById(R.id.back);
//        bookDoctorBtn = findViewById(R.id.bookDoctorBtn);
//        bookDoctorBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplication(), ChooseDoctorActivity.class));
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        getAllClinics();
    }

    public void getAllClinics(){
        viewModel.getClinicListLiveData().observe(this, new Observer<ClinicList>() {
            @Override
            public void onChanged(ClinicList clinicList) {
                clinics = clinicList;
                displayClinicsInRecyclerview();
            }
        });
    }

    public void displayClinicsInRecyclerview(){
        clinicAdapter = new ClinicAdapter(this, clinics,this::onItemClicked);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clinicAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clinicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(Clinic clinic) {
        Intent intent = new Intent(getApplication(), ChooseDoctorActivity.class);
        intent.putExtra("clinic", clinic);
        startActivity(intent);
    }
}