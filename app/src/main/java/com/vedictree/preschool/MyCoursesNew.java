package com.vedictree.preschool;

import android.animation.ValueAnimator;
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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.Adapters.MyCourseAdapter;
import com.vedictree.preschool.POJO.Get_live_session_detail;
import com.vedictree.preschool.POJO.Login_token;
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

public class MyCoursesNew extends AppCompatActivity implements View.OnClickListener{

    LinearLayout mRockStar;
    LinearLayout mSuperStart;
    String amountShiningStar;
    String amountRockStar;
    String amountSuperStar;
    LinearLayout mNurseryLayout;
    LinearLayout mSeniorLayout;
    LinearLayout mJuniorLayout;
    ImageView mLogo;

    TextView mCourseName;

    ArrayList<String> nursery_course;
    ArrayList<String> junior_course;
    ArrayList<String> senior_course;

    ArrayList<String> nursery_course_planid;
    ArrayList<String> junior_course_planid;
    ArrayList<String> senior_course_planid;

    RecyclerView nueseryRecyclerview;
    RecyclerView seniorRecyclerview;
    RecyclerView juniorRecyclerview;

    RecyclerView.LayoutManager layoutManager;
    MyCourseAdapter myCourseAdapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    TextView mStudentName;
    ImageView mBack;

    private List<Get_live_session_detail> get_live_session_detailList;
    String package_name;

    String profile_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_courses_new);

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
                        Intent login_intent=new Intent(MyCoursesNew.this, LoginActivity.class);
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
        String course_name=preferences.getString("CLASS","");
        String student_name=preferences.getString("NAME","");
        profile_str=preferences.getString("PROFILE_PICTURE","");

        amountShiningStar="12999";
        amountRockStar="21999";
        amountSuperStar="31999";
        mStudentName=findViewById(R.id.my_course_student_name_new);
        mStudentName.setText(student_name);
        mCourseName=findViewById(R.id.my_course_coursename_new);
        mBack=findViewById(R.id.my_curse_back_to_home__button);
        mLogo=findViewById(R.id.my_courses_logo);
        if (course_name.equals("1")){
            mCourseName.setText("Nursery");

        }else if (course_name.equals("2")){
            mCourseName.setText("Junior Kg");
        }else if (course_name.equals("3")){
            mCourseName.setText("Senior Kg");
        }else if (course_name.equals("4")) {
            mCourseName.setText("Nursery Physical");
        }else if (course_name.equals("5")) {
            mCourseName.setText("Junior Physical");
        }else if (course_name.equals("6")) {
            mCourseName.setText("Senior Physical");
        }

        nursery_course=new ArrayList<>();
        junior_course=new ArrayList<>();
        senior_course=new ArrayList<>();

        nursery_course_planid=new ArrayList<>();
        junior_course_planid=new ArrayList<>();
        senior_course_planid=new ArrayList<>();


        nueseryRecyclerview=findViewById(R.id.nurseryRecyclerView_new);
        seniorRecyclerview=findViewById(R.id.seniorRecyclerView_new);
        juniorRecyclerview=findViewById(R.id.juniorRecyclerView_new);

        mNurseryLayout=findViewById(R.id.coursesNursery_new);
        mSeniorLayout=findViewById(R.id.coursesSenior_new);
        mJuniorLayout=findViewById(R.id.coursesJunior_new);
        String plan_id=preferences.getString("PLAN_ID","");
//        if (course_name.equals("1")){
//            if (plan_id.equals("1")){
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_golden_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_twink_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_shin_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_rock_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_super_prev_ui.png");
//
//                nursery_course_planid.add("1");
//                nursery_course_planid.add("2");
//                nursery_course_planid.add("3");
//                nursery_course_planid.add("4");
//                nursery_course_planid.add("16");
//            }else if (plan_id.equals("2")){
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_twink_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_shin_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_rock_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_super_prev_ui.png");
////                nursery_course.add(R.drawable.nursery_twink_prev_ui);
////                nursery_course.add(R.drawable.nursery_shin_prev_ui);
////                nursery_course.add(R.drawable.nursery_rock_prev_ui);
////                nursery_course.add(R.drawable.nursery_super_prev_ui);
//
//                nursery_course_planid.add("2");
//                nursery_course_planid.add("3");
//                nursery_course_planid.add("4");
//                nursery_course_planid.add("16");
//
//            }else if (plan_id.equals("3")){
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_shin_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_rock_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_super_prev_ui.png");
//
////                nursery_course.add(R.drawable.nursery_shin_prev_ui);
////                nursery_course.add(R.drawable.nursery_rock_prev_ui);
////                nursery_course.add(R.drawable.nursery_super_prev_ui);
//
//                nursery_course_planid.add("3");
//                nursery_course_planid.add("4");
//                nursery_course_planid.add("16");
//
//            }else if (plan_id.equals("4")){
//
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_rock_prev_ui.png");
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_super_prev_ui.png");
//
////                nursery_course.add(R.drawable.nursery_rock_prev_ui);
////                nursery_course.add(R.drawable.nursery_super_prev_ui);
//
//                nursery_course_planid.add("4");
//                nursery_course_planid.add("16");
//
//            }else if (plan_id.equals("16")){
//                nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_super_prev_ui.png");
//
////                nursery_course.add(R.drawable.nursery_super_prev_ui);
//
//                nursery_course_planid.add("16");
//
//            }
//
//
//        }
//        else if (course_name.equals("2")){
//            if (plan_id.equals("6")){
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_gold_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_twink_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_shin_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_rock_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_super_prev_ui.png");
//
//
////                junior_course.add(R.drawable.junior_gold_prev_ui);
////                junior_course.add(R.drawable.junior_twink_prev_ui);
////                junior_course.add(R.drawable.junior_shin_prev_ui);
////                junior_course.add(R.drawable.junior_rock_prev_ui);
////                junior_course.add(R.drawable.junior_super_prev_ui);
//
//                junior_course_planid.add("6");
//                junior_course_planid.add("7");
//                junior_course_planid.add("8");
//                junior_course_planid.add("9");
//                junior_course_planid.add("10");
//
//            }else if (plan_id.equals("7")){
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_twink_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_shin_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_rock_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_super_prev_ui.png");
//
////                junior_course.add(R.drawable.junior_twink_prev_ui);
////                junior_course.add(R.drawable.junior_shin_prev_ui);
////                junior_course.add(R.drawable.junior_rock_prev_ui);
////                junior_course.add(R.drawable.junior_super_prev_ui);
//
//                junior_course_planid.add("7");
//                junior_course_planid.add("8");
//                junior_course_planid.add("9");
//                junior_course_planid.add("10");
//
//            }else if (plan_id.equals("8")){
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_shin_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_rock_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_super_prev_ui.png");
////                junior_course.add(R.drawable.junior_shin_prev_ui);
////                junior_course.add(R.drawable.junior_rock_prev_ui);
////                junior_course.add(R.drawable.junior_super_prev_ui);
//
//                junior_course_planid.add("8");
//                junior_course_planid.add("9");
//                junior_course_planid.add("10");
//
//            }else if (plan_id.equals("9")){
////                junior_course.add(R.drawable.junior_rock_prev_ui);
////                junior_course.add(R.drawable.junior_super_prev_ui);
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_rock_prev_ui.png");
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_super_prev_ui.png");
//
//                junior_course_planid.add("9");
//                junior_course_planid.add("10");
//
//            }else if (plan_id.equals("10")){
////                junior_course.add(R.drawable.junior_super_prev_ui);
//
//                junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_super_prev_ui.png");
//
//                junior_course_planid.add("10");
//            }
//
//        }
//        else if (course_name.equals("3")){
//            if (plan_id.equals("11")){
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_golden_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_twink_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_shin_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_rock_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/s_super_new_prev_ui.png");
//
////                senior_course.add(R.drawable.senior_golden_prev_ui);
////                senior_course.add(R.drawable.senior_twink_prev_ui);
////                senior_course.add(R.drawable.senior_shin_prev_ui);
////                senior_course.add(R.drawable.senior_rock_prev_ui);
////                senior_course.add(R.drawable.s_super_new_prev_ui);
//
//                senior_course_planid.add("11");
//                senior_course_planid.add("12");
//                senior_course_planid.add("13");
//                senior_course_planid.add("14");
//                senior_course_planid.add("15");
//
//
//            }else if (plan_id.equals("12")){
////                senior_course.add(R.drawable.senior_twink_prev_ui);
////                senior_course.add(R.drawable.senior_shin_prev_ui);
////                senior_course.add(R.drawable.senior_rock_prev_ui);
////                senior_course.add(R.drawable.s_super_new_prev_ui);
//
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_twink_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_shin_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_rock_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/s_super_new_prev_ui.png");
//
//                senior_course_planid.add("12");
//                senior_course_planid.add("13");
//                senior_course_planid.add("14");
//                senior_course_planid.add("15");
//
//            }else if (plan_id.equals("13")){
////                senior_course.add(R.drawable.senior_shin_prev_ui);
////                senior_course.add(R.drawable.senior_rock_prev_ui);
////                senior_course.add(R.drawable.s_super_new_prev_ui);
//
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_shin_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_rock_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/s_super_new_prev_ui.png");
//
//                senior_course_planid.add("13");
//                senior_course_planid.add("14");
//                senior_course_planid.add("15");
//
//            }else if (plan_id.equals("14")){
////                senior_course.add(R.drawable.senior_rock_prev_ui);
////                senior_course.add(R.drawable.s_super_new_prev_ui);
//
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_rock_prev_ui.png");
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/s_super_new_prev_ui.png");
//
//                senior_course_planid.add("14");
//                senior_course_planid.add("15");
//
//            }else if (plan_id.equals("15")){
////                senior_course.add(R.drawable.s_super_new_prev_ui);
//                senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/s_super_new_prev_ui.png");
//
//
//                senior_course_planid.add("15");
//            }
//
//        }

        if (course_name.equals("1")){
            nursery_course.add("https://www.vedictreeschool.com/vtmobapp/images/nursery_rock_prev_ui.png");
            nursery_course_planid.add("4");
        }
        else if (course_name.equals("2")){
            junior_course.add("https://www.vedictreeschool.com/vtmobapp/images/junior_rock_prev_ui.png");
            junior_course_planid.add("9");
        }
        else if (course_name.equals("3")){
            senior_course.add("https://www.vedictreeschool.com/vtmobapp/images/senior_rock_prev_ui.png");
            senior_course_planid.add("14");
        }

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        nueseryRecyclerview.setLayoutManager(mLayoutManager);
        myCourseAdapter = new MyCourseAdapter(getApplicationContext(), "Nursery", nursery_course,nursery_course_planid);
        nueseryRecyclerview.setAdapter(myCourseAdapter);

        StaggeredGridLayoutManager mLayoutManager2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        seniorRecyclerview.setLayoutManager(mLayoutManager2);
         myCourseAdapter = new MyCourseAdapter(getApplicationContext(), "Senior", senior_course,senior_course_planid);
         seniorRecyclerview.setAdapter(myCourseAdapter);

        StaggeredGridLayoutManager mLayoutManager3 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
            juniorRecyclerview.setLayoutManager(mLayoutManager3);
            myCourseAdapter = new MyCourseAdapter(getApplicationContext(), "Junior", junior_course,junior_course_planid);
            juniorRecyclerview.setAdapter(myCourseAdapter);

        selectCourseLayout();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mBack.setScaleX((Float) animation.getAnimatedValue());
                        mBack.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                finish();
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){
        }
        else {
            setLogoutPopUp(this);
        }
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
                                        Window window = MyCoursesNew.this.getWindow();
                                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MyCoursesNew.this);
                                        ViewGroup viewGroup = findViewById(android.R.id.content);
                                        View dialog = LayoutInflater.from(MyCoursesNew.this).inflate(R.layout.live_sessions_pop_up, viewGroup, false);
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
                                                        Intent webIntent1 = new Intent(MyCoursesNew.this, YouTubeVideo.class);
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

    private void selectCourseLayout() {
        if (mCourseName.getText().toString().equals("Nursery")){
            mSeniorLayout.setVisibility(View.GONE);
            mNurseryLayout.setVisibility(View.VISIBLE);
            mJuniorLayout.setVisibility(View.GONE);
        }else if (mCourseName.getText().toString().equals("Senior Kg")){
            mSeniorLayout.setVisibility(View.VISIBLE);
            mNurseryLayout.setVisibility(View.GONE);
            mJuniorLayout.setVisibility(View.GONE);
        }else if (mCourseName.getText().toString().equals("Junior Kg")){
            mSeniorLayout.setVisibility(View.GONE);
            mNurseryLayout.setVisibility(View.GONE);
            mJuniorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.selectCourse:
//                selectCourse();
//                break;
//            default:break;
//        }
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


}