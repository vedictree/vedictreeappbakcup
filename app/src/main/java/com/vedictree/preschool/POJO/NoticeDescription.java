package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeDescription {
    @SerializedName("notId")
    @Expose
    private String notId;
    @SerializedName("noticedesc")
    @Expose
    private String noticedesc;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createDT")
    @Expose
    private String createDT;
    @SerializedName("readMsg")
    @Expose
    private String readMsg;

    public String getNotId() {
        return notId;
    }

    public void setNotId(String notId) {
        this.notId = notId;
    }

    public String getNoticedesc() {
        return noticedesc;
    }

    public void setNoticedesc(String noticedesc) {
        this.noticedesc = noticedesc;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDT() {
        return createDT;
    }

    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }

    public String getReadMsg() {
        return readMsg;
    }

    public void setReadMsg(String readMsg) {
        this.readMsg = readMsg;
    }

}
