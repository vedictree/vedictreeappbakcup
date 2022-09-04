package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitTestPojo {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("gethomework_data")
    @Expose
    private Integer gethomeworkData;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getGethomeworkData() {
        return gethomeworkData;
    }

    public void setGethomeworkData(Integer gethomeworkData) {
        this.gethomeworkData = gethomeworkData;
    }
}
