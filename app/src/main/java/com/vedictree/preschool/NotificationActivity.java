package com.vedictree.preschool;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.Adapters.NotificationAdapter;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.Notice;
import com.vedictree.preschool.POJO.NoticeDescription;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationActivity extends AppCompatActivity {

    ImageView back;
    SharedPreferences preferences;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    com.vedictree.preschool.Adapters.NotificationAdapter notificationAdapter;
    ArrayList<String> text_array;
    private List<NoticeDescription> noticeDescriptions;
    TextView mNoNotifications;
    Timer t;
    ImageView mLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
                        preferences.edit().remove("NAME").apply();
                        preferences.edit().remove("REGISTER_EMAIL").apply();
                        preferences.edit().remove("PREVIOUS_UNLOCK_MONTH").apply();
                        preferences.edit().remove("SELECT_MONTH").apply();
                        preferences.edit().remove("ENABLE_PREVIOUS_MONTH").apply();
                        preferences.edit().remove("PRESS_PREVIOUS_MONTH_BUTTON").apply();
                        preferences.edit().remove("FINAL_DAY").apply();
                        preferences.edit().remove("FINAL_MONTH").apply();
                        preferences.edit().remove("COURSE_PERIOD").apply();
                        preferences.edit().remove("YOU_TUBE").apply();
                        preferences.edit().clear();

                        Intent login_intent=new Intent(NotificationActivity.this, LoginActivity.class);
                        login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login_intent);
                        finish();
                        Toast.makeText(getApplicationContext(),"User Already Login in another device.Please login to continue", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Login_token> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        back=findViewById(R.id.back_notification);
        Bundle extras = getIntent().getExtras();
        mNoNotifications=findViewById(R.id.no_notifications);
        mLogo=findViewById(R.id.notification_logo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String sessionId = preferences.getString("NOTIFICATION_FROM","");
                Log.i("sessionId",sessionId);

                Intent intent = new Intent(NotificationActivity.this, NewParentDashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

            }
        });
        recyclerView=findViewById(R.id.recyclerview_notification);

        getAllNotifications();
        readNotice();
        t= new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      ((MyApplication)getApplication()).cancelNotification(R.integer.notificationId_noti);
                                      Log.i("Timer home","Timer home");
                                  }
                              },
                0,
                10);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){
        }
        else {
            setLogoutPopUp(this);
        }
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


    private void readNotice() {
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<ResponseBody> call = service.readNormalNotification();
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void getAllNotifications() {
        text_array=new ArrayList<>();
            String class_string=preferences.getString("CLASS","");
        APIInterface service = RetrofitSignletonTeacher.getLiveIntrface();
            Call<Notice> call = service.getNotification(class_string);
            call.enqueue(new Callback<Notice>() {
                @Override
                public void onResponse(Call<Notice> call, Response<Notice> response) {
                    if (response.body() != null) {
                        Notice notice_object=response.body();
                        noticeDescriptions=notice_object.getRes();
                        if (notice_object.getRes()!=null){
                            for (int i=0;i<noticeDescriptions.size();i++){
                                Log.i("Notification","Notification");
                                text_array.add(noticeDescriptions.get(i).getNoticedesc());
                            }
                            mNoNotifications.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            notificationAdapter = new NotificationAdapter(getApplicationContext(),text_array);
                            recyclerView.setAdapter(notificationAdapter);
                        }else {
                            mNoNotifications.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                    mNoNotifications.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Notice> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String sessionId = preferences.getString("NOTIFICATION_FROM","");
        Log.i("sessionId",sessionId);

            Intent intent = new Intent(NotificationActivity.this, NewParentDashboard.class);
            startActivity(intent);
            finish();

    }

    @Override
    protected void onStop() {
        super.onStop();
        t.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    @Override
    protected void onResume() {
        super.onResume();
        if(isMyServiceRunning(MusicBackground.class)){
        }else {
            startService(new Intent(this,MusicBackground.class));
        }
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

    public static boolean isApplicationGoingToBackground(final Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                Log.i("App b","a3");
                return true;
            }

        }
        Log.i("App b","b3");
        return false;
    }
}