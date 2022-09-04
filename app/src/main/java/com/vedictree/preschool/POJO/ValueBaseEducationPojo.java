package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ValueBaseEducationPojo {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("res")
    @Expose
    private List<ValueBaseEducationPojoResponse> res = null;
    @SerializedName("code")
    @Expose
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ValueBaseEducationPojoResponse> getRes() {
        return res;
    }

    public void setRes(List<ValueBaseEducationPojoResponse> res) {
        this.res = res;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
