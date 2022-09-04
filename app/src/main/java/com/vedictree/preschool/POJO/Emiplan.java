package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Emiplan {
    @SerializedName("emi_Id")
    @Expose
    private String emiId;
    @SerializedName("final_fees_emi")
    @Expose
    private String finalFeesEmi;
    @SerializedName("emipercentage")
    @Expose
    private String emipercentage;
    @SerializedName("emicharges")
    @Expose
    private String emicharges;
    @SerializedName("monthlyFess")
    @Expose
    private String monthlyFess;
    @SerializedName("fk_feesId")
    @Expose
    private String fkFeesId;
    @SerializedName("createDT")
    @Expose
    private String createDT;
    @SerializedName("fk_classId")
    @Expose
    private String fkClassId;
    @SerializedName("fk_tid")
    @Expose
    private String fkTid;
    @SerializedName("checkout_emi_amt")
    @Expose
    private String checkoutEmiAmt;
    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("tenureName")
    @Expose
    private String tenureName;
    @SerializedName("feesId")
    @Expose
    private String feesId;
    @SerializedName("school_fees")
    @Expose
    private String schoolFees;
    @SerializedName("book_fees")
    @Expose
    private String bookFees;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("monthly_fees")
    @Expose
    private String monthlyFees;
    @SerializedName("final_fees")
    @Expose
    private String finalFees;
    @SerializedName("packageName")
    @Expose
    private String packageName;

    public String getEmiId() {
        return emiId;
    }

    public void setEmiId(String emiId) {
        this.emiId = emiId;
    }

    public String getFinalFeesEmi() {
        return finalFeesEmi;
    }

    public void setFinalFeesEmi(String finalFeesEmi) {
        this.finalFeesEmi = finalFeesEmi;
    }

    public String getEmipercentage() {
        return emipercentage;
    }

    public void setEmipercentage(String emipercentage) {
        this.emipercentage = emipercentage;
    }

    public String getEmicharges() {
        return emicharges;
    }

    public void setEmicharges(String emicharges) {
        this.emicharges = emicharges;
    }

    public String getMonthlyFess() {
        return monthlyFess;
    }

    public void setMonthlyFess(String monthlyFess) {
        this.monthlyFess = monthlyFess;
    }

    public String getFkFeesId() {
        return fkFeesId;
    }

    public void setFkFeesId(String fkFeesId) {
        this.fkFeesId = fkFeesId;
    }

    public String getCreateDT() {
        return createDT;
    }

    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }

    public String getFkClassId() {
        return fkClassId;
    }

    public void setFkClassId(String fkClassId) {
        this.fkClassId = fkClassId;
    }

    public String getFkTid() {
        return fkTid;
    }

    public void setFkTid(String fkTid) {
        this.fkTid = fkTid;
    }
    public String getCheckoutEmiAmt() {
        return checkoutEmiAmt;
    }

    public void setCheckoutEmiAmt(String checkoutEmiAmt) {
        this.checkoutEmiAmt = checkoutEmiAmt;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTenureName() {
        return tenureName;
    }

    public void setTenureName(String tenureName) {
        this.tenureName = tenureName;
    }

    public String getFeesId() {
        return feesId;
    }

    public void setFeesId(String feesId) {
        this.feesId = feesId;
    }

    public String getSchoolFees() {
        return schoolFees;
    }

    public void setSchoolFees(String schoolFees) {
        this.schoolFees = schoolFees;
    }

    public String getBookFees() {
        return bookFees;
    }

    public void setBookFees(String bookFees) {
        this.bookFees = bookFees;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getMonthlyFees() {
        return monthlyFees;
    }

    public void setMonthlyFees(String monthlyFees) {
        this.monthlyFees = monthlyFees;
    }

    public String getFinalFees() {
        return finalFees;
    }

    public void setFinalFees(String finalFees) {
        this.finalFees = finalFees;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
