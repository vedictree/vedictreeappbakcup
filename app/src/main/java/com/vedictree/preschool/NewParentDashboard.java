package com.vedictree.preschool;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.POJO.Get_live_session_detail;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.Receipt_poj;
import com.vedictree.preschool.POJO.TestPojo;
import com.vedictree.preschool.POJO.TestPojoResponse;
import com.vedictree.preschool.POJO.live_session;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewParentDashboard extends AppCompatActivity implements View.OnClickListener {

    ImageView mHome;
    LinearLayout profile_ll;
    LinearLayout mProgressReport;
    LinearLayout mMyCourse;
    LinearLayout mReportCard;

    ImageView mProgressReportText;
    ImageView mMyCourseText;
    ImageView mReportCardText;
    ImageView mProfileText;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    FrameLayout mNotificationll;
    FrameLayout mLogoutll;
    private SoundPool soundPool;
    ImageView mLogo;

//    int a, b,c,d;
//    MediaPlayer mediaPlayer;

    private List<Get_live_session_detail> get_live_session_detailList;
    String package_name;
    LinearLayout mLockReportCard;

    private int scrollMax;
    private int scrollPos =	0;
    private TimerTask scrollerSchedule;
    private Timer scrollTimer		=	null;

    HorizontalScrollView enroll_hev;
    LinearLayout enroll_ll;
    ImageView mProfileImage;
    ImageView mCourseImage;
    ImageView mProgressImg;
    ImageView mReportImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_parent_dashboard);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

//        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
//        mediaPlayer.start();

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){
        }
        else {
            setLogoutPopUp(this);
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();
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
                        Intent login_intent=new Intent(NewParentDashboard.this, LoginActivity.class);
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

        soundPool=new SoundPool.Builder()
                .setMaxStreams(6)
                .build();


//        a = soundPool.load(this, R.raw.g1, 1);
//        b=soundPool.load(this,R.raw.gd2,1);
//        c=soundPool.load(this,R.raw.cd2,1);
//        d=soundPool.load(this,R.raw.c2,1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int soundID, int status) {

            }
        });

        mHome=findViewById(R.id.activity_to_home_parent_d);
        profile_ll=findViewById(R.id.profile_ddd);
        mProgressReport=findViewById(R.id.new_progress_report);
        mProgressReportText=findViewById(R.id.progress_report_new_text);
        mMyCourse=findViewById(R.id.new_my_course);
        mMyCourseText=findViewById(R.id.new_my_course_text);
        mProfileText=findViewById(R.id.parent_profile_button_new);
        mReportCard=findViewById(R.id.new_report_card);
        mReportCardText=findViewById(R.id.new_report_card_text);
        mNotificationll=findViewById(R.id.notification_ll_new);
        mLogoutll=findViewById(R.id.logout_ll_new);
        mLockReportCard=findViewById(R.id.lock_report_card);
        mLogo=findViewById(R.id.paret_dash_logo);

        mProfileImage=findViewById(R.id.profile_ddd_image);
        mCourseImage=findViewById(R.id.new_my_course_image);
        mReportImg=findViewById(R.id.new_report_card_image);
        mProgressImg=findViewById(R.id.new_progress_report_image);

        mHome.setOnClickListener(this);
        profile_ll.setOnClickListener(this);
        mProgressReport.setOnClickListener(this);
        mProgressReportText.setOnClickListener(this);
        mMyCourseText.setOnClickListener(this);
        mMyCourse.setOnClickListener(this);
        mReportCard.setOnClickListener(this);
        mReportCardText.setOnClickListener(this);
        mNotificationll.setOnClickListener(this);
        mProfileText.setOnClickListener(this);
        mLogoutll.setOnClickListener(this);
        mLockReportCard.setOnClickListener(this);

        setImage();

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

        String mClass=preferences.getString("CLASS","");
        String stude_id=preferences.getString("STUDENT_ID","");

        Call<live_session> call2 = service2.getLiveSession(mClass,stude_id);
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
                                        Window window = NewParentDashboard.this.getWindow();
                                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(NewParentDashboard.this);
                                        ViewGroup viewGroup = findViewById(android.R.id.content);
                                        View dialog = LayoutInflater.from(NewParentDashboard.this).inflate(R.layout.live_sessions_pop_up, viewGroup, false);
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
                                                        Intent webIntent1 = new Intent(NewParentDashboard.this, YouTubeVideo.class);
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
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<live_session> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        String account_paid_unpaid=preferences.getString("ACCOUNT_PAID","");
        if (account_paid_unpaid.equals("Yes")){
            mLockReportCard.setVisibility(View.GONE);
            mReportCard.setAlpha(1.0f);
            mReportCardText.setAlpha(1.0f);

        }else {
            mLockReportCard.setVisibility(View.VISIBLE);
            mReportCard.setAlpha(0.7f);
            mReportCardText.setAlpha(0.7f);
        }

        checkHomewWork();

    }

    private void setImage() {

        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(0.5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();


        Glide.with(mProfileImage.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/parent_profile_icon_two.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mProfileImage);

        Glide.with(mCourseImage.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/parent_progress_report_icon_two.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mCourseImage);

        Glide.with(mProgressImg.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/parent_courses_icon.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mProgressImg);

        Glide.with(mReportImg.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/parent_report_card_icon.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mReportImg);

        Glide.with(mProfileText.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/parent_profile_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mProfileText);

        Glide.with(mProgressReportText.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/parent_ptogress_report_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mProgressReportText);

        Glide.with(mMyCourseText.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/parent_courses_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMyCourseText);

        Glide.with(mReportCardText.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/parent_report_card_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mReportCardText);
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
                            Dialog alertDialog = new Dialog(NewParentDashboard.this);
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

    @Override
    public void onClick(View view) {
        Intent login_intent;
        String account_paid_unpaid=preferences.getString("ACCOUNT_PAID","");

        switch (view.getId()){
            case R.id.activity_to_home_parent_d:
                login_intent = new Intent(NewParentDashboard.this, ChildeDashboardNew.class);
                startActivity(login_intent);
                finish();
//                mediaPlayer.stop();
                break;

            case R.id.profile_ddd:
                final ValueAnimator anim2 = ValueAnimator.ofFloat(1f, 1.05f);
                anim2.setDuration(300);
                anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        profile_ll.setScaleX((Float) animation.getAnimatedValue());
                        profile_ll.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim2.setRepeatCount(1);
                anim2.setRepeatMode(ValueAnimator.REVERSE);
                anim2.start();
//                soundPool.play(a,1f,1f,1,0,1);
                login_intent=new Intent(NewParentDashboard.this,ProfileActivityNew.class);
                startActivity(login_intent);
                break;

            case R.id.parent_profile_button_new:
                final ValueAnimator anim_profile = ValueAnimator.ofFloat(1f, 1.05f);
                anim_profile.setDuration(300);
                anim_profile.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mProfileText.setScaleX((Float) animation.getAnimatedValue());
                        mProfileText.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim_profile.setRepeatCount(1);
                anim_profile.setRepeatMode(ValueAnimator.REVERSE);
                anim_profile.start();
//                soundPool.play(a,1f,1f,1,0,1);
                login_intent=new Intent(NewParentDashboard.this,ProfileActivityNew.class);
                startActivity(login_intent);
                break;

            case R.id.new_progress_report:
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mProgressReport.setScaleX((Float) animation.getAnimatedValue());
                        mProgressReport.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
//                soundPool.play(b,1f,1f,1,0,1);
                login_intent=new Intent(NewParentDashboard.this,ProgressReportNew.class);
                startActivity(login_intent);
                break;

            case R.id.progress_report_new_text:
                final ValueAnimator anim4 = ValueAnimator.ofFloat(1f, 1.05f);
                anim4.setDuration(300);
                anim4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mProgressReportText.setScaleX((Float) animation.getAnimatedValue());
                        mProgressReportText.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim4.setRepeatCount(1);
                anim4.setRepeatMode(ValueAnimator.REVERSE);
                anim4.start();
//                soundPool.play(b,1f,1f,1,0,1);
                login_intent=new Intent(NewParentDashboard.this,ProgressReportNew.class);
                startActivity(login_intent);
                break;

            case R.id.new_my_course:
                final ValueAnimator my_course = ValueAnimator.ofFloat(1f, 1.05f);
                my_course.setDuration(300);
                my_course.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mMyCourse.setScaleX((Float) animation.getAnimatedValue());
                        mMyCourse.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                my_course.setRepeatCount(1);
                my_course.setRepeatMode(ValueAnimator.REVERSE);
                my_course.start();
//                soundPool.play(c,1f,1f,1,0,1);
                editor.putString("MY_COURSE_FROM","parent_dashboard");
                editor.commit();
                login_intent=new Intent(NewParentDashboard.this,MyCoursesNew.class);
                startActivity(login_intent);
                break;

            case R.id.new_my_course_text:
                final ValueAnimator my_course_text = ValueAnimator.ofFloat(1f, 1.05f);
                my_course_text.setDuration(300);
                my_course_text.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mMyCourseText.setScaleX((Float) animation.getAnimatedValue());
                        mMyCourseText.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                my_course_text.setRepeatCount(1);
                my_course_text.setRepeatMode(ValueAnimator.REVERSE);
                my_course_text.start();
//                soundPool.play(c,1f,1f,1,0,1);
                editor.putString("MY_COURSE_FROM","parent_dashboard");
                editor.commit();
                login_intent=new Intent(NewParentDashboard.this,MyCoursesNew.class);
                startActivity(login_intent);
                break;
            case R.id.new_report_card:
                if(account_paid_unpaid.equals("Yes")) {
                    final ValueAnimator card_text_one = ValueAnimator.ofFloat(1f, 1.05f);
                    card_text_one.setDuration(300);
                    card_text_one.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mReportCard.setScaleX((Float) animation.getAnimatedValue());
                            mReportCard.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    card_text_one.setRepeatCount(1);
                    card_text_one.setRepeatMode(ValueAnimator.REVERSE);
                    card_text_one.start();
//                    soundPool.play(d, 1f, 1f, 1, 0, 1);
                    String student_id2 = preferences.getString("STUDENT_ID", "");
                    APIInterface service2 = RetrofitSignleton.getAPIInterface();
                    Call<Receipt_poj> receipt_call2 = service2.semOneReportCard(student_id2);
                    receipt_call2.enqueue(new Callback<Receipt_poj>() {
                        @Override
                        public void onResponse(Call<Receipt_poj> call, Response<Receipt_poj> response) {
                            if (response.body() != null) {
                                String payment_utl = response.body().getPaymentUrl();
                                if (!payment_utl.equals("")) {
                                    if (!payment_utl.isEmpty()) {
                                        Intent report_card_intent = new Intent(NewParentDashboard.this, ReportCardNew.class);
                                        startActivity(report_card_intent);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "No report available", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No report available", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Receipt_poj> call, Throwable t) {
                            Log.i("Error", String.valueOf(t.getMessage()));

                        }
                    });
                }else {
                    showNextSession();
                }
                break;

            case R.id.new_report_card_text:
                if (account_paid_unpaid.equals("Yes")) {
                    final ValueAnimator card_text = ValueAnimator.ofFloat(1f, 1.05f);
                    card_text.setDuration(300);
                    card_text.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mReportCardText.setScaleX((Float) animation.getAnimatedValue());
                            mReportCardText.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    card_text.setRepeatCount(1);
                    card_text.setRepeatMode(ValueAnimator.REVERSE);
                    card_text.start();
//                    soundPool.play(d, 1f, 1f, 1, 0, 1);
                    String student_id = preferences.getString("STUDENT_ID", "");
                    APIInterface service = RetrofitSignleton.getAPIInterface();
                    Call<Receipt_poj> receipt_call = service.semOneReportCard(student_id);
                    receipt_call.enqueue(new Callback<Receipt_poj>() {
                        @Override
                        public void onResponse(Call<Receipt_poj> call, Response<Receipt_poj> response) {
                            if (response.body() != null) {
                                String payment_utl = response.body().getPaymentUrl();
                                if (!payment_utl.equals("")) {
                                    if (!payment_utl.isEmpty()) {
                                        Intent report_card_intent = new Intent(NewParentDashboard.this, ReportCardNew.class);
                                        startActivity(report_card_intent);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "No report available", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No report available", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Receipt_poj> call, Throwable t) {
                            Log.i("Error", String.valueOf(t.getMessage()));

                        }
                    });
                }else {
                    showNextSession();
                }
                break;

            case R.id.notification_ll_new:
                final ValueAnimator note_ll = ValueAnimator.ofFloat(1f, 1.05f);
                note_ll.setDuration(300);
                note_ll.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mNotificationll.setScaleX((Float) animation.getAnimatedValue());
                        mNotificationll.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                note_ll.setRepeatCount(1);
                note_ll.setRepeatMode(ValueAnimator.REVERSE);
                note_ll.start();
                login_intent=new Intent(NewParentDashboard.this,NotificationActivity.class);
                startActivity(login_intent);
                break;
                
            case R.id.logout_ll_new:
                Logout();
                break;



        }
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
        else if (student_class.equals("2")){
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
        }else {
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_gold.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_twink.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_shine.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_rock.png");
            arr.add("https://www.vedictreeschool.com/vtmobapp/images/sr_super.png");
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

    private void Logout() {
        Dialog alertDialog = new Dialog(this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.logout_pop_up);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button button=alertDialog.findViewById(R.id.logout_yes_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

                Intent login_intent=new Intent(NewParentDashboard.this, LoginActivity.class);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login_intent);
                finish();
            }
        });

        Button no_button=alertDialog.findViewById(R.id.logout_no_button);
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
    protected void onStop() {
        super.onStop();
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

}