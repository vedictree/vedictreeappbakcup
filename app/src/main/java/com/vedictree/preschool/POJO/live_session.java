package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class live_session {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("getfeesdata")
    @Expose
    private List<Get_live_session_detail> getfeesdata;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Get_live_session_detail> getGetfeesdata() {
        return getfeesdata;
    }

    public void setGetfeesdata(List<Get_live_session_detail> getfeesdata) {
        this.getfeesdata = getfeesdata;
    }
}
