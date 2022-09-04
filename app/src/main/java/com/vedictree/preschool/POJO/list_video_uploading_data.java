package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class list_video_uploading_data {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("list_video_uploading_data")
    @Expose
    private List<list_video_uploading_data_detail> listVideoUploadingData = null;

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

    public List<list_video_uploading_data_detail> getListVideoUploadingData() {
        return listVideoUploadingData;
    }

    public void setListVideoUploadingData(List<list_video_uploading_data_detail> listVideoUploadingData) {
        this.listVideoUploadingData = listVideoUploadingData;
    }
}
