package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Progress_report_detail {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("sessionName")
    @Expose
    private ArrayList<String> sessionName = null;
    @SerializedName("trackDuration")
    @Expose
    private ArrayList<String> trackDuration = null;
    @SerializedName("trackEndTime")
    @Expose
    private ArrayList<String> trackEndTime = null;
    @SerializedName("trackStartTime")
    @Expose
    private ArrayList<String> trackStartTime = null;
    @SerializedName("sessionImages")
    @Expose
    private ArrayList<String> sessionImages = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<String> getSessionName() {
        return sessionName;
    }

    public void setSessionName(ArrayList<String> sessionName) {
        this.sessionName = sessionName;
    }

    public ArrayList<String> getTrackDuration() {
        return trackDuration;
    }

    public void setTrackDuration(ArrayList<String> trackDuration) {
        this.trackDuration = trackDuration;
    }

    public ArrayList<String> getTrackEndTime() {
        return trackEndTime;
    }

    public void setTrackEndTime(ArrayList<String> trackEndTime) {
        this.trackEndTime = trackEndTime;
    }

    public ArrayList<String> getTrackStartTime() {
        return trackStartTime;
    }

    public void setTrackStartTime(ArrayList<String> trackStartTime) {
        this.trackStartTime = trackStartTime;
    }
    public ArrayList<String> getSessionImages() {
        return sessionImages;
    }

    public void setSessionImages(ArrayList<String> sessionImages) {
        this.sessionImages = sessionImages;
    }

}
