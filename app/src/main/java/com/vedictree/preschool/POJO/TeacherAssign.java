package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherAssign {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("res")
    @Expose
    private List<TeacherAssignResponse> res = null;
    @SerializedName("code")
    @Expose
    private long code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TeacherAssignResponse> getRes() {
        return res;
    }

    public void setRes(List<TeacherAssignResponse> res) {
        this.res = res;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

}
