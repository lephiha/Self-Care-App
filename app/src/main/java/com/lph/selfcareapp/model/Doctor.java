package com.lph.selfcareapp.model;

import android.content.res.AssetManager;
import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lph.selfcareapp.R;

public class Doctor extends BaseObservable {
    @SerializedName("doc_id")
    @Expose
    private Integer docId;
    @SerializedName("docemail")
    @Expose
    private String docemail;
    @SerializedName("docname")
    @Expose
    private String docname;
    @SerializedName("specialties")
    @Expose
    private Integer specialties;
    @SerializedName("sname")
    @Expose
    private String sname;
    @SerializedName("academic_rank")
    @Expose
    private String academicRank;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("price")
    @Expose
    private int price;
    @Bindable
    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }
    @Bindable
    public String getDocemail() {
        return docemail;
    }

    public void setDocemail(String docemail) {
        this.docemail = docemail;
    }
    @Bindable
    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }
    @Bindable
    public Integer getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Integer specialties) {
        this.specialties = specialties;
    }
    @Bindable
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
    @Bindable
    public String getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(String academicRank) {
        this.academicRank = academicRank;
    }
    @Bindable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @BindingAdapter("docImagePath")
    public static void loadImage(ImageView imageView, String sex){

        if(sex.equals("M"))
            imageView.setImageResource(R.drawable.maledoctor);
        else
            imageView.setImageResource(R.drawable.femaledoctor);
    }

    @Bindable
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
