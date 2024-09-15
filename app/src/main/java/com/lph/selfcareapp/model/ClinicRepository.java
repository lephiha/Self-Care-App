package com.lph.selfcareapp.model;

import android.app.Application;
import android.nfc.Tag;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.lph.selfcareapp.serviceAPI.ClinicApiService;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicRepository {
    private ClinicApiService clinicApiService;
    public ClinicRepository(){
        this.clinicApiService = new RetrofitInstance().getService();
    }
    public MutableLiveData<ClinicList> getMutableLivedData(){
        MutableLiveData<ClinicList> mutableLiveData =new MutableLiveData<>();
        Call<ClinicList> call = clinicApiService.getAllClinics();
        call.enqueue(new Callback<ClinicList>() {
            @Override
            public void onResponse(Call<ClinicList> call, Response<ClinicList> response) {
                ClinicList list = response.body();
                mutableLiveData.setValue(list);
            }

            @Override
            public void onFailure(Call<ClinicList> call, Throwable throwable) {
                Log.d("TAG", throwable.toString());
            }
        });
        return mutableLiveData;
    }
}
