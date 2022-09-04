package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatHistoryDetail {
    @SerializedName("techcId")
    @Expose
    private String techcId;
    @SerializedName("fk_teachId")
    @Expose
    private String fkTeachId;
    @SerializedName("fk_studId")
    @Expose
    private String fkStudId;
    @SerializedName("chatMsg")
    @Expose
    private String chatMsg;
    @SerializedName("currentDate")
    @Expose
    private String currentDate;
    @SerializedName("chat_messages_status")
    @Expose
    private String chatMessagesStatus;
    @SerializedName("chatimgup")
    @Expose
    private String chatimgup;
    @SerializedName("readMsg")
    @Expose
    private String readMsg;
    @SerializedName("stud_planid")
    @Expose
    private String studPlanid;
    @SerializedName("group_plan_id")
    @Expose
    private String groupPlanId;

    public String getTechcId() {
        return techcId;
    }

    public void setTechcId(String techcId) {
        this.techcId = techcId;
    }

    public String getFkTeachId() {
        return fkTeachId;
    }

    public void setFkTeachId(String fkTeachId) {
        this.fkTeachId = fkTeachId;
    }

    public String getFkStudId() {
        return fkStudId;
    }

    public void setFkStudId(String fkStudId) {
        this.fkStudId = fkStudId;
    }

    public String getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(String chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getChatMessagesStatus() {
        return chatMessagesStatus;
    }

    public void setChatMessagesStatus(String chatMessagesStatus) {
        this.chatMessagesStatus = chatMessagesStatus;
    }

    public String getChatimgup() {
        return chatimgup;
    }

    public void setChatimgup(String chatimgup) {
        this.chatimgup = chatimgup;
    }

    public String getReadMsg() {
        return readMsg;
    }

    public void setReadMsg(String readMsg) {
        this.readMsg = readMsg;
    }

    public String getStudPlanid() {
        return studPlanid;
    }

    public void setStudPlanid(String studPlanid) {
        this.studPlanid = studPlanid;
    }

    public String getGroupPlanId() {
        return groupPlanId;
    }

    public void setGroupPlanId(String groupPlanId) {
        this.groupPlanId = groupPlanId;
    }
}
