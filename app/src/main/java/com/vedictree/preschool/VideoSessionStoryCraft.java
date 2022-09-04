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
import com.vedictree.preschool.Adapters.StorySessionAdapter;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.StoryCraftPojo;
import com.vedictree.preschool.POJO.StoryCraftPojoDetail;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoSessionStoryCraft extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout mStory;
    LinearLayout mCraft;
    RecyclerView mStoryRecyclerView;
    RecyclerView mCraftRecyclerView;
    StorySessionAdapter activitySessionAdapter;
    RecyclerView.LayoutManager layoutManager;


    ArrayList<String> story_session_name;
    ArrayList<String> story_session_image;
    ArrayList<String> story_session_video_id;
    ArrayList<String> story_session_sessionid;
    ArrayList<String> story_session_video_type;

    ArrayList<String> craft_session_name;
    ArrayList<String> craft_session_image;
    ArrayList<String> craft_session_video_id;
    ArrayList<String> craft_session_sessionid;
    ArrayList<String> craft_session_video_type;
    ImageView mLogo;

//    ArrayList<Integer> session_background;
//    ArrayList<Integer> session_background_two;

    private StoryCraftPojo weeklyData;
    private List<StoryCraftPojoDetail> weeklyDataDetailList;

    ImageView mBack;

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
//

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_session_story_craft);

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
                        Intent login_intent=new Intent(VideoSessionStoryCraft.this, LoginActivity.class);
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

        mStory=findViewById(R.id.story_value_ll);
        mCraft=findViewById(R.id.craft_ll);
        mStoryRecyclerView=findViewById(R.id.story_session_recyclerview_2);
        mCraftRecyclerView=findViewById(R.id.craft_session_recyclerview_2);
        mBack=findViewById(R.id.s_c_back_button);
        mLogo=findViewById(R.id.story_craft_logo);

        craft_session_image=new ArrayList<>();
        craft_session_sessionid=new ArrayList<>();
        craft_session_video_type=new ArrayList<>();
        craft_session_video_id=new ArrayList<>();
        craft_session_name=new ArrayList<>();

        story_session_image=new ArrayList<>();
        story_session_video_type=new ArrayList<>();
        story_session_sessionid=new ArrayList<>();
        story_session_name=new ArrayList<>();
        story_session_video_id=new ArrayList<>();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();

        String story_or_craft=preferences.getString("STORY_OR_CRAFT","");
        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        if (story_or_craft.equals("STORY")){
            mCraft.setVisibility(View.GONE);
            mStory.setVisibility(View.VISIBLE);
            setStoryVideo(tabletSize);

        }else if (story_or_craft.equals("CRAFT")){
            mCraft.setVisibility(View.VISIBLE);
            mStory.setVisibility(View.GONE);
            setCraftVideo(tabletSize);
        }else {

        }

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

    private void setStoryVideo(boolean table_size) {
        story_session_name.clear();
        story_session_video_id.clear();
        story_session_image.clear();
        story_session_video_type.clear();
        story_session_sessionid.clear();

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<StoryCraftPojo> call = service.getStoryCraft("1");
        call.enqueue(new Callback<StoryCraftPojo>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<StoryCraftPojo> call, Response<StoryCraftPojo> response) {
                if (response.isSuccessful()) {
                    Log.i("Respons", String.valueOf(response.body()));
                    weeklyData=response.body();
                    weeklyDataDetailList=weeklyData.getRes();
                    if (weeklyDataDetailList!=null){
                        for (int i=0;i<weeklyDataDetailList.size();i++){
                            if (weeklyDataDetailList.get(i).getYoutubelink().equals("")){
                                story_session_video_id.add(weeklyDataDetailList.get(i).getVimeoId());
                                story_session_video_type.add("Vimeo");
                            }else {
                                story_session_video_id.add(weeklyDataDetailList.get(i).getYoutubelink());
                                story_session_video_type.add("Youtube");
                            }
                            story_session_name.add(weeklyDataDetailList.get(i).getStoryTitle());
                             story_session_image.add(weeklyDataDetailList.get(i).getCraftbanner());
                        }
                    }

                    if ( story_session_name!=null) {

                        if (table_size){
                            mStoryRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                            mStoryRecyclerView.setLayoutManager(mLayoutManager);
                            activitySessionAdapter = new StorySessionAdapter(getApplicationContext(),story_session_name,story_session_image,story_session_video_id,story_session_sessionid,story_session_video_type);
                            mStoryRecyclerView.setAdapter(activitySessionAdapter);
                        }else {
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            mStoryRecyclerView.setLayoutManager(layoutManager);
                            activitySessionAdapter = new StorySessionAdapter(getApplicationContext(),story_session_name,story_session_image,story_session_video_id,story_session_sessionid,story_session_video_type);
                            mStoryRecyclerView.setAdapter(activitySessionAdapter);
//
                        }

//                        mStoryRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
//                        mStoryRecyclerView.setLayoutManager(mLayoutManager);
//                        activitySessionAdapter = new StorySessionAdapter(getApplicationContext(),story_session_name,story_session_image,story_session_video_id,story_session_sessionid,story_session_video_type);
//                        mStoryRecyclerView.setAdapter(activitySessionAdapter);
                    }else {
                    }
                } else {
                    Log.i("Failure", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<StoryCraftPojo> call, Throwable t) {
            }
        });
    }

    private void setCraftVideo(boolean tablet_size) {
        story_session_name.clear();
        story_session_video_id.clear();
        story_session_image.clear();
        story_session_video_type.clear();
        story_session_sessionid.clear();

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<StoryCraftPojo> call = service.getStoryCraft("2");
        call.enqueue(new Callback<StoryCraftPojo>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<StoryCraftPojo> call, Response<StoryCraftPojo> response) {
                if (response.isSuccessful()) {
                    Log.i("Respons", String.valueOf(response.body()));
                    weeklyData=response.body();
                    weeklyDataDetailList=weeklyData.getRes();
                    if (weeklyDataDetailList!=null){
                        for (int i=0;i<weeklyDataDetailList.size();i++){
                            if (weeklyDataDetailList.get(i).getYoutubelink().equals("")){
                                story_session_video_id.add(weeklyDataDetailList.get(i).getVimeoId());
                                story_session_video_type.add("Vimeo");
                            }else {
                                story_session_video_id.add(weeklyDataDetailList.get(i).getYoutubelink());
                                story_session_video_type.add("Youtube");
                            }
                            story_session_name.add(weeklyDataDetailList.get(i).getStoryTitle());
                             story_session_image.add(weeklyDataDetailList.get(i).getCraftbanner());
                        }
                    }

                    if ( story_session_name!=null) {
                        if (tablet_size){
                            mCraftRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                            mCraftRecyclerView.setLayoutManager(mLayoutManager);
                            activitySessionAdapter = new StorySessionAdapter(getApplicationContext(),story_session_name,story_session_image,story_session_video_id,story_session_sessionid,story_session_video_type);
                            mCraftRecyclerView.setAdapter(activitySessionAdapter);
                        }else {
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            mCraftRecyclerView.setLayoutManager(layoutManager);
                            activitySessionAdapter = new StorySessionAdapter(getApplicationContext(),story_session_name,story_session_image,story_session_video_id,story_session_sessionid,story_session_video_type);
                            mCraftRecyclerView.setAdapter(activitySessionAdapter);
//
                        }

//                        mCraftRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
//                        mCraftRecyclerView.setLayoutManager(mLayoutManager);
//                        activitySessionAdapter = new StorySessionAdapter(getApplicationContext(),story_session_name,story_session_image,story_session_video_id,story_session_sessionid,story_session_video_type);
//                        mCraftRecyclerView.setAdapter(activitySessionAdapter);
                    }else {
                    }
                } else {
                    Log.i("Failure", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<StoryCraftPojo> call, Throwable t) {
            }
        });
    }


}