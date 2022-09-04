package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserHistory {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("student_history")
    @Expose
    private List<StudentHistory> studentHistory = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<StudentHistory> getStudentHistory() {
        return studentHistory;
    }

    public void setStudentHistory(List<StudentHistory> studentHistory) {
        this.studentHistory = studentHistory;
    }

}
