package com.lph.selfcareapp.serviceAPI;

import com.lph.selfcareapp.model.Appointment;
import com.lph.selfcareapp.model.Appointment2;
import com.lph.selfcareapp.model.CallDoctor;
import com.lph.selfcareapp.model.ClinicList;
import com.lph.selfcareapp.model.Doctor;
import com.lph.selfcareapp.model.DoctorList;
import com.lph.selfcareapp.model.Reschedule;
import com.lph.selfcareapp.model.Result;
import com.lph.selfcareapp.model.ReturnData;
import com.lph.selfcareapp.model.ScheduleTime;
import com.lph.selfcareapp.model.SpecialtyList;
import com.lph.selfcareapp.view.TimeAdapter;

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
    @FormUrlEncoded
    @POST("book-doctor/vnpay_create_payment.php")
    Call<ReturnData> createPayment(@Field("amount") int amount,
                                   @Field("order_id") long orderId,
                                    @Field("order_info") String orderInfo);

    @GET("book-doctor/appointment.php")
    Call<List<Appointment>> getAppointment(@Query("pid") int pid);

    @GET("book-doctor/appointment2.php")
    Call<List<Appointment>> getAppointment2(@Query("docid") int docid);

    @FormUrlEncoded
    @POST("api/1/upload")
    Call<String> getImageUrl(@Field("key") String key,
                             @Field("source") String source,
                             @Field("format") String format);

    @FormUrlEncoded
    @POST("book-doctor/uploadImage.php")
    Call<Result> uploadImage(@Field("image") String image,
                             @Field("appoid") int appoid);

    @GET("book-doctor/diagnose.php")
    Call<List<Appointment2>> getResult(@Query("pid") int pid);

    @GET("book-doctor/getcalldoctor.php")
    Call<List<CallDoctor>> getCallDoctor(@Query("pid") int pid);

    @GET("book-doctor/reschedule.php")
    Call<Result> insertDate(@Query("pid") int pid,
                            @Query("docid") int docid,
                            @Query("date") String date);

    @GET("book-doctor/showreschedule.php")
    Call<List<Reschedule>> showreschedule(@Query("pid") int pid);

    @FormUrlEncoded
    @POST("book-doctor/addschedule.php")
    Call<TimeAdapter> addSchedule(@Field("docid") int docid,
                                  @Field("date") String date,
                                  @Field("time") String time,
                                  @Field("patient_id") int patientId);

}
