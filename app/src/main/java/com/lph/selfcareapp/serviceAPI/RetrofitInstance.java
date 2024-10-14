package com.lph.selfcareapp.serviceAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitInstance {
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;
//    private static String BASE_URL ="http://10.0.2.2/";
    private static String BASE_URL ="https://edoc.cloudkma.fun/";
    private static String IMAGE_URL="https://anh.moe/";
    public static ApiService getService(){
        if(retrofit==null)
            retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(ApiService.class);
    }

    public static ApiService getImage(){
        if(retrofit2==null)
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(IMAGE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

        return retrofit2.create(ApiService.class);
    }

}
