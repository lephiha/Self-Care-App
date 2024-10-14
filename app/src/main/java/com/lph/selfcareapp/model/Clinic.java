package com.lph.selfcareapp.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Clinic extends BaseObservable implements Serializable {
    @SerializedName("clinic_id")
    @Expose
    private int clinic_id;
    @SerializedName("clinic_name")
    @Expose
    private String clinic_name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("clinic_image")
    @Expose
    private String clinic_image;
    @SerializedName("chief_id")
    @Expose
    private int chief_id;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @BindingAdapter("imagePath")
    public static void loadImage(ImageView imageView, String imageUrl){
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }
    private float distance;

    @Bindable
    public int getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(int clinic_id) {
        this.clinic_id = clinic_id;
    }

    @Bindable
    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Bindable
    public String getClinic_image() {
        return clinic_image;
    }

    public void setClinic_image(String clinic_image) {
        this.clinic_image = clinic_image;
    }

    @Bindable
    public int getChief_id() {
        return chief_id;
    }

    public void setChief_id(int chief_id) {
        this.chief_id = chief_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
