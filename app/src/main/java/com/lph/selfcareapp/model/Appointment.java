package com.lph.selfcareapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {
    @SerializedName("appoid")
    @Expose
    private Integer appoid;
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("hasDone")
    @Expose
    private Integer hasDone;
    @SerializedName("docname")
    @Expose
    private String docname;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("scheduledate")
    @Expose
    private String scheduledate;
    @SerializedName("starttime")
    @Expose
    private String starttime;
    @SerializedName("endtime")
    @Expose
    private String endtime;

    public Integer getAppoid() {
        return appoid;
    }

    public void setAppoid(Integer appoid) {
        this.appoid = appoid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getHasDone() {
        return hasDone;
    }

    public void setHasDone(Integer hasDone) {
        this.hasDone = hasDone;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocid(String docname) {
        this.docname = docname;
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

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
