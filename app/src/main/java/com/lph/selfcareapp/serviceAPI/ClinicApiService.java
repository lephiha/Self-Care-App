package com.lph.selfcareapp.serviceAPI;

import com.lph.selfcareapp.model.ClinicList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClinicApiService {
    @GET("book-doctor/clinic.php")
    Call<ClinicList> getAllClinics();
}
