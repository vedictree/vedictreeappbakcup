package com.vedictree.preschool;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chaos.view.PinView;
//import com.giphy.sdk.core.models.Video;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.JsonObject;
import com.vedictree.preschool.POJO.ChatHistory;
import com.vedictree.preschool.POJO.ChatHistoryDetail;
import com.vedictree.preschool.POJO.ChatHistoryNEw;
import com.vedictree.preschool.POJO.ChatHistoryNEwDetail;
import com.vedictree.preschool.POJO.ChatRole;
import com.vedictree.preschool.POJO.ChatRoleResponse;
import com.vedictree.preschool.POJO.Get_live_session_detail;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.Register;
import com.vedictree.preschool.POJO.TestPojo;
import com.vedictree.preschool.POJO.TestPojoResponse;
import com.vedictree.preschool.POJO.live_session;
import com.vedictree.preschool.POJO.parentPinPojo;
import com.vedictree.preschool.POJO.parentPinPojoResponse;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
//import com.vedictree.preschool.Utils.RetrofitSignletonNew;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import org.jetbrains.annotations.NotNull;
//import org.jitsi.meet.sdk.BroadcastEvent;
//import org.jitsi.meet.sdk.BroadcastReceiver;
//import org.jitsi.meet.sdk.JitsiMeet;
//import org.jitsi.meet.sdk.JitsiMeetActivity;
//import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;


import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import timber.log.Timber;


public class ChildeDashboardNew extends AppCompatActivity implements View.OnClickListener {

    ImageView mVideoButton;
    ImageView mActivityButton;
    ImageView mHomeworkButton;
    ImageView mChatButton;
    ImageView mLiveButton;

    ImageView mVideoImage;
    ImageView mActivityImage;
    ImageView mHomeworkImage;
    ImageView mChatImage;
    ImageView mLiveImage;
    ImageView mParentPin;
    ImageView mLogout;
    ImageView mLogo;
    ImageView mTodaySession;

    LinearLayout mTodaySessionLL;
    LinearLayout mLiveSessionLL;
    LinearLayout mVideoSessionLL;
    LinearLayout mChatSessionLL;


    private SoundPool soundPool;

    int a, b,c,d,e;
    PinView pinView;
    PinView forgot_pinview;

    String student_id;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ImageView one;


    List<parentPinPojoResponse> parentPinPojoResponseList;

    private int scrollMax;
    private int scrollPos =	0;
    private TimerTask scrollerSchedule;
    private Timer scrollTimer		=	null;

    HorizontalScrollView enroll_hev;
    LinearLayout enroll_ll;

    String class_string;
    TextView mHomeworkCount;
    TextView mChatCount;
    private List<Get_live_session_detail> get_live_session_detailList;
    String package_name;

    LinearLayout mLockHomework;
    LinearLayout mLockChat;
    LinearLayout mLockLive;

    private List<ChatRoleResponse> chatRoleResponseList;
    private List<ChatHistoryNEwDetail> chatHistoryNEwDetailList;
        Handler handler;
//        MediaPlayer mediaPlayer;

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

//    private void onBroadcastReceived(Intent intent) {
//        if (intent != null) {
//            BroadcastEvent event = new BroadcastEvent(intent);
//
//            switch (event.getType()) {
//                case CONFERENCE_JOINED:
//                    Timber.i("Conference Joined with url%s", event.getData().get("url"));
//                    break;
//                case PARTICIPANT_JOINED:
//                    Timber.i("Participant joined%s", event.getData().get("name"));
//                    break;
//            }
//        }
//    }
//    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            onBroadcastReceived(intent);
//        }
//    };

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
                Log.i("App b","b");
                return false;

            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    Log.i("App b","a");
                    return true;
                }
            }
            Log.i("App b","b");
            return false;
        }
    }

    public static boolean isAppOnForeground(Context context)
    {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            Log.i("App b","b2");
            return false;
        }
        final String packageName = "com.vedictree.preschool";
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {

                if (appProcess.processName.equals(packageName)) {
                    Log.i("App b","a2");
                return true;
            }
        }
        Log.i("App b","b2");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childe_dashboard_new);
        if (isMyServiceRunning(MusicBackground.class)) {

        } else {
//            startForegroundService(new Intent(this,MusicBackground.class));
            startService(new Intent(this, MusicBackground.class));
        }



//        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
//        mediaPlayer.start();

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){

        mVideoButton=findViewById(R.id.child_video_button);
        mActivityButton=findViewById(R.id.child_activity_button);
        mHomeworkButton=findViewById(R.id.child_homework_button);
        mChatButton=findViewById(R.id.child_chatt_button);
        mVideoImage=findViewById(R.id.video_image_icon);
        mLiveButton=findViewById(R.id.child_video_live_button);
        mLiveImage=findViewById(R.id.child_video_live_image);
        mLockChat=findViewById(R.id.lock_chat);
        mLockHomework=findViewById(R.id.lock_homework);
        mLockLive=findViewById(R.id.lock_live);

        mActivityImage=findViewById(R.id.activities_image);
        mHomeworkImage=findViewById(R.id.homework_image);
        mChatImage=findViewById(R.id.chat_image);
        mParentPin=findViewById(R.id.child_parent_pin);
        mLogout=findViewById(R.id.child_logout);
        mLogo=findViewById(R.id.main_dashboard_logo);
        mHomeworkCount=findViewById(R.id.homework_count);
        mChatCount=findViewById(R.id.chat_count);
        mTodaySessionLL=findViewById(R.id.todaySessionll);
        mLiveSessionLL=findViewById(R.id.liveSessionll);
        mVideoSessionLL=findViewById(R.id.videoSessionll);
        mChatSessionLL=findViewById(R.id.chatSessionll);

        mLockLive.setOnClickListener(this);
        mVideoImage.setOnClickListener(this);
        mLiveImage.setOnClickListener(this);
        mLiveButton.setOnClickListener(this);
        mActivityImage.setOnClickListener(this);
        mHomeworkImage.setOnClickListener(this);
        mChatImage.setOnClickListener(this);
        mVideoButton.setOnClickListener(this);
        mActivityButton.setOnClickListener(this);
        mHomeworkButton.setOnClickListener(this);
        mChatButton.setOnClickListener(this);
        mParentPin.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mLockHomework.setOnClickListener(this);
        mLockChat.setOnClickListener(this);

         soundPool=new SoundPool.Builder()
                .setMaxStreams(6)
                .build();


//        a = soundPool.load(this, R.raw.g1, 1);
//         b=soundPool.load(this,R.raw.gd2,1);
//         c=soundPool.load(this,R.raw.cd2,1);
//         d=soundPool.load(this,R.raw.c2,1);


        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int soundID, int status) {

            }
        });

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences. edit();
        student_id=preferences.getString("STUDENT_ID","");
        class_string=preferences.getString("CLASS","");
            mTodaySession=findViewById(R.id.today_session);
            mTodaySession.setOnClickListener(this);

            setImages();

        setChatCount();
        setChatCountNew();
        setHomeworkCount();
        showYouTubeVideo(this);
        String account_paid_unpaid=preferences.getString("ACCOUNT_PAID","");
        if (account_paid_unpaid.equals("Yes")){
            mLockHomework.setVisibility(View.GONE);
            mLockChat.setVisibility(View.GONE);
            mLockLive.setVisibility(View.GONE);
            mChatImage.setAlpha(1.0f);
            mChatButton.setAlpha(1.0f);
            mHomeworkImage.setAlpha(1.0f);
            mHomeworkButton.setAlpha(1.0f);
            mLiveImage.setAlpha(1.0f);
            mLiveButton.setAlpha(1.0f);

//            showNextSession();
        }
        else {
//            showNextSession();
            mLockHomework.setVisibility(View.VISIBLE);
            mLockChat.setVisibility(View.VISIBLE);
            mLockLive.setVisibility(View.VISIBLE);
            mChatImage.setAlpha(0.6f);
            mChatButton.setAlpha(0.6f);
            mHomeworkImage.setAlpha(0.6f);
            mHomeworkButton.setAlpha(0.6f);
            mLiveImage.setAlpha(0.6f);
            mLiveButton.setAlpha(0.6f);
        }
        }
        else {
            setNetworkPopUp(this);
        }


        if (class_string.equals("1") || class_string.equals("2")|| class_string.equals("3")){
            mTodaySessionLL.setVisibility(View.GONE);
            mVideoSessionLL.setVisibility(View.VISIBLE);
            mLiveSessionLL.setVisibility(View.VISIBLE);
            mLiveImage.setVisibility(View.VISIBLE);
            mLiveButton.setVisibility(View.VISIBLE);
            mVideoButton.setVisibility(View.VISIBLE);
            mVideoImage.setVisibility(View.VISIBLE);
            mTodaySession.setVisibility(View.GONE);
        }else if (class_string.equals("4") || class_string.equals("5")|| class_string.equals("6")) {
            mLiveImage.setVisibility(View.GONE);
            mLiveButton.setVisibility(View.GONE);
            mVideoButton.setVisibility(View.GONE);
            mVideoImage.setVisibility(View.GONE);
            mTodaySession.setVisibility(View.VISIBLE);
            mTodaySessionLL.setVisibility(View.VISIBLE);
            mVideoSessionLL.setVisibility(View.GONE);
            mLiveSessionLL.setVisibility(View.GONE);

        }else  {
            mLiveImage.setVisibility(View.GONE);
            mLiveButton.setVisibility(View.GONE);
            mVideoButton.setVisibility(View.GONE);
            mVideoImage.setVisibility(View.GONE);
            mTodaySession.setVisibility(View.VISIBLE);
            mTodaySessionLL.setVisibility(View.VISIBLE);
            mVideoSessionLL.setVisibility(View.GONE);
            mLiveSessionLL.setVisibility(View.GONE);

        }



    }

    private void showYouTubeVideo(Context context) {
        String check_you_tube_status=preferences.getString("YOU_TUBE","");
        if (check_you_tube_status.equals("read")){

        }else {
            Intent intent = new Intent(getApplicationContext(), YouTubePopUp.class);
            startActivity(intent); editor.putString("YOU_TUBE","read");
            editor.commit();
        }


    }

    private void setImages() {
        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(2.0f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

//        Glide.with(mLiveImage.getContext()).load("https://www.vedictreeschool.com/vtmobapp/images/live_image.png").into(mLiveImage);
        Glide.with(mVideoImage.getContext()).load("https://www.vedictreeschool.com/vtmobapp/images/video_logo.png").into(mVideoImage);

        Glide.with(mActivityImage.getContext())
                .asBitmap()
                .load("https://www.vedictreeschool.com/vtmobapp/images/creativity_icon.png")
                .placeholder(circularProgressDrawable)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                Transition<? super Bitmap> transition) {
                        int w = bitmap.getWidth();
                        int h = bitmap.getHeight();
                        mActivityImage.setImageBitmap(bitmap);
                    }
                });

        Glide.with(mHomeworkImage.getContext())
                .asBitmap()
                .load("https://www.vedictreeschool.com/vtmobapp/images/home_work_icon.png")
                .placeholder(circularProgressDrawable)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                Transition<? super Bitmap> transition) {
                        int w = bitmap.getWidth();
                        int h = bitmap.getHeight();
                        mHomeworkImage.setImageBitmap(bitmap);
                    }
                });
        Glide.with(mChatImage.getContext())
                .asBitmap()
                .load("https://www.vedictreeschool.com/vtmobapp/images/chat_icon.png")
                .placeholder(circularProgressDrawable)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                Transition<? super Bitmap> transition) {
                        int w = bitmap.getWidth();
                        int h = bitmap.getHeight();
                        mChatImage.setImageBitmap(bitmap);
                    }
                });

        Glide.with(mLiveButton.getContext()).load("https://www.vedictreeschool.com/vtmobapp/images/live_button.png").placeholder(circularProgressDrawable).into(mLiveButton);
        Glide.with(mVideoButton.getContext()).load("https://www.vedictreeschool.com/vtmobapp/images/video_button.png")
                .placeholder(circularProgressDrawable)
                .into(mVideoButton);
        Glide.with(mActivityButton.getContext()).load("https://www.vedictreeschool.com/vtmobapp/images/activity_button.png")
                .placeholder(circularProgressDrawable)
                .into(mActivityButton);
        Glide.with(mHomeworkButton.getContext()).load("https://www.vedictreeschool.com/vtmobapp/images/homework_button.png").into(mHomeworkButton);
        Glide.with(mChatButton.getContext()).load("https://www.vedictreeschool.com/vtmobapp/images/chat_new_button.png").into(mChatButton);


        Glide.with(mLiveImage.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/live_image.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
        .into(mLiveImage);


        Glide.with(mLiveImage.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/live_image.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.i("onLoadFailed","onLoadFailed");
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.i("onResourceReady","onResourceReady");
                        return false;
                    }
                })
                .into(mLiveImage);

//        Glide.with(mTodaySession.getContext())
//                .load("https://www.vedictreeschool.com/vtmobapp/images/live_image.png")
//                .placeholder(circularProgressDrawable)
//                .listener(new RequestListener<Drawable>() {
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(mTodaySession);

//        Glide.with(mParentPin.getContext())
//                .load("https://www.vedictreeschool.com/vtmobapp/images/parent_login_image.png")
//                .placeholder(circularProgressDrawable)
//                .listener(new RequestListener<Drawable>() {
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(mParentPin);

        Glide.with(mLogout.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/close_button_image.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mLogout);

        Glide.with(mLogo.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/logo_ui_two.png")
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

//       Handler newhandler = new Handler();
//        newhandler .postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressBar.setVisibility(View.GONE);
//            }
//        }, 5000);

    }

    private void check_live_session() {
        APIInterface service2 = RetrofitSignletonTeacher.getLiveIntrface();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        String currentDateandTime = sdf.format(new Date());
        Log.i("Current time:",currentDateandTime);

        Calendar calendar;
        SimpleDateFormat dateFormat;
        String date;
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("hh:mma");
        date = dateFormat.format(calendar.getTime());

        Call<live_session> call2 = service2.getLiveSession(class_string,student_id);
        call2.enqueue(new Callback<live_session>() {
            @Override
            public void onResponse(Call<live_session> call, Response<live_session> response) {
                if (response.body() != null) {
                    if (response.body().getGetfeesdata()!=null) {
                        if (!response.body().getGetfeesdata().isEmpty()) {
                            get_live_session_detailList=response.body().getGetfeesdata();
//
                            String date2=get_live_session_detailList.get(0).getStartDate();
                            String end_date2=get_live_session_detailList.get(0).getStartTime();
                            String end_date3=get_live_session_detailList.get(0).getEndTime();

                            try {
                                Date start_date=new SimpleDateFormat("yyyy-MM-dd").parse(date2);

                                Date s_time=new SimpleDateFormat("hh:mma").parse(end_date2);
                                Date e_time=new SimpleDateFormat("hh:mma").parse(end_date3);

                                String date3=sdf.format(start_date);
                                String s_str=dateFormat.format(s_time);
                                String e_str=dateFormat.format(e_time);

                                String date_diff=String.valueOf(currentDateandTime.compareTo(date3));
                                if (date_diff.equals("0")) {
                                    String match_string = getDatesBetween(s_str,e_str, date);
                                    if (match_string.equals("Match")) {
                                        Log.i("currentDateandTime", match_string);
                                        Log.i("currentDateandTime", get_live_session_detailList.get(0).getStartDate());

                                        Rect displayRectangle = new Rect();
                                        Window window = ChildeDashboardNew.this.getWindow();
                                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ChildeDashboardNew.this);
                                        ViewGroup viewGroup = findViewById(android.R.id.content);
                                        View dialog = LayoutInflater.from(ChildeDashboardNew.this).inflate(R.layout.live_sessions_pop_up, viewGroup, false);
                                        dialog.setMinimumWidth((int) (displayRectangle.width() * 1f));
                                        dialog.setMinimumHeight((int) (displayRectangle.height() * 1f));
                                        builder.setView(dialog);
                                        ImageView live_session_connect = dialog.findViewById(R.id.connectLiveSession);

                                        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(live_session_connect.getContext());
                                        circularProgressDrawable.setStrokeWidth(0.5f);
                                        circularProgressDrawable.setCenterRadius(50f);
                                        circularProgressDrawable.start();
                                        Glide.with(live_session_connect.getContext())
                                                .load("https://www.vedictreeschool.com/vtmobapp/images/image_live.png")
                                                .placeholder(circularProgressDrawable)
                                                .listener(new RequestListener<Drawable>() {
                                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                        return false;
                                                    }
                                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                        return false;
                                                    }
                                                })
                                                .into(live_session_connect);

                                        live_session_connect.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Uri uri = Uri.parse(response.body().getGetfeesdata().get(0).getMicrosoftLink());
                                                String server = uri.getAuthority();

                                                String androidStrings = server;
                                                String app_name = "";
                                                PackageManager pm = getApplicationContext().getPackageManager();
                                                if ((androidStrings).contains("zoom")) {
                                                    package_name = "us.zoom.videomeetings";
                                                    app_name = "Zoom app.";

                                                } else if ((androidStrings).contains("microsoft")) {
                                                    package_name = "com.microsoft.teams";
                                                    app_name = "Microsoft team app.";
                                                } else if ((androidStrings).contains("youtube")) {
                                                    package_name = "com.google.android.youtube";
                                                    app_name = "Youtube app.";
                                                } else {
                                                    Uri webUri1 = Uri.parse(response.body().getGetfeesdata().get(0).getMicrosoftLink());
                                                    Intent webIntent1 = new Intent(Intent.ACTION_VIEW, webUri1);
                                                    startActivity(webIntent1);
                                                }
                                                if (app_name.equals("Youtube app.")) {
                                                    String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
                                                    Pattern compiledPattern = Pattern.compile(pattern);
                                                    Matcher matcher = compiledPattern.matcher(uri.toString());
                                                    if (matcher.find()) {
                                                        Log.i("Match is", matcher.group());
                                                        editor.putString("YOU_TUBE_ID", matcher.group());
                                                        editor.commit();
                                                        Intent webIntent1 = new Intent(ChildeDashboardNew.this, YouTubeVideo.class);
                                                        startActivity(webIntent1);
                                                    } else {
                                                        Log.i("Video id not found", "Video id not found for liva session");
                                                    }

                                                } else {

                                                    boolean isInstalled = isPackageInstalled(package_name, pm);
                                                    if (isInstalled) {

                                                        Log.i("Package", package_name);
                                                        Log.i("App Installed", response.body().getGetfeesdata().get(0).getMicrosoftLink());
                                                        try {
                                                            Uri webUri1 = Uri.parse(response.body().getGetfeesdata().get(0).getMicrosoftLink());
                                                            Intent webIntent1 = new Intent(Intent.ACTION_VIEW, webUri1);
                                                            startActivity(webIntent1);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    } else {
                                                        Log.i("App not Installed", "App not installed");
                                                        AlertDialog.Builder builder3 = new AlertDialog.Builder(view.getContext());
                                                        builder3.setTitle("Meeting alert")
                                                                .setMessage("You do not have" + " " + app_name + " " + "Please install to continue?")
                                                                .setCancelable(false)
                                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + package_name)));
                                                                    }
                                                                });
                                                        AlertDialog dialog_team = builder3.create();
                                                        dialog_team.show();

                                                    }
                                                }
                                            }

                                        });
                                        final AlertDialog alertDialog = builder.create();
                                        Button dissmiss_popup = dialog.findViewById(R.id.dismiss_live_pop_up);
                                        dissmiss_popup.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                        alertDialog.show();
                                        alertDialog.setCancelable(false);
                                        alertDialog.setCanceledOnTouchOutside(false);
                                    }
                                    else {
                                        Dialog alertDialog = new Dialog(ChildeDashboardNew.this);
                                        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        alertDialog.setContentView(R.layout.no_session_pop_up);
                                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        Button button=alertDialog.findViewById(R.id.no_session_button);
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
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Dialog alertDialog = new Dialog(ChildeDashboardNew.this);
                            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            alertDialog.setContentView(R.layout.no_session_pop_up);
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            Button button=alertDialog.findViewById(R.id.no_session_button);
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
                    }
                    else {
                        Dialog alertDialog = new Dialog(ChildeDashboardNew.this);
                        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alertDialog.setContentView(R.layout.no_session_pop_up);
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Button button=alertDialog.findViewById(R.id.no_session_button);
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
                }
                else {
                    Dialog alertDialog = new Dialog(ChildeDashboardNew.this);
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.setContentView(R.layout.no_session_pop_up);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Button button=alertDialog.findViewById(R.id.no_session_button);
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
            }

            @Override
            public void onFailure(Call<live_session> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
//        Intent webIntent1 = new Intent(ChildeDashboardNew.this, VideoCallActivity.class);
//        startActivity(webIntent1);

//        URL serverURL;
//        try {
//            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
//            serverURL = new URL("https://meet.jit.si/");
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Invalid server URL!");
//        }
//        JitsiMeetConferenceOptions defaultOptions
//                = new JitsiMeetConferenceOptions.Builder()
//                .setServerURL(serverURL)
//                // When using JaaS, set the obtained JWT here
//                //.setToken("MyJWT")
//                // Different features flags can be set
//                // .setFeatureFlag("toolbox.enabled", false)
//                // .setFeatureFlag("filmstrip.enabled", false)
//                .setFeatureFlag("welcomepage.enabled", false)
//                .build();
//        JitsiMeet.setDefaultConferenceOptions(defaultOptions);
//
//        registerForBroadcastMessages();
//
//        JitsiMeetConferenceOptions options
//                = new JitsiMeetConferenceOptions.Builder()
//                .setRoom("TerrificLeisuresForceIronically")
//                .setServerURL(serverURL)
//                // Settings for audio and video
//                //.setAudioMuted(true)
//                //.setVideoMuted(true)
//                .build();
//        // Launch the new activity with the given options. The launch() method takes care
//        // of creating the required Intent and passing the options.
//        JitsiMeetActivity.launch(this, options);
    }

    private String getDatesBetween(String startDate, String endDate, String currentDate) {
        String match_str = "";
        try {
            Date date1=new SimpleDateFormat("hh:mma").parse(startDate);
            Date date2=new SimpleDateFormat("hh:mma").parse(endDate);
            Date date3=new SimpleDateFormat("hh:mma").parse(currentDate);
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

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void setHomeworkCount() {
        ArrayList<String> homework_notification = new ArrayList<>();
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
                    if (testPojoResponseList!=null) {
                        for (int i = 0; i < testPojoResponseList.size(); i++) {
                            if (testPojoResponseList.get(i).getFk_studentId().equals(student_id)) {
                                if (testPojoResponseList.get(i).getNotification_status().equals("1")) {
                                    homework_notification.add(String.valueOf(i));
                                } else {

                                }
                            }
                        }

                        if (homework_notification.isEmpty()){
//                            mHomeworkCount.setText("0");
                            mHomeworkCount.setVisibility(View.GONE);
                        }else {
                            mHomeworkCount.setVisibility(View.VISIBLE);
                            mHomeworkCount.setText(String.valueOf(homework_notification.size()));
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<TestPojo> call, Throwable t) {
            }
        });
    }

    private void setChatCount() {
        ArrayList<String> read_unread_count=new ArrayList<>();
        String student_id=preferences.getString("STUDENT_ID","");
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

                                }else {
                                    read_unread_count.add(chatHistoryDetails.get(i).getReadMsg());
                                }
                            }

                        }

                    }
                    Log.i("Chat Count", String.valueOf(read_unread_count.size()));
                    if (read_unread_count.isEmpty()) {
                        mChatCount.setVisibility(View.GONE);
//                            mChatCount.setText("0");
                    }
                    else {
                        mChatCount.setVisibility(View.VISIBLE);
                        mChatCount.setText(String.valueOf(read_unread_count.size()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatHistory> call, Throwable t) {

            }
        });
    }

    private  void setChatCountNew(){
        ArrayList<String> read_unread_count=new ArrayList<>();
        String student_id=preferences.getString("STUDENT_ID","");

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
//                            if (chatRoleResponseList != null) {
//                                for (int i = 0; i < chatRoleResponseList.size(); i++) {
//                                    if (chatRoleResponseList.get(i).getChatf() == 0) {
//
//                                    } else {
//                                        JsonObject jsonObject = new JsonObject();
//                                        jsonObject.addProperty("rlid", chatRoleResponseList.get(i).getRoleid());
//                                        jsonObject.addProperty("studid", Integer.parseInt(student_id));
//                                        Call<ChatHistoryNEw> call_count = service.getNewChat(jsonObject);
//                                        call_count.enqueue(new Callback<ChatHistoryNEw>() {
//                                            @SuppressLint("LongLogTag")
//                                            @Override
//                                            public void onResponse(@NotNull Call<ChatHistoryNEw> call, @NotNull Response<ChatHistoryNEw> response) {
//                                                if (response.body() != null) {
//                                                    ChatHistoryNEw chatHistory = response.body();
//                                                    if (response.body() != null) {
//                                                        if (chatHistory.getData() != null) {
//                                                            chatHistoryNEwDetailList = chatHistory.getData();
//                                                            for (int i = 0; i < chatHistoryNEwDetailList.size(); i++) {
//                                                                if (chatHistoryNEwDetailList.get(i).getRdstat() == 0) {
//                                                                    read_unread_count.add(chatHistoryNEwDetailList.get(i).getChtdtt());
//                                                                } else {
//
//                                                                }
//                                                                Log.i("Chat Count", String.valueOf(read_unread_count.size()));
//                                                                if (read_unread_count.isEmpty()) {
//                                                                    mChatCount.setVisibility(View.GONE);
//                                                                } else {
//                                                                    mChatCount.setVisibility(View.VISIBLE);
//                                                                    mChatCount.setText(String.valueOf(read_unread_count.size()));
//                                                                }
//                                                            }
//                                                        }
//
//                                                    }
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onFailure(@NotNull Call<ChatHistoryNEw> call, @NotNull Throwable t) {
//                                                Log.i("Error", String.valueOf(t.getMessage()));
//                                            }
//                                        });
//
//                                    }
//                                }
//                            }
//                        }
//
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
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );


        if(isMyServiceRunning(MusicBackground.class)){
        }else {
//            startForegroundService(new Intent(this,MusicBackground.class));

            startService(new Intent(this,MusicBackground.class));
        }

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
                        Intent login_intent=new Intent(ChildeDashboardNew.this, LoginActivity.class);
                        login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login_intent);
                        finish();

//                        mediaPlayer.stop();
                        Toast.makeText(getApplicationContext(),"User Already Login in another device.Please login to continue", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Login_token> call, Throwable t) {
            }
        });


    }

//    private void registerForBroadcastMessages() {
//        IntentFilter intentFilter = new IntentFilter();

        /* This registers for every possible event sent from JitsiMeetSDK
           If only some of the events are needed, the for loop can be replaced
           with individual statements:
           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.getAction());
                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.getAction());
                ... other events
         */
//        for (BroadcastEvent.Type type : BroadcastEvent.Type.values()) {
//            intentFilter.addAction(type.getAction());
//        }

//        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        isAppOnForeground(this);
        isApplicationGoingToBackground(this);
//        try {
//            boolean foregroud = new ForegroundCheckTask().execute(this).get();
//            if(foregroud){
//                stopService(new Intent(this,MusicBackground.class));//stop on navigation
//            }else {
////                stopService(new Intent(this,MusicBackground.class)); //not working
//            }
//        } catch (ExecutionException | InterruptedException executionException) {
//            executionException.printStackTrace();
//        }
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
                if (!topActivity.getPackageName().equals(context.getPackageName())) {
                    Log.i("App b", "a3");
                    return true;
                }
            }

        return false;
    }
    @Override
    protected void onStop() {
//        mediaPlayer.pause();
//        stopService(new Intent(this,MusicBackground.class));
        super.onStop();

    }

    private void setNetworkPopUp(Context context) {
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

    @Override
    public void onClick(View view) {
        Intent intent;
        String account_paid_unpaid=preferences.getString("ACCOUNT_PAID","");
        switch (view.getId()){
            case R.id.child_video_button:
                mVideoButton.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                soundPool.play(a,1f,1f,1,0,1);
                intent=new Intent(ChildeDashboardNew.this,VideoSessionActivity.class);
                startActivity(intent);
                finish();
//                mediaPlayer.stop();
                break;

            case R.id.video_image_icon:
                mVideoImage.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                soundPool.play(a,1f,1f,1,0,1);
                intent=new Intent(ChildeDashboardNew.this,VideoSessionActivity.class);
                startActivity(intent);
                finish();
//                mediaPlayer.stop();
                break;

            case R.id.child_activity_button:
                mActivityButton.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                soundPool.play(b,1f,1f,1,0,1);
                intent=new Intent(ChildeDashboardNew.this,ActivitiesActivity.class);
                startActivity(intent);
                finish();
//                mediaPlayer.stop();
                break;

            case R.id.activities_image:
                mActivityImage.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                soundPool.play(b,1f,1f,1,0,1);
                 intent=new Intent(ChildeDashboardNew.this,ActivitiesActivity.class);
                startActivity(intent);
                finish();
//                mediaPlayer.stop();

                break;

            case R.id.child_homework_button:
                if (account_paid_unpaid.equals("Yes")) {
                    mHomeworkButton.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                    soundPool.play(c, 1f, 1f, 1, 0, 1);
                    intent = new Intent(ChildeDashboardNew.this, HomeworkNew.class);
                    startActivity(intent);
                    finish();
//                    mediaPlayer.stop();
                }else {
                    showNextSession();
                }
                break;

            case R.id.homework_image:
                if (account_paid_unpaid.equals("Yes")) {
                    mHomeworkImage.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                    soundPool.play(c, 1f, 1f, 1, 0, 1);
                    intent = new Intent(ChildeDashboardNew.this, HomeworkNew.class);
                    startActivity(intent);
                    finish();
//                    mediaPlayer.stop();
                }else {
                    showNextSession();
                }
                break;

            case R.id.chat_image:
                if (account_paid_unpaid.equals("Yes")) {
                    mChatImage.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                    soundPool.play(d, 1f, 1f, 1, 0, 1);
                    intent = new Intent(ChildeDashboardNew.this, NewChatActivity.class);
                    startActivity(intent);
                    finish();
//                    mediaPlayer.stop();
                }else {
                    showNextSession();
                }
                break;

            case R.id.child_chatt_button:
                if (account_paid_unpaid.equals("Yes")) {
                    mChatButton.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                    soundPool.play(d, 1f, 1f, 1, 0, 1);
                    intent = new Intent(ChildeDashboardNew.this, NewChatActivity.class);
                    startActivity(intent);
                    finish();
//                    mediaPlayer.stop();

                }else {
                    showNextSession();
                }
                break;

            case R.id.child_video_live_button:
                if (account_paid_unpaid.equals("Yes")) {
                    mLiveButton.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                    soundPool.play(d, 1f, 1f, 1, 0, 1);
                    check_live_session();
                }else {
                    showNextSession();
                }
                break;

            case R.id.child_video_live_image:
                if (account_paid_unpaid.equals("Yes")) {
                    mLiveImage.startAnimation(AnimationUtils.loadAnimation(ChildeDashboardNew.this, R.anim.shake));
//                    soundPool.play(d, 1f, 1f, 1, 0, 1);
                    check_live_session();
                }else {
                    showNextSession();
                }
                break;

            case R.id.child_parent_pin:
                final ValueAnimator anim2 = ValueAnimator.ofFloat(1f, 1.05f);
                anim2.setDuration(300);
                anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mParentPin.setScaleX((Float) animation.getAnimatedValue());
                        mParentPin.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim2.setRepeatCount(1);
                anim2.setRepeatMode(ValueAnimator.REVERSE);
                anim2.start();
                intent = new Intent(ChildeDashboardNew.this, ProfileActivityNew.class);
                startActivity(intent);
//                setParentPinPouUp(view.getContext());
                break;

            case R.id.child_logout:
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mLogout.setScaleX((Float) animation.getAnimatedValue());
                        mLogout.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                setLogoutPopUp(view.getContext());
                break;

            case R.id.lock_chat:
                showNextSession();
                break;
            case R.id.lock_homework:
                showNextSession();
                break;
            case R.id.lock_live:
                showNextSession();
                break;
            case R.id.today_session:
                Intent intent1=new Intent(ChildeDashboardNew.this,TodaySessionActivity.class);
                startActivity(intent1);
                break;

        }
    }

    private void setLogoutPopUp(Context context) {
        Dialog alertDialog = new Dialog(this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.exit_pop_up);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button button=alertDialog.findViewById(R.id.exit_yes_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                stopService(new Intent(view.getContext(),MusicBackground.class));
//                mediaPlayer.stop();
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
                finish();
            }
        });

        Button no_button=alertDialog.findViewById(R.id.exit_no_button);
        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                final ValueAnimator no_button_text = ValueAnimator.ofFloat(1f, 1.05f);
                no_button_text.setDuration(300);
                no_button_text.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        no_button.setScaleX((Float) animation.getAnimatedValue());
                        no_button.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                no_button_text.setRepeatCount(1);
                no_button_text.setRepeatMode(ValueAnimator.REVERSE);
                no_button_text.start();
            }
        });
        alertDialog.show();
    }

    private void setParentPinPouUp(Context context) {
        Dialog alertDialog = new Dialog(this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.child_parent_pin_pop_up);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        String parent_pin=preferences.getString("PARENT_PIN_LOGIN","");
        String mobile_number=preferences.getString("MOBILE","");

        TextView parent_pin_label=alertDialog.findViewById(R.id.parent_pin_label);
        LinearLayout mParentPinll=alertDialog.findViewById(R.id.parent_pin_ll);
        LinearLayout mForgotParentPinll=alertDialog.findViewById(R.id.forgot_parent_pin_ll);

        Button forgot_pin=alertDialog.findViewById(R.id.forgot_pin_new);
        Button set_pin=alertDialog.findViewById(R.id.ok_pin_new);
        Button skip_button=alertDialog.findViewById(R.id.skip_forgot_pin);
        Button submit_forgot_pin=alertDialog.findViewById(R.id.submit_pin_new);

        submit_forgot_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialog = LayoutInflater.from(view.getContext()).inflate(R.layout.progress_layout, viewGroup, false);
                builder.setView(dialog);
                AlertDialog alertDialog_onCreate= builder.create();
                alertDialog_onCreate.show();
                APIInterface service = RetrofitSignleton.getAPIInterface();
                Call<Register> call_resetPin = service.setParentPin(forgot_pinview.getText().toString(),student_id);
                call_resetPin.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if (response.body() != null) {
                            checkParentPin();
                            Toast.makeText(getApplicationContext(), "Parent pin reset successfully", Toast.LENGTH_LONG).show();
                            alertDialog_onCreate.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        alertDialog_onCreate.dismiss();
                    }
                });
            }
        });
        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if (parent_pin.equals("0")){
            parent_pin_label.setText("SET PARENT PIN");
            forgot_pin.setVisibility(View.GONE);
            set_pin.setVisibility(View.VISIBLE);
        }else {
            parent_pin_label.setText("ENTER PIN");
            forgot_pin.setVisibility(View.VISIBLE);
            set_pin.setVisibility(View.GONE);
        }
        pinView=alertDialog.findViewById(R.id.firstPinView);
        forgot_pinview=alertDialog.findViewById(R.id.forgot_firstPinView);

        ImageView imageView=alertDialog.findViewById(R.id.eye_view);
        ImageView forgot_imageView=alertDialog.findViewById(R.id.forgot_eye_view);

        imageView.setImageResource(R.drawable.eye);
        forgot_imageView.setImageResource(R.drawable.eye);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        imageView.setScaleX((Float) animation.getAnimatedValue());
                        imageView.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                String pin_str="";
                if (pinView.getText().equals(""))
                {
                    pin_str="";
                }
                pin_str=pinView.getText().toString();

                if (pinView.isPasswordHidden()){
                    pinView.setPasswordHidden(false);
                ((ImageView)(view)).setImageResource(R.drawable.invisible);
                pinView.setText(pin_str);
                }else {
                    pinView.setPasswordHidden(true);
                    ((ImageView)(view)).setImageResource(R.drawable.eye);
                    pinView.setText(pin_str);

                }
            }
        });
        forgot_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        forgot_imageView.setScaleX((Float) animation.getAnimatedValue());
                        forgot_imageView.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                String pin_str="";
                if (forgot_pinview.getText().equals(""))
                {
                    pin_str="";
                }
                pin_str=forgot_pinview.getText().toString();

                if (forgot_pinview.isPasswordHidden()){
                    forgot_pinview.setPasswordHidden(false);
                    ((ImageView)(view)).setImageResource(R.drawable.invisible);
                    forgot_pinview.setText(pin_str);
                }else {
                    forgot_pinview.setPasswordHidden(true);
                    ((ImageView)(view)).setImageResource(R.drawable.eye);
                    forgot_pinview.setText(pin_str);

                }
            }
        });

         one=alertDialog.findViewById(R.id.child_key_one);
        ImageView two=alertDialog.findViewById(R.id.child_key_two);
        ImageView three=alertDialog.findViewById(R.id.child_key_three);
        ImageView four=alertDialog.findViewById(R.id.child_key_four);
        ImageView five=alertDialog.findViewById(R.id.child_key_five);
        ImageView six=alertDialog.findViewById(R.id.child_key_six);
        ImageView seven=alertDialog.findViewById(R.id.child_key_seven);
        ImageView eight=alertDialog.findViewById(R.id.child_key_eight);
        ImageView nine=alertDialog.findViewById(R.id.child_key_nine);
        ImageView zero=alertDialog.findViewById(R.id.child_key_zero);
        ImageView back_space=alertDialog.findViewById(R.id.child_key_backspace);
        ImageView close_logout=alertDialog.findViewById(R.id.exit_logout);

        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(0.5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Glide.with(one.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/one_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(one);

        Glide.with(two.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/two_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(two);

        Glide.with(three.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/three_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(three);
        Glide.with(four.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/four_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(four);
        Glide.with(five.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/five_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(five);

        Glide.with(six.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/six_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(six);
        Glide.with(seven.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/seven_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(seven);

        Glide.with(eight.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/eight_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(eight);
        Glide.with(nine.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/nine_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(nine);

        Glide.with(zero.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/zero_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(zero);

        Glide.with(back_space.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/backspace.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(back_space);

        Glide.with(close_logout.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/close_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(close_logout);

        set_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                    anim3.setDuration(300);
                    anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            eight.setScaleX((Float) animation.getAnimatedValue());
                            eight.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    anim3.setRepeatCount(1);
                    anim3.setRepeatMode(ValueAnimator.REVERSE);
                    anim3.start();

                    if (pinView.getText().toString().length()<4||pinView.getText().length()>4){
                        Toast.makeText(getApplicationContext(), "Set Pin of 4 digit", Toast.LENGTH_LONG).show();

                    }else {
                        APIInterface service = RetrofitSignleton.getAPIInterface();
                        Call<Register> call = service.setParentPin(pinView.getText().toString(), student_id);
                        call.enqueue(new Callback<Register>() {
                            @Override
                            public void onResponse(Call<Register> call, Response<Register> response) {
                                if (response.body() != null) {
                                    checkParentPin();

                                }
                            }

                            @Override
                            public void onFailure(Call<Register> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
        close_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        close_logout.setScaleX((Float) animation.getAnimatedValue());
                        close_logout.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                alertDialog.dismiss();
            }
        });
        forgot_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        forgot_pin.setScaleX((Float) animation.getAnimatedValue());
                        forgot_pin.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();

                mParentPinll.setVisibility(View.GONE);
                forgot_pin.setVisibility(View.GONE);
                mForgotParentPinll.setVisibility(View.VISIBLE);
                skip_button.setVisibility(View.VISIBLE);
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        one.setScaleX((Float) animation.getAnimatedValue());
                        one.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();

//                if (pinView.getVisibility()==View.VISIBLE) {
                    if (pinView.getText().equals("")) {
                        pinView.setText("1");
                    }
                    pinView.setText(pinView.getText().toString() + "1");
//                }else if (forgot_pinview.getVisibility()==View.VISIBLE){
                    if (forgot_pinview.getText().equals("")) {
                        forgot_pinview.setText("1");
                    }
                    forgot_pinview.setText(forgot_pinview.getText().toString() + "1");
//                }

            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        two.setScaleX((Float) animation.getAnimatedValue());
                        two.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
//                if (pinView.getVisibility()==View.VISIBLE) {
                    if (pinView.getText().equals("")) {
                        pinView.setText("2");
                    }
                    pinView.setText(pinView.getText().toString() + "2");
//                }else if (forgot_pinview.getVisibility()==View.VISIBLE){
                    if (forgot_pinview.getText().equals("")) {
                        forgot_pinview.setText("2");
                    }
                    forgot_pinview.setText(forgot_pinview.getText().toString() + "2");
//                }
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        three.setScaleX((Float) animation.getAnimatedValue());
                        three.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
//                if (pinView.getVisibility()==View.VISIBLE) {
                    if (pinView.getText().equals("")) {
                        pinView.setText("3");
                    }
                    pinView.setText(pinView.getText().toString() + "3");
//                }else if (forgot_pinview.getVisibility()==View.VISIBLE){
                    if (forgot_pinview.getText().equals("")) {
                        forgot_pinview.setText("3");
                    }
                    forgot_pinview.setText(forgot_pinview.getText().toString() + "3");
//                }
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        four.setScaleX((Float) animation.getAnimatedValue());
                        four.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
//                if (pinView.getVisibility()==View.VISIBLE) {
                    if (pinView.getText().equals("")) {
                        pinView.setText("4");
                    }
                    pinView.setText(pinView.getText().toString() + "4");
//                }else if (forgot_pinview.getVisibility()==View.VISIBLE){
                    if (forgot_pinview.getText().equals("")) {
                        forgot_pinview.setText("4");
                    }
                    forgot_pinview.setText(forgot_pinview.getText().toString() + "4");
//                }
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        five.setScaleX((Float) animation.getAnimatedValue());
                        five.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
//                if (pinView.getVisibility()==View.VISIBLE) {
                    if (pinView.getText().equals("")) {
                        pinView.setText("5");
                    }
                    pinView.setText(pinView.getText().toString() + "5");
//                }else if (forgot_pinview.getVisibility()==View.VISIBLE){
                    if (forgot_pinview.getText().equals("")) {
                        forgot_pinview.setText("5");
                    }
                    forgot_pinview.setText(forgot_pinview.getText().toString() + "5");
//                }
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        six.setScaleX((Float) animation.getAnimatedValue());
                        six.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
//                if (pinView.getVisibility()==View.VISIBLE) {
                    if (pinView.getText().equals("")) {
                        pinView.setText("6");
                    }
                    pinView.setText(pinView.getText().toString() + "6");
//                }else if (forgot_pinview.getVisibility()==View.VISIBLE){
                    if (forgot_pinview.getText().equals("")) {
                        forgot_pinview.setText("6");
                    }
                    forgot_pinview.setText(forgot_pinview.getText().toString() + "6");
//                }
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        seven.setScaleX((Float) animation.getAnimatedValue());
                        seven.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                if (pinView.getText().equals(""))
                {
                    pinView.setText("7");
                }
                pinView.setText(pinView.getText().toString()+"7");

                if (forgot_pinview.getText().equals("")) {
                    forgot_pinview.setText("7");
                }
                forgot_pinview.setText(forgot_pinview.getText().toString() + "7");
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        eight.setScaleX((Float) animation.getAnimatedValue());
                        eight.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                if (pinView.getText().equals(""))
                {
                    pinView.setText("8");
                }
                pinView.setText(pinView.getText().toString()+"8");

                if (forgot_pinview.getText().equals("")) {
                    forgot_pinview.setText("8");
                }
                forgot_pinview.setText(forgot_pinview.getText().toString() + "8");
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        nine.setScaleX((Float) animation.getAnimatedValue());
                        nine.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                if (pinView.getText().equals(""))
                {
                    pinView.setText("9");
                }
                pinView.setText(pinView.getText().toString()+"9");

                if (forgot_pinview.getText().equals("")) {
                    forgot_pinview.setText("9");
                }
                forgot_pinview.setText(forgot_pinview.getText().toString() + "9");
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        zero.setScaleX((Float) animation.getAnimatedValue());
                        zero.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                if (pinView.getText().equals(""))
                {
                    pinView.setText("0");
                }
                pinView.setText(pinView.getText().toString()+"0");

                if (forgot_pinview.getText().equals("")) {
                    forgot_pinview.setText("0");
                }
                forgot_pinview.setText(forgot_pinview.getText().toString() + "0");
            }
        });
        back_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        back_space.setScaleX((Float) animation.getAnimatedValue());
                        back_space.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                int length = pinView.getText().length();
                if (length > 0) {
                    pinView.getText().delete(length - 1, length);
                }

                int length_forgot = forgot_pinview.getText().length();
                if (length_forgot > 0) {
                    forgot_pinview.getText().delete(length_forgot - 1, length_forgot);
                }

            }
        });
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    if (pinView.length()!=0) {
                        if (pinView.getText().toString().equals(parent_pin)) {
                            alertDialog.dismiss();
                            Intent login_intent = new Intent(ChildeDashboardNew.this, NewParentDashboard.class);
                            startActivity(login_intent);
                            finish();
//                            mediaPlayer.stop();
                        } else {
                            pinView.setError("Enter correct parent pin");
                        }
                    }
            }
        });
        alertDialog.show();
    }

    private void checkParentPin() {
        {
            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<parentPinPojo> call = service.studentParentPin(student_id);
            call.enqueue(new Callback<parentPinPojo>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(Call<parentPinPojo> call, Response<parentPinPojo> response) {
                    if (response.body() != null) {
                        parentPinPojo parentPinPojo = response.body();
                        parentPinPojoResponseList = parentPinPojo.getRes();
                        if (!parentPinPojoResponseList.isEmpty()) {
                            Log.i("Parent pin", parentPinPojoResponseList.get(0).getPinNumber());
                            String pin_string=parentPinPojoResponseList.get(0).getPinNumber();
                            if (pin_string.equals("0")) {
                                Intent login_intent = new Intent(ChildeDashboardNew.this, ChildeDashboardNew.class);
                                startActivity(login_intent);
                                finish();
//                                mediaPlayer.stop();

                            } else {
                                Intent login_intent = new Intent(ChildeDashboardNew.this, NewParentDashboard.class);
                                startActivity(login_intent);
                                finish();
//                                mediaPlayer.stop();
                                editor.putString("PARENT_PIN_LOGIN", parentPinPojoResponseList.get(0).getPinNumber());
                                editor.commit();
                            }


                        }
                    }

                }

                @Override
                public void onFailure(Call<parentPinPojo> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }


    @Override
    public void onBackPressed() {
        setLogoutPopUp(this);
    }

    private void showNextSession() {{
        
        String student_class=preferences.getString("CLASS","");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.enroll_pop_up, viewGroup, false);
        builder.setView(dialog);
        final AlertDialog alertDialog = builder.create();


        ArrayList<String> arr=new ArrayList<>();
        if (student_class.equals("1")){
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/j_gold_1.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/j_twink.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/j_shin.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/j_rock.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/j_super.png");

        }
        else if (student_class.equals("2")) {
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/s_gold.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/s_twink.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/s_shin.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/s_rock.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/s_super.png");
        }
        else if (student_class.equals("3")){
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_gold.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_twink.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_shine.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_rock.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_super.png");

//            arr.add(R.drawable.sr_gold);
//            arr.add(R.drawable.sr_twink);
//            arr.add(R.drawable.sr_shine);
//            arr.add(R.drawable.sr_rock);
//            arr.add(R.drawable.sr_super);
        }else {

            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_gold.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_twink.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_shine.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_rock.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_super.png");
//            arr.add(R.drawable.sr_gold);
//            arr.add(R.drawable.sr_twink);
//            arr.add(R.drawable.sr_shine);
//            arr.add(R.drawable.sr_rock);
//            arr.add(R.drawable.sr_super);
        }
        enroll_hev=dialog.findViewById(R.id.enroll_hsv);
        enroll_ll=dialog.findViewById(R.id.enroll_linearlayout);
        ImageView submit=dialog.findViewById(R.id.imageView_close);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        for (int i=0;i<arr.size();i++){
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View enroll_view = layoutInflater.inflate(R.layout.enroll_item, enroll_ll, false);
            ImageView imageView = enroll_view.findViewById(R.id.enroll_image);
            imageView.setClickable(true);
            imageView.bringToFront();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(), MyCoursesNew.class);
                    startActivity(intent);
                    alertDialog.dismiss();
                }
            });

            CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(imageView.getContext());
            circularProgressDrawable.setStrokeWidth(2.0f);
            circularProgressDrawable.setCenterRadius(50f);
            circularProgressDrawable.start();
            Glide.with(imageView.getContext())
                    .load(arr.get(i))
                    .placeholder(circularProgressDrawable)
                    .listener(new RequestListener<Drawable>() {
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);

//            imageView.setImageResource(arr.get(i));
            enroll_ll.addView(enroll_view);
        }
        ViewTreeObserver vto 		=	enroll_ll.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                enroll_ll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                getScrollMaxAmount();
                startAutoScrolling();
            }
        });

        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    }
    private void startAutoScrolling() {
        if (scrollTimer == null) {
            scrollTimer					=	new Timer();
            final Runnable Timer_Tick 	= 	new Runnable() {
                public void run() {
                    moveScrollView();
                }
            };

            if(scrollerSchedule != null){
                scrollerSchedule.cancel();
                scrollerSchedule = null;
            }
            scrollerSchedule = new TimerTask(){
                @Override
                public void run(){
                    runOnUiThread(Timer_Tick);
                }
            };

            scrollTimer.schedule(scrollerSchedule, 30, 30);
        }
    }


    private void getScrollMaxAmount() {
        int actualWidth = (enroll_ll.getMeasuredWidth()-512);
        Log.i("Value width:",String.valueOf(actualWidth));
        scrollMax   = actualWidth;
    }
    private void moveScrollView() {
        scrollPos							= 	(int) (enroll_hev.getScrollX() + 1.0);
        if(scrollPos >= scrollMax){
            scrollPos						=	0;
        }
        enroll_hev.scrollTo(scrollPos, 0);

    }

}