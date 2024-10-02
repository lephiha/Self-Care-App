package com.lph.selfcareapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lph.selfcareapp.model.SpecialtyList;
import com.lph.selfcareapp.serviceAPI.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialtyViewModel extends ViewModel {
    LiveData<SpecialtyList> specialtyListLiveData;
    public SpecialtyViewModel(){
        MutableLiveData<SpecialtyList> mutableLiveData = new MutableLiveData<>();
        Call<SpecialtyList> call = new RetrofitInstance().getService().getAllSpecialties();
        call.enqueue(new Callback<SpecialtyList>() {
            @Override
            public void onResponse(Call<SpecialtyList> call, Response<SpecialtyList> response) {
                SpecialtyList list = response.body();
                mutableLiveData.setValue(list);
            }

            @Override
            public void onFailure(Call<SpecialtyList> call, Throwable throwable) {

            }
        });
        specialtyListLiveData = mutableLiveData;
    }

    public LiveData<SpecialtyList> getSpecialtyListLiveData(){
        return specialtyListLiveData;
    }
}
