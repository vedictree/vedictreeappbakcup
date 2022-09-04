package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatHistoryNEw {

    @SerializedName("data")
    @Expose
    private List<ChatHistoryNEwDetail> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ChatHistoryNEwDetail> getData() {
        return data;
    }

    public void setData(List<ChatHistoryNEwDetail> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
