package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoCodePojo {
    @SerializedName("error")
    @Expose
    private PromoCodeError error;

    public PromoCodeError getError() {
        return error;
    }

    public void setError(PromoCodeError error) {
        this.error = error;
    }

}
