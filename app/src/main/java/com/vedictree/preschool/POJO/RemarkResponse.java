package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemarkResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fk_homework_id")
    @Expose
    private String fkHomeworkId;
    @SerializedName("allocated_file")
    @Expose
    private String allocatedFile;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("fk_feesId")
    @Expose
    private String fkFeesId;
    @SerializedName("fk_class_id")
    @Expose
    private String fkClassId;
    @SerializedName("home_title")
    @Expose
    private String homeTitle;
    @SerializedName("fk_studentId")
    @Expose
    private String fkStudentId;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFkHomeworkId() {
        return fkHomeworkId;
    }

    public void setFkHomeworkId(String fkHomeworkId) {
        this.fkHomeworkId = fkHomeworkId;
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

    public String getFkFeesId() {
        return fkFeesId;
    }

    public void setFkFeesId(String fkFeesId) {
        this.fkFeesId = fkFeesId;
    }

    public String getFkClassId() {
        return fkClassId;
    }

    public void setFkClassId(String fkClassId) {
        this.fkClassId = fkClassId;
    }

    public String getHomeTitle() {
        return homeTitle;
    }

    public void setHomeTitle(String homeTitle) {
        this.homeTitle = homeTitle;
    }

    public String getFkStudentId() {
        return fkStudentId;
    }

    public void setFkStudentId(String fkStudentId) {
        this.fkStudentId = fkStudentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
