package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestPojo {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("getfeesdata")
    @Expose
    private List<TestPojoResponse> getfeesdata = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TestPojoResponse> getGetfeesdata() {
        return getfeesdata;
    }

    public void setGetfeesdata(List<TestPojoResponse> getfeesdata) {
        this.getfeesdata = getfeesdata;
    }
}
