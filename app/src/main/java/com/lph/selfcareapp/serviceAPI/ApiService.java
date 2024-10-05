package com.lph.selfcareapp.serviceAPI;

import com.lph.selfcareapp.model.Appointment;
import com.lph.selfcareapp.model.ClinicList;
import com.lph.selfcareapp.model.Doctor;
import com.lph.selfcareapp.model.DoctorList;
import com.lph.selfcareapp.model.ReturnData;
import com.lph.selfcareapp.model.ScheduleTime;
import com.lph.selfcareapp.model.SpecialtyList;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("book-doctor/clinvsic.php")
    Call<ClinicList> getAllClinics();

    @GET("book-doctor/specialties.php")
    Call<SpecialtyList> getAllSpecialties();

    @GET("book-doctor/doctor.php")
    Call<List<Doctor>> getAllDoctor(@Query("chief_id") int chiefId,
                                         @Query("spe_id") int speId);

    @GET("book-doctor/time.php")
    Call<List<ScheduleTime>> getScheduleTime(@Query("docid") int doctorId,
                                             @Query("date") String date);
    @FormUrlEncoded
    @POST("book-doctor/vnpay_create_payment.php")
    Call<ReturnData> createPayment(@Field("amount") int amount,
                                   @Field("order_id") int orderId,
                                    @Field("order_info") String orderInfo);

    @GET("book-doctor/appointment.php")
    Call<List<Appointment>> getAppointment(@Query("pid") int pid);
}
