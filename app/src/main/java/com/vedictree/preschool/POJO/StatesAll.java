package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatesAll {
    @SerializedName("error")
    @Expose
    private AllStateList error;

    public AllStateList getError() {
        return error;
    }

    public void AllStateList(AllStateList error) {
        this.error = error;
    }
}
