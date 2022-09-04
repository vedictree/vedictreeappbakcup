package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherAssignResponse {
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
    @SerializedName("studId")
    @Expose
    private String studId;
    @SerializedName("studentEmail")
    @Expose
    private String studentEmail;
    @SerializedName("studentMobile")
    @Expose
    private String studentMobile;
    @SerializedName("studentPass")
    @Expose
    private String studentPass;
    @SerializedName("studentClass")
    @Expose
    private String studentClass;
    @SerializedName("studentGender")
    @Expose
    private String studentGender;
    @SerializedName("studentName")
    @Expose
    private String studentName;
    @SerializedName("studentStatus")
    @Expose
    private String studentStatus;
    @SerializedName("logstatus")
    @Expose
    private String logstatus;
    @SerializedName("OTP")
    @Expose
    private String otp;
    @SerializedName("refferalCode")
    @Expose
    private Object refferalCode;
    @SerializedName("NewrefferalCode")
    @Expose
    private Object newrefferalCode;
    @SerializedName("oauth_uid")
    @Expose
    private Object oauthUid;
    @SerializedName("fk_fees_remark")
    @Expose
    private String fkFeesRemark;
    @SerializedName("usr_landline")
    @Expose
    private Object usrLandline;
    @SerializedName("usr_firstname")
    @Expose
    private Object usrFirstname;
    @SerializedName("usr_lastname")
    @Expose
    private Object usrLastname;
    @SerializedName("user_abt_info")
    @Expose
    private Object userAbtInfo;
    @SerializedName("usr_add1")
    @Expose
    private Object usrAdd1;
    @SerializedName("usr_add2")
    @Expose
    private Object usrAdd2;
    @SerializedName("usr_country")
    @Expose
    private Object usrCountry;
    @SerializedName("usr_profile")
    @Expose
    private Object usrProfile;
    @SerializedName("studentAltMobile")
    @Expose
    private Object studentAltMobile;
    @SerializedName("usr_dob")
    @Expose
    private Object usrDob;
    @SerializedName("adminRole")
    @Expose
    private String adminRole;
    @SerializedName("adharno")
    @Expose
    private String adharno;
    @SerializedName("studentReligion")
    @Expose
    private String studentReligion;
    @SerializedName("studentCaste")
    @Expose
    private String studentCaste;
    @SerializedName("studentSubcast")
    @Expose
    private String studentSubcast;
    @SerializedName("preschool")
    @Expose
    private String preschool;
    @SerializedName("mothertoungue")
    @Expose
    private String mothertoungue;
    @SerializedName("usr_state")
    @Expose
    private Object usrState;
    @SerializedName("usr_city")
    @Expose
    private Object usrCity;
    @SerializedName("usr_middlename")
    @Expose
    private String usrMiddlename;
    @SerializedName("unlockdayId")
    @Expose
    private String unlockdayId;
    @SerializedName("unlock_monthId")
    @Expose
    private String unlockMonthId;
    @SerializedName("lock_update_date")
    @Expose
    private String lockUpdateDate;
    @SerializedName("fbloginStatus")
    @Expose
    private Object fbloginStatus;
    @SerializedName("pinNumber")
    @Expose
    private String pinNumber;
    @SerializedName("studentId")
    @Expose
    private Object studentId;
    @SerializedName("promoCode")
    @Expose
    private String promoCode;
    @SerializedName("freemonthDT")
    @Expose
    private String freemonthDT;
    @SerializedName("promoCodeExp")
    @Expose
    private String promoCodeExp;
    @SerializedName("dob_certificate")
    @Expose
    private String dobCertificate;
    @SerializedName("mobwebStatus")
    @Expose
    private String mobwebStatus;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("alternate_email")
    @Expose
    private String alternateEmail;
    @SerializedName("teacherId")
    @Expose
    private String teacherId;
    @SerializedName("teacherEmail")
    @Expose
    private String teacherEmail;
    @SerializedName("teacherMobile")
    @Expose
    private String teacherMobile;
    @SerializedName("teacherPass")
    @Expose
    private String teacherPass;
    @SerializedName("teacherClass")
    @Expose
    private String teacherClass;
    @SerializedName("teacherGender")
    @Expose
    private String teacherGender;
    @SerializedName("teacherName")
    @Expose
    private String teacherName;
    @SerializedName("teacherStatus")
    @Expose
    private String teacherStatus;
    @SerializedName("adhar_name")
    @Expose
    private String adharName;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("caste")
    @Expose
    private String caste;
    @SerializedName("sub_caste")
    @Expose
    private String subCaste;
    @SerializedName("pre_school")
    @Expose
    private String preSchool;
    @SerializedName("mother_tounge")
    @Expose
    private String motherTounge;
    @SerializedName("teacherStatusonline")
    @Expose
    private String teacherStatusonline;

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

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public String getStudentPass() {
        return studentPass;
    }

    public void setStudentPass(String studentPass) {
        this.studentPass = studentPass;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getLogstatus() {
        return logstatus;
    }

    public void setLogstatus(String logstatus) {
        this.logstatus = logstatus;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Object getRefferalCode() {
        return refferalCode;
    }

    public void setRefferalCode(Object refferalCode) {
        this.refferalCode = refferalCode;
    }

    public Object getNewrefferalCode() {
        return newrefferalCode;
    }

    public void setNewrefferalCode(Object newrefferalCode) {
        this.newrefferalCode = newrefferalCode;
    }

    public Object getOauthUid() {
        return oauthUid;
    }

    public void setOauthUid(Object oauthUid) {
        this.oauthUid = oauthUid;
    }

    public String getFkFeesRemark() {
        return fkFeesRemark;
    }

    public void setFkFeesRemark(String fkFeesRemark) {
        this.fkFeesRemark = fkFeesRemark;
    }

    public Object getUsrLandline() {
        return usrLandline;
    }

    public void setUsrLandline(Object usrLandline) {
        this.usrLandline = usrLandline;
    }

    public Object getUsrFirstname() {
        return usrFirstname;
    }

    public void setUsrFirstname(Object usrFirstname) {
        this.usrFirstname = usrFirstname;
    }

    public Object getUsrLastname() {
        return usrLastname;
    }

    public void setUsrLastname(Object usrLastname) {
        this.usrLastname = usrLastname;
    }

    public Object getUserAbtInfo() {
        return userAbtInfo;
    }

    public void setUserAbtInfo(Object userAbtInfo) {
        this.userAbtInfo = userAbtInfo;
    }

    public Object getUsrAdd1() {
        return usrAdd1;
    }

    public void setUsrAdd1(Object usrAdd1) {
        this.usrAdd1 = usrAdd1;
    }

    public Object getUsrAdd2() {
        return usrAdd2;
    }

    public void setUsrAdd2(Object usrAdd2) {
        this.usrAdd2 = usrAdd2;
    }

    public Object getUsrCountry() {
        return usrCountry;
    }

    public void setUsrCountry(Object usrCountry) {
        this.usrCountry = usrCountry;
    }

    public Object getUsrProfile() {
        return usrProfile;
    }

    public void setUsrProfile(Object usrProfile) {
        this.usrProfile = usrProfile;
    }

    public Object getStudentAltMobile() {
        return studentAltMobile;
    }

    public void setStudentAltMobile(Object studentAltMobile) {
        this.studentAltMobile = studentAltMobile;
    }

    public Object getUsrDob() {
        return usrDob;
    }

    public void setUsrDob(Object usrDob) {
        this.usrDob = usrDob;
    }

    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    public String getAdharno() {
        return adharno;
    }

    public void setAdharno(String adharno) {
        this.adharno = adharno;
    }

    public String getStudentReligion() {
        return studentReligion;
    }

    public void setStudentReligion(String studentReligion) {
        this.studentReligion = studentReligion;
    }

    public String getStudentCaste() {
        return studentCaste;
    }

    public void setStudentCaste(String studentCaste) {
        this.studentCaste = studentCaste;
    }

    public String getStudentSubcast() {
        return studentSubcast;
    }

    public void setStudentSubcast(String studentSubcast) {
        this.studentSubcast = studentSubcast;
    }

    public String getPreschool() {
        return preschool;
    }

    public void setPreschool(String preschool) {
        this.preschool = preschool;
    }

    public String getMothertoungue() {
        return mothertoungue;
    }

    public void setMothertoungue(String mothertoungue) {
        this.mothertoungue = mothertoungue;
    }

    public Object getUsrState() {
        return usrState;
    }

    public void setUsrState(Object usrState) {
        this.usrState = usrState;
    }

    public Object getUsrCity() {
        return usrCity;
    }

    public void setUsrCity(Object usrCity) {
        this.usrCity = usrCity;
    }

    public String getUsrMiddlename() {
        return usrMiddlename;
    }

    public void setUsrMiddlename(String usrMiddlename) {
        this.usrMiddlename = usrMiddlename;
    }

    public String getUnlockdayId() {
        return unlockdayId;
    }

    public void setUnlockdayId(String unlockdayId) {
        this.unlockdayId = unlockdayId;
    }

    public String getUnlockMonthId() {
        return unlockMonthId;
    }

    public void setUnlockMonthId(String unlockMonthId) {
        this.unlockMonthId = unlockMonthId;
    }

    public String getLockUpdateDate() {
        return lockUpdateDate;
    }

    public void setLockUpdateDate(String lockUpdateDate) {
        this.lockUpdateDate = lockUpdateDate;
    }

    public Object getFbloginStatus() {
        return fbloginStatus;
    }

    public void setFbloginStatus(Object fbloginStatus) {
        this.fbloginStatus = fbloginStatus;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public Object getStudentId() {
        return studentId;
    }

    public void setStudentId(Object studentId) {
        this.studentId = studentId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getFreemonthDT() {
        return freemonthDT;
    }

    public void setFreemonthDT(String freemonthDT) {
        this.freemonthDT = freemonthDT;
    }

    public String getPromoCodeExp() {
        return promoCodeExp;
    }

    public void setPromoCodeExp(String promoCodeExp) {
        this.promoCodeExp = promoCodeExp;
    }

    public String getDobCertificate() {
        return dobCertificate;
    }

    public void setDobCertificate(String dobCertificate) {
        this.dobCertificate = dobCertificate;
    }

    public String getMobwebStatus() {
        return mobwebStatus;
    }

    public void setMobwebStatus(String mobwebStatus) {
        this.mobwebStatus = mobwebStatus;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherMobile() {
        return teacherMobile;
    }

    public void setTeacherMobile(String teacherMobile) {
        this.teacherMobile = teacherMobile;
    }

    public String getTeacherPass() {
        return teacherPass;
    }

    public void setTeacherPass(String teacherPass) {
        this.teacherPass = teacherPass;
    }

    public String getTeacherClass() {
        return teacherClass;
    }

    public void setTeacherClass(String teacherClass) {
        this.teacherClass = teacherClass;
    }

    public String getTeacherGender() {
        return teacherGender;
    }

    public void setTeacherGender(String teacherGender) {
        this.teacherGender = teacherGender;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherStatus() {
        return teacherStatus;
    }

    public void setTeacherStatus(String teacherStatus) {
        this.teacherStatus = teacherStatus;
    }
    public String getTeacherStatusonline() {
        return teacherStatusonline;
    }

    public void setTeacherStatusonline(String teacherStatusonline) {
        this.teacherStatusonline = teacherStatusonline;
    }

    public String getAdharName() {
        return adharName;
    }

    public void setAdharName(String adharName) {
        this.adharName = adharName;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getSubCaste() {
        return subCaste;
    }

    public void setSubCaste(String subCaste) {
        this.subCaste = subCaste;
    }

    public String getPreSchool() {
        return preSchool;
    }

    public void setPreSchool(String preSchool) {
        this.preSchool = preSchool;
    }

    public String getMotherTounge() {
        return motherTounge;
    }

    public void setMotherTounge(String motherTounge) {
        this.motherTounge = motherTounge;
    }

}
