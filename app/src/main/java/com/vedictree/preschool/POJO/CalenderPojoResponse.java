package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalenderPojoResponse {
    @SerializedName("calId")
    @Expose
    private String calId;
    @SerializedName("Days")
    @Expose
    private String days;
    @SerializedName("Months")
    @Expose
    private String months;
    @SerializedName("calDate")
    @Expose
    private String calDate;
    @SerializedName("calFlag")
    @Expose
    private String calFlag;

    public String getCalId() {
        return calId;
    }

    public void setCalId(String calId) {
        this.calId = calId;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
    }

    public String getCalFlag() {
        return calFlag;
    }

    public void setCalFlag(String calFlag) {
        this.calFlag = calFlag;
    }
}
