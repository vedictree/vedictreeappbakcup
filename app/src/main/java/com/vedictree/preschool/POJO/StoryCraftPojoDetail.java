package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoryCraftPojoDetail {
    @SerializedName("craftId")
    @Expose
    private String craftId;
    @SerializedName("craftbanner")
    @Expose
    private  String craftbanner;
    @SerializedName("youtubelink")
    @Expose
    private String youtubelink;
    @SerializedName("vimeoId")
    @Expose
    private String vimeoId;
    @SerializedName("storyflag")
    @Expose
    private String storyflag;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("storyTitle")
    @Expose
    private String storyTitle;


    public String getCraftId() {
        return craftId;
    }

    public void setCraftId(String craftId) {
        this.craftId = craftId;
    }

    public String getCraftbanner() {
        return craftbanner;
    }

    public void setCraftbanner(String craftbanner) {
        this.craftbanner = craftbanner;

    }
    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }

    public String getVimeoId() {
        return vimeoId;
    }

    public void setVimeoId(String vimeoId) {
        this.vimeoId = vimeoId;
    }

    public String getStoryflag() {
        return storyflag;
    }

    public void setStoryflag(String storyflag) {
        this.storyflag = storyflag;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

}
