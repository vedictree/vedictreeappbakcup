package com.vedictree.preschool;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.vedictree.preschool.POJO.ChatHistory;
import com.vedictree.preschool.POJO.ChatHistoryDetail;
import com.vedictree.preschool.POJO.ChatHistoryNEw;
import com.vedictree.preschool.POJO.ChatHistoryNEwDetail;
import com.vedictree.preschool.POJO.ChatRole;
import com.vedictree.preschool.POJO.ChatRoleResponse;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.Notice;
import com.vedictree.preschool.POJO.NoticeDescription;
import com.vedictree.preschool.POJO.TestPojo;
import com.vedictree.preschool.POJO.TestPojoResponse;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
//import com.vedictree.preschool.Utils.RetrofitSignletonNew;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MyLifecycleHandler implements Application.ActivityLifecycleCallbacks {
    StatusBarNotification statusBarNotification;
    private List<ChatRoleResponse> chatRoleResponseList;
    private List<ChatHistoryNEwDetail> chatHistoryNEwDetailList;


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        Log.i("Activity create", "Activity");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String mName = preferences.getString("NAME", "");
        checkNetwork(activity.getApplicationContext());

        if (mName.equals("")) {

        } else {
            NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.cancel(Integer.parseInt("my_channel_100"));
            ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId);
            ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId_noti);
            ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId_noti);
            ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId);
            ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId_home);
            showHomework(activity);
            addNotification(activity);


        }
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void checkNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String mName = preferences.getString("NAME", "");

        if (isConnected) {

//                checkLogStatus(context);


//            Intent intent =new Intent(context, VideoSessionActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
        } else {
//            Toast.makeText(context, "Please Check Internet ", Toast.LENGTH_LONG).show();
//            setLogoutPopUp(context.getApplicationContext());
        }
    }

    private void checkLogStatus(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String token_str = preferences.getString("LOG_STRING", "");
        Log.i("LOG_STRING", token_str);

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<Login_token> call = service.checkLogId(token_str);
        call.enqueue(new Callback<Login_token>() {
            @Override
            public void onResponse(Call<Login_token> call, Response<Login_token> response) {
                if (response.body() != null) {
                    Login_token login_token = response.body();
                    if (login_token.getCode().equals(200)) {

                    } else {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        preferences.edit().remove("NAME").apply();

                        Toast.makeText(context, "User Already Login in another device.Please login to continue", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Login_token> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Log.i("Activity create", "Activity start");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String mName = preferences.getString("NAME", "");
        checkNetwork(activity.getApplicationContext());
        if (mName.equals("")) {

        } else {

            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                                          notificationManager.cancel(100);
                                      }
                                  },
                    0,
                    10000);
        }

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.i("Activity create", "Activity Resume");
        ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId);

        checkNetwork(activity.getApplicationContext());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String mName = preferences.getString("NAME", "");
        if (mName.equals("")) {

        } else {

            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                                          ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId);
                                          showChatUnreadMsg(activity);
//                                          showChatCountNew(activity);
                                          Log.i("Notice", "notice1");
                                      }
                                  },
                    0,
                    30000);
        }
    }

    private void showChatCountNew(Activity activity) {
//        APIInterface service = RetrofitSignletonNew.getAPIInterface();
//        Call<ChatRole> call = service.getChatRoleNAme();
//        call.enqueue(new Callback<ChatRole>() {
//            @Override
//            public void onResponse(@NotNull Call<ChatRole> call, @NotNull Response<ChatRole> response) {
//                if (response.body() != null) {
//                    ChatRole chatRole=response.body();
//                    if (chatRole!=null) {
//                        if (chatRole.getData() != null) {
//                            chatRoleResponseList = chatRole.getData();
//                            for (int i = 0; i < chatRoleResponseList.size(); i++) {
//                                if (chatRoleResponseList.get(i).getChatf() == 0) {
//
//                                } else {
//                                    showChatNotification(chatRoleResponseList.get(i).getRolename(), chatRoleResponseList.get(i).getRoleid(), activity);
//
//                                }
//                            }
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<ChatRole> call, @NotNull Throwable t) {
//                Log.i("Error", String.valueOf(t.getMessage()));
//            }
//        });
    }

    private void showChatNotification(String rolename, Integer roleid,Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        String student_id = preferences.getString("STUDENT_ID", "");
        ArrayList<String> read_unread_count = new ArrayList<>();

        if (student_id.equals("")){

        }else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("rlid", roleid);
            jsonObject.addProperty("studid", Integer.parseInt(student_id));

//            APIInterface service = RetrofitSignletonNew.getAPIInterface();
//            Call<ChatHistoryNEw> call = service.getNewChat(jsonObject);
//            call.enqueue(new Callback<ChatHistoryNEw>() {
//                @SuppressLint("LongLogTag")
//                @Override
//                public void onResponse(@NotNull Call<ChatHistoryNEw> call, @NotNull Response<ChatHistoryNEw> response) {
//                    if (response.body() != null) {
//                        ChatHistoryNEw chatHistory = response.body();
//                        if (response.body() != null) {
//                            if (chatHistory.getData() != null) {
//                                chatHistoryNEwDetailList = chatHistory.getData();
//                                for (int i = 0; i < chatHistoryNEwDetailList.size(); i++) {
//                                    if (chatHistoryNEwDetailList.get(i).getRdstat() == 0) {
//                                        read_unread_count.add(chatHistoryNEwDetailList.get(i).getChtdtt());
//                                    } else {
//
//                                    }
//                                }
//                                if (read_unread_count.isEmpty()) {
//                                    ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId);
//                                } else {
//                                    ((MyApplication) activity.getApplication()).updateNotification(NewChatActivity.class,
//                                            "Vedic Tree Kids Learning App",
//                                            String.valueOf(read_unread_count.size()) + " " + rolename + " Meassage",
//                                            "news",
//                                            (R.integer.notificationId),
//                                            "",
//                                            PendingIntent.FLAG_UPDATE_CURRENT);
//                                    editor.putString("CHAT_FROM", "STATUS_BAR");
//                                    editor.commit();
//                                }
//                            }
//
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(@NotNull Call<ChatHistoryNEw> call, @NotNull Throwable t) {
//                    Log.i("Error", String.valueOf(t.getMessage()));
//                }
//            });
        }

    }


    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Log.i("Activity create", "Activity Pause");
        ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId);

        checkNetwork(activity.getApplicationContext());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String mName = preferences.getString("NAME", "");
        if (mName.equals("")) {

        } else {
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Log.i("Activity create", "Activity stop");
        checkNetwork(activity.getApplicationContext());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String mName = preferences.getString("NAME", "");
        if (mName.equals("")) {

        } else {
        }


    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        Log.i("Activity create", "Activity save");
        checkNetwork(activity.getApplicationContext());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String mName = preferences.getString("NAME", "");
        if (mName.equals("")) {

        } else {

        }
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Log.i("Activity create", "Activity destroy");
        checkNetwork(activity.getApplicationContext());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String mName = preferences.getString("NAME", "");
        if (mName.equals("")) {

        } else {

        }

    }

    private void showHomework(Activity activity) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

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
                            ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId_home);

                        } else {
                            ((MyApplication) activity.getApplication()).updateNotification(HomeworkNew.class,
                                    "Vedic Tree Kids Learning App",
                                    String.valueOf(homework_notification.size()) + " " + "Homework",
                                    "home",
                                    (R.integer.notificationId_home),
                                    "",
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            editor.putString("HOMEWORK_AVAIBLE", "STATUS_BAR");
                            editor.commit();
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<TestPojo> call, Throwable t) {
            }
        });

    }

    private void showChatUnreadMsg(Activity activity) {

        ArrayList<String> read_unread_count = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        String student_id = preferences.getString("STUDENT_ID", "");
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<ChatHistory> call = service.chatHistory(student_id);
        call.enqueue(new Callback<ChatHistory>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ChatHistory> call, Response<ChatHistory> response) {
                if (response.body() != null) {
                    ChatHistory chatHistory = response.body();
                    List<ChatHistoryDetail> chatHistoryDetails = chatHistory.getRes();
                    if (!chatHistoryDetails.isEmpty()) {
                        for (int i = 0; i < chatHistoryDetails.size(); i++) {
                            if (chatHistoryDetails.get(i).getFkStudId().equals(student_id)) {

                            } else {
                                if (chatHistoryDetails.get(i).getReadMsg().equals("1")) {

                                } else {
                                    read_unread_count.add(chatHistoryDetails.get(i).getReadMsg());
                                }
                            }

                        }
                        if (read_unread_count.isEmpty()) {
                            ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId);
                        } else {
                            ((MyApplication) activity.getApplication()).updateNotification(NewChatActivity.class,
                                    "Vedic Tree Kids Learning App",
                                    String.valueOf(read_unread_count.size()) + " " + "Chat Meassage",
                                    "news",
                                    (R.integer.notificationId),
                                    "",
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            editor.putString("CHAT_FROM", "STATUS_BAR");
                            editor.commit();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatHistory> call, Throwable t) {

            }
        });
    }

    private void addNotification(Activity activity) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        ArrayList<String> note_array = new ArrayList<>();
        String class_string = preferences.getString("CLASS", "");
        APIInterface service = RetrofitSignletonTeacher.getLiveIntrface();
        Call<Notice> call = service.getNotification(class_string);
        call.enqueue(new Callback<Notice>() {
            @Override
            public void onResponse(Call<Notice> call, Response<Notice> response) {
                if (response.body() != null) {
                    Notice notice_object = response.body();
                    List<NoticeDescription> noticeDescriptions = notice_object.getRes();
                    if (notice_object.getRes() != null) {
                        for (int i = 0; i < noticeDescriptions.size(); i++) {
                            if (noticeDescriptions.get(i).getReadMsg().equals("2")) {
                                note_array.add(String.valueOf(i));
                            }
                            if (note_array.isEmpty()) {
                                ((MyApplication) activity.getApplication()).cancelNotification(R.integer.notificationId_noti);
                            } else {
                                ((MyApplication) activity.getApplication()).updateNotification(NotificationActivity.class,
                                        "Vedic Tree Kids Learning App",
                                        String.valueOf(note_array.size()) + " " + "Notification",
                                        "noti",
                                        (R.integer.notificationId_noti),
                                        "",
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                editor.putString("NOTIFICATION_FROM", "STATUS_BAR");
                                editor.commit();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Notice> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
//        if (!notification_str.equals("")) {

    }

    private void setLogoutPopUp(Context context) {
        Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.no_internet_pop_up);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button button=alertDialog.findViewById(R.id.no_network);
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
        alertDialog.show();
    }

    private String getDatesBetween(String startDate, String endDate, String currentDate) {
        String match_str = "";
        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            Date date3=new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
            if(date3.after(date1) && date3.before(date2)){
                Log.i("Yes","Yes");
                match_str="Match";

            }else if (currentDate.equals(startDate)||currentDate.equals(endDate)){
                Log.i("Yes","Equal");
                match_str="Match";

            }else {
                Log.i("Yes","No");
                match_str="Not Match";


            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return match_str;
    }


}
