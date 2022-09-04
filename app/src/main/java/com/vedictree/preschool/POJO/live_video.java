package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class live_video {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("get_data_day_month_classid")
    @Expose
    private List<live_video_day_session_of> getDataDayMonthClassid = null;
    @SerializedName("error")
    @Expose
    private errorDescription error;

    public errorDescription getError() {
        return error;
    }

    public void setError(errorDescription error) {
        this.error = error;
    }

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

    public List<live_video_day_session_of> getGetDataDayMonthClassid() {
        return getDataDayMonthClassid;
    }

    public void setGetDataDayMonthClassid(List<live_video_day_session_of> getDataDayMonthClassid) {
        this.getDataDayMonthClassid = getDataDayMonthClassid;
    }


}
