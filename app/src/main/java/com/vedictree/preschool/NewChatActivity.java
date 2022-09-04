package com.vedictree.preschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.JsonObject;
import com.vedictree.preschool.Adapters.chatAdapterNEw;
import com.vedictree.preschool.POJO.ChatHistory;
import com.vedictree.preschool.POJO.ChatHistoryDetail;
import com.vedictree.preschool.POJO.ChatHistoryNEw;
import com.vedictree.preschool.POJO.ChatHistoryNEwDetail;
import com.vedictree.preschool.POJO.ChatPojo;
import com.vedictree.preschool.POJO.ChatRole;
import com.vedictree.preschool.POJO.ChatRoleResponse;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.TeacherAssign;
import com.vedictree.preschool.POJO.TeacherAssignResponse;
import com.vedictree.preschool.POJO.TeacherDetail;
import com.vedictree.preschool.POJO.TeacherModule;
import com.vedictree.preschool.POJO.TestPojo;
import com.vedictree.preschool.POJO.TestPojoResponse;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
//import com.vedictree.preschool.Utils.RetrofitSignletonNew;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView chat_rcv;
    RecyclerView.LayoutManager layoutManagerDay;
    ArrayList<String> chatArray;
    ArrayList<String> chatDate;
    ArrayList<String> chatTextOrAttachment;
    ArrayList<String> chatSide;
    ArrayList<String> timeAgoArray;
    ArrayList<String> dayHourMinuteArray;
    ArrayList<String> time_str_array;
    ArrayList<Integer> read_unread_count;
    ArrayList<String> unread_count;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private List<ChatHistoryDetail> chatHistoryDetails;
    private List<ChatHistoryNEwDetail> chatHistoryNEwDetailList;

    Date dateOfMsg;
    DateFormat dateOfMsgFormat;
    com.vedictree.preschool.Adapters.chatAdapterNEw chatAdapterNEw;
    private EditText chatText;
    private Button buttonSend;
    ImageView mChatAttachment;
    private final int select_IdProof = 2; // request code fot Id proof intent
    Uri businessId_uri;
    String real_Path_id;
    String student_id;
    private TeacherModule teacherModule;
    private TeacherDetail teacherDetail;
    private List<TeacherDetail> teacherDetailList;
    private List<TeacherAssignResponse> teacherAssignResponses;
    private List<ChatRoleResponse> chatRoleResponseList;

    AlertDialog alertDialog_onCreate;
    TextView mStudentName;
    TextView mCourseName;
    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    String studentNameString;
    String classString;
    String profile_str;
    TextView mTeacherName;
    TextView mTeacherEmail;
    TextView mOffline;
    TextView mOline;
    int chatCount;
    boolean chatFlag;
    Handler handler;
    Timer t;
    ImageView mHome;
    Spinner mChatWith;
    String mChatString;
    ArrayList<Integer> chatId;
    Integer chat_id_int;

    String mStudentClass;
    String mStudentNameString;
    Integer mTeacherId;
    ImageView mLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

//        stopService(new Intent(this, MusicBackground.class));
//        startService(new Intent(this, MusicBackground.class));

        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (tabletSize) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();
        t= new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      ((MyApplication)getApplication()).cancelNotification(R.integer.notificationId);
                                      Log.i("Timer home","Timer home");
                                  }
                              },
                0,
                10);

        String token_str=preferences.getString("LOG_STRING","");
        Log.i("LOG_STRING",token_str);

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<Login_token> call = service.checkLogId(token_str);
        call.enqueue(new Callback<Login_token>() {
            @Override
            public void onResponse(Call<Login_token> call, Response<Login_token> response) {
                if (response.body() != null) {
                    Login_token login_token=response.body();
                    if(login_token.getCode().equals(200)){
                    }else {
                        preferences.edit().remove("EMAIL").apply();
                        preferences.edit().remove("MOBILE").apply();
                        preferences.edit().remove("CLASS").apply();
                        preferences.edit().remove("GENDER").apply();
                        preferences.edit().remove("CODE").apply();
                        preferences.edit().remove("USER_FIRST_NAME").apply();
                        preferences.edit().remove("USER_LAST_NAME").apply();
                        preferences.edit().remove("STUDENT_ID").apply();
                        preferences.edit().remove("PARENT_PIN_LOGIN").apply();
                        preferences.edit().remove("REFFERAL_CODE").apply();
                        preferences.edit().remove("PROMO_CODE").apply();
                        preferences.edit().remove("PROFILE_PICTURE").apply();
                        preferences.edit().remove("LOG_STRING").apply();
                        preferences.edit().remove("COURSE_PERIOD").apply();
                        preferences.edit().remove("NAME").apply();
                        preferences.edit().remove("PLAN_ID").apply();
                        preferences.edit().remove("PAYMENT_DATE").apply();
                        preferences.edit().remove("ACCOUNT_PAID").apply();
                        preferences.edit().remove("FINAL_DAY").apply();
                        preferences.edit().remove("FINAL_MONTH").apply();
                        preferences.edit().remove("SELECT_FINAL_MONTH").apply();
                        preferences.edit().remove("SELECT_FINAL_DAY").apply();
                        preferences.edit().remove("LOCK_FINAL_DAY").apply();
                        preferences.edit().remove("LOCK_FINAL_MONTH").apply();
                        preferences.edit().remove("PREVIOUS_UNLOCK_MONTH").apply();
                        preferences.edit().remove("SELECT_MONTH").apply();
                        preferences.edit().remove("ENABLE_PREVIOUS_MONTH").apply();
                        preferences.edit().remove("PRESS_PREVIOUS_MONTH_BUTTON").apply();
                        preferences.edit().remove("YOU_TUBE").apply();

                        preferences.edit().clear();
                        Intent login_intent=new Intent(NewChatActivity.this, LoginActivity.class);
                        login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login_intent);
                        finish();
                        Toast.makeText(getApplicationContext(),"User Already Login in another device.Please login to continue", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Login_token> call, Throwable t) {
            }
        });

        student_id=preferences.getString("STUDENT_ID","");
        studentNameString=preferences.getString("NAME","");
        classString=preferences.getString("CLASS","");
        profile_str=preferences.getString("PROFILE_PICTURE","");

        chat_rcv=findViewById(R.id.chat_recyclerview_2);
        chatText=findViewById(R.id.msg_new_text);
        buttonSend=findViewById(R.id.send_new_button);
        mChatAttachment=findViewById(R.id.chat_attachment_new_pin);
        mTeacherName=findViewById(R.id.chat_teacher_name_text);
        mOffline=findViewById(R.id.chat_offline);
        mOline=findViewById(R.id.chat_online);
        mHome=findViewById(R.id.chat_back_to_home__button);
        mChatWith=findViewById(R.id.chat_with_spinner);
        mLogo=findViewById(R.id.new_chat_logo);

        if (classString.equals("1")){
            mStudentClass="nursery";

        }else if (classString.equals("2")){
            mStudentClass="junior";
        }else if (classString.equals("3")){
            mStudentClass="senior";
        }
        chatCount=0;
        getChatHistory();
        getTeachId();
        getTeacherDetailMethod();
//        checkNewMessage();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setReadChatFlagMethod();
            }
        },10000);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      checkHistory();
                                      checkNewHistory(chat_id_int);
                                  }
                              },
                0,
                1000);

        mChatAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent businessId_intent = new Intent(Intent.ACTION_PICK);
                businessId_intent.setType("image/*");
                startActivityForResult(businessId_intent, select_IdProof);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (chatText.getText().length()==0){

                }else {
                    if (real_Path_id != null) {
                        uploadFile();
                    } else {
                        uploadMessage();
//                        uploadNewMsg();
                    }
                }

            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewChatActivity.this, ChildeDashboardNew.class);
                startActivity(intent);
                finish();
            }
        });

        chatId=new ArrayList<>();

//        getNewchatHistory(12);
        getRole();


        mChatWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mChatString =mChatWith.getSelectedItem().toString();
                chat_id_int=chatId.get(position);
                Log.i("chat id",String.valueOf(chat_id_int));
                getNewchatHistory(chat_id_int);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        checkHomewWork();

        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(0.5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();
        Glide.with(mLogo.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/child_video_logo.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mLogo);

    }

    private void checkHomewWork() {

        ArrayList<String> homework_notification = new ArrayList<>();
        homework_notification.clear();
        String plan_id = preferences.getString("PLAN_ID", "");
        String classString = preferences.getString("CLASS", "");
        String student_id = preferences.getString("STUDENT_ID", "");


        APIInterface service = RetrofitSignletonTeacher.getLiveIntrface();
        Call<TestPojo> call = service.getTestHistory(classString, plan_id, "2");
        call.enqueue(new Callback<TestPojo>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<TestPojo> call, Response<TestPojo> response) {
                if (response.body() != null) {
                    TestPojo testPojo = response.body();
                    List<TestPojoResponse> testPojoResponseList = testPojo.getGetfeesdata();
                    if (testPojoResponseList != null) {
                        for (int i = 0; i < testPojoResponseList.size(); i++) {
                            if (testPojoResponseList.get(i).getFk_studentId().equals(student_id)) {
                                if (testPojoResponseList.get(i).getNotification_status().equals("1")) {
                                    homework_notification.add(String.valueOf(i));
                                } else {

                                }
                            }
                        }

                        if (homework_notification.isEmpty()) {

                        } else {
                            Dialog alertDialog = new Dialog(NewChatActivity.this);
                            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            alertDialog.setContentView(R.layout.homework_available_pop_up);
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            Button button=alertDialog.findViewById(R.id.no_homework_button);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    final ValueAnimator yes_button = ValueAnimator.ofFloat(1f, 1.05f);
                                    yes_button.setDuration(300);
                                    yes_button.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            button.setScaleX((Float) animation.getAnimatedValue());
                                            button.setScaleY((Float) animation.getAnimatedValue());
                                        }
                                    });
                                    yes_button.setRepeatCount(1);
                                    yes_button.setRepeatMode(ValueAnimator.REVERSE);
                                    yes_button.start();
                                    alertDialog.dismiss();

                                }
                            });

                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<TestPojo> call, Throwable t) {
            }
        });

    }

    private void checkNewHistory(Integer role_id) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rlid",role_id);
        jsonObject.addProperty("studid",Integer.parseInt(student_id));

//        APIInterface service = RetrofitSignletonNew.getAPIInterface();
//        Call<ChatHistoryNEw> call = service.getNewChat(jsonObject);
//        call.enqueue(new Callback<ChatHistoryNEw>() {
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onResponse(@NotNull Call<ChatHistoryNEw> call, @NotNull Response<ChatHistoryNEw> response) {
//                if (response.body() != null) {
//                    ChatHistoryNEw chatHistory=response.body();
//                    if (response.body() != null) {
//                        if (chatHistory.getData() != null) {
//                            chatHistoryNEwDetailList = chatHistory.getData();
//                            if (chatCount==chatHistoryNEwDetailList.size()){
//                                Log.i("Match","Match");
//                            }else {
//                                Log.i("Not Match","Not Match");
//
//                            getNewchatHistory(role_id);
//                            }
//
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onFailure(@NotNull Call<ChatHistoryNEw> call, @NotNull Throwable t) {
//                Log.i("Error", String.valueOf(t.getMessage()));
//            }
//        });

    }

    private void uploadNewMsg() {
        if (mChatString.equals("Select Chat")){
            Toast.makeText(this,"Select chat person",Toast.LENGTH_LONG).show();
        }
        else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("rlid", chat_id_int);
            jsonObject.addProperty("studid", Integer.parseInt(student_id));
            jsonObject.addProperty("msgs", chatText.getText().toString());
            jsonObject.addProperty("studn", studentNameString);
            jsonObject.addProperty("studc", mStudentClass);
            jsonObject.addProperty("tof", 1);

//            APIInterface service = RetrofitSignletonNew.getAPIInterface();
//            Call<ResponseBody> call = service.postNewChatMsg(jsonObject);
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
//                    if (response.body() != null) {
//                        Log.i("response-success", response.body().toString());
//                        getNewchatHistory(chat_id_int);
//                        chatText.setText("");
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
//                    Log.i("Error", String.valueOf(t.getMessage()));
//                }
//            });
        }
    }

    private void getNewchatHistory(Integer role_id) {
            chat_rcv.removeAllViewsInLayout();

            chatArray=new ArrayList<>();
            chatDate=new ArrayList<>();
            chatTextOrAttachment=new ArrayList<>();
            chatSide=new ArrayList<>();
            dayHourMinuteArray=new ArrayList<>();
            timeAgoArray=new ArrayList<>();
            time_str_array=new ArrayList<>();
            read_unread_count=new ArrayList<>();
            unread_count=new ArrayList<>();

            chatArray.clear();
            chatDate.clear();
            chatTextOrAttachment.clear();
            chatSide.clear();
            dayHourMinuteArray.clear();
            timeAgoArray.clear();
            read_unread_count.clear();
            unread_count.clear();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rlid",role_id);
        jsonObject.addProperty("studid",Integer.parseInt(student_id));

//        APIInterface service = RetrofitSignletonNew.getAPIInterface();
//        Call<ChatHistoryNEw> call = service.getNewChat(jsonObject);
//        call.enqueue(new Callback<ChatHistoryNEw>() {
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onResponse(@NotNull Call<ChatHistoryNEw> call, @NotNull Response<ChatHistoryNEw> response) {
//                if (response.body() != null) {
//                    ChatHistoryNEw chatHistory=response.body();
//                    if (response.body() != null) {
//                        if (chatHistory.getData() != null) {
//                            chatHistoryNEwDetailList = chatHistory.getData();
//                            chatCount=chatHistoryNEwDetailList.size();
//
//                            if (!chatHistoryNEwDetailList.isEmpty()) {
//                                for (int i = 0; i < chatHistoryNEwDetailList.size(); i++) {
//                                    chatCount = chatHistoryNEwDetailList.size();
//                                    if (chatHistoryNEwDetailList.get(i).getMsgs() == null) {
//                                        if (chatHistoryNEwDetailList.get(i).getChtimg().equals("")) {
//                                            chatArray.add(chatHistoryNEwDetailList.get(i).getMsgs());
//                                            chatTextOrAttachment.add("Text");
//                                            Log.i("Chat msg is", chatHistoryNEwDetailList.get(i).getMsgs());
//
//                                        } else {
//                                            chatArray.add(chatHistoryNEwDetailList.get(i).getChtimg());
//                                            chatTextOrAttachment.add("Attachment");
//                                            Log.i("Chat msg is", "null");
//                                            Log.i("Chat msg is", chatHistoryNEwDetailList.get(i).getChtimg());
//
//                                        }
//                                    } else if (chatHistoryNEwDetailList.get(i).getMsgs().equals("")) {
//                                        if (chatHistoryNEwDetailList.get(i).getChtimg().equals("")) {
//                                            chatArray.add(chatHistoryNEwDetailList.get(i).getChtimg());
//                                            chatTextOrAttachment.add("Text");
//                                            Log.i("Chat msg is", chatHistoryNEwDetailList.get(i).getMsgs());
//
//                                        } else {
//                                            chatArray.add(chatHistoryNEwDetailList.get(i).getChtimg());
//                                            chatTextOrAttachment.add("Attachment");
//                                            Log.i("Chat msg is", chatHistoryNEwDetailList.get(i).getChtimg());
//
//                                        }
//
//                                    } else {
//                                        chatArray.add(chatHistoryNEwDetailList.get(i).getMsgs());
//                                        chatTextOrAttachment.add("Text");
//                                    }
//                                    chatDate.add(chatHistoryNEwDetailList.get(i).getChtdtt());
//                                    read_unread_count.add(chatHistoryNEwDetailList.get(i).getRdstat());
//
//                                    if (chatHistoryNEwDetailList.get(i).getTof().equals(2)) {
//                                        chatSide.add("Student");
//                                    } else {
//                                        chatSide.add("Admin");
//                                        if (chatHistoryNEwDetailList.get(i).getRdstat().equals("0")) {
//                                            unread_count.add(chatHistoryNEwDetailList.get(i).getMsgs());
//                                        }
//                                    }
//
//                                }
//                            }
//                            Calendar calendar;
//                            SimpleDateFormat dateFormat;
//                            String date;
//                            calendar = Calendar.getInstance();
//                            dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//                            date = dateFormat.format(calendar.getTime());
//                            Log.i("Current date:", date);
//
//                            if (chatDate != null) {
//                                Log.i("Date array", String.valueOf(chatDate.size()));
//                                for (int j = 0; j < chatDate.size(); j++) {
//                                    Log.i("date is", chatDate.get(j));
//
//                                    try {
//                                        DateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
//                                        dateOfMsg = f.parse(chatDate.get(j));
//                                        dateOfMsgFormat = new SimpleDateFormat("MM/dd/yyyy");
//                                        Log.i("Time current ---", String.valueOf(dateOfMsgFormat.parse(String.valueOf(dateOfMsg))));
//
//
//                                    } catch (java.text.ParseException e) {
//                                        e.printStackTrace();
//                                    }
//                                    Date date1;
//                                    Date date2;
//                                    SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
//                                    SimpleDateFormat msg_time = new SimpleDateFormat("hh:mm:aa");
//
//                                    try {
//                                        date1 = dates.parse(date);
//                                        date2 = dates.parse(dateOfMsgFormat.format(dateOfMsg));
//
//                                        long difference = Math.abs(date1.getTime() - date2.getTime());
//                                        long differenceDates = difference / (24 * 60 * 60 * 1000);
//                                        String dayDifference = Long.toString(differenceDates);
//                                        Log.i("The difference between two dates is : ", String.valueOf(dayDifference));
//
//                                        if (dayDifference.equals("0")) {
////                                    Log.i("Time current is", String.valueOf(dateOfMsg.));
//                                            try {
//                                                DateFormat new_f = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
//                                                Date d = new_f.parse(chatDate.get(j));
//                                                DateFormat date_new = new SimpleDateFormat("hh:mm aa");
//                                                Log.i("Date of msg", date_new.format(d));
//                                                timeAgoArray.add(String.valueOf(date_new.format(d)));
//                                                dayHourMinuteArray.add("");
//                                            } catch (ParseException e) {
//                                                e.printStackTrace();
//                                            }
//                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
//                                            String time = simpleDateFormat.format(calendar.getTime());
//                                            Date date1_time = simpleDateFormat.parse(time);
//
//                                            String msg_time_str = msg_time.format(dateOfMsg);
//                                            Log.i("Time current is", String.valueOf(date2));
//                                            Log.i("Time is", msg_time_str);
//                                            Date date2_time = simpleDateFormat.parse(msg_time_str);
//
//                                            long difference_time = date2_time.getTime() - date1_time.getTime();
//                                            int days = (int) (difference_time / (1000 * 60 * 60 * 24));
//                                            int hours = (int) ((difference_time - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
//                                            int min = (int) (difference_time - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
//                                            Log.i("======= Hours", " :: " + hours);
//                                            hours = (hours < 0 ? -hours : hours);
//                                            Log.i("======= Min", " :: " + min);
//
//
//                                            long diff = date2_time.getTime() - date1_time.getTime();
//
//                                            long seconds = diff / 1000;
//                                            Log.i("======= Seconds", " :: " + seconds);
//                                            long minutes = seconds / 60;
//                                            Log.i("======= minutes", " :: " + minutes);
//
//                                            long hours_new = minutes / 60;
//                                            Log.i("======= hours_new", " :: " + hours_new);
//
//                                            long days_new = hours_new / 24;
//                                            Log.i("======= days_new", " :: " + days_new);
//
//
////                                    timeAgoArray.add(String.valueOf(hours));
////                                    dayHourMinuteArray.add(" ");
////                                    timeAgoArray.add("0");
////                                    dayHourMinuteArray.add("minute");
//                                        } else {
//                                            timeAgoArray.add(String.valueOf(dayDifference));
//                                            dayHourMinuteArray.add("Day");
//                                        }
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                for (int i = 0; i < chatArray.size(); i++) {
//                                    if (dayHourMinuteArray.get(i).equals("Day")) {
//                                        time_str_array.add(timeAgoArray.get(i) + " Day ago");
//                                    } else if (dayHourMinuteArray.get(i).equals("Minute")) {
//                                        time_str_array.add(timeAgoArray.get(i) + " minute ago");
//                                    } else if (dayHourMinuteArray.get(i).equals("")) {
//                                        time_str_array.add(timeAgoArray.get(i));
//                                    }
//                                }
//
//                                if (!chatArray.isEmpty()) {
////                            for (int j=0;j<chatArray.size();j++){
//                                    sendChatMessage();
////                                alertDialog_onCreate.dismiss();
//
////                                chatText.setText("");
//
////                            }
//                                }
//                            }
////                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onFailure(@NotNull Call<ChatHistoryNEw> call, @NotNull Throwable t) {
//                Log.i("Error", String.valueOf(t.getMessage()));
//            }
//        });



    }

    private void getChatHistoryNew(Integer chat_id_int) {

    }

    private void getRole() {
        ArrayList<String> roleArray=new ArrayList<>();
        chatId.clear();
        roleArray.clear();

        roleArray.add("Select Chat");
        chatId.add(0);

//        APIInterface service = RetrofitSignletonNew.getAPIInterface();
//        Call<ChatRole> call = service.getChatRoleNAme();
//        call.enqueue(new Callback<ChatRole>() {
//            @Override
//            public void onResponse(@NotNull Call<ChatRole> call, @NotNull Response<ChatRole> response) {
//                if (response.body() != null) {
//                    ChatRole chatRole=response.body();
//                    if (chatRole!=null){
//                        if(chatRole.getData()!=null) {
//                            chatRoleResponseList = chatRole.getData();
//                            for (int i = 0; i < chatRoleResponseList.size(); i++) {
//                                if (chatRoleResponseList.get(i).getChatf() == 0) {
//
//                                } else {
//                                    roleArray.add(chatRoleResponseList.get(i).getRolename());
//                                    chatId.add(chatRoleResponseList.get(i).getRoleid());
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (roleArray.size()==1){
//                    ArrayAdapter<String> genderAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_chat_role,roleArray);
//                    mChatWith.setAdapter(genderAdapter);
//                }else {
//                    ArrayAdapter<String> genderAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_chat_role,roleArray);
//                    mChatWith.setAdapter(genderAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ChatRole> call, @NotNull Throwable t) {
//                Log.i("Error", String.valueOf(t.getMessage()));
//            }
//        });

    }

    private void uploadMessage() {
        Log.i("Message","Message");
        student_id=preferences.getString("STUDENT_ID","");
        String plan_id=preferences.getString("PLAN_ID","");
        if (plan_id.equals("")){
            chatText.setText("");
            chatText.setText("");
        }else {
            String teacher_id = preferences.getString("TEACHER_ID", "");
            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<ChatPojo> call = service.uploadStudentMessage(student_id, teacher_id, plan_id,chatText.getText().toString());
            call.enqueue(new Callback<ChatPojo>() {
                @Override
                public void onResponse(Call<ChatPojo> call, Response<ChatPojo> response) {
                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        chat_rcv.removeAllViewsInLayout();
                        chatText.setText("");
                        getChatHistory();

                    }

                }

                @Override
                public void onFailure(Call<ChatPojo> call, Throwable t) {

                }
            });
        }
    }

    private void uploadFile() {
        if (mChatString.equals("Select Chat")){
            Toast.makeText(this,"Select chat person",Toast.LENGTH_LONG).show();
        }else {
            Log.i("File", "File");
            String teacher_id = preferences.getString("TEACHER_ID", "");
            String plan_id = preferences.getString("PLAN_ID", "");

            File image_file = new File(getRealPathFromURI(businessId_uri, getApplicationContext()));
            Log.i("Image file", String.valueOf(image_file));
//        File file = FileUtils.getFile(this, businessId_uri);

            RequestBody attachment_img = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
            RequestBody student_body = RequestBody.create(MediaType.parse("multipart/form-data"), student_id);
            RequestBody roleid_body = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(chat_id_int));
            RequestBody tof_body = RequestBody.create(MediaType.parse("multipart/form-data"), "2");

//        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(businessId_uri)), image_file);

//            student_id = preferences.getString("STUDENT_ID", "");
//            APIInterface service = RetrofitSignletonNew.getAPIInterface();
//            Call<ResponseBody> call = service.uploadNewStudentAttachment(student_body, roleid_body, MultipartBody.Part.createFormData("file_field", chatText.getText().toString(), attachment_img), tof_body);
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    if (response.body() != null) {
////                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
//                        real_Path_id = null;
//                        chatText.setText("");
//                        Log.i("Image upload", "Image Upload");
//                        getNewchatHistory(chat_id_int);
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(),"Allow permissions to upload file.Go to setting->App premisions->Storage->Allow", Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }

    private String getRealPathFromURI(Uri contentUri, Context c) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(c, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void checkHistory() {


        String student_id=preferences.getString("STUDENT_ID","");
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<ChatHistory> call = service.chatHistory(student_id);
        call.enqueue(new Callback<ChatHistory>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ChatHistory> call, Response<ChatHistory> response) {
                if (response.body() != null) {
                    ChatHistory chatHistory=response.body();
                    chatHistoryDetails=chatHistory.getRes();
                    if (!chatHistoryDetails.isEmpty()){
                        for (int i=0;i<chatHistoryDetails.size();i++) {

                        }
                        if (chatCount==chatHistoryDetails.size()){
                            Log.i("Match","Match");
                        }else {
                            Log.i("Not Match","Not Match");

//                            getChatHistory();
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<ChatHistory> call, Throwable t) {

            }
        });
    }

    private void setReadChatFlagMethod() {
        String student_id=preferences.getString("STUDENT_ID","");
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<ResponseBody> call = service.setReadChatFlag(student_id,"1");
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
//                    getChatHistory();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getTeacherDetailMethod() {
        student_id=preferences.getString("STUDENT_ID","");
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<TeacherAssign> call = service.getTeacherDetail(student_id);
        call.enqueue(new Callback<TeacherAssign>() {
            @Override
            public void onResponse(Call<TeacherAssign> call, Response<TeacherAssign> response) {
                if (response.body() != null) {
                    TeacherAssign teacherAssign=response.body();
                    teacherAssignResponses=teacherAssign.getRes();
                    if (!teacherAssignResponses.isEmpty()){
//                        mTeacherEmail.setText(teacherAssignResponses.get(0).getTeacherEmail());
                        mTeacherName.setText(teacherAssignResponses.get(0).getTeacherName());
                        Log.i("Teacher name",teacherAssignResponses.get(0).getTeacherName());

                        if (teacherAssignResponses.get(0).getTeacherStatusonline().equals("1")){
                            mOffline.setVisibility(View.GONE);
                            mOline.setVisibility(View.VISIBLE);
                        }else if (teacherAssignResponses.get(0).getTeacherStatusonline().equals("0")){
                            mOffline.setVisibility(View.VISIBLE);
                            mOline.setVisibility(View.GONE);
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "No Teacher available", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TeacherAssign> call, Throwable t) {

            }
        });
    }

    private void checkNewMessage() {
        Log.i("date New:",String.valueOf(chatDate.size()));
        Log.i("Day New:",String.valueOf(dayHourMinuteArray));
        Log.i("TimeAgoArray New:",String.valueOf(timeAgoArray));
        Log.i("Chatside New:",String.valueOf(chatSide));
    }

    private void getTeachId() {
        student_id=preferences.getString("STUDENT_ID","");
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<TeacherModule> call = service.getTeacher(student_id);
        call.enqueue(new Callback<TeacherModule>() {
            @Override
            public void onResponse(Call<TeacherModule> call, Response<TeacherModule> response) {
                if (response.body() != null) {
                    teacherModule=response.body();
                    teacherDetailList=teacherModule.getRes();
                    if (!teacherDetailList.isEmpty()){
                        editor.putString("TEACHER_ID",teacherDetailList.get(0).getFkTeachId());
                        editor.commit();
                    }else {
                        chat_rcv.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), "No Teacher available", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TeacherModule> call, Throwable t) {

            }
        });
    }

    private void getChatHistory() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
//        ViewGroup viewGroup = findViewById(android.R.id.content);
//        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
//        builder.setView(dialog);
//        alertDialog_onCreate= builder.create();
//        alertDialog_onCreate.show();

        chat_rcv.removeAllViewsInLayout();

        chatArray=new ArrayList<>();
        chatDate=new ArrayList<>();
        chatTextOrAttachment=new ArrayList<>();
        chatSide=new ArrayList<>();
        dayHourMinuteArray=new ArrayList<>();
        timeAgoArray=new ArrayList<>();
        time_str_array=new ArrayList<>();
        read_unread_count=new ArrayList<>();
        unread_count=new ArrayList<>();

        chatArray.clear();
        chatDate.clear();
        chatTextOrAttachment.clear();
        chatSide.clear();
        dayHourMinuteArray.clear();
        timeAgoArray.clear();
        read_unread_count.clear();
        unread_count.clear();

//        chatArray.add("Teacher msg");
//        chatSide.add("teacher");
//        dayHourMinuteArray.add("Minute");
//        time_str_array.add("2 minute ago");
//        chatDate.add("2021-08-07 12:00:00");
//        chatTextOrAttachment.add("Text");

        String student_id=preferences.getString("STUDENT_ID","");
        String teachr_id=preferences.getString("TEACHER_ID","");

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<ChatHistory> call = service.chatHistory(student_id);
        call.enqueue(new Callback<ChatHistory>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ChatHistory> call, Response<ChatHistory> response) {
                if (response.body() != null) {
                    ChatHistory chatHistory=response.body();
                    chatHistoryDetails=chatHistory.getRes();
                    if (!chatHistoryDetails.isEmpty()){
                        for (int i=0;i<chatHistoryDetails.size();i++) {
                            chatCount=chatHistoryDetails.size();
                            if (chatHistoryDetails.get(i).getChatMsg()==null){
                                if (chatHistoryDetails.get(i).getChatimgup().equals("")){
                                    chatArray.add(chatHistoryDetails.get(i).getChatMsg());
                                    chatTextOrAttachment.add("Text");
                                    Log.i("Chat msg is",chatHistoryDetails.get(i).getChatMsg());

                                }else {
                                    chatArray.add(chatHistoryDetails.get(i).getChatimgup());
                                    chatTextOrAttachment.add("Attachment");
                                    Log.i("Chat msg is","null");
                                    Log.i("Chat msg is",chatHistoryDetails.get(i).getChatimgup());

                                }
                            }
                            else if (chatHistoryDetails.get(i).getChatMsg().equals("")){
                                if (chatHistoryDetails.get(i).getChatimgup().equals("")){
                                    chatArray.add(chatHistoryDetails.get(i).getChatMsg());
                                    chatTextOrAttachment.add("Text");
                                    Log.i("Chat msg is",chatHistoryDetails.get(i).getChatMsg());

                                }else {
                                    chatArray.add(chatHistoryDetails.get(i).getChatimgup());
                                    chatTextOrAttachment.add("Attachment");
                                    Log.i("Chat msg is",chatHistoryDetails.get(i).getChatimgup());

                                }

                            }
                            else {
                                chatArray.add(chatHistoryDetails.get(i).getChatMsg());
                                chatTextOrAttachment.add("Text");
                            }
                            chatDate.add(chatHistoryDetails.get(i).getCurrentDate());
                            read_unread_count.add(Integer.parseInt(chatHistoryDetails.get(i).getReadMsg()));

//                            chatSide.add("Student");
                            Log.i("Teacher id",teachr_id);
                            if (chatHistoryDetails.get(i).getFkStudId().equals(student_id)){
//                                chatSide.add("teacher");
                                chatSide.add("Student");

                            }
                            else {
                                chatSide.add("Teacher");
                                if (chatHistoryDetails.get(i).getReadMsg().equals("2")) {
                                    unread_count.add(chatHistoryDetails.get(i).getReadMsg());
                                }
                            }

                        }
                    }
                    Calendar calendar;
                    SimpleDateFormat dateFormat;
                    String date;
                    calendar = Calendar.getInstance();
                    dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    date = dateFormat.format(calendar.getTime());
                    Log.i("Current date:",date);

                    if (chatDate!=null){
                        Log.i("Date array",String.valueOf(chatDate.size()));
                        for (int j=0;j<chatDate.size();j++){
                            Log.i("date is",chatDate.get(j));

                            try {
                                DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                dateOfMsg = f.parse(chatDate.get(j));
                                dateOfMsgFormat= new SimpleDateFormat("MM/dd/yyyy");
//                                Log.i("Time current ---", String.valueOf(dateOfMsgFormat.parse(dateOfMsg)));



                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                            Date date1;
                            Date date2;
                            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
                            SimpleDateFormat msg_time = new SimpleDateFormat("hh:mm:ss");

                            try {
                                date1 = dates.parse(date);
                                date2 = dates.parse(dateOfMsgFormat.format(dateOfMsg));

                                long difference = Math.abs(date1.getTime() - date2.getTime());
                                long differenceDates = difference / (24 * 60 * 60 * 1000);
                                String dayDifference = Long.toString(differenceDates);
                                Log.i("The difference between two dates is : " , String.valueOf(dayDifference));

                                if (dayDifference.equals("0")){
//                                    Log.i("Time current is", String.valueOf(dateOfMsg.));
                                    try {
                                        DateFormat new_f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                        Date d = new_f.parse(chatDate.get(j));
                                        DateFormat date_new = new SimpleDateFormat("hh:mm");
                                        Log.i("Date of msg",date_new.format(d));
                                        timeAgoArray.add(String.valueOf(date_new.format(d)));
                                        dayHourMinuteArray.add("");
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
                                    String time = simpleDateFormat.format(calendar.getTime());
                                    Date date1_time = simpleDateFormat.parse(time);

                                    String msg_time_str=msg_time.format(dateOfMsg);
                                    Log.i("Time current is", String.valueOf(date2));
                                    Log.i("Time is",msg_time_str);
                                    Date date2_time = simpleDateFormat.parse(msg_time_str);

                                    long difference_time = date2_time.getTime() - date1_time.getTime();
                                    int days = (int) (difference_time / (1000*60*60*24));
                                    int hours = (int) ((difference_time - (1000*60*60*24*days)) / (1000*60*60));
                                    int min = (int) (difference_time - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                                    Log.i("======= Hours"," :: "+hours);
                                    hours = (hours < 0 ? -hours : hours);
                                    Log.i("======= Min"," :: "+min);


                                    long diff = date2_time.getTime() - date1_time.getTime();

                                    long seconds = diff / 1000;
                                    Log.i("======= Seconds"," :: "+seconds);
                                    long minutes = seconds / 60;
                                    Log.i("======= minutes"," :: "+minutes);

                                    long hours_new = minutes / 60;
                                    Log.i("======= hours_new"," :: "+hours_new);

                                    long days_new = hours_new / 24;
                                    Log.i("======= days_new"," :: "+days_new);


//                                    timeAgoArray.add(String.valueOf(hours));
//                                    dayHourMinuteArray.add(" ");
//                                    timeAgoArray.add("0");
//                                    dayHourMinuteArray.add("minute");
                                }else {
                                    timeAgoArray.add(String.valueOf(dayDifference));
                                    dayHourMinuteArray.add("Day");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        for (int i=0;i<chatArray.size();i++){
                            if (dayHourMinuteArray.get(i).equals("Day")){
                                time_str_array.add(timeAgoArray.get(i)+" Day ago");
                            }else if (dayHourMinuteArray.get(i).equals("Minute")){
                                time_str_array.add(timeAgoArray.get(i)+" minute ago");
                            }else if (dayHourMinuteArray.get(i).equals("")){
                                time_str_array.add(timeAgoArray.get(i));
                            }
                        }

                        if (!chatArray.isEmpty()){
//                            for (int j=0;j<chatArray.size();j++){
                            sendChatMessage();
//                                alertDialog_onCreate.dismiss();

//                                chatText.setText("");

//                            }
                        }
                    }
//                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ChatHistory> call, Throwable t) {
                Log.i("Feilure chat",String.valueOf(t));
            }
        });
    }

    private void sendChatMessage() {

//        Collections.reverse(chatSide);
//        Collections.reverse(chatArray);
//        Collections.reverse(time_str_array);
//        Collections.reverse(chatTextOrAttachment);
//        Collections.reverse(read_unread_count);

        Log.i("Side", String.valueOf(chatSide));
        Log.i("Side", String.valueOf(chatArray));
        Log.i("Side", String.valueOf(time_str_array));
        Log.i("Side", String.valueOf(chatTextOrAttachment));
        Log.i("Side", String.valueOf(chatSide.size()));
        Log.i("Side", String.valueOf(chatArray.size()));
        Log.i("Side", String.valueOf(time_str_array.size()));
        Log.i("Side", String.valueOf(chatTextOrAttachment.size()));
        Log.i("Side",String.valueOf(read_unread_count));


        layoutManagerDay = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        chat_rcv.setLayoutManager(layoutManagerDay);
        int chat_size=chatArray.size();

        chatAdapterNEw = new chatAdapterNEw(getApplicationContext(),chatSide,chatArray,time_str_array,chatTextOrAttachment,read_unread_count);
        chat_rcv.setAdapter(chatAdapterNEw);
        Log.i("Chat size:",String.valueOf(chat_rcv.getAdapter().getItemCount()));
        if (chat_rcv!=null) {
            if (!unread_count.isEmpty()){
                chat_rcv.scrollToPosition(Integer.parseInt(unread_count.get(0)));
            }else {
                chat_rcv.scrollToPosition(chat_rcv.getAdapter().getItemCount() - 1);
            }
        }

//        alertDialog_onCreate.dismiss();
        chatText.setText("");
    }

    public void onActivityResult(int requestcode, int resultcode,
                                 Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);
        switch (requestcode) {
            case select_IdProof:
                if (resultcode == RESULT_OK) {
                    businessId_uri = imagereturnintent.getData();
                    real_Path_id = getRealPathFromUri(getApplicationContext(),
                            businessId_uri);
                    String filename=real_Path_id.substring(real_Path_id.lastIndexOf("/")+1);
                    Log.i("Real Path:",filename);
                    chatText.setText(filename);
                }
                break;
            default:break;
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] selected_media = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, selected_media, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        String back_str=preferences.getString("CHAT_FROM","");
//        if (back_str.equals("STATUS_BAR")){
//            Intent intent = new Intent(NewChatActivity.this, VideoSessionActivity.class);
//            startActivity(intent);
//            finish();
//        }else if (back_str.equals("Parent_dashboard")){
            Intent intent = new Intent(NewChatActivity.this, ChildeDashboardNew.class);
            startActivity(intent);
            finish();
//        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        if(isMyServiceRunning(MusicBackground.class)){
        }else {
            startService(new Intent(this,MusicBackground.class));
        }

        ((MyApplication) this.getApplication()).cancelNotification(R.integer.notificationId);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor = preferences.edit();
//        String token_str=preferences.getString("LOG_STRING","");
//        APIInterface service = RetrofitSignleton.getAPIInterface();
//        Call<Login_token> call = service.checkLogId(token_str);
//        call.enqueue(new Callback<Login_token>() {
//            @Override
//            public void onResponse(Call<Login_token> call, Response<Login_token> response) {
//                if (response.body() != null) {
//                    Login_token login_token=response.body();
//                    if(login_token.getCode().equals(200)){
//                    }else {
//                        preferences.edit().remove("NAME").apply();
//                        preferences.edit().remove("REGISTER_EMAIL").apply();
//                        preferences.edit().remove("PREVIOUS_UNLOCK_MONTH").apply();
//                        preferences.edit().remove("SELECT_MONTH").apply();
//                        preferences.edit().remove("ENABLE_PREVIOUS_MONTH").apply();
//                        preferences.edit().remove("PRESS_PREVIOUS_MONTH_BUTTON").apply();
//                        preferences.edit().remove("FINAL_DAY").apply();
//                        preferences.edit().remove("FINAL_MONTH").apply();
//                        preferences.edit().clear();
//                        preferences.edit().remove("COURSE_PERIOD").apply();
//                        Intent login_intent=new Intent(NewChatActivity.this,LoginScreen.class);
//                        login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(login_intent);
//                        finish();
//
//                        Toast.makeText(getApplicationContext(),"User Already Login in another device.Please login to continue", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<Login_token> call, Throwable t) {
////                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public static boolean isAppOnForeground(Context context)
    {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = "com.vedictree.preschool";
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName))
            if (appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;

//        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
//        ActivityManager.getMyMemoryState(myProcess);
//        boolean isInBackground = myProcess.importance!= ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
//        if(isInBackground) {
//            return  false;
//        }else{
//            return true;
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        t.cancel();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        if (isApplicationGoingToBackground(this))
        {
            stopService(new Intent(this,MusicBackground.class));
        }else {

        }
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn){

        }else {
            stopService(new Intent(this,MusicBackground.class));
        }
    }

    public static boolean isApplicationGoingToBackground(final Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
//            ComponentName secondActivity = tasks.get(1).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                Log.i("App b","a3");
                return true;
            }

//            else if (!secondActivity.getPackageName().equals(context.getPackageName())){
//                Log.i("App b","d3");
//                return true;
//            }
        }
        Log.i("App b","b3");
        return false;
    }

     class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.cancel();
//        stopService(new Intent(this,MusicBackground.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        t.cancel();
//        stopService(new Intent(this,MusicBackground.class));

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        ((MyApplication)this.getApplication()).cancelNotification(R.integer.notificationId);
    }

}