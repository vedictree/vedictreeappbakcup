package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Demo_video {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("res")
    @Expose
    private List<Demo_video_detail> res;
    @SerializedName("code")
    @Expose
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Demo_video_detail> getRes() {
        return res;
    }

    public void setRes(List<Demo_video_detail> res) {
        this.res = res;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
