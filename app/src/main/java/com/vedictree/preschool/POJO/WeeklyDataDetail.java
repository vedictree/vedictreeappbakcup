package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeeklyDataDetail {
    @SerializedName("vbId")
    @Expose
    private String vbId;
    @SerializedName("sessionName")
    @Expose
    private String sessionName;
    @SerializedName("fk_classId")
    @Expose
    private String fkClassId;
    @SerializedName("youtubelink")
    @Expose
    private String youtubelink;
    @SerializedName("vimeoId")
    @Expose
    private String vimeoId;
    @SerializedName("fromDT")
    @Expose
    private String fromDT;
    @SerializedName("toDT")
    @Expose
    private String toDT;
    @SerializedName("sessionImage")
    @Expose
    private String sessionImage;
    @SerializedName("weekId")
    @Expose
    private String weekId;
    @SerializedName("classId")
    @Expose
    private String classId;
    @SerializedName("className")
    @Expose
    private String className;
    @SerializedName("createDT")
    @Expose
    private String createDT;
    @SerializedName("status")
    @Expose
    private String status;

    public String getVbId() {
        return vbId;
    }

    public void setVbId(String vbId) {
        this.vbId = vbId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getFkClassId() {
        return fkClassId;
    }

    public void setFkClassId(String fkClassId) {
        this.fkClassId = fkClassId;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }

    public String getVimeoId() {
        return vimeoId;
    }

    public void setVimeoId(String vimeoId) {
        this.vimeoId = vimeoId;
    }

    public String getFromDT() {
        return fromDT;
    }

    public void setFromDT(String fromDT) {
        this.fromDT = fromDT;
    }

    public String getToDT() {
        return toDT;
    }

    public void setToDT(String toDT) {
        this.toDT = toDT;
    }

    public String getSessionImage() {
        return sessionImage;
    }

    public void setSessionImage(String sessionImage) {
        this.sessionImage = sessionImage;
    }

    public String getWeekId() {
        return weekId;
    }

    public void setWeekId(String weekId) {
        this.weekId = weekId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreateDT() {
        return createDT;
    }

    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
