package com.lph.selfcareapp.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clinic extends BaseObservable {
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

    @BindingAdapter("imagePath")
    public static void loadImage(ImageView imageView, String imageUrl){
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }
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
}
