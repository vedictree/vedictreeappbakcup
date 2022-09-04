package com.vedictree.preschool;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.Adapters.ActivitySessionAdapter;
import com.vedictree.preschool.Adapters.testAdapterNew;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.ValueBaseEducationPojo;
import com.vedictree.preschool.POJO.ValueBaseEducationPojoResponse;
import com.vedictree.preschool.POJO.WeeklyData;
import com.vedictree.preschool.POJO.WeeklyDataDetail;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FestivalSession extends AppCompatActivity implements View.OnClickListener{

    RecyclerView actictyRecyclerView;
    RecyclerView weeklyRecyclerView;

    LinearLayout festivalLL;
    LinearLayout weeklyLL;
    ImageView mLogo;

    ActivitySessionAdapter activitySessionAdapter;

    ArrayList<Integer> session_background;
    ArrayList<Integer> session_background_two;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RecyclerView.LayoutManager layoutManager;


    ArrayList<String> festival_session_name;
    ArrayList<String> festival_session_image;
    ArrayList<String> festival_session_video_id;
    ArrayList<String> festival_session_sessionid;
    ArrayList<String> festival_session_video_type;

    ArrayList<String> weekly_session_name;
    ArrayList<String> weekly_session_image;
    ArrayList<String> weekly_session_video_id;
    ArrayList<String> weekly_session_sessionid;
    ArrayList<String> weekly_session_video_type;


    private WeeklyData weeklyData;
    private List<WeeklyDataDetail> weeklyDataDetailList;
    private ValueBaseEducationPojo valueBaseEducationPojo;
    private List<ValueBaseEducationPojoResponse> valueBaseEducationPojoResponseList;

    ImageView mBack;
//    MusicManager mm;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival_session);

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
                        Intent login_intent=new Intent(FestivalSession.this, LoginActivity.class);
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

        actictyRecyclerView=findViewById(R.id.festival_session_recyclerview_2);
        weeklyRecyclerView=findViewById(R.id.weekly_session_recyclerview_2);
        festivalLL=findViewById(R.id.festivel_value_ll);
        weeklyLL=findViewById(R.id.weekly_ll);
        mLogo=findViewById(R.id.festival_logo);

        mBack=findViewById(R.id.activity_back_button);
        mBack.setOnClickListener(this);

        session_background=new ArrayList<>();
        session_background_two=new ArrayList<>();

        festival_session_image=new ArrayList<>();
        festival_session_name=new ArrayList<>();
        festival_session_sessionid=new ArrayList<>();
        festival_session_video_id=new ArrayList<>();
        festival_session_video_type=new ArrayList<>();

        weekly_session_image=new ArrayList<>();
        weekly_session_name=new ArrayList<>();
        weekly_session_sessionid=new ArrayList<>();
        weekly_session_video_id=new ArrayList<>();
        weekly_session_video_type=new ArrayList<>();



        String day_string=preferences.getString("SELECT_FINAL_DAY","1");
        String month_string=preferences.getString("SELECT_FINAL_MONTH","1");
        String class_string=preferences.getString("CLASS","");
        String student_id=preferences.getString("STUDENT_ID","");

        String activity_from=preferences.getString("ACTIVITY_START","");
        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        if (activity_from.equals("valuebase")) {
            festivalLL.setVisibility(View.VISIBLE);
            weeklyLL.setVisibility(View.GONE);
            setFestivalSession(student_id, month_string, day_string, class_string,tabletSize);

        }else {
            festivalLL.setVisibility(View.GONE);
            weeklyLL.setVisibility(View.VISIBLE);
            setWeeklySession(class_string,tabletSize);
        }

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

    private void setWeeklySession(String class_string,Boolean table_size) {
        weekly_session_name.clear();
        weekly_session_video_id.clear();
        weekly_session_sessionid.clear();
        weekly_session_video_type.clear();
        weekly_session_image.clear();

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<WeeklyData> call = service.getWeeklyActivity(class_string);
        call.enqueue(new Callback<WeeklyData>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<WeeklyData> call, Response<WeeklyData> response) {
                if (response.isSuccessful()) {
                    Log.i("Respons", String.valueOf(response.body()));
                    weeklyData=response.body();
                    weeklyDataDetailList=weeklyData.getRes();
                    if (weeklyDataDetailList!=null){
                        for (int i=0;i<weeklyDataDetailList.size();i++){
                            if (weeklyDataDetailList.get(i).getYoutubelink().equals("")){
                                weekly_session_video_id.add(weeklyDataDetailList.get(i).getVimeoId());
                                weekly_session_video_type.add("Vimeo");
                            }else {
                                weekly_session_video_id.add(weeklyDataDetailList.get(i).getYoutubelink());
                                weekly_session_video_type.add("Youtube");
                            }
                            weekly_session_name.add(weeklyDataDetailList.get(i).getSessionName());
//                            String match_string=getDatesBetween(valueBaseEducationPojoResponseList.get(i).getFromDT(),valueBaseEducationPojoResponseList.get(i).getToDT(),date);
//                            match_date_array.add(match_string);
//                            date_array.add(weeklyDataDetailList.get(i).getFromDT());
                            weekly_session_image.add(weeklyDataDetailList.get(i).getSessionImage());
                        }
                    }

                    if (weekly_session_name!=null) {
                        if (table_size){
                            weeklyRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                            weeklyRecyclerView.setLayoutManager(mLayoutManager);
                            activitySessionAdapter = new ActivitySessionAdapter(getApplicationContext(),weekly_session_name,weekly_session_image,weekly_session_video_id,weekly_session_sessionid,weekly_session_video_type);
                            weeklyRecyclerView.setAdapter(activitySessionAdapter);
                        }else {
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            weeklyRecyclerView.setLayoutManager(layoutManager);
                            activitySessionAdapter = new ActivitySessionAdapter(getApplicationContext(),weekly_session_name,weekly_session_image,weekly_session_video_id,weekly_session_sessionid,weekly_session_video_type);
                            weeklyRecyclerView.setAdapter(activitySessionAdapter);
//
                        }
//                        weeklyRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
//                        weeklyRecyclerView.setLayoutManager(mLayoutManager);
//                        activitySessionAdapter = new ActivitySessionAdapter(getApplicationContext(),weekly_session_name,weekly_session_image,weekly_session_video_id,weekly_session_sessionid,weekly_session_video_type);
//                        weeklyRecyclerView.setAdapter(activitySessionAdapter);
                    }else {
                    }
                } else {
                    Log.i("Failure", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<WeeklyData> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setFestivalSession(String student_id, String month_string, String day_string, String class_string,Boolean tablet_size) {
        festival_session_image.clear();
        festival_session_video_type.clear();
        festival_session_name.clear();
        festival_session_video_id.clear();
        festival_session_sessionid.clear();

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<ValueBaseEducationPojo> call = service.getValueBaseD(class_string);
        call.enqueue(new Callback<ValueBaseEducationPojo>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ValueBaseEducationPojo> call, Response<ValueBaseEducationPojo> response) {
                if (response.isSuccessful()) {
                    Log.i("Respons", String.valueOf(response.body()));
                    valueBaseEducationPojo=response.body();
                    valueBaseEducationPojoResponseList=valueBaseEducationPojo.getRes();
                    if (valueBaseEducationPojoResponseList!=null){
                        for (int i=0;i<valueBaseEducationPojoResponseList.size();i++){
                            if (valueBaseEducationPojoResponseList.get(i).getYoutubelink().equals("")){
                                festival_session_video_id.add(valueBaseEducationPojoResponseList.get(i).getVimeoId());
                                festival_session_video_type.add("Vimeo");
                            }else {
                                festival_session_video_id.add(valueBaseEducationPojoResponseList.get(i).getYoutubelink());
                                festival_session_video_type.add("Youtube");
                            }
                            festival_session_name.add(valueBaseEducationPojoResponseList.get(i).getSessionName());
                            festival_session_image.add(valueBaseEducationPojoResponseList.get(i).getSessionImage());
                        }
                    }

                    if (festival_session_name!=null) {

                        if (tablet_size){
                            actictyRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                            actictyRecyclerView.setLayoutManager(mLayoutManager);
                            activitySessionAdapter = new ActivitySessionAdapter(getApplicationContext(),festival_session_name,festival_session_image,festival_session_video_id,festival_session_sessionid,festival_session_video_type);
                            actictyRecyclerView.setAdapter(activitySessionAdapter);
                        }else {
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            actictyRecyclerView.setLayoutManager(layoutManager);
                            activitySessionAdapter = new ActivitySessionAdapter(getApplicationContext(),festival_session_name,festival_session_image,festival_session_video_id,festival_session_sessionid,festival_session_video_type);
                            actictyRecyclerView.setAdapter(activitySessionAdapter);
//
                        }

//                        actictyRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
//                        actictyRecyclerView.setLayoutManager(mLayoutManager);
//                        activitySessionAdapter = new ActivitySessionAdapter(getApplicationContext(),festival_session_name,festival_session_image,festival_session_video_id,festival_session_sessionid,festival_session_video_type);
//                        actictyRecyclerView.setAdapter(activitySessionAdapter);
                    }else {
                    }
                } else {
                    Log.i("Failure", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<ValueBaseEducationPojo> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_back_button:
                finish();
                break;
        }
    }
}