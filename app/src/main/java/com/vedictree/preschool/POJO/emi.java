package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class emi {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("emiplan")
    @Expose
    private List<Emiplan> emiplan = null;

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

    public List<Emiplan> getEmiplan() {
        return emiplan;
    }

    public void setEmiplan(List<Emiplan> emiplan) {
        this.emiplan = emiplan;
    }
}
