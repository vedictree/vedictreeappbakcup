package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRole {
    @SerializedName("data")
    @Expose
    private List<ChatRoleResponse> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ChatRoleResponse> getData() {
        return data;
    }

    public void setData(List<ChatRoleResponse> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
