package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatHistoryNEwDetail {
    @SerializedName("chid")
    @Expose
    private Integer chid;
    @SerializedName("rlid")
    @Expose
    private Integer rlid;
    @SerializedName("scfrid")
    @Expose
    private Integer scfrid;
    @SerializedName("studid")
    @Expose
    private Integer studid;
    @SerializedName("msgs")
    @Expose
    private String msgs;
    @SerializedName("rdstat")
    @Expose
    private Integer rdstat;
    @SerializedName("chtimg")
    @Expose
    private String chtimg;
    @SerializedName("tof")
    @Expose
    private Integer tof;
    @SerializedName("frf")
    @Expose
    private Integer frf;
    @SerializedName("chtdtt")
    @Expose
    private String chtdtt;
    @SerializedName("studn")
    @Expose
    private String studn;
    @SerializedName("studc")
    @Expose
    private String studc;

    public Integer getChid() {
        return chid;
    }

    public void setChid(Integer chid) {
        this.chid = chid;
    }

    public Integer getRlid() {
        return rlid;
    }

    public void setRlid(Integer rlid) {
        this.rlid = rlid;
    }

    public Integer getScfrid() {
        return scfrid;
    }

    public void setScfrid(Integer scfrid) {
        this.scfrid = scfrid;
    }

    public Integer getStudid() {
        return studid;
    }

    public void setStudid(Integer studid) {
        this.studid = studid;
    }

    public String getMsgs() {
        return msgs;
    }

    public void setMsgs(String msgs) {
        this.msgs = msgs;
    }

    public Integer getRdstat() {
        return rdstat;
    }

    public void setRdstat(Integer rdstat) {
        this.rdstat = rdstat;
    }

    public String getChtimg() {
        return chtimg;
    }

    public void setChtimg(String chtimg) {
        this.chtimg = chtimg;
    }

    public Integer getTof() {
        return tof;
    }

    public void setTof(Integer tof) {
        this.tof = tof;
    }

    public Integer getFrf() {
        return frf;
    }

    public void setFrf(Integer frf) {
        this.frf = frf;
    }

    public String getChtdtt() {
        return chtdtt;
    }

    public void setChtdtt(String chtdtt) {
        this.chtdtt = chtdtt;
    }

    public String getStudn() {
        return studn;
    }

    public void setStudn(String studn) {
        this.studn = studn;
    }

    public String getStudc() {
        return studc;
    }

    public void setStudc(String studc) {
        this.studc = studc;
    }
}
