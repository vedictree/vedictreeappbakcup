package com.vedictree.preschool.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentHistory {
    @SerializedName("logId")
    @Expose
    private String logId;
    @SerializedName("usr_firstname")
    @Expose
    private String usrFirstname;
    @SerializedName("usr_lastname")
    @Expose
    private String usrLastname;
    @SerializedName("usr_email")
    @Expose
    private String usrEmail;
    @SerializedName("usr_mobile_no")
    @Expose
    private String usrMobileNo;
    @SerializedName("payAmount")
    @Expose
    private String payAmount;
    @SerializedName("fk_studId")
    @Expose
    private String fkStudId;
    @SerializedName("paystatus")
    @Expose
    private String paystatus;
    @SerializedName("createDT")
    @Expose
    private String createDT;
    @SerializedName("razorpay_order_id")
    @Expose
    private Object razorpayOrderId;
    @SerializedName("razorpay_payment_id")
    @Expose
    private Object razorpayPaymentId;
    @SerializedName("razorpay_signature")
    @Expose
    private String razorpaySignature;
    @SerializedName("paystatusId")
    @Expose
    private String paystatusId;
    @SerializedName("planId")
    @Expose
    private String planId;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("Receiptpic")
    @Expose
    private String receiptpic;
    @SerializedName("fk_monthId")
    @Expose
    private String fkMonthId;
    @SerializedName("paymentSatusByadmin")
    @Expose
    private String paymentSatusByadmin;
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
    private String refferalCode;
    @SerializedName("NewrefferalCode")
    @Expose
    private String newrefferalCode;
    @SerializedName("oauth_uid")
    @Expose
    private Object oauthUid;
    @SerializedName("fk_fees_remark")
    @Expose
    private String fkFeesRemark;
    @SerializedName("usr_landline")
    @Expose
    private String usrLandline;
    @SerializedName("user_abt_info")
    @Expose
    private Object userAbtInfo;
    @SerializedName("usr_add1")
    @Expose
    private String usrAdd1;
    @SerializedName("usr_add2")
    @Expose
    private String usrAdd2;
    @SerializedName("usr_country")
    @Expose
    private String usrCountry;
    @SerializedName("usr_profile")
    @Expose
    private String usrProfile;
    @SerializedName("studentAltMobile")
    @Expose
    private String studentAltMobile;
    @SerializedName("usr_dob")
    @Expose
    private String usrDob;
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
    private String usrState;
    @SerializedName("usr_city")
    @Expose
    private String usrCity;
    @SerializedName("usr_middlename")
    @Expose
    private String usrMiddlename;
    @SerializedName("unlockdayId")
    @Expose
    private String unlockdayId;
    @SerializedName("unlock_monthId")
    @Expose
    private String unlockMonthId;
    @SerializedName("fbloginStatus")
    @Expose
    private String fbloginStatus;
    @SerializedName("pinNumber")
    @Expose
    private String pinNumber;
    @SerializedName("studentId")
    @Expose
    private String studentId;
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
    private String alternate_email;
    @SerializedName("fk_coursePeriod")
    @Expose
    private String fk_coursePeriod;
    @SerializedName("paymentDate")
    @Expose
    private String paymentDate;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getUsrFirstname() {
        return usrFirstname;
    }

    public void setUsrFirstname(String usrFirstname) {
        this.usrFirstname = usrFirstname;
    }

    public String getUsrLastname() {
        return usrLastname;
    }

    public void setUsrLastname(String usrLastname) {
        this.usrLastname = usrLastname;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrMobileNo() {
        return usrMobileNo;
    }

    public void setUsrMobileNo(String usrMobileNo) {
        this.usrMobileNo = usrMobileNo;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getFkStudId() {
        return fkStudId;
    }

    public void setFkStudId(String fkStudId) {
        this.fkStudId = fkStudId;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getCreateDT() {
        return createDT;
    }

    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }

    public Object getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(Object razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public Object getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(Object razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }

    public String getPaystatusId() {
        return paystatusId;
    }

    public void setPaystatusId(String paystatusId) {
        this.paystatusId = paystatusId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getReceiptpic() {
        return receiptpic;
    }

    public void setReceiptpic(String receiptpic) {
        this.receiptpic = receiptpic;
    }

    public String getFkMonthId() {
        return fkMonthId;
    }

    public void setFkMonthId(String fkMonthId) {
        this.fkMonthId = fkMonthId;
    }

    public String getPaymentSatusByadmin() {
        return paymentSatusByadmin;
    }

    public void setPaymentSatusByadmin(String paymentSatusByadmin) {
        this.paymentSatusByadmin = paymentSatusByadmin;
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

    public String getRefferalCode() {
        return refferalCode;
    }

    public void setRefferalCode(String refferalCode) {
        this.refferalCode = refferalCode;
    }

    public String getNewrefferalCode() {
        return newrefferalCode;
    }

    public void setNewrefferalCode(String newrefferalCode) {
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

    public String getUsrLandline() {
        return usrLandline;
    }

    public void setUsrLandline(String usrLandline) {
        this.usrLandline = usrLandline;
    }

    public Object getUserAbtInfo() {
        return userAbtInfo;
    }

    public void setUserAbtInfo(Object userAbtInfo) {
        this.userAbtInfo = userAbtInfo;
    }

    public String getUsrAdd1() {
        return usrAdd1;
    }

    public void setUsrAdd1(String usrAdd1) {
        this.usrAdd1 = usrAdd1;
    }

    public String getUsrAdd2() {
        return usrAdd2;
    }

    public void setUsrAdd2(String usrAdd2) {
        this.usrAdd2 = usrAdd2;
    }

    public String getUsrCountry() {
        return usrCountry;
    }

    public void setUsrCountry(String usrCountry) {
        this.usrCountry = usrCountry;
    }

    public String getUsrProfile() {
        return usrProfile;
    }

    public void setUsrProfile(String usrProfile) {
        this.usrProfile = usrProfile;
    }

    public String getStudentAltMobile() {
        return studentAltMobile;
    }

    public void setStudentAltMobile(String studentAltMobile) {
        this.studentAltMobile = studentAltMobile;
    }

    public String getUsrDob() {
        return usrDob;
    }

    public void setUsrDob(String usrDob) {
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

    public String getUsrState() {
        return usrState;
    }

    public void setUsrState(String usrState) {
        this.usrState = usrState;
    }

    public String getUsrCity() {
        return usrCity;
    }

    public void setUsrCity(String usrCity) {
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

    public String getFbloginStatus() {
        return fbloginStatus;
    }

    public void setFbloginStatus(String fbloginStatus) {
        this.fbloginStatus = fbloginStatus;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
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
    public String getAlternate_email() {
        return alternate_email;
    }

    public void setAlternate_email(String alternate_email) {
        this.alternate_email = alternate_email;
    }

    public String getFk_coursePeriod() {
        return fk_coursePeriod;
    }

    public void setFk_coursePeriod(String fk_coursePeriod) {
        this.fk_coursePeriod = fk_coursePeriod;
    }
    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
