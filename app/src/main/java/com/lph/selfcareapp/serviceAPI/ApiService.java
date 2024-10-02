package com.lph.selfcareapp.serviceAPI;

import com.lph.selfcareapp.model.ClinicList;
import com.lph.selfcareapp.model.Doctor;
import com.lph.selfcareapp.model.DoctorList;
import com.lph.selfcareapp.model.ScheduleTime;
import com.lph.selfcareapp.model.SpecialtyList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("book-doctor/clinic.php")
    Call<ClinicList> getAllClinics();

    @GET("book-doctor/specialties.php")
    Call<SpecialtyList> getAllSpecialties();

    @GET("book-doctor/doctor.php")
    Call<List<Doctor>> getAllDoctor(@Query("chief_id") int chiefId,
                                         @Query("spe_id") int speId);

    @GET("book-doctor/time.php")
    Call<List<ScheduleTime>> getScheduleTime(@Query("docid") int doctorId,
                                             @Query("date") String date);
}
