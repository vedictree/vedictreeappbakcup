package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Progress_report {
    @SerializedName("error")
    @Expose
    private Progress_report_detail error;


    public Progress_report_detail getError() {
        return error;
    }

    public void setError(Progress_report_detail error) {
        this.error = error;
    }


}
