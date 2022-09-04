package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialLogin {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("SocialloginData")
    @Expose
    private List<SocialloginDatum> socialloginData;

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

    public List<SocialloginDatum> getSocialloginData() {
        return socialloginData;
    }

    public void setSocialloginData(List<SocialloginDatum> socialloginData) {
        this.socialloginData = socialloginData;
    }

}
