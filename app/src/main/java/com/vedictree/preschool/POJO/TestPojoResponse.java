package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestPojoResponse {
    @SerializedName("homework_id")
    @Expose
    private String homeworkId;
    @SerializedName("home_title")
    @Expose
    private String homeTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("allocated_file")
    @Expose
    private String allocatedFile;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("fk_feesId")
    @Expose
    private String fkFeesId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("admin_approv_status")
    @Expose
    private String adminApprovStatus;
    @SerializedName("notification_status")
    @Expose
    private String notification_status;
    @SerializedName("fk_studentId")
    @Expose
    private  String fk_studentId;


    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getHomeTitle() {
        return homeTitle;
    }

    public void setHomeTitle(String homeTitle) {
        this.homeTitle = homeTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllocatedFile() {
        return allocatedFile;
    }

    public void setAllocatedFile(String allocatedFile) {
        this.allocatedFile = allocatedFile;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFkFeesId() {
        return fkFeesId;
    }

    public void setFkFeesId(String fkFeesId) {
        this.fkFeesId = fkFeesId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getAdminApprovStatus() {
        return adminApprovStatus;
    }

    public void setAdminApprovStatus(String adminApprovStatus) {
        this.adminApprovStatus = adminApprovStatus;
    }
    public String getNotification_status() {
        return notification_status;
    }

    public void setNotification_status(String notification_status) {
        this.notification_status = notification_status;
    }
    public String getFk_studentId() {
        return fk_studentId;
    }

    public void setFk_studentId(String fk_studentId) {
        this.fk_studentId = fk_studentId;
    }
}
