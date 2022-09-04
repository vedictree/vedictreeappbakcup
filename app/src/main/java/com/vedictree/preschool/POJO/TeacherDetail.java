package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherDetail {
    @SerializedName("techid")
    @Expose
    private String techid;
    @SerializedName("fk_studId")
    @Expose
    private String fkStudId;
    @SerializedName("fk_classId")
    @Expose
    private String fkClassId;
    @SerializedName("fk_teachId")
    @Expose
    private String fkTeachId;
    @SerializedName("fk_feesId")
    @Expose
    private String fkFeesId;
    @SerializedName("fk_date")
    @Expose
    private String fkDate;
    @SerializedName("createDT")
    @Expose
    private String createDT;
    @SerializedName("fk_batchId")
    @Expose
    private String fkBatchId;

    public String getTechid() {
        return techid;
    }

    public void setTechid(String techid) {
        this.techid = techid;
    }

    public String getFkStudId() {
        return fkStudId;
    }

    public void setFkStudId(String fkStudId) {
        this.fkStudId = fkStudId;
    }

    public String getFkClassId() {
        return fkClassId;
    }

    public void setFkClassId(String fkClassId) {
        this.fkClassId = fkClassId;
    }

    public String getFkTeachId() {
        return fkTeachId;
    }

    public void setFkTeachId(String fkTeachId) {
        this.fkTeachId = fkTeachId;
    }

    public String getFkFeesId() {
        return fkFeesId;
    }

    public void setFkFeesId(String fkFeesId) {
        this.fkFeesId = fkFeesId;
    }

    public String getFkDate() {
        return fkDate;
    }

    public void setFkDate(String fkDate) {
        this.fkDate = fkDate;
    }

    public String getCreateDT() {
        return createDT;
    }

    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }

    public String getFkBatchId() {
        return fkBatchId;
    }

    public void setFkBatchId(String fkBatchId) {
        this.fkBatchId = fkBatchId;
    }
}
