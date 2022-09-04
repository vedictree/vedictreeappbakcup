package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllStateList {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("States")
    @Expose
    private List<StateList> states = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<StateList> getStates() {
        return states;
    }

    public void setStates(List<StateList> states) {
        this.states = states;
    }
}
