package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalenderUpdate {

    @SerializedName("error")
    @Expose
    private CalenderUpdateError error;

    public CalenderUpdateError getError() {
        return error;
    }

    public void setError(CalenderUpdateError error) {
        this.error = error;
    }
}
