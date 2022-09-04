package com.vedictree.preschool.Utils;

import com.google.gson.JsonObject;
import com.vedictree.preschool.POJO.AccessPojo;
import com.vedictree.preschool.POJO.AccessPreviousMonth;
import com.vedictree.preschool.POJO.CalenderPojo;
import com.vedictree.preschool.POJO.CalenderUpdate;
import com.vedictree.preschool.POJO.ChatHistory;
import com.vedictree.preschool.POJO.ChatHistoryNEw;
import com.vedictree.preschool.POJO.ChatPojo;
import com.vedictree.preschool.POJO.ChatRole;
import com.vedictree.preschool.POJO.Course_unlock_month;
import com.vedictree.preschool.POJO.Demo_video;
import com.vedictree.preschool.POJO.EmailCheck;
import com.vedictree.preschool.POJO.Forgot_password;
import com.vedictree.preschool.POJO.Homework_notification;
import com.vedictree.preschool.POJO.InformationPojo;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.MorningSession;
import com.vedictree.preschool.POJO.Notice;
import com.vedictree.preschool.POJO.ProfilePojo;
import com.vedictree.preschool.POJO.Profile_pojo;
import com.vedictree.preschool.POJO.Progress_report;
import com.vedictree.preschool.POJO.PromoCodePojo;
import com.vedictree.preschool.POJO.Receipt_poj;
import com.vedictree.preschool.POJO.Register;
import com.vedictree.preschool.POJO.RemarkPojo;
import com.vedictree.preschool.POJO.SocialLogin;
import com.vedictree.preschool.POJO.LoginPojo;

import com.vedictree.preschool.POJO.StatesAll;
import com.vedictree.preschool.POJO.StoryCraftPojo;
import com.vedictree.preschool.POJO.SubmitTestPojo;
import com.vedictree.preschool.POJO.TeacherAssign;
import com.vedictree.preschool.POJO.TeacherModule;
import com.vedictree.preschool.POJO.TestPojo;
import com.vedictree.preschool.POJO.UploadChatattachment;
import com.vedictree.preschool.POJO.UserHistory;
import com.vedictree.preschool.POJO.ValueBaseEducationPojo;
import com.vedictree.preschool.POJO.WeeklyData;
import com.vedictree.preschool.POJO.emi;
import com.vedictree.preschool.POJO.list_video_uploading_data;
import com.vedictree.preschool.POJO.live_session;
import com.vedictree.preschool.POJO.live_video;
import com.vedictree.preschool.POJO.parentPinPojo;
import com.vedictree.preschool.POJO.verify_otp;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface APIInterface {

    @FormUrlEncoded
    @POST("student/register")
    Call<Register> addUser(
            @Field("studentName") String studentName,
            @Field("studentEmail") String studentEmail,
            @Field("studentGender") String studentGender,
            @Field("studentClass") String studentClass,
            @Field("studentMobile") String studentMobile,
            @Field("studentPass") String studentPassword,
            @Field("studentCPass") String studentConfirmPassword,
            @Field("usr_city") String usr_city,
            @Field("age") String age,
            @Field("onlyapplogin") String onlyapplogin);

    @FormUrlEncoded
    @POST("student/register/verifyotp")
    Call<verify_otp> verifyOtp(
            @Field("studentMobile") String studentMobile,
            @Field("otp") String otp);


    @Multipart
    @POST("student/login")
    Call<LoginPojo> userLogin(
            @Part("studentMobile")RequestBody studentEmail,
            @Part("studentPass")RequestBody studentPass);

    @FormUrlEncoded
    @POST("student/Sociallogin")
    Call<SocialLogin> socialLogin(
            @Field("studentEmail")String studentEmail);

    @FormUrlEncoded
    @POST("student/Socialreg")
    Call<SocialLogin> socialRegister(
            @Field("studentEmail")String studentEmail,
            @Field("studentName")String studentName,
            @Field("studentMobile")String studentMobile,
            @Field("studentClass")String studentClass,
            @Field("studentGender")String studentGender,
            @Field("studentPass")String studentPass,
            @Field("usr_city") String usr_city,
            @Field("age") String age);

    @FormUrlEncoded
    @POST("student/forgetpass")
    Call<Register> sendOtp(
            @Field("studentMobile")String studentMobile);

    @FormUrlEncoded
    @POST("student/Get_day_wise_data/morning")
    Call<MorningSession> getMorningSession(
            @Field("studId") String studId,
            @Field("monthId") String monthId,
            @Field("dayId") String dayId,
            @Field("fk_classId") String fk_classId
    );

    @FormUrlEncoded
    @POST("student/Get_day_wise_data/curricular")
    Call<MorningSession> getCurricularSession(
            @Field("studId") String studId,
            @Field("monthId") String monthId,
            @Field("dayId") String dayId,
            @Field("fk_classId") String fk_classId
    );

    @FormUrlEncoded
    @POST("student/Get_day_wise_data/ecurricular")
    Call<MorningSession> getExtraCurricularSession(
            @Field("studId") String studId,
            @Field("monthId") String monthId,
            @Field("dayId") String dayId,
            @Field("fk_classId") String fk_classId
    );

    @FormUrlEncoded
    @POST("student/ChecklogId")
    Call<Login_token> checkLogId(
            @Field("logRandomId")String logRandomId);


    @FormUrlEncoded
    @POST("student/forgetpass/updatepass")
    Call<Forgot_password> resetPassword(
            @Field("otp")String otp,
            @Field("newpass")String newpass,
            @Field("studentMobile")String studentMobile
    );

    @FormUrlEncoded
    @POST("student/Forgetpass/updatemobpass")
    Call<Register> changePassword(
            @Field("newpass")String newpass,
            @Field("studId")String studId
    );
    @FormUrlEncoded
    @POST("student/Setpin")
    Call<Register> setParentPin(
            @Field("pinNumber")String pinNumber,
            @Field("studId")String studId
    );

    @FormUrlEncoded
    @POST("student/Homeworknotification")
    Call<Homework_notification> readHomeworkNotification(
            @Field("fk_studentId")String fk_studentId,
            @Field("start_time")String start_time
    );

    @FormUrlEncoded
    @POST("student/dashboard/listvideouploading_by_class")
    Call<list_video_uploading_data> getDashboardData(
            @Field("fk_classId") String fk_classId
    );

    @FormUrlEncoded
    @POST("student/dashboard/get_day_sessions")
    Call<live_video> getDataFromCalender(
            @Field("fk_classId") String fk_classId,
            @Field("fk_dayId") String fk_dayId,
            @Field("fk_monthId") String fk_monthId,
            @Field("studId") String studId
    );

    @Multipart
    @POST("student/dashboard/get_day_sessions")
    Call<live_video> getDataFromCalenderBody(
            @Part("fk_classId") RequestBody fk_classId,
            @Part("fk_dayId") RequestBody fk_dayId,
            @Part("fk_monthId") RequestBody fk_monthId,
            @Part("studId") RequestBody studId
    );


    @POST("student/dashboard/sessions")
    Call<ResponseBody> getAllSession();


    @GET("assets/BACKGROUND.png")
    Call<ResponseBody> getBackImage();

//    @Headers({"Username:rzp_test_qRAor8vDLEokHz","Password:WK6Hyu2e5cT4MyC8zHMVashw"})
//    @POST("orders")
//    Call<OrderCredentials> OrderDetail(@Body OrderCredentials credentials);

    @FormUrlEncoded
    @POST("student/payment")
    Call<verify_otp> submitPayment(
            @Field("usr_firstname") String usr_firstname,
            @Field("usr_lastname") String usr_lastname,
            @Field("usr_email") String usr_email,
            @Field("usr_mobile_no") String usr_mobile_no,
            @Field("payAmount") String payAmount,
            @Field("fk_studId") String fk_studId,
            @Field("razorpay_order_id") String razorpay_order_id,
            @Field("razorpay_payment_id") String razorpay_payment_id,
            @Field("razorpay_signature") String razorpay_signature,
            @Field("planId") String planId,
            @Field("paymentType") String paymentType,
            @Field("paystatus") String paystatus,
            @Field("paystatusId") String paystatusId);

    @FormUrlEncoded
    @POST("student/emi")
    Call<ResponseBody> emiPayment(
            @Field("usr_firstname") String usr_firstname,
            @Field("student_dob") String student_dob,
            @Field("course_id") String course_id,
            @Field("branch_id") String branch_id,
            @Field("requested_amount") String requested_amount,
            @Field("requested_tenure") String requested_tenure,
            @Field("student_reference_no") String student_reference_no,
            @Field("applicant_name") String applicant_name,
            @Field("applicant_gender") String applicant_gender,
            @Field("applicant_dob") String applicant_dob,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("aadhar_no") String aadhar_no,
            @Field("marital_status") String marital_status,
            @Field("pan_no") String pan_no,
            @Field("profession") String profession,
            @Field("employer_name") String employer_name,
            @Field("annual_income") String annual_income,
            @Field("relationship_with_student") String relationship_with_student,
            @Field("instituteId") String instituteId,
            @Field("redirect_enabled") String redirect_enabled,
            @Field("fk_studId") String fk_studId,
            @Field("planId") String planId,
            @Field("paymentType") String paymentType);

    @FormUrlEncoded
    @POST("student/history")
    Call<UserHistory> studentHistory(
            @Field("fk_studId") String  fk_studId);

    @FormUrlEncoded
    @POST("student/Homeworkremark")
    Call<RemarkPojo> homewrokRemark(
            @Field("homework_id") String homework_id);


    @FormUrlEncoded
    @POST("student/Parentpin")
    Call<parentPinPojo> studentParentPin(
            @Field("studId") String  studId);

    @FormUrlEncoded
    @POST("student/pdfgenerator")
    Call<Receipt_poj> paymentReceipt(
            @Field("studId")String studId,
            @Field("logId") String logId);

    @FormUrlEncoded
    @POST("student/Reportcardgenerator")
    Call<Receipt_poj> semOneReportCard(
            @Field("studId")String studId);

    @FormUrlEncoded
    @POST("student/Reportcardsecondgenerator")
    Call<Receipt_poj> semTwoReportCard(
            @Field("studId")String studId);

//    @Part("usr_profile\"; filename=\"myProfile.jpg\" ") RequestBody usr_profile,

    @Multipart
    @POST("student/Updateinfomation")
    Call<Profile_pojo> updateProfile2(
            @Part("usr_mobile_no") RequestBody usr_mobile_no,
            @Part("usr_landline") RequestBody usr_landline,
            @Part("usr_firstname") RequestBody usr_firstname,
            @Part("usr_lastname") RequestBody usr_lastname,
            @Part("user_abt_info") RequestBody user_abt_info,
            @Part("usr_add1") RequestBody usr_add1,
            @Part("usr_add2") RequestBody usr_add2,
            @Part("usr_city") RequestBody usr_city,
            @Part("usr_state") RequestBody usr_state,
            @Part("usr_country") RequestBody usr_country,
            @Part("usr_email") RequestBody usr_email,
            @Part("studId") RequestBody studId,
            @Part("role") RequestBody role,
            @Part("usr_profile\"; filename=\"profilePicture2.jpg\" ") RequestBody usr_profile,
            @Part("adharno") RequestBody adharno,
            @Part("studentReligion") RequestBody studentReligion,
            @Part("studentCaste") RequestBody studentCaste,
            @Part("studentSubcast") RequestBody studentSubcast,
            @Part("preschool") RequestBody preschool,
            @Part("mothertoungue") RequestBody mothertoungue,
            @Part("usr_middlename") RequestBody usr_middlename,
            @Part("dob_certificate\"; filename=\"birthCertificate.jpg\" ") RequestBody dob_certificate,
            @Part("old_user_profile") RequestBody old_user_profile,
            @Part("old_dob_certificate") RequestBody old_dob_certificate,
            @Part("usr_dob") RequestBody usr_dob);

    @Multipart
    @POST("student/Updateinfomation")
    Call<Profile_pojo> updateProfile3(
            @Part("usr_mobile_no") RequestBody usr_mobile_no,
            @Part("usr_landline") RequestBody usr_landline,
            @Part("usr_firstname") RequestBody usr_firstname,
            @Part("usr_lastname") RequestBody usr_lastname,
            @Part("user_abt_info") RequestBody user_abt_info,
            @Part("usr_add1") RequestBody usr_add1,
            @Part("usr_add2") RequestBody usr_add2,
            @Part("usr_city") RequestBody usr_city,
            @Part("usr_state") RequestBody usr_state,
            @Part("usr_country") RequestBody usr_country,
            @Part("usr_email") RequestBody usr_email,
            @Part("studId") RequestBody studId,
            @Part("role") RequestBody role,
            @Part MultipartBody.Part part,
            @Part("adharno") RequestBody adharno,
            @Part("studentReligion") RequestBody studentReligion,
            @Part("studentCaste") RequestBody studentCaste,
            @Part("studentSubcast") RequestBody studentSubcast,
            @Part("preschool") RequestBody preschool,
            @Part("mothertoungue") RequestBody mothertoungue,
            @Part("usr_middlename") RequestBody usr_middlename,
            @Part MultipartBody.Part part2,
            @Part("old_user_profile") RequestBody old_user_profile,
            @Part("old_dob_certificate") RequestBody old_dob_certificate,
            @Part("usr_dob") RequestBody usr_dob);

    @POST("student/emiplan")
    Call<emi> getEmiPlan();

    @FormUrlEncoded
    @POST("student/Get_day_wise_data")
    Call<Demo_video> getDaywiseData(
            @Field("studId") String studId,
            @Field("monthId") String monthId,
            @Field("dayId") String dayId,
            @Field("fk_classId") String fk_classId
    );

    @FormUrlEncoded
    @POST("student/recap")
    Call<Demo_video> getRecapSession(
            @Field("studId") String studId,
            @Field("monthId") String monthId,
            @Field("dayId") String dayId,
            @Field("fk_classId") String fk_classId
    );

    @FormUrlEncoded
    @POST("student/recap/demo")
    Call<Demo_video> getDemoSession(
            @Field("studId") String studId,
            @Field("monthId") String monthId,
            @Field("dayId") String dayId,
            @Field("fk_classId") String fk_classId
    );

    @FormUrlEncoded
    @POST("student/Studentlive")
    Call<live_session> getLiveSession(
            @Field("studentClass") String studentClass,
            @Field("studId") String studId);

    @FormUrlEncoded
    @POST("student/Updatemonth")
    Call<CalenderUpdate> updateDayAndMonth(
            @Field("studId") String studId,
            @Field("unlockdayId") String unlockdayId,
            @Field("unlock_monthId") String unlock_monthId

    );

    @FormUrlEncoded
    @POST("student/Openpredata")
    Call<Course_unlock_month> updateDayAndMonthCourse(
            @Field("studId") String studId,
            @Field("unlockdayId") String unlockdayId,
            @Field("unlock_monthId") String unlock_monthId

    );

    @FormUrlEncoded
    @POST("student/Studentnotice")
    Call<Notice> getNotification(
            @Field("class_id") String class_id
    );

    @FormUrlEncoded
    @POST("student/Studentprevious")
    Call<AccessPreviousMonth> checkPreviousMonth(
            @Field("student_id") String student_id
    );

    @FormUrlEncoded
    @POST("student/Studentinfo")
    Call<InformationPojo> getStudentDetail(
            @Field("studId")String studId);

    @FormUrlEncoded
    @POST("student/Fatherinfo")
    Call<InformationPojo> getFatherDetail(
            @Field("studId")String studId);

    @FormUrlEncoded
    @POST("student/Motherinfo")
    Call<InformationPojo> getMotherDetail(
            @Field("studId")String studId);

    @FormUrlEncoded
    @POST("student/Videotrack")
    Call<ResponseBody> getTracking(
            @Field("durations")Float durations,
            @Field("secondss")Float secondss,
            @Field("videoId")String videoId,
            @Field("monthId")String monthId,
            @Field("dayId")String dayId,
            @Field("studId")String studId,
            @Field("fk_sessionId") String fk_sessionId,
            @Field("fk_classId")String fk_classId);

    @FormUrlEncoded
    @POST("student/Videotrack/progress")
    Call<Progress_report> getProgressReport(
            @Field("studId")String studId);

    @FormUrlEncoded
    @POST("student/Videotrack/filter")
    Call<Progress_report> getFilterProgress(
            @Field("weekdata")String weekdata,
            @Field("studId")String studId);

    @FormUrlEncoded
    @POST("student/Chatwithteacher/checkstudentallocatedteacher")
    Call<TeacherModule> getTeacher(
            @Field("studId")String studId);

    @FormUrlEncoded
    @POST("student/Chatwithteacher")
    Call<ChatPojo> uploadStudentMessage(
            @Field("studId")String studId,
            @Field("fk_teachId")String fk_teachId,
            @Field("planId") String planId,
            @Field("msg")String msg);

//    @Multipart
//    @POST("student/uploadchatfile")
//    Call<UploadChatattachment> uploadStudentAttachment(
//            @Part("fk_studId")RequestBody fk_studId,
//            @Part("fk_teachId")RequestBody fk_teachId,
//            @Part("chatimgup\"; filename=\"attachment2.jpg\" ") RequestBody chatimgup,
//            @Part("planId") RequestBody planId);

    @Multipart
    @POST("student/uploadchatfile")
    Call<UploadChatattachment> uploadStudentAttachment(
            @Part("fk_studId")RequestBody fk_studId,
            @Part("fk_teachId")RequestBody fk_teachId,
            @Part MultipartBody.Part part,
            @Part("planId") RequestBody planId);

    @Multipart
    @POST("student/Uploadhomework")
    Call<UploadChatattachment> uploadSingleHomework(
            @Part("studId")RequestBody studId,
            @Part("start_time")RequestBody start_time,
            @Part("homework_id")RequestBody homework_id,
            @Part("fk_feesId")RequestBody fk_feesId,
            @Part("class_id")RequestBody class_id,
            @Part("home_title")RequestBody home_title,
            @Part MultipartBody.Part part);



    @Multipart
    @POST("student/Uploadhomework")
    Call<SubmitTestPojo> uploadTestImage(
            @Part("studId")RequestBody studId,
            @Part("start_time")RequestBody start_time,
            @Part("homework_id")RequestBody homework_id,
            @Part("fk_feesId")RequestBody fk_feesId,
            @Part("class_id") RequestBody class_id,
            @Part("home_title")RequestBody home_title,
            @Part List<MultipartBody.Part> part);

//    @Part List<MultipartBody.Part> files);

//    @Part MultipartBody.Part[] parts);



    @FormUrlEncoded
    @POST("student/Checkexistmobile")
    Call<EmailCheck> mobileNumberExist(
            @Field("studentMobile")String studentMobile);

    @FormUrlEncoded
    @POST("student/Chathistory")
    Call<ChatHistory> chatHistory(
            @Field("studId")String studId);

    @FormUrlEncoded
    @POST("student/Readnotificationchat")
    Call<ResponseBody> setReadChatFlag(
            @Field("studId")String studId,
            @Field("readMsg")String readMsg);

    @POST("student/Readnotification")
    Call<ResponseBody> readNormalNotification();

    @FormUrlEncoded
    @POST("student/Viewhomework")
    Call<TestPojo> getTestHistory(
            @Field("class_id")String class_id,
            @Field("package_id") String package_id,
            @Field("approvel_status") String approvel_status);

    @FormUrlEncoded
    @POST("student/Checkexistemail")
    Call<EmailCheck> emailExistCheck(
            @Field("studentEmail")String studentEmail);

    @FormUrlEncoded
    @POST("student/Checkpromocode")
    Call<Register> checkPromoCode(
            @Field("promoCode")String promoCode);

    @FormUrlEncoded
    @POST("student/Valuebasededu")
    Call<ValueBaseEducationPojo> getValueBaseD(
            @Field("fk_classId") String studentId);

    @FormUrlEncoded
    @POST("student/Valubasedweek")
    Call<WeeklyData> getWeeklyActivity(
            @Field("fk_classId") String studentId);

    @FormUrlEncoded
    @POST("student/Getcrartstory")
    Call<StoryCraftPojo> getStoryCraft(
            @Field("flagId") String flagId);


    @POST("student/State")
    Call<StatesAll> getState();

    @FormUrlEncoded
    @POST("student/UpdatePromocode")
    Call<PromoCodePojo> sendPromoCode(
            @Field("promoCode")String promoCode,
            @Field("studId")String studentMobile);

    @FormUrlEncoded
    @POST("student/Fetchimg")
    Call<ProfilePojo> getImageUploaded(
            @Field("img_name")String img_name);

//    @FormUrlEncoded
//    @POST("student/motherprofile")
//    Call<ProfilePojo> getMotherImageUploaded(
//            @Field("img_name")String img_name);

    @FormUrlEncoded
    @POST("student/Deniedmobileaccess/change_mobile_status")
    Call<AccessPojo> deniedMobileStatus(
            @Field("studentMobile")String studentMobile);

    @FormUrlEncoded
    @POST("student/Deniedmobileaccess")
    Call<AccessPojo> accessMobileStatus(
            @Field("studentMobile")String studentMobile);

    @POST("student/Calender")
    Call<CalenderPojo> getCalenderDate();

    @POST("student/Coursewisecalender")
    Call<CalenderPojo> getCoursesDate();

//    @FormUrlEncoded
//    @POST("student/motherprofile")
//    Call<ProfilePojo> getMotherImageUploaded(
//            @Field("img_name")String img_name);

    @GET
    public Call<ResponseBody> profilePicture(@Url String url);

//    Call<ResponseBody> getImageAll();

    @FormUrlEncoded
    @POST("student/Checkteacherassign")
    Call<TeacherAssign> getTeacherDetail(
            @Field("studId") String studId
    );

    @GET("roles/2")
    public Call<ChatRole> getChatRoleNAme();

    @POST("chatstudents")
    Call<ResponseBody> postNewChatMsg(@Body JsonObject locationPost);


    @POST("chats")
    Call<ChatHistoryNEw> getNewChat(@Body JsonObject locationPost);

    @Multipart
    @POST("uploadchat")
    Call<ResponseBody> uploadNewStudentAttachment(
            @Part("studid")RequestBody fk_studId,
            @Part("roleid")RequestBody fk_roleId,
            @Part MultipartBody.Part part,
            @Part("tof") RequestBody planId);

    @POST("chatsfile")
    Call<ResponseBody> getChatImage(@Body JsonObject chatBodyParametr);


}


