package com.lph.selfcareapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lph.selfcareapp.model.ClinicList;
import com.lph.selfcareapp.model.ClinicRepository;

public class ClinicViewModel extends ViewModel {
    ClinicRepository clinicRepository = new ClinicRepository();
    LiveData<ClinicList> clinicListLiveData;
    public ClinicViewModel() {
        clinicListLiveData = clinicRepository.getMutableLivedData();
    }

    public LiveData<ClinicList> getClinicListLiveData(){return clinicListLiveData;}
}
