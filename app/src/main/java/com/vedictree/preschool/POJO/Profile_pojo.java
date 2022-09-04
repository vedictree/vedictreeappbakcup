package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile_pojo {
    @SerializedName("error")
    @Expose
    private Profile_error error;
    @SerializedName("userinfo")
    @Expose
    private List<Profile_student> userinfo = null;
    @SerializedName("userinfo_father")
    @Expose
    private List<Profile_Father> userinfoFather = null;
    @SerializedName("userinfo_mother")
    @Expose
    private List<Profile_Mother> userinfoMother = null;

    public Profile_error getError() {
        return error;
    }

    public void setError(Profile_error error) {
        this.error = error;
    }

    public List<Profile_student> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(List<Profile_student> userinfo) {
        this.userinfo = userinfo;
    }

    public List<Profile_Father> getUserinfoFather() {
        return userinfoFather;
    }

    public void setUserinfoFather(List<Profile_Father> userinfoFather) {
        this.userinfoFather = userinfoFather;
    }

    public List<Profile_Mother> getUserinfoMother() {
        return userinfoMother;
    }

    public void setUserinfoMother(List<Profile_Mother> userinfoMother) {
        this.userinfoMother = userinfoMother;
    }

}
