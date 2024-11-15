package com.lph.selfcareapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reschedule {
    @SerializedName("scheduuledate")
    @Expose
    private String scheduuledate;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("docname")
    @Expose
    private String docname;
    @SerializedName("clinicname")
    @Expose
    private String clinicname;
    @SerializedName("address")
    @Expose
    private String address;

    public String getScheduuledate() {
        return scheduuledate;
    }

    public void setScheduuledate(String scheduuledate) {
        this.scheduuledate = scheduuledate;
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

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
