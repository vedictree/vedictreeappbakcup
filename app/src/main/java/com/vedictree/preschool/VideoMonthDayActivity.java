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
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.POJO.Get_live_session_detail;
import com.vedictree.preschool.POJO.Login_token;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoMonthDayActivity extends AppCompatActivity implements View.OnClickListener {

    Button mDayButton;
    Button mMonthButton;
    ImageView mMonthOne;
    ImageView mMonthTwo;
    ImageView mMonthThree;
    ImageView mMonthFour;
    ImageView mMonthFive;
    ImageView mMonthSix;
    ImageView mMonthSeven;
    ImageView mMonthEight;
    ImageView mMonthNine;
    ImageView mMonthTen;

    ImageView mLogo;

    LinearLayout mMonthll;
    LinearLayout mDayll;

    ImageView mDayOne;
    ImageView mDayTwo;
    ImageView mDayThree;
    ImageView mDayFour;
    ImageView mDayFive;
    ImageView mDaySix;
    ImageView mDaySeven;
    ImageView mDayEight;
    ImageView mDayNine;
    ImageView mDayTen;
    ImageView mDayElevn;
    ImageView mDayTwelve;
    ImageView mDayThirteen;
    ImageView mDayFourteen;
    ImageView mDayFifteen;
    ImageView mDaySixteen;
    ImageView mDaySeventeen;
    ImageView mDayEighteen;
    ImageView mDayNineteen;
    ImageView mDayTwenty;

    ImageView mMonthLockOne;
    ImageView mMonthLockTwo;
    ImageView mMonthLockThree;
    ImageView mMonthLockFour;
    ImageView mMonthLockFive;
    ImageView mMonthLockSix;
    ImageView mMonthLockSeven;
    ImageView mMonthLockEight;
    ImageView mMonthLockNine;
    ImageView mMonthLockTen;

    ImageView mDayLockOne;
    ImageView mDayLockTwo;
    ImageView mDayLockThree;
    ImageView mDayLockFour;
    ImageView mDayLockFive;
    ImageView mDayLockSix;
    ImageView mDayLockSeven;
    ImageView mDayLockEight;
    ImageView mDayLockNine;
    ImageView mDayLockTen;
    ImageView mDayLockElevn;
    ImageView mDayLockTwelve;
    ImageView mDayLockThirteen;
    ImageView mDayLockFourteen;
    ImageView mDayLockFifteen;
    ImageView mDayLockSixteen;
    ImageView mDayLockSeventeen;
    ImageView mDayLockEighteen;
    ImageView mDayLockNineteen;
    ImageView mDayLockTwenty;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    ImageView mHome;
//    Button mExtrasButton;
    Button mVideoText;
    Button mSubjectText;
    LinearLayout mMonthButtonll;
    LinearLayout mDayButtonll;
    Button mMonthShow;
    Button mDayShow;
    String day_string;
    String month_string;
    String initial_final_day;
    int initial_final_day_int;
    String access_month;

    LinearLayout mStoryCraftll;
    LinearLayout mDayMonthll;


    TextView mStoryButton;
    ImageView mStoryImage;
    TextView mCraftButton;
    ImageView mCraftImage;

    private List<Get_live_session_detail> get_live_session_detailList;
    String package_name;

//    MediaPlayer mediaPlayer;

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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_month_day);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();

        String token_str=preferences.getString("LOG_STRING","");
        Log.i("LOG_STRING",token_str);
        APIInterface service2 = RetrofitSignleton.getAPIInterface();
        Call<Login_token> call = service2.checkLogId(token_str);
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
                        Intent login_intent=new Intent(VideoMonthDayActivity.this, LoginActivity.class);
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

        mDayButton=findViewById(R.id.video_day_button);
        mMonthButton=findViewById(R.id.video_month_button);
        mMonthll=findViewById(R.id.video_month_layout);
        mDayll=findViewById(R.id.video_day_layout);
        mHome=findViewById(R.id.video_day_home);
        mMonthButtonll=findViewById(R.id.month_button_ll);
        mDayButtonll=findViewById(R.id.day_button_ll);
//        mExtrasButton=findViewById(R.id.video_extra_button);
        mVideoText=findViewById(R.id.vide_video_text);
        mSubjectText=findViewById(R.id.video_subject_text);
        mMonthShow=findViewById(R.id.month_show);
        mDayShow=findViewById(R.id.day_show);

        mDayMonthll=findViewById(R.id.video_day_month_ll);
        mStoryCraftll=findViewById(R.id.calender_session_story_craft_ll);
        mStoryButton=findViewById(R.id.calender_story_button);
        mStoryImage=findViewById(R.id.calender_story_image);
        mCraftButton=findViewById(R.id.calender_craft_button);
        mCraftImage=findViewById(R.id.calender_craft_image);

        mMonthll.setVisibility(View.VISIBLE);
        mStoryCraftll.setVisibility(View.GONE);

        mMonthOne=findViewById(R.id.video_month_one);
        mMonthTwo=findViewById(R.id.video_month_two);
        mMonthThree=findViewById(R.id.video_month_three);
        mMonthFour=findViewById(R.id.video_month_four);
        mMonthFive=findViewById(R.id.video_month_five);
        mMonthSix=findViewById(R.id.video_month_six);
        mMonthSeven=findViewById(R.id.video_month_seven);
        mMonthEight=findViewById(R.id.video_month_eight);
        mMonthNine=findViewById(R.id.video_month_nine);
        mMonthTen=findViewById(R.id.video_month_ten);
        mLogo=findViewById(R.id.video_day_month_logo);

        mDayOne=findViewById(R.id.video_day_one);
        mDayTwo=findViewById(R.id.video_day_two);
        mDayThree=findViewById(R.id.video_day_three);
        mDayFour=findViewById(R.id.video_day_four);
        mDayFive=findViewById(R.id.video_day_five);
        mDaySix=findViewById(R.id.video_day_six);
        mDaySeven=findViewById(R.id.video_day_seven);
        mDayEight=findViewById(R.id.video_day_eight);
        mDayNine=findViewById(R.id.video_day_nine);
        mDayTen=findViewById(R.id.video_day_ten);
        mDayElevn=findViewById(R.id.video_day_elevn);
        mDayTwelve=findViewById(R.id.video_day_twelve);
        mDayThirteen=findViewById(R.id.video_day_thirteen);
        mDayFourteen=findViewById(R.id.video_day_fourteen);
        mDayFifteen=findViewById(R.id.video_day_fifteen);
        mDaySixteen=findViewById(R.id.video_day_sixteen);
        mDaySeventeen=findViewById(R.id.video_day_seventeen);
        mDayEighteen=findViewById(R.id.video_day_eighteen);
        mDayNineteen=findViewById(R.id.video_day_nineteen);
        mDayTwenty=findViewById(R.id.video_day_twenty);

        mMonthLockOne=findViewById(R.id.month_one_lock);
        mMonthLockTwo=findViewById(R.id.month_two_lock);
        mMonthLockThree=findViewById(R.id.month_three_lock);
        mMonthLockFour=findViewById(R.id.month_four_lock);
        mMonthLockFive=findViewById(R.id.month_five_lock);
        mMonthLockSix=findViewById(R.id.month_six_lock);
        mMonthLockSeven=findViewById(R.id.month_seven_lock);
        mMonthLockEight=findViewById(R.id.month_eight_lock);
        mMonthLockNine=findViewById(R.id.month_nine_lock);
        mMonthLockTen=findViewById(R.id.month_ten_lock);

        mDayLockOne=findViewById(R.id.day_one_lock);
        mDayLockTwo=findViewById(R.id.day_two_lock);
        mDayLockThree=findViewById(R.id.day_three_lock);
        mDayLockFour=findViewById(R.id.day_four_lock);
        mDayLockFive=findViewById(R.id.day_five_lock);
        mDayLockSix=findViewById(R.id.day_six_lock);
        mDayLockSeven=findViewById(R.id.day_seven_lock);
        mDayLockEight=findViewById(R.id.day_eight_lock);
        mDayLockNine=findViewById(R.id.day_nine_lock);
        mDayLockTen=findViewById(R.id.day_ten_lock);
        mDayLockElevn=findViewById(R.id.day_elevn_lock);
        mDayLockTwelve=findViewById(R.id.day_twelve_lock);
        mDayLockThirteen=findViewById(R.id.day_thirteen_lock);
        mDayLockFourteen=findViewById(R.id.day_fourteen_lock);
        mDayLockFifteen=findViewById(R.id.day_fifteen_lock);
        mDayLockSixteen=findViewById(R.id.day_sixteen_lock);
        mDayLockSeventeen=findViewById(R.id.day_seventeen_lock);
        mDayLockEighteen=findViewById(R.id.day_eighteen_lock);
        mDayLockNineteen=findViewById(R.id.day_nineteen_lock);
        mDayLockTwenty=findViewById(R.id.day_twenty_lock);


        mDayOne.setOnClickListener(this);
        mDayTwo.setOnClickListener(this);
        mDayThree.setOnClickListener(this);
        mDayFour.setOnClickListener(this);
        mDayFive.setOnClickListener(this);
        mDaySix.setOnClickListener(this);
        mDaySeven.setOnClickListener(this);
        mDayEight.setOnClickListener(this);
        mDayNine.setOnClickListener(this);
        mDayTen.setOnClickListener(this);
        mDayElevn.setOnClickListener(this);
        mDayTwelve.setOnClickListener(this);
        mDayThirteen.setOnClickListener(this);
        mDayFourteen.setOnClickListener(this);
        mDayFifteen.setOnClickListener(this);
        mDaySixteen.setOnClickListener(this);
        mDaySeventeen.setOnClickListener(this);
        mDayEighteen.setOnClickListener(this);
        mDayNineteen.setOnClickListener(this);
        mDayTwenty.setOnClickListener(this);

        mHome.setOnClickListener(this);
        mVideoText.setOnClickListener(this);
        mSubjectText.setOnClickListener(this);

        mMonthButton.setOnClickListener(this);
        mDayButton.setOnClickListener(this);
        mMonthOne.setOnClickListener(this);
        mMonthTwo.setOnClickListener(this);
        mMonthThree.setOnClickListener(this);
        mMonthFour.setOnClickListener(this);
        mMonthFive.setOnClickListener(this);
        mMonthSix.setOnClickListener(this);
        mMonthSeven.setOnClickListener(this);
        mMonthEight.setOnClickListener(this);
        mMonthNine.setOnClickListener(this);
        mMonthTen.setOnClickListener(this);

        mStoryButton.setOnClickListener(this);
        mStoryImage.setOnClickListener(this);
        mCraftImage.setOnClickListener(this);
        mCraftButton.setOnClickListener(this);

        setImage();

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

//        mMonthOne.getBackground().setAlpha(128);  // 50% transparent
        String account_paid_unpaid=preferences.getString("ACCOUNT_PAID","");
        day_string=preferences.getString("FINAL_DAY","1");
        month_string=preferences.getString("FINAL_MONTH","1");
        access_month=preferences.getString("ACCESS_GRAND","");


        if (account_paid_unpaid.equals("No")){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);
            setUnlockDay("1","3");

        }
        else {
            if (access_month.equals("Yes")){
                setAccessMonth(month_string,day_string);
                setUnlockDay2(month_string,day_string);
            }else {
                setUnlockUI(month_string, day_string);
                setUnlockDay(month_string, day_string);
            }

        }

        String cal_from=preferences.getString("CAL_MONTH","");
        if (cal_from.equals("day")){
            mMonthll.setVisibility(View.GONE);
            mDayll.setVisibility(View.VISIBLE);
            mMonthButton.setVisibility(View.VISIBLE);
            mDayButton.setVisibility(View.VISIBLE);
            mMonthButtonll.setVisibility(View.VISIBLE);
            String month_str=preferences.getString("SELECT_FINAL_MONTH","");
            if (month_str.equals("")){
                mMonthShow.setText("1");
            }else {
                mMonthShow.setText(month_str);
            }
        }
        else {
            mMonthll.setVisibility(View.VISIBLE);
            mDayll.setVisibility(View.GONE);
        }

        APIInterface service = RetrofitSignletonTeacher.getLiveIntrface();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        String currentDateandTime = sdf.format(new Date());
        Log.i("Current time:",currentDateandTime);

        Calendar calendar;
        SimpleDateFormat dateFormat;
        String date;
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("hh:mma");
        date = dateFormat.format(calendar.getTime());
        String class_string=preferences.getString("CLASS","");
        String student_id=preferences.getString("STUDENT_ID","");
        Call<live_session> call2 = service.getLiveSession(class_string,student_id);
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
                                        Window window = VideoMonthDayActivity.this.getWindow();
                                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VideoMonthDayActivity.this);
                                        ViewGroup viewGroup = findViewById(android.R.id.content);
                                        View dialog = LayoutInflater.from(VideoMonthDayActivity.this).inflate(R.layout.live_sessions_pop_up, viewGroup, false);
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
                                                        Intent webIntent1 = new Intent(VideoMonthDayActivity.this, YouTubeVideo.class);
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

        checkHomewWork();
    }

    private void setImage() {

        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(0.5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

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


        Glide.with(mDayOne.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_one.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayOne);

        Glide.with(mDayTwo.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_two.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayTwo);

        Glide.with(mDayThree.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_three.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayThree);

        Glide.with(mDayFour.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_four.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayFour);

        Glide.with(mDayFive.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_five.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayFive);

        Glide.with(mDaySix.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_six.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDaySix);

        Glide.with(mDaySeven.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_seven.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDaySeven);

        Glide.with(mDayEight.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_eight.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayEight);

        Glide.with(mDayNine.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_nine.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayNine);
        Glide.with(mDayTen.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_ten.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayTen);
        Glide.with(mDayElevn.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_elevan.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayElevn);

        Glide.with(mDayTwelve.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_twelvw.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayTwelve);

        Glide.with(mDayThirteen.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_thirteen.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayThirteen);

        Glide.with(mDayFourteen.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_fourteen.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayFourteen);

        Glide.with(mDayFifteen.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_fifteen.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayFifteen);
        Glide.with(mDaySixteen.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_sixteen.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDaySixteen);
        Glide.with(mDaySeventeen.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_seventeen.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDaySeventeen);
        Glide.with(mDayEighteen.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_eighteen.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayEighteen);
        Glide.with(mDayNineteen.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_nineteen.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayNineteen);
        Glide.with(mDayTwenty.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/day_twenty.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mDayTwenty);


        Glide.with(mMonthOne.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_one.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthOne);

        Glide.with(mMonthTwo.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_two.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthTwo);

        Glide.with(mMonthThree.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_three.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthThree);

        Glide.with(mMonthFour.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_four.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthFour);

        Glide.with(mMonthFive.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_five_two.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthFive);

        Glide.with(mMonthSix.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_six.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthSix);

        Glide.with(mMonthSeven.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_seven.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthSeven);

        Glide.with(mMonthEight.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_eight.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthEight);

        Glide.with(mMonthNine.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_nine.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthNine);

        Glide.with(mMonthTen.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/month_ten.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMonthTen);


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
                            Dialog alertDialog = new Dialog(VideoMonthDayActivity.this);
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

    private void setAccessDay(String month_string, String day_string) {
        int day_int=Integer.parseInt(day_string);
        if (day_int==1){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==2){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==3){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==4){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==5){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==6){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==7){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==8){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==9){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==10){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==11){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==12){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==13){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==14){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==15){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==16){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==17){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==18){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.GONE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(1.0f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==19){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.GONE);
            mDayLockNineteen.setVisibility(View.GONE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(1.0f);
            mDayNineteen.setAlpha(1.0f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==20){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.GONE);
            mDayLockNineteen.setVisibility(View.GONE);
            mDayLockTwenty.setVisibility(View.GONE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(1.0f);
            mDayNineteen.setAlpha(1.0f);
            mDayTwenty.setAlpha(1.0f);
        }
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

    private void setAccessMonth(String month_string, String day_string) {

        int month_int=Integer.parseInt(month_string);
        if (month_int==1){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==2){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==3){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.GONE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(1.0f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==4){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.GONE);
            mMonthLockFour.setVisibility(View.GONE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(1.0f);
            mMonthFour.setAlpha(1.0f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==5){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.GONE);
            mMonthLockFour.setVisibility(View.GONE);
            mMonthLockFive.setVisibility(View.GONE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(1.0f);
            mMonthFour.setAlpha(1.0f);
            mMonthFive.setAlpha(1.0f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==6){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.GONE);
            mMonthLockFour.setVisibility(View.GONE);
            mMonthLockFive.setVisibility(View.GONE);
            mMonthLockSix.setVisibility(View.GONE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(1.0f);
            mMonthFour.setAlpha(1.0f);
            mMonthFive.setAlpha(1.0f);
            mMonthSix.setAlpha(1.0f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==7){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.GONE);
            mMonthLockFour.setVisibility(View.GONE);
            mMonthLockFive.setVisibility(View.GONE);
            mMonthLockSix.setVisibility(View.GONE);
            mMonthLockSeven.setVisibility(View.GONE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(1.0f);
            mMonthFour.setAlpha(1.0f);
            mMonthFive.setAlpha(1.0f);
            mMonthSix.setAlpha(1.0f);
            mMonthSeven.setAlpha(1.0f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==8){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.GONE);
            mMonthLockFour.setVisibility(View.GONE);
            mMonthLockFive.setVisibility(View.GONE);
            mMonthLockSix.setVisibility(View.GONE);
            mMonthLockSeven.setVisibility(View.GONE);
            mMonthLockEight.setVisibility(View.GONE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(1.0f);
            mMonthFour.setAlpha(1.0f);
            mMonthFive.setAlpha(1.0f);
            mMonthSix.setAlpha(1.0f);
            mMonthSeven.setAlpha(1.0f);
            mMonthEight.setAlpha(1.0f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==9){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.GONE);
            mMonthLockFour.setVisibility(View.GONE);
            mMonthLockFive.setVisibility(View.GONE);
            mMonthLockSix.setVisibility(View.GONE);
            mMonthLockSeven.setVisibility(View.GONE);
            mMonthLockEight.setVisibility(View.GONE);
            mMonthLockNine.setVisibility(View.GONE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(1.0f);
            mMonthFour.setAlpha(1.0f);
            mMonthFive.setAlpha(1.0f);
            mMonthSix.setAlpha(1.0f);
            mMonthSeven.setAlpha(1.0f);
            mMonthEight.setAlpha(1.0f);
            mMonthNine.setAlpha(1.0f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==10){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.GONE);
            mMonthLockFour.setVisibility(View.GONE);
            mMonthLockFive.setVisibility(View.GONE);
            mMonthLockSix.setVisibility(View.GONE);
            mMonthLockSeven.setVisibility(View.GONE);
            mMonthLockEight.setVisibility(View.GONE);
            mMonthLockNine.setVisibility(View.GONE);
            mMonthLockTen.setVisibility(View.GONE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(1.0f);
            mMonthFour.setAlpha(1.0f);
            mMonthFive.setAlpha(1.0f);
            mMonthSix.setAlpha(1.0f);
            mMonthSeven.setAlpha(1.0f);
            mMonthEight.setAlpha(1.0f);
            mMonthNine.setAlpha(1.0f);
            mMonthTen.setAlpha(1.0f);

        }

    }

    private void setUnlockDay2(String month_string, String day_string) {
        Log.i("ACCOUNT_PAID",day_string);
        int day_int=Integer.parseInt(day_string);
        if (day_int==1){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(0.5f);
            mDayThree.setAlpha(0.5f);
            mDayFour.setAlpha(0.5f);
            mDayFive.setAlpha(0.5f);
            mDaySix.setAlpha(0.5f);
            mDaySeven.setAlpha(0.5f);
            mDayEight.setAlpha(0.5f);
            mDayNine.setAlpha(0.5f);
            mDayTen.setAlpha(0.5f);
            mDayElevn.setAlpha(0.5f);
            mDayTwelve.setAlpha(0.5f);
            mDayThirteen.setAlpha(0.5f);
            mDayFourteen.setAlpha(0.5f);
            mDayFifteen.setAlpha(0.5f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==2){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(0.7f);
            mDayFour.setAlpha(0.7f);
            mDayFive.setAlpha(0.7f);
            mDaySix.setAlpha(0.7f);
            mDaySeven.setAlpha(0.7f);
            mDayEight.setAlpha(0.7f);
            mDayNine.setAlpha(0.7f);
            mDayTen.setAlpha(0.7f);
            mDayElevn.setAlpha(0.7f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==3){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(0.7f);
            mDayFive.setAlpha(0.7f);
            mDaySix.setAlpha(0.7f);
            mDaySeven.setAlpha(0.7f);
            mDayEight.setAlpha(0.7f);
            mDayNine.setAlpha(0.7f);
            mDayTen.setAlpha(0.7f);
            mDayElevn.setAlpha(0.7f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==4){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(0.7f);
            mDaySix.setAlpha(0.7f);
            mDaySeven.setAlpha(0.7f);
            mDayEight.setAlpha(0.7f);
            mDayNine.setAlpha(0.7f);
            mDayTen.setAlpha(0.7f);
            mDayElevn.setAlpha(0.7f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==5){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(0.7f);
            mDaySeven.setAlpha(0.7f);
            mDayEight.setAlpha(0.7f);
            mDayNine.setAlpha(0.7f);
            mDayTen.setAlpha(0.7f);
            mDayElevn.setAlpha(0.7f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==6){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(0.7f);
            mDayEight.setAlpha(0.7f);
            mDayNine.setAlpha(0.7f);
            mDayTen.setAlpha(0.7f);
            mDayElevn.setAlpha(0.7f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==7){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(0.7f);
            mDayNine.setAlpha(0.7f);
            mDayTen.setAlpha(0.7f);
            mDayElevn.setAlpha(0.7f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==8){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(0.7f);
            mDayTen.setAlpha(0.7f);
            mDayElevn.setAlpha(0.7f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==9){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(0.7f);
            mDayElevn.setAlpha(0.7f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==10){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(0.7f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==11){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(0.7f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==12){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(0.7f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==13){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(0.7f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==14){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(0.7f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==15){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(0.7f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==16){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(0.7f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==17){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(0.7f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==18){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.GONE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(1.0f);
            mDayNineteen.setAlpha(0.7f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==19){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.GONE);
            mDayLockNineteen.setVisibility(View.GONE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(1.0f);
            mDayNineteen.setAlpha(1.0f);
            mDayTwenty.setAlpha(0.7f);
        }
        else  if (day_int==20){
            mDayLockOne.setVisibility(View.VISIBLE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.GONE);
            mDayLockNineteen.setVisibility(View.GONE);
            mDayLockTwenty.setVisibility(View.GONE);

            mDayOne.setAlpha(0.6f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(1.0f);
            mDayNineteen.setAlpha(1.0f);
            mDayTwenty.setAlpha(1.0f);
        }
    }

    private void setUnlockDay(String month_string, String day_string) {
        int day_int=Integer.parseInt(day_string);
        if (day_int==1){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.VISIBLE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(0.6f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==2){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.VISIBLE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(0.6f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==3){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.VISIBLE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(0.6f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==4){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.VISIBLE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(0.6f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==5){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.VISIBLE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(0.6f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==6){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.VISIBLE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(0.6f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==7){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.VISIBLE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(0.6f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==8){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.VISIBLE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(0.6f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==9){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.VISIBLE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(0.6f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==10){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.VISIBLE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(0.6f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==11){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.VISIBLE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(0.6f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==12){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.VISIBLE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(0.6f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==13){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.VISIBLE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(0.6f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==14){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.VISIBLE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(0.6f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==15){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.VISIBLE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(0.6f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==16){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.VISIBLE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(0.6f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==17){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.VISIBLE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(0.6f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==18){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.GONE);
            mDayLockNineteen.setVisibility(View.VISIBLE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(1.0f);
            mDayNineteen.setAlpha(0.6f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==19){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.GONE);
            mDayLockNineteen.setVisibility(View.GONE);
            mDayLockTwenty.setVisibility(View.VISIBLE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(1.0f);
            mDayNineteen.setAlpha(1.0f);
            mDayTwenty.setAlpha(0.6f);
        }
        else  if (day_int==20){
            mDayLockOne.setVisibility(View.GONE);
            mDayLockTwo.setVisibility(View.GONE);
            mDayLockThree.setVisibility(View.GONE);
            mDayLockFour.setVisibility(View.GONE);
            mDayLockFive.setVisibility(View.GONE);
            mDayLockSix.setVisibility(View.GONE);
            mDayLockSeven.setVisibility(View.GONE);
            mDayLockEight.setVisibility(View.GONE);
            mDayLockNine.setVisibility(View.GONE);
            mDayLockTen.setVisibility(View.GONE);
            mDayLockElevn.setVisibility(View.GONE);
            mDayLockTwelve.setVisibility(View.GONE);
            mDayLockThirteen.setVisibility(View.GONE);
            mDayLockFourteen.setVisibility(View.GONE);
            mDayLockFifteen.setVisibility(View.GONE);
            mDayLockSixteen.setVisibility(View.GONE);
            mDayLockSeventeen.setVisibility(View.GONE);
            mDayLockEighteen.setVisibility(View.GONE);
            mDayLockNineteen.setVisibility(View.GONE);
            mDayLockTwenty.setVisibility(View.GONE);

            mDayOne.setAlpha(1.0f);
            mDayTwo.setAlpha(1.0f);
            mDayThree.setAlpha(1.0f);
            mDayFour.setAlpha(1.0f);
            mDayFive.setAlpha(1.0f);
            mDaySix.setAlpha(1.0f);
            mDaySeven.setAlpha(1.0f);
            mDayEight.setAlpha(1.0f);
            mDayNine.setAlpha(1.0f);
            mDayTen.setAlpha(1.0f);
            mDayElevn.setAlpha(1.0f);
            mDayTwelve.setAlpha(1.0f);
            mDayThirteen.setAlpha(1.0f);
            mDayFourteen.setAlpha(1.0f);
            mDayFifteen.setAlpha(1.0f);
            mDaySixteen.setAlpha(1.0f);
            mDaySeventeen.setAlpha(1.0f);
            mDayEighteen.setAlpha(1.0f);
            mDayNineteen.setAlpha(1.0f);
            mDayTwenty.setAlpha(1.0f);
        }
    }

    private void setUnlockUI(String month_string ,String day_string) {
        int month_int=Integer.parseInt(month_string);
        if (month_int==1){
            mMonthLockOne.setVisibility(View.GONE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(1.0f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==2){
            mMonthLockOne.setVisibility(View.VISIBLE);
            mMonthLockTwo.setVisibility(View.GONE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(0.6f);
            mMonthTwo.setAlpha(1.0f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==3){
            mMonthLockOne.setVisibility(View.VISIBLE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.GONE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(0.6f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(1.0f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==4){
            mMonthLockOne.setVisibility(View.VISIBLE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.GONE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(0.6f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(1.0f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==5){
            mMonthLockOne.setVisibility(View.VISIBLE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.GONE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(0.6f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(1.0f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==6){
            mMonthLockOne.setVisibility(View.VISIBLE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.GONE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(0.6f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(1.0f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==7){
            mMonthLockOne.setVisibility(View.VISIBLE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.GONE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(0.6f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(1.0f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==8){
            mMonthLockOne.setVisibility(View.VISIBLE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.GONE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(0.6f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(1.0f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==9){
            mMonthLockOne.setVisibility(View.VISIBLE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.GONE);
            mMonthLockTen.setVisibility(View.VISIBLE);

            mMonthOne.setAlpha(0.6f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(1.0f);
            mMonthTen.setAlpha(0.6f);

        }
        else if (month_int==10){
            mMonthLockOne.setVisibility(View.VISIBLE);
            mMonthLockTwo.setVisibility(View.VISIBLE);
            mMonthLockThree.setVisibility(View.VISIBLE);
            mMonthLockFour.setVisibility(View.VISIBLE);
            mMonthLockFive.setVisibility(View.VISIBLE);
            mMonthLockSix.setVisibility(View.VISIBLE);
            mMonthLockSeven.setVisibility(View.VISIBLE);
            mMonthLockEight.setVisibility(View.VISIBLE);
            mMonthLockNine.setVisibility(View.VISIBLE);
            mMonthLockTen.setVisibility(View.GONE);

            mMonthOne.setAlpha(0.6f);
            mMonthTwo.setAlpha(0.6f);
            mMonthThree.setAlpha(0.6f);
            mMonthFour.setAlpha(0.6f);
            mMonthFive.setAlpha(0.6f);
            mMonthSix.setAlpha(0.6f);
            mMonthSeven.setAlpha(0.6f);
            mMonthEight.setAlpha(0.6f);
            mMonthNine.setAlpha(0.6f);
            mMonthTen.setAlpha(1.0f);

        }

    }

    @Override
    public void onClick(View view) {
        Intent new_intent;
        switch (view.getId()){
            case R.id.video_month_one:
                if (mMonthLockOne.getVisibility()==View.VISIBLE){

                }
                else {
                    final ValueAnimator month_one_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_one_anim.setDuration(300);
                    month_one_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthOne.setScaleX((Float) animation.getAnimatedValue());
                            mMonthOne.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_one_anim.setRepeatCount(1);
                    month_one_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_one_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>1){
                            setUnlockDay("1","20");
                        }else if (month_int==1){
                            setUnlockDay(month_string,day_string);

                        }

                    }else {
                        if (month_string.equals("1")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("1");
                    editor.putString("SELECT_FINAL_MONTH", "1");
                    editor.commit();
                }
                break;

            case R.id.video_month_two:
                if (mMonthLockTwo.getVisibility()==View.VISIBLE){

                }
                else {
                    final ValueAnimator month_two_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_two_anim.setDuration(300);
                    month_two_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthTwo.setScaleX((Float) animation.getAnimatedValue());
                            mMonthTwo.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_two_anim.setRepeatCount(1);
                    month_two_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_two_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>2){
                            setUnlockDay("2","20");
                        }else if (month_int==2){
                            setUnlockDay(month_string,day_string);
                        }

                    }else {
                        if (month_string.equals("2")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("2");
                    editor.putString("SELECT_FINAL_MONTH", "2");
                    editor.commit();
                }
                break;

            case R.id.video_month_three:
                if (mMonthLockThree.getVisibility()==View.VISIBLE){

                }
                else {
                    final ValueAnimator month_three_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_three_anim.setDuration(300);
                    month_three_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthThree.setScaleX((Float) animation.getAnimatedValue());
                            mMonthThree.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_three_anim.setRepeatCount(1);
                    month_three_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_three_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>3){
                            setUnlockDay("2","20");
                        }else if (month_int==3){
                            setUnlockDay(month_string,day_string);
                        }

                    }else {
                        if (month_string.equals("3")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("3");
                    editor.putString("SELECT_FINAL_MONTH", "3");
                    editor.commit();
                }
                break;

            case R.id.video_month_four:
                if (mMonthLockFour.getVisibility()==View.VISIBLE){

                }
                else {
                    final ValueAnimator month_four_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_four_anim.setDuration(300);
                    month_four_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthFour.setScaleX((Float) animation.getAnimatedValue());
                            mMonthFour.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_four_anim.setRepeatCount(1);
                    month_four_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_four_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>4){
                            setUnlockDay("2","20");
                        }else if (month_int==4){
                            setUnlockDay(month_string,day_string);
                        }

                    }else {
                        if (month_string.equals("4")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("4");
                    editor.putString("SELECT_FINAL_MONTH", "4");
                    editor.commit();
                }
                break;

            case R.id.video_month_five:
                if (mMonthLockFive.getVisibility()==View.VISIBLE){

                }
                else {
                    final ValueAnimator month_five_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_five_anim.setDuration(300);
                    month_five_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthFive.setScaleX((Float) animation.getAnimatedValue());
                            mMonthFive.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_five_anim.setRepeatCount(1);
                    month_five_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_five_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>5){
                            setUnlockDay("5","20");
                        }else if (month_int==5){
                            setUnlockDay(month_string,day_string);
                        }

                    }else {
                        if (month_string.equals("5")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("5");
                    editor.putString("SELECT_FINAL_MONTH", "5");
                    editor.commit();
                }
                break;

            case R.id.video_month_six:
                if (mMonthLockSix.getVisibility()==View.VISIBLE){
                }
                else {
                    final ValueAnimator month_six_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_six_anim.setDuration(300);
                    month_six_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthSix.setScaleX((Float) animation.getAnimatedValue());
                            mMonthSix.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_six_anim.setRepeatCount(1);
                    month_six_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_six_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>6){
                            setUnlockDay("6","20");
                        }else if (month_int==6){
                            setUnlockDay(month_string,day_string);
                        }

                    }else {
                        if (month_string.equals("6")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("6");
                    editor.putString("SELECT_FINAL_MONTH","6");
                    editor.commit();
                }
                break;

            case R.id.video_month_seven:
                if (mMonthLockSeven.getVisibility()==View.VISIBLE){

                }
                else {
                    final ValueAnimator month_seven_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_seven_anim.setDuration(300);
                    month_seven_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthSeven.setScaleX((Float) animation.getAnimatedValue());
                            mMonthSeven.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_seven_anim.setRepeatCount(1);
                    month_seven_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_seven_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>7){
                            setUnlockDay("2","20");
                        }else if (month_int==7){
                            setUnlockDay(month_string,day_string);
                        }

                    }else {
                        if (month_string.equals("7")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("7");
                    editor.putString("SELECT_FINAL_MONTH","7");
                    editor.commit();
                }
                break;

            case R.id.video_month_eight:
                if (mMonthLockEight.getVisibility()==View.VISIBLE){

                }
                else {
                    final ValueAnimator month_eight_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_eight_anim.setDuration(300);
                    month_eight_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthEight.setScaleX((Float) animation.getAnimatedValue());
                            mMonthEight.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_eight_anim.setRepeatCount(1);
                    month_eight_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_eight_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>8){
                            setUnlockDay("2","20");
                        }else if (month_int==8){
                            setUnlockDay(month_string,day_string);
                        }

                    }else {
                        if (month_string.equals("8")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("8");
                    editor.putString("SELECT_FINAL_MONTH", "8");
                    editor.commit();
                }
                break;

            case R.id.video_month_nine:
                if (mMonthLockNine.getVisibility()==View.VISIBLE){

                }
                else {
                    final ValueAnimator month_nine_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_nine_anim.setDuration(300);
                    month_nine_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthNine.setScaleX((Float) animation.getAnimatedValue());
                            mMonthNine.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_nine_anim.setRepeatCount(1);
                    month_nine_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_nine_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>9){
                            setUnlockDay("2","20");
                        }else if (month_int==9){
                            setUnlockDay(month_string,day_string);
                        }

                    }else {
                        if (month_string.equals("9")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("9");
                    editor.putString("SELECT_FINAL_MONTH", "9");
                    editor.commit();
                }
                break;

            case R.id.video_month_ten:
                if (mMonthLockTen.getVisibility()==View.VISIBLE){

                }
                else {
                    final ValueAnimator month_ten_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    month_ten_anim.setDuration(300);
                    month_ten_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mMonthTen.setScaleX((Float) animation.getAnimatedValue());
                            mMonthTen.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    month_ten_anim.setRepeatCount(1);
                    month_ten_anim.setRepeatMode(ValueAnimator.REVERSE);
                    month_ten_anim.start();
                    if (access_month.equals("Yes")){
                        int month_int=Integer.parseInt(month_string);
                        if (month_int>10){
                            setUnlockDay("2","20");
                        }else if (month_int==10){
                            setUnlockDay(month_string,day_string);
                        }

                    }else {
                        if (month_string.equals("10")) {
                            setUnlockDay2(month_string, day_string);
                        } else {
                            setAllDayLock();
                        }
                    }
                    mMonthll.setVisibility(View.GONE);
                    mDayll.setVisibility(View.VISIBLE);
                    mDayButton.setVisibility(View.VISIBLE);
                    mMonthButtonll.setVisibility(View.VISIBLE);
                    mMonthShow.setText("10");
                    editor.putString("SELECT_FINAL_MONTH", "10");
                    editor.commit();
                }
                break;

            case R.id.video_day_button:
//                mMonthll.setVisibility(View.GONE);
//                mDayll.setVisibility(View.VISIBLE);
                final ValueAnimator video_day_anim = ValueAnimator.ofFloat(1f, 1.05f);
                video_day_anim.setDuration(300);
                video_day_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mDayButton.setScaleX((Float) animation.getAnimatedValue());
                        mDayButton.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                video_day_anim.setRepeatCount(1);
                video_day_anim.setRepeatMode(ValueAnimator.REVERSE);
                video_day_anim.start();
                break;

            case R.id.video_month_button:
                mMonthll.setVisibility(View.VISIBLE);
                mDayll.setVisibility(View.GONE);
                final ValueAnimator video_month_anim = ValueAnimator.ofFloat(1f, 1.05f);
                video_month_anim.setDuration(300);
                video_month_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mMonthButton.setScaleX((Float) animation.getAnimatedValue());
                        mMonthButton.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                video_month_anim.setRepeatCount(1);
                video_month_anim.setRepeatMode(ValueAnimator.REVERSE);
                video_month_anim.start();
                break;
            case R.id.video_day_one:
                if (mDayLockOne.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("1");

                    final ValueAnimator anim = ValueAnimator.ofFloat(1f, 1.05f);
                    anim.setDuration(300);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayOne.setScaleX((Float) animation.getAnimatedValue());
                            mDayOne.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    anim.setRepeatCount(1);
                    anim.setRepeatMode(ValueAnimator.REVERSE);
                    anim.start();
                    editor.putString("SELECT_FINAL_DAY", "1");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_two:
                if (mDayLockTwo.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("2");

                    final ValueAnimator video_day_button = ValueAnimator.ofFloat(1f, 1.05f);
                    video_day_button.setDuration(300);
                    video_day_button.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayTwo.setScaleX((Float) animation.getAnimatedValue());
                            mDayTwo.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    video_day_button.setRepeatCount(1);
                    video_day_button.setRepeatMode(ValueAnimator.REVERSE);
                    video_day_button.start();
                    editor.putString("SELECT_FINAL_DAY", "2");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }

                break;
            case R.id.video_day_three:
                if (mDayLockThree.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("3");

                    final ValueAnimator three_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    three_anim.setDuration(300);
                    three_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayThree.setScaleX((Float) animation.getAnimatedValue());
                            mDayThree.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    three_anim.setRepeatCount(1);
                    three_anim.setRepeatMode(ValueAnimator.REVERSE);
                    three_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "3");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_four:
                if (mDayLockFour.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("4");

                    final ValueAnimator four_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    four_anim.setDuration(300);
                    four_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayFour.setScaleX((Float) animation.getAnimatedValue());
                            mDayFour.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    four_anim.setRepeatCount(1);
                    four_anim.setRepeatMode(ValueAnimator.REVERSE);
                    four_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "4");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_five:
                if (mDayLockFive.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("5");

                    final ValueAnimator five_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    five_anim.setDuration(300);
                    five_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayFive.setScaleX((Float) animation.getAnimatedValue());
                            mDayFive.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    five_anim.setRepeatCount(1);
                    five_anim.setRepeatMode(ValueAnimator.REVERSE);
                    five_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "5");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_six:
                if (mDayLockSix.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("6");

                    final ValueAnimator six_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    six_anim.setDuration(300);
                    six_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDaySix.setScaleX((Float) animation.getAnimatedValue());
                            mDaySix.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    six_anim.setRepeatCount(1);
                    six_anim.setRepeatMode(ValueAnimator.REVERSE);
                    six_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "6");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;
            case R.id.video_day_seven:
                if (mDayLockSeven.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("7");

                    final ValueAnimator seven_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    seven_anim.setDuration(300);
                    seven_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDaySeven.setScaleX((Float) animation.getAnimatedValue());
                            mDaySeven.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    seven_anim.setRepeatCount(1);
                    seven_anim.setRepeatMode(ValueAnimator.REVERSE);
                    seven_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "7");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;


            case R.id.video_day_eight:
                if (mDayLockEight.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("8");

                    final ValueAnimator eight_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    eight_anim.setDuration(300);
                    eight_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayEight.setScaleX((Float) animation.getAnimatedValue());
                            mDayEight.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    eight_anim.setRepeatCount(1);
                    eight_anim.setRepeatMode(ValueAnimator.REVERSE);
                    eight_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "8");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_nine:
                if (mDayLockNine.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("9");

                    final ValueAnimator nine_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    nine_anim.setDuration(300);
                    nine_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayNine.setScaleX((Float) animation.getAnimatedValue());
                            mDayNine.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    nine_anim.setRepeatCount(1);
                    nine_anim.setRepeatMode(ValueAnimator.REVERSE);
                    nine_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "9");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_ten:
                if (mDayLockTen.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("10");

                    final ValueAnimator ten_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    ten_anim.setDuration(300);
                    ten_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayTen.setScaleX((Float) animation.getAnimatedValue());
                            mDayTen.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    ten_anim.setRepeatCount(1);
                    ten_anim.setRepeatMode(ValueAnimator.REVERSE);
                    ten_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "10");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_elevn:
                if (mDayLockElevn.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("11");

                    final ValueAnimator elevn_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    elevn_anim.setDuration(300);
                    elevn_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayElevn.setScaleX((Float) animation.getAnimatedValue());
                            mDayElevn.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    elevn_anim.setRepeatCount(1);
                    elevn_anim.setRepeatMode(ValueAnimator.REVERSE);
                    elevn_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "11");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_twelve:
                if (mDayLockTwelve.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("12");

                    final ValueAnimator twelve_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    twelve_anim.setDuration(300);
                    twelve_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayTwelve.setScaleX((Float) animation.getAnimatedValue());
                            mDayTwelve.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    twelve_anim.setRepeatCount(1);
                    twelve_anim.setRepeatMode(ValueAnimator.REVERSE);
                    twelve_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "12");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_thirteen:
                if (mDayLockThirteen.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("13");

                    final ValueAnimator thirteen_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    thirteen_anim.setDuration(300);
                    thirteen_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayThirteen.setScaleX((Float) animation.getAnimatedValue());
                            mDayThirteen.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    thirteen_anim.setRepeatCount(1);
                    thirteen_anim.setRepeatMode(ValueAnimator.REVERSE);
                    thirteen_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "13");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
//                    mediaPlayer.stop();

                }
                break;
            case R.id.video_day_fourteen:
                if (mDayLockFourteen.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("14");

                    final ValueAnimator fourteen_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    fourteen_anim.setDuration(300);
                    fourteen_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayFourteen.setScaleX((Float) animation.getAnimatedValue());
                            mDayFourteen.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    fourteen_anim.setRepeatCount(1);
                    fourteen_anim.setRepeatMode(ValueAnimator.REVERSE);
                    fourteen_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "14");
                    editor.commit();
                    new_intent = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(new_intent);
                    finish();
                }
                break;

            case R.id.video_day_fifteen:
                if (mDayLockFifteen.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("15");
                    final ValueAnimator fifteen_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    fifteen_anim.setDuration(300);
                    fifteen_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayFifteen.setScaleX((Float) animation.getAnimatedValue());
                            mDayFifteen.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    fifteen_anim.setRepeatCount(1);
                    fifteen_anim.setRepeatMode(ValueAnimator.REVERSE);
                    fifteen_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "15");
                    editor.commit();
                    Intent intent15 = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(intent15);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_sixteen:
                if (mDayLockSixteen.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("16");

                    final ValueAnimator sixteen_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    sixteen_anim.setDuration(300);
                    sixteen_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDaySixteen.setScaleX((Float) animation.getAnimatedValue());
                            mDaySixteen.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    sixteen_anim.setRepeatCount(1);
                    sixteen_anim.setRepeatMode(ValueAnimator.REVERSE);
                    sixteen_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "16");
                    editor.commit();
                    Intent intent16 = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(intent16);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_seventeen:
                if (mDayLockSeventeen.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("17");

                    final ValueAnimator seventeen_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    seventeen_anim.setDuration(300);
                    seventeen_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDaySeventeen.setScaleX((Float) animation.getAnimatedValue());
                            mDaySeventeen.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    seventeen_anim.setRepeatCount(1);
                    seventeen_anim.setRepeatMode(ValueAnimator.REVERSE);
                    seventeen_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "17");
                    editor.commit();
                    Intent intent17 = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(intent17);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_eighteen:
                if (mDayLockEighteen.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("18");

                    final ValueAnimator eighteen_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    eighteen_anim.setDuration(300);
                    eighteen_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayEighteen.setScaleX((Float) animation.getAnimatedValue());
                            mDayEighteen.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    eighteen_anim.setRepeatCount(1);
                    eighteen_anim.setRepeatMode(ValueAnimator.REVERSE);
                    eighteen_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "18");
                    editor.commit();
                    Intent intent18 = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(intent18);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_nineteen:
                if (mDayLockNineteen.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("19");

                    final ValueAnimator nineteen_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    nineteen_anim.setDuration(300);
                    nineteen_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayNineteen.setScaleX((Float) animation.getAnimatedValue());
                            mDayNineteen.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    nineteen_anim.setRepeatCount(1);
                    nineteen_anim.setRepeatMode(ValueAnimator.REVERSE);
                    nineteen_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "19");
                    editor.commit();
                    Intent intent19 = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(intent19);
                    finish();
//                    mediaPlayer.stop();

                }
                break;



            case R.id.video_day_twenty:
                if (mDayLockTwenty.getVisibility()==View.VISIBLE){

                }
                else {
                    mDayButtonll.setVisibility(View.VISIBLE);
                    mDayShow.setText("20");

                    final ValueAnimator twenty_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    twenty_anim.setDuration(300);
                    twenty_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mDayTwenty.setScaleX((Float) animation.getAnimatedValue());
                            mDayTwenty.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    twenty_anim.setRepeatCount(1);
                    twenty_anim.setRepeatMode(ValueAnimator.REVERSE);
                    twenty_anim.start();
                    editor.putString("SELECT_FINAL_DAY", "20");
                    editor.commit();
                    Intent intent20 = new Intent(VideoMonthDayActivity.this, VideoSessionActivity.class);
                    startActivity(intent20);
                    finish();
//                    mediaPlayer.stop();

                }
                break;

            case R.id.video_day_home:
                final ValueAnimator day_home_anim = ValueAnimator.ofFloat(1f, 1.05f);
                day_home_anim.setDuration(300);
                day_home_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mHome.setScaleX((Float) animation.getAnimatedValue());
                        mHome.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                day_home_anim.setRepeatCount(1);
                day_home_anim.setRepeatMode(ValueAnimator.REVERSE);
                day_home_anim.start();
                Intent intent_home=new Intent(VideoMonthDayActivity.this,ChildeDashboardNew.class);
                startActivity(intent_home);
                finish();
//                mediaPlayer.stop();

                break;

            case R.id.vide_video_text:
                final ValueAnimator day_video_anim = ValueAnimator.ofFloat(1f, 1.05f);
                day_video_anim.setDuration(300);
                day_video_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mVideoText.setScaleX((Float) animation.getAnimatedValue());
                        mVideoText.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                day_video_anim.setRepeatCount(1);
                day_video_anim.setRepeatMode(ValueAnimator.REVERSE);
                day_video_anim.start();
                mMonthll.setVisibility(View.VISIBLE);
                mStoryCraftll.setVisibility(View.GONE);
                mDayMonthll.setVisibility(View.VISIBLE);
//                Intent home_intent=new Intent(VideoMonthDayActivity.this,ChildeDashboardNew.class);
//                startActivity(home_intent);
//                finish();
//                mediaPlayer.stop();

                break;

            case R.id.video_subject_text:
                final ValueAnimator day_subject_anim = ValueAnimator.ofFloat(1f, 1.05f);
                day_subject_anim.setDuration(300);
                day_subject_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mSubjectText.setScaleX((Float) animation.getAnimatedValue());
                        mSubjectText.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                day_subject_anim.setRepeatCount(1);
                day_subject_anim.setRepeatMode(ValueAnimator.REVERSE);
                day_subject_anim.start();
                mMonthll.setVisibility(View.GONE);
                mDayll.setVisibility(View.GONE);
                mDayMonthll.setVisibility(View.GONE);
                mStoryCraftll.setVisibility(View.VISIBLE);
                break;

            case R.id.calender_story_button:
                editor.putString("STORY_OR_CRAFT","STORY");
                editor.commit();
//                final ValueAnimator anim1 = ValueAnimator.ofFloat(1f, 1.05f);
//                anim1.setDuration(300);
//                anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mStoryButton.setScaleX((Float) animation.getAnimatedValue());
//                        mStoryButton.setScaleY((Float) animation.getAnimatedValue());
//                    }
//                });
//                anim1.setRepeatCount(1);
//                anim1.setRepeatMode(ValueAnimator.REVERSE);
//                anim1.start();
                new_intent=new Intent(VideoMonthDayActivity.this,VideoSessionStoryCraft.class);
                startActivity(new_intent);
                break;
            case R.id.calender_story_image:
                editor.putString("STORY_OR_CRAFT","STORY");
                editor.commit();
                final ValueAnimator anim2 = ValueAnimator.ofFloat(1f, 1.05f);
                anim2.setDuration(300);
                anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mStoryImage.setScaleX((Float) animation.getAnimatedValue());
                        mStoryImage.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim2.setRepeatCount(1);
                anim2.setRepeatMode(ValueAnimator.REVERSE);
                anim2.start();
                new_intent=new Intent(VideoMonthDayActivity.this,VideoSessionStoryCraft.class);
                startActivity(new_intent);
                break;
            case R.id.calender_craft_button:
                editor.putString("STORY_OR_CRAFT","CRAFT");
                editor.commit();
//                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
//                anim3.setDuration(300);
//                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mCraftButton.setScaleX((Float) animation.getAnimatedValue());
//                        mCraftButton.setScaleY((Float) animation.getAnimatedValue());
//                    }
//                });
//                anim3.setRepeatCount(1);
//                anim3.setRepeatMode(ValueAnimator.REVERSE);
//                anim3.start();
                new_intent=new Intent(VideoMonthDayActivity.this,VideoSessionStoryCraft.class);
                startActivity(new_intent);
                break;
            case R.id.calender_craft_image:
                editor.putString("STORY_OR_CRAFT","CRAFT");
                editor.commit();
                final ValueAnimator anim4 = ValueAnimator.ofFloat(1f, 1.05f);
                anim4.setDuration(300);
                anim4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mCraftImage.setScaleX((Float) animation.getAnimatedValue());
                        mCraftImage.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim4.setRepeatCount(1);
                anim4.setRepeatMode(ValueAnimator.REVERSE);
                anim4.start();
                new_intent=new Intent(VideoMonthDayActivity.this,VideoSessionStoryCraft.class);
                startActivity(new_intent);
                break;
        }
    }

    private void setAllDayLock() {
        mDayLockOne.setVisibility(View.VISIBLE);
        mDayLockTwo.setVisibility(View.VISIBLE);
        mDayLockThree.setVisibility(View.VISIBLE);
        mDayLockFour.setVisibility(View.VISIBLE);
        mDayLockFive.setVisibility(View.VISIBLE);
        mDayLockSix.setVisibility(View.VISIBLE);
        mDayLockSeven.setVisibility(View.VISIBLE);
        mDayLockEight.setVisibility(View.VISIBLE);
        mDayLockNine.setVisibility(View.VISIBLE);
        mDayLockTen.setVisibility(View.VISIBLE);
        mDayLockElevn.setVisibility(View.VISIBLE);
        mDayLockTwelve.setVisibility(View.VISIBLE);
        mDayLockThirteen.setVisibility(View.VISIBLE);
        mDayLockFourteen.setVisibility(View.VISIBLE);
        mDayLockFifteen.setVisibility(View.VISIBLE);
        mDayLockSixteen.setVisibility(View.VISIBLE);
        mDayLockSeventeen.setVisibility(View.VISIBLE);
        mDayLockEighteen.setVisibility(View.VISIBLE);
        mDayLockNineteen.setVisibility(View.VISIBLE);
        mDayLockTwenty.setVisibility(View.VISIBLE);

        mDayOne.setAlpha(0.5f);
        mDayTwo.setAlpha(0.5f);
        mDayThree.setAlpha(0.5f);
        mDayFour.setAlpha(0.5f);
        mDayFive.setAlpha(0.5f);
        mDaySix.setAlpha(0.5f);
        mDaySeven.setAlpha(0.5f);
        mDayEight.setAlpha(0.5f);
        mDayNine.setAlpha(0.5f);
        mDayTen.setAlpha(0.5f);
        mDayElevn.setAlpha(0.5f);
        mDayTwelve.setAlpha(0.5f);
        mDayThirteen.setAlpha(0.5f);
        mDayFourteen.setAlpha(0.5f);
        mDayFifteen.setAlpha(0.5f);
        mDaySixteen.setAlpha(0.5f);
        mDaySeventeen.setAlpha(0.5f);
        mDayEighteen.setAlpha(0.5f);
        mDayNineteen.setAlpha(0.5f);
        mDayTwenty.setAlpha(0.5f);
    }

    @Override
    public void onBackPressed() {
        Log.i("Back","Back");
    }
}