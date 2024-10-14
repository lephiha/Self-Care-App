package com.lph.selfcareapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CallDoctor implements Serializable {
    @SerializedName("docid")
    @Expose
    private Integer docid;
    @SerializedName("docAvailable")
    @Expose
    private String docAvailable;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("docname")
    @Expose
    private String docname;
    @SerializedName("sname")
    @Expose
    private String sname;
    @SerializedName("acacdemicrank")
    @Expose
    private String acacdemicrank;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("call_id")
    @Expose
    private String callId;

    public Integer getDocid() {
        return docid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }

    public String getDocAvailable() {
        return docAvailable;
    }

    public void setDocAvailable(String docAvailable) {
        this.docAvailable = docAvailable;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getAcacdemicrank() {
        return acacdemicrank;
    }

    public void setAcacdemicrank(String acacdemicrank) {
        this.acacdemicrank = acacdemicrank;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

}
