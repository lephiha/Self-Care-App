package com.lph.selfcareapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment2 {

    @SerializedName("appoid")
    @Expose
    private Integer appoid;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("docname")
    @Expose
    private String docname;
    @SerializedName("hasDone")
    @Expose
    private Integer hasDone;
    @SerializedName("scheduledate")
    @Expose
    private String scheduledate;
    @SerializedName("starttime")
    @Expose
    private String starttime;
    @SerializedName("endtime")
    @Expose
    private String endtime;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getAppoid() {
        return appoid;
    }

    public void setAppoid(Integer appoid) {
        this.appoid = appoid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public Integer getHasDone() {
        return hasDone;
    }

    public void setHasDone(Integer hasDone) {
        this.hasDone = hasDone;
    }

    public String getScheduledate() {
        return scheduledate;
    }

    public void setScheduledate(String scheduledate) {
        this.scheduledate = scheduledate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
