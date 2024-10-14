package com.lph.selfcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
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
                for(Clinic clinic: clinics){
                    SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
                    double longitude = Double.parseDouble(sp.getString("longitude",""));
                    double latitude = Double.parseDouble(sp.getString("latitude",""));
                    Location startLocation = new Location("start");
                    startLocation.setLatitude(latitude);
                    startLocation.setLongitude(longitude);

                    Location endLocation = new Location("end");
                    endLocation.setLatitude(clinic.getLatitude());
                    endLocation.setLongitude(clinic.getLongitude());
                    float distance = startLocation.distanceTo(endLocation);
                    clinic.setDistance(distance);
                }
                clinics.sort((o1, o2) -> (int)(o1.getDistance()-o2.getDistance()));
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