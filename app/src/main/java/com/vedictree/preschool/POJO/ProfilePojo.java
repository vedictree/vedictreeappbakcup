package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePojo {

    @SerializedName("error")
    @Expose
    private ProfileErrorObject error;

    public ProfileErrorObject getError() {
        return error;
    }

    public void setError(ProfileErrorObject error) {
        this.error = error;
    }
}
