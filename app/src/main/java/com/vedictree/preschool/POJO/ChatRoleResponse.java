package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ChatRoleResponse {
    @SerializedName("roleid")
    @Expose
    private Integer roleid;
    @SerializedName("rolename")
    @Expose
    private String rolename;
    @SerializedName("scid")
    @Expose
    private Integer scid;
    @SerializedName("scman")
    @Expose
    private String scman;
    @SerializedName("modby")
    @Expose
    private String modby;
    @SerializedName("moddt")
    @Expose
    private String moddt;
    @SerializedName("addby")
    @Expose
    private String addby;
    @SerializedName("adddt")
    @Expose
    private String adddt;
    @SerializedName("chatf")
    @Expose
    private Integer chatf;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Integer getScid() {
        return scid;
    }

    public void setScid(Integer scid) {
        this.scid = scid;
    }

    public String getScman() {
        return scman;
    }

    public void setScman(String scman) {
        this.scman = scman;
    }

    public String getModby() {
        return modby;
    }

    public void setModby(String modby) {
        this.modby = modby;
    }

    public String getModdt() {
        return moddt;
    }

    public void setModdt(String moddt) {
        this.moddt = moddt;
    }

    public String getAddby() {
        return addby;
    }

    public void setAddby(String addby) {
        this.addby = addby;
    }

    public String getAdddt() {
        return adddt;
    }

    public void setAdddt(String adddt) {
        this.adddt = adddt;
    }

    public Integer getChatf() {
        return chatf;
    }

    public void setChatf(Integer chatf) {
        this.chatf = chatf;
    }
}
