package com.vedictree.preschool;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
import com.vedictree.preschool.Adapters.SessionAdapter;
import com.vedictree.preschool.Adapters.StorySessionAdapter;
import com.vedictree.preschool.POJO.CalenderPojo;
import com.vedictree.preschool.POJO.CalenderPojoResponse;
import com.vedictree.preschool.POJO.Get_live_session_detail;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.MorningSession;
import com.vedictree.preschool.POJO.MorningSessionDetail;
import com.vedictree.preschool.POJO.Notice;
import com.vedictree.preschool.POJO.NoticeDescription;
import com.vedictree.preschool.POJO.StoryCraftPojo;
import com.vedictree.preschool.POJO.StoryCraftPojoDetail;
import com.vedictree.preschool.POJO.StudentHistory;
import com.vedictree.preschool.POJO.UserHistory;
import com.vedictree.preschool.POJO.live_session;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import org.jetbrains.annotations.NotNull;

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

public class TodaySessionActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mHome;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button mDayButton;
    Button mMonthButton;
    LinearLayout mMorning_ll;
    LinearLayout mCurriculam_ll;
    LinearLayout mExtra_curriculaum_ll;
    Button mMorning_button;
    Button mCurriculum_button;
    Button mExtra_curriculum_button;
    ImageView mLogo;

    RecyclerView MorningRecyclerView;
    RecyclerView CurriculumRecylerview;
    RecyclerView ExtraCurriculumRecyclerView;

    RecyclerView.LayoutManager MorningLayoutManager;
    RecyclerView.LayoutManager CurriculumLayoutManager;
    RecyclerView.LayoutManager ExtraCurriculumLayoutManager;

    SessionAdapter sessionAdapter;

    ArrayList<String> morning_session_name;
    ArrayList<String> morning_session_video_id;
    ArrayList<String> morning_session_image;
    ArrayList<String> morning_session_sessionid;

    //    ArrayList<Integer> morning_session_button_image;
    ArrayList<String> curriculum_session_name;
    ArrayList<String> curriculum_session_image;
    ArrayList<String> curriculum_session_video_id;
    ArrayList<String> curriculum_session_sessionid;

    ArrayList<String> extra_curriculum_session_name;
    ArrayList<String> extra_curriculum_session_image;
    ArrayList<String> extra_curriculum_session_video_id;
    ArrayList<String> extra_curriculum_session_sessionid;

    ArrayList<Integer> session_background_two;

    private UserHistory userHistory;
    private List<StudentHistory> studentHistoryList;
    private List<CalenderPojoResponse> calenderPojoResponseList;
    private MorningSession morningSession;
    private List<MorningSessionDetail> morningSessionDetailList;
    private MorningSessionDetail morningSessionDetail;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    LinearLayout mDayMonthll;
    LinearLayout SessionVideoll;

    ArrayList<String> session_name;
    ArrayList<String> session_image;
    ArrayList<String> session_video_id;
    ArrayList<String> session_sessionid;
    ArrayList<String> session_video_type;

    private StoryCraftPojo weeklyData;
    private List<StoryCraftPojoDetail> weeklyDataDetailList;
    private List<Get_live_session_detail> get_live_session_detailList;
    StorySessionAdapter activitySessionAdapter;

    String package_name;
    Button mMonth;
    Button mDay;

    Button mPreviousButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_session);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){
            showNotificationPopUp(this);
        }
        else {
//            setLogoutPopUp(this);
        }

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
                        Intent login_intent=new Intent(TodaySessionActivity.this, LoginActivity.class);
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

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        mLogo=findViewById(R.id.today_video_session_logo);
        mHome=findViewById(R.id.today_video_day_home);
        mMonthButton=findViewById(R.id.today_month);
        mDayButton=findViewById(R.id.today_day);
        mDayMonthll=findViewById(R.id.today_day_month_ll);
        SessionVideoll=findViewById(R.id.today_video_layout);
        mMonth=findViewById(R.id.today_video_month_button);
        mDay=findViewById(R.id.today_video_day_button);
        mPreviousButton=findViewById(R.id.previousButton);

        SessionVideoll.setVisibility(View.VISIBLE);
        mDayMonthll.setVisibility(View.VISIBLE);

        ExtraCurriculumRecyclerView=findViewById(R.id.today_extra_curriculum_recyclerview_2);
        CurriculumRecylerview=findViewById(R.id.today_curriculum_recyclerview_2);
        MorningRecyclerView=findViewById(R.id.today_morning_recyclerview_2);

        mCurriculam_ll=findViewById(R.id.today_curriculum_ll_2);
        mExtra_curriculaum_ll=findViewById(R.id.today_extra_curriculum_ll_2);
        mMorning_ll=findViewById(R.id.today_morning_ll_2);

        morning_session_name=new ArrayList<>();
        morning_session_video_id=new ArrayList<>();
        morning_session_image=new ArrayList<>();
        morning_session_sessionid=new ArrayList<>();

        curriculum_session_name=new ArrayList<>();
        curriculum_session_image=new ArrayList<>();
        curriculum_session_video_id=new ArrayList<>();
        curriculum_session_sessionid=new ArrayList<>();

        extra_curriculum_session_image=new ArrayList<>();
        extra_curriculum_session_name=new ArrayList<>();
        extra_curriculum_session_sessionid=new ArrayList<>();
        extra_curriculum_session_video_id=new ArrayList<>();

        session_background_two=new ArrayList<>();

        session_image=new ArrayList<>();
        session_name=new ArrayList<>();
        session_sessionid=new ArrayList<>();
        session_video_id=new ArrayList<>();
        session_video_type=new ArrayList<>();

        mMorning_button=findViewById(R.id.today_morning_routine_button);
        mCurriculum_button=findViewById(R.id.today_curriculum);
        mExtra_curriculum_button=findViewById(R.id.today_extra_curriculum);

        mMorning_button.setAlpha(0.7f);
        mCurriculum_button.setAlpha(1.0f);
        mExtra_curriculum_button.setAlpha(0.7f);

        mMorning_button.setOnClickListener(this);
        mCurriculum_button.setOnClickListener(this);
        mExtra_curriculum_button.setOnClickListener(this);
        mMonth.setOnClickListener(this);
        mDay.setOnClickListener(this);
        mPreviousButton.setOnClickListener(this);
        mMorning_ll.setVisibility(View.GONE);
        mCurriculam_ll.setVisibility(View.VISIBLE);
        mExtra_curriculaum_ll.setVisibility(View.GONE);

        CurriculumLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        CurriculumRecylerview.setLayoutManager(CurriculumLayoutManager);
        sessionAdapter = new SessionAdapter(getApplicationContext(), curriculum_session_name, curriculum_session_image,curriculum_session_video_id,curriculum_session_sessionid);

        CurriculumRecylerview.setAdapter(sessionAdapter);

        String month_str=preferences.getString("NEW_MONTH","");
        if (month_str.equals("")){
            mMonthButton.setText("1");
        }else {
            mMonthButton.setText(month_str);
        }
        String day_str=preferences.getString("NEW_DAY","");

        mHome.setOnClickListener(this);

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
                                        Window window = TodaySessionActivity.this.getWindow();
                                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(TodaySessionActivity.this);
                                        ViewGroup viewGroup = findViewById(android.R.id.content);
                                        View dialog = LayoutInflater.from(TodaySessionActivity.this).inflate(R.layout.live_sessions_pop_up, viewGroup, false);
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
                                                        Intent webIntent1 = new Intent(TodaySessionActivity.this, YouTubeVideo.class);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previousButton:
                mMorning_button.setAlpha(0.7f);
                mExtra_curriculum_button.setAlpha(0.7f);
                mCurriculum_button.setAlpha(1.0f);
                editor.putString("CURRENT_SESSION","CURRICULUM");
                editor.commit();
                mCurriculam_ll.setVisibility(View.VISIBLE);
                mExtra_curriculaum_ll.setVisibility(View.GONE);
                mMorning_ll.setVisibility(View.GONE);

                String day_string=preferences.getString("SELECT_FINAL_DAY","1");
                String month_string=preferences.getString("SELECT_FINAL_MONTH","1");
                String class_string=preferences.getString("CLASS","");
                String student_id=preferences.getString("STUDENT_ID","");


                getMorningSession(student_id,month_string,day_string,class_string,this);
                getCurriculumSession(student_id,month_string,day_string,class_string,this);
                getExtraCurriculumSession(student_id,month_string,day_string,class_string,this);
                break;

            case R.id.today_morning_routine_button:
                mCurriculum_button.setAlpha(0.7f);
                mExtra_curriculum_button.setAlpha(0.7f);
                mMorning_button.setAlpha(1.0f);
                editor.putString("CURRENT_SESSION","MORNING_ROUTINE");
                editor.commit();
                mCurriculam_ll.setVisibility(View.GONE);
                mExtra_curriculaum_ll.setVisibility(View.GONE);
                mMorning_ll.setVisibility(View.VISIBLE);
                MorningLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                MorningRecyclerView.setLayoutManager(MorningLayoutManager);
                sessionAdapter = new SessionAdapter(getApplicationContext(),morning_session_name,morning_session_image,morning_session_video_id,morning_session_video_id);
//                sessionAdapter = new SessionAdapter(getApplicationContext(),morning_session_name,image_array,morning_session_video_id,morning_session_video_id);
                MorningRecyclerView.setAdapter(sessionAdapter);
                break;

            case R.id.today_curriculum:
                mMorning_button.setAlpha(0.7f);
                mExtra_curriculum_button.setAlpha(0.7f);
                mCurriculum_button.setAlpha(1.0f);
                editor.putString("CURRENT_SESSION","CURRICULUM");
                editor.commit();
                mCurriculam_ll.setVisibility(View.VISIBLE);
                mExtra_curriculaum_ll.setVisibility(View.GONE);
                mMorning_ll.setVisibility(View.GONE);
                break;

            case R.id.today_extra_curriculum:
                mMorning_button.setAlpha(0.7f);
                mCurriculum_button.setAlpha(0.7f);
                mExtra_curriculum_button.setAlpha(1.0f);
                editor.putString("CURRENT_SESSION","EXTRA_CURRICULUM");
                editor.commit();
                mCurriculam_ll.setVisibility(View.GONE);
                mExtra_curriculaum_ll.setVisibility(View.VISIBLE);
                mMorning_ll.setVisibility(View.GONE);
                break;
        }
    }

    private void showNotificationPopUp(Context context) {
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
                                note_array.add(noticeDescriptions.get(i).getNoticedesc());
                            }
                            if (note_array.isEmpty()) {
                            } else {
                                Dialog alertDialog = new Dialog(context);
                                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                alertDialog.setContentView(R.layout.notification_pop_up);
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                Button button=alertDialog.findViewById(R.id.go_to_notification);
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
                                        Intent intent=new Intent(TodaySessionActivity.this,NotificationActivity.class);
                                        startActivity(intent);

                                    }
                                });
                                TextView note_text=alertDialog.findViewById(R.id.notification_desc);
//                                for (int j=0;j<=note_array.size();j++){
//                                    if (j==0){
                                note_text.setText(String.valueOf(note_array));
//                                    }else {
//                                        note_text.setText(note_text + "\n" + note_array.get(j));
//                                    }
//                                }
                                alertDialog.show();
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
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        if(isMyServiceRunning(MusicBackground.class)){
        }else {
            startService(new Intent(this,MusicBackground.class));
        }

        String day_string=preferences.getString("SELECT_FINAL_DAY","1");
        String month_string=preferences.getString("SELECT_FINAL_MONTH","1");
        String class_string=preferences.getString("CLASS","");
        String student_id=preferences.getString("STUDENT_ID","");

        String cal_day=preferences.getString("CAL_DAY","");
        String cal_month=preferences.getString("CAL_MONTH","");
        String course_str=preferences.getString("COURSE_PERIOD","");

        String paid_unpaid_account=preferences.getString("ACCOUNT_PAID","");
        if (paid_unpaid_account.equals("No")){

            mDayButton.setText(day_string);
            mMonthButton.setText(month_string);
        }
        else {
            if (course_str.equals("3") || course_str.equals("7")) {
//                String course_cal_day = preferences.getString("SELECT_FINAL_DAY", "");
//                String course_cal_month = preferences.getString("SELECT_FINAL_MONTH", "");
//                Log.i("DayMonth2",course_cal_day+","+course_cal_month);
                mDayButton.setText(day_string);
                mMonthButton.setText(month_string);
            }
            else {
                if (cal_month.equals(month_string)) {
                    int day_int = Integer.parseInt(day_string);
                    int cal_day_int = Integer.parseInt(cal_day);
                    if (day_int > cal_day_int) {
                        mDayButton.setText(day_string);
                        mMonthButton.setText(month_string);
                    } else if (day_int == 0) {
                        mDayButton.setText(day_string);
                        mMonthButton.setText(month_string);
                    } else {
                        mDayButton.setText(day_string);
                        mMonthButton.setText(month_string);
                    }
                } else {
                    mDayButton.setText(day_string);
                    mMonthButton.setText(month_string);
                }
            }
        }

        getMorningSession(student_id,month_string,day_string,class_string,this);
        getCurriculumSession(student_id,month_string,day_string,class_string,this);
        getExtraCurriculumSession(student_id,month_string,day_string,class_string,this);
        getStudentHistory(student_id);
        getPreviousDayLogic();

    }

    private void getPreviousDayLogic() {

    }

    private void getExtraCurriculumSession(String student_id, String month_string, String day_string, String class_string,Context context) {
        extra_curriculum_session_image.clear();
        extra_curriculum_session_name.clear();
        extra_curriculum_session_video_id.clear();
        extra_curriculum_session_sessionid.clear();

        APIInterface service= RetrofitSignleton.getAPIInterface();
//        Call<MorningSession> call=service.getExtraCurricularSession(student_id,month_string,day_string,class_string);
        Call<MorningSession> call=service.getMorningSession("76","1","1","1");
        call.enqueue(new Callback<MorningSession>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<MorningSession> call, Response<MorningSession> response) {
                if (response.isSuccessful())
                {
                    morningSession =response.body();
                    morningSessionDetailList = morningSession.getRes();
                    if (!morningSessionDetailList.isEmpty()) {
                        for (int i = 0; i < morningSessionDetailList.size(); i++) {
                            morningSessionDetail = morningSessionDetailList.get(i);
                            extra_curriculum_session_name.add(morningSessionDetail.getSessionName());
                            extra_curriculum_session_video_id.add(morningSessionDetail.getVimeoId());
                            extra_curriculum_session_image.add(morningSessionDetail.getSessionImages());
                            extra_curriculum_session_sessionid.add(morningSessionDetail.getSessionId());

                        }
                    }
                    else {
//                        setSessionPopUp();
//                        Toast.makeText(getApplicationContext(), "No sessions available", Toast.LENGTH_LONG).show();

                    }
                    if (extra_curriculum_session_image.isEmpty()){

                    }else {
                        ExtraCurriculumLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        ExtraCurriculumRecyclerView.setLayoutManager(ExtraCurriculumLayoutManager);
                        sessionAdapter = new SessionAdapter(getApplicationContext(),extra_curriculum_session_name,extra_curriculum_session_image,extra_curriculum_session_video_id,extra_curriculum_session_sessionid);
//                        sessionAdapter = new SessionAdapter(getApplicationContext(),extra_curriculum_session_name,image_array,extra_curriculum_session_video_id,extra_curriculum_session_sessionid);
                        ExtraCurriculumRecyclerView.setAdapter(sessionAdapter);
                    }
                }
                else
                {

                }
            }
            @Override
            public void onFailure(Call<MorningSession> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getCurriculumSession(String student_id, String month_string, String day_string, String class_string,Context context) {
        curriculum_session_image.clear();
        curriculum_session_name.clear();
        curriculum_session_sessionid.clear();
        curriculum_session_video_id.clear();
        APIInterface service= RetrofitSignleton.getAPIInterface();
//        Call<MorningSession> call=service.getCurricularSession(student_id,month_string,day_string,class_string);
        Call<MorningSession> call=service.getMorningSession("76","1","1","1");
        call.enqueue(new Callback<MorningSession>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<MorningSession> call, Response<MorningSession> response) {
                if (response.isSuccessful())
                {
                    morningSession =response.body();
                    morningSessionDetailList = morningSession.getRes();
                    if (!morningSessionDetailList.isEmpty()) {
                        for (int i = 0; i < morningSessionDetailList.size(); i++) {
                            morningSessionDetail = morningSessionDetailList.get(i);
                            curriculum_session_name.add(morningSessionDetail.getSessionName());
                            curriculum_session_video_id.add(morningSessionDetail.getVimeoId());
                            curriculum_session_image.add(morningSessionDetail.getSessionImages());
                            curriculum_session_sessionid.add(morningSessionDetail.getSessionId());

                        }
                    }
                    else {
                        setSessionPopUp();

//                        Toast.makeText(getApplicationContext(), "No sessions available", Toast.LENGTH_LONG).show();

                    }
                    if (curriculum_session_image.isEmpty()){

                    }else {
                        CurriculumLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        CurriculumRecylerview.setLayoutManager(CurriculumLayoutManager);
                        sessionAdapter = new SessionAdapter(getApplicationContext(), curriculum_session_name, curriculum_session_image,curriculum_session_video_id,curriculum_session_sessionid);
//                        sessionAdapter = new SessionAdapter(getApplicationContext(), curriculum_session_name, image_array,curriculum_session_video_id,curriculum_session_sessionid);
                        CurriculumRecylerview.setAdapter(sessionAdapter);
                    }
                }
                else
                {

                }
            }
            @Override
            public void onFailure(Call<MorningSession> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getMorningSession(String student_id,String month_string, String day_string, String class_string,Context context) {
        morning_session_name.clear();
        morning_session_video_id.clear();
        morning_session_image.clear();
        morning_session_sessionid.clear();

        APIInterface service= RetrofitSignleton.getAPIInterface();
        Call<MorningSession> call=service.getMorningSession(student_id,month_string,day_string,class_string);
        call.enqueue(new Callback<MorningSession>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<MorningSession> call, Response<MorningSession> response) {
                if (response.isSuccessful())
                {
                    morningSession =response.body();
                    morningSessionDetailList = morningSession.getRes();
                    if (!morningSessionDetailList.isEmpty()) {
                        for (int i = 0; i < morningSessionDetailList.size(); i++) {
                            morningSessionDetail = morningSessionDetailList.get(i);
                            morning_session_name.add(morningSessionDetail.getSessionName());
                            morning_session_video_id.add(morningSessionDetail.getVimeoId());
                            morning_session_image.add(morningSessionDetail.getSessionImages());
                            morning_session_sessionid.add(morningSessionDetail.getSessionId());

                        }
                    }
                    else {
//                        setSessionPopUp();

//                        Toast.makeText(getApplicationContext(), "No sessions available", Toast.LENGTH_LONG).show();

                    }
                    if (morning_session_image.isEmpty()){

                    }else {
                        MorningLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        MorningRecyclerView.setLayoutManager(MorningLayoutManager);
                        sessionAdapter = new SessionAdapter(getApplicationContext(),morning_session_name,morning_session_image,morning_session_video_id,morning_session_sessionid);
//                        sessionAdapter = new SessionAdapter(getApplicationContext(),morning_session_name,image_array,morning_session_video_id,morning_session_sessionid);
                        MorningRecyclerView.setAdapter(sessionAdapter);
                    }
                }
                else
                {

                }
            }
            @Override
            public void onFailure(Call<MorningSession> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getStudentHistory(String student_id) {
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<UserHistory> call = service.studentHistory(student_id);
        call.enqueue(new Callback<UserHistory>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<UserHistory> call, Response<UserHistory> response) {
                if (response.body() != null) {
                    userHistory=response.body();
                    studentHistoryList=userHistory.getStudentHistory();
                    if (!studentHistoryList.isEmpty()){

                        editor.putString("PLAN_ID",studentHistoryList.get(0).getPlanId());
                        editor.putString("PAYMENT_DATE",studentHistoryList.get(0).getPaymentDate());
                        editor.putString("ACCOUNT_PAID","Yes");
                        editor.commit();
//                        if (studentHistoryList.get(0).getUnlockdayId().equals("0")){
                        setHolidayLogic();
//                        }else {
//                            editor.putString("FINAL_DAY", studentHistoryList.get(0).getUnlockdayId());
//                            editor.putString("FINAL_MONTH", studentHistoryList.get(0).getUnlockMonthId());
//                            editor.commit();

//                        }

                    }
                    else if (studentHistoryList.isEmpty()){
//                        unpaid
                        editor.putString("ACCOUNT_PAID","No");
                        editor.putString("FINAL_DAY","3");
                        editor.putString("FINAL_MONTH", "1");
                        editor.commit();

                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<UserHistory> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setHolidayLogic() {
        String course_str=preferences.getString("COURSE_PERIOD","");
        Log.i("Couser is",course_str);
        if (course_str.equals("3") ||course_str.equals("7")){
            callCourseCalender();
        }else {
            callNormalCalender();
        }

    }

    private void callNormalCalender() {

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<CalenderPojo> call = service.getCalenderDate();
        call.enqueue(new Callback<CalenderPojo>() {
            @Override
            public void onResponse(@NotNull Call<CalenderPojo> call, @NotNull Response<CalenderPojo> response) {
                if (response.body() != null) {
                    CalenderPojo calenderPojo = response.body();
                    calenderPojoResponseList = calenderPojo.getRes();
                    for (int i = 0; i < calenderPojoResponseList.size(); i++) {
                        Calendar calendar;
                        SimpleDateFormat dateFormat;
                        String date;
                        calendar = Calendar.getInstance();
                        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        date = dateFormat.format(calendar.getTime());
//                        date="2022-06-15";
                        if (date.equals(calenderPojoResponseList.get(i).getCalDate())) {


                            String my_day = calenderPojoResponseList.get(i).getDays();
                            int day_int = Integer.parseInt(my_day);
                            if (calenderPojoResponseList.get(i).getDays().equals("0")) {
                                if (calenderPojoResponseList.get(i - 1).getDays().equals("0")) {
                                    if (calenderPojoResponseList.get(i - 2).getDays().equals("0")) {
                                        if (calenderPojoResponseList.get(i - 3).getDays().equals("0")) {
                                            if (calenderPojoResponseList.get(i - 4).getDays().equals("0")) {
                                                if (calenderPojoResponseList.get(i - 5).getDays().equals("0")) {
                                                    if (calenderPojoResponseList.get(i - 6).getDays().equals("0")) {
                                                        if (calenderPojoResponseList.get(i - 7).getDays().equals("0")) {
                                                            if (calenderPojoResponseList.get(i - 8).getDays().equals("0")) {
                                                                if (calenderPojoResponseList.get(i - 9).getDays().equals("0")) {

                                                                } else {
                                                                    day_int = Integer.parseInt(calenderPojoResponseList.get(i - 9).getDays());
                                                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 9).getDays());
                                                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 9).getMonths());
                                                                    editor.commit();
                                                                }
                                                            }
                                                            else {
                                                                day_int = Integer.parseInt(calenderPojoResponseList.get(i - 8).getDays());
                                                                editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 8).getDays());
                                                                editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 8).getMonths());
                                                                editor.commit();
                                                            }
                                                        }
                                                        else {
                                                            day_int = Integer.parseInt(calenderPojoResponseList.get(i - 7).getDays());
                                                            editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 7).getDays());
                                                            editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 7).getMonths());
                                                            editor.commit();
                                                        }
                                                    }
                                                    else {
                                                        day_int = Integer.parseInt(calenderPojoResponseList.get(i - 6).getDays());
                                                        editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 6).getDays());
                                                        editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 6).getMonths());
                                                        editor.commit();
                                                    }
                                                }

                                                else {
                                                    day_int = Integer.parseInt(calenderPojoResponseList.get(i - 5).getDays());
                                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 5).getDays());
                                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 5).getMonths());
                                                    editor.commit();
                                                }
                                            }
                                            else {
                                                day_int = Integer.parseInt(calenderPojoResponseList.get(i - 4).getDays());
                                                editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 4).getDays());
                                                editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 4).getMonths());
                                                editor.commit();
                                            }
                                        }
                                        else {
                                            day_int = Integer.parseInt(calenderPojoResponseList.get(i - 3).getDays());
                                            editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 3).getDays());
                                            editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 3).getMonths());
                                            editor.commit();
                                        }

                                    }
                                    else {
                                        day_int = Integer.parseInt(calenderPojoResponseList.get(i - 2).getDays());
                                        editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 2).getDays());
                                        editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 2).getMonths());
                                        editor.commit();
                                    }
                                }
                                else {
                                    day_int = Integer.parseInt(calenderPojoResponseList.get(i - 1).getDays());
                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 1).getDays());
                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 1).getMonths());
                                    editor.commit();

                                }
                            }
                            else {

                                if (day_int > 7) {
                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i).getDays());
                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i).getMonths());
                                    editor.commit();
                                }
                                else {
                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i).getDays());
                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i).getMonths());

                                    editor.commit();
                                }

                            }

                        }

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CalenderPojo> call, @NotNull Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
            }
        });
    }

    private void callCourseCalender() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(calendar.getTime());
        checkUnlockCourseDayAndMonth(date);
    }

    private void setSessionPopUp() {
        Dialog alertDialog = new Dialog(this);
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

    private void checkUnlockCourseDayAndMonth(String date) {
        {
            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<CalenderPojo> call = service.getCoursesDate();
            call.enqueue(new Callback<CalenderPojo>() {
                @Override
                public void onResponse(@NotNull Call<CalenderPojo> call, @NotNull Response<CalenderPojo> response) {
                    if (response.body() != null) {
                        CalenderPojo calenderPojo = response.body();
                        List<CalenderPojoResponse> calenderPojoResponseList = calenderPojo.getRes();
                        for (int i = 0; i < calenderPojoResponseList.size(); i++) {
                            if (date.equals(calenderPojoResponseList.get(i).getCalDate())){
                                if (calenderPojoResponseList.get(i).getDays().equals("0")){
                                    calendar.add(Calendar.DATE, -1);
                                    System.out.println("Updated------- " + calendar.getTime());
                                    String new_date =dateFormat.format(calendar.getTime());
                                    System.out.println("Date------ = " + new_date);
                                    checkUnlockCourseDayAndMonth(new_date);
                                }else {
                                    Log.i("DayMonth",calenderPojoResponseList.get(i).getDays()+","+calenderPojoResponseList.get(i).getMonths());
                                    editor.putString("FINAL_DAY",calenderPojoResponseList.get(i).getDays());
                                    editor.putString("FINAL_MONTH",calenderPojoResponseList.get(i).getMonths());
                                    editor.commit();

                                }
                            }

                        }

                    }
                }

                @Override
                public void onFailure(@NotNull Call<CalenderPojo> call, @NotNull Throwable t) {
                    Log.i("Error", String.valueOf(t.getMessage()));
                }
            });
        }
    }

}