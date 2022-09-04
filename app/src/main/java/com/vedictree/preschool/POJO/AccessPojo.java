package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessPojo {
    @SerializedName("error")
    @Expose
    private AccessPojoError error;

    public AccessPojoError getError() {
        return error;
    }

    public void setError(AccessPojoError error) {
        this.error = error;
    }

}
