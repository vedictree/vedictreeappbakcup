package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_live_session_detail {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fkclassId")
    @Expose
    private String fkclassId;
    @SerializedName("fk_batchId")
    @Expose
    private String fkBatchId;
    @SerializedName("fk_planId")
    @Expose
    private String fkPlanId;
    @SerializedName("microsoft_link")
    @Expose
    private String microsoftLink;
    @SerializedName("subjectid")
    @Expose
    private String subjectid;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("duration")
    @Expose
    private Object duration;
    @SerializedName("meeting_id")
    @Expose
    private Object meetingId;
    @SerializedName("host_id")
    @Expose
    private Object hostId;
    @SerializedName("uuid")
    @Expose
    private Object uuid;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFkclassId() {
        return fkclassId;
    }

    public void setFkclassId(String fkclassId) {
        this.fkclassId = fkclassId;
    }

    public String getFkBatchId() {
        return fkBatchId;
    }

    public void setFkBatchId(String fkBatchId) {
        this.fkBatchId = fkBatchId;
    }

    public String getFkPlanId() {
        return fkPlanId;
    }

    public void setFkPlanId(String fkPlanId) {
        this.fkPlanId = fkPlanId;
    }

    public String getMicrosoftLink() {
        return microsoftLink;
    }

    public void setMicrosoftLink(String microsoftLink) {
        this.microsoftLink = microsoftLink;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Object getDuration() {
        return duration;
    }

    public void setDuration(Object duration) {
        this.duration = duration;
    }

    public Object getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Object meetingId) {
        this.meetingId = meetingId;
    }

    public Object getHostId() {
        return hostId;
    }

    public void setHostId(Object hostId) {
        this.hostId = hostId;
    }

    public Object getUuid() {
        return uuid;
    }

    public void setUuid(Object uuid) {
        this.uuid = uuid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
