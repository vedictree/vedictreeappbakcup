package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forgot_password {
    @SerializedName("msg")
    @Expose
    private String msg;
//    @SerializedName("res")
//    @Expose
//    private Boolean res;
    @SerializedName("code")
    @Expose
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public Boolean getRes() {
//        return res;
//    }
//
//    public void setRes(Boolean res) {
//        this.res = res;
//    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}

