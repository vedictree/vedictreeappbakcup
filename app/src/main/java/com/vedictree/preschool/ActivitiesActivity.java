package com.vedictree.preschool;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.TestPojo;
import com.vedictree.preschool.POJO.TestPojoResponse;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitiesActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mHome;
    ImageView mFestivalImage;
    ImageView mFestivalButton;
    ImageView mActivitiesWeekly;
    ImageView mActivitiesWeeklyButton;
    LinearLayout mainll;
    private SoundPool soundPool;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ImageView mStoryButton;
    ImageView mStoryImage;
    ImageView mCraftButton;
    ImageView mCraftImage;

    ImageView mLogo;

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
        setContentView(R.layout.activity_activities);

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
                        Intent login_intent=new Intent(ActivitiesActivity.this, LoginActivity.class);
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

        mHome=findViewById(R.id.activity_to_home);
        mFestivalButton=findViewById(R.id.activity_festival_button);
        mFestivalImage=findViewById(R.id.activity_festival);
        mActivitiesWeekly=findViewById(R.id.activity_weekly);
        mActivitiesWeeklyButton=findViewById(R.id.activity_weekly_button);
        mLogo=findViewById(R.id.activity_logo_img);

        mStoryButton=findViewById(R.id.story_activity_button);
        mStoryImage=findViewById(R.id.story_activity);
        mCraftImage=findViewById(R.id.craft_activity);
        mCraftButton=findViewById(R.id.craft_activity_button);

        mHome.setOnClickListener(this);
        mFestivalImage.setOnClickListener(this);
        mFestivalButton.setOnClickListener(this);
        mActivitiesWeekly.setOnClickListener(this);
        mActivitiesWeeklyButton.setOnClickListener(this);
        mStoryImage.setOnClickListener(this);
        mStoryButton.setOnClickListener(this);
        mCraftImage.setOnClickListener(this);
        mCraftButton.setOnClickListener(this);


        checkHomewWork();
        setImages();
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
                            Dialog alertDialog = new Dialog(ActivitiesActivity.this);
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
        Intent intent;
        switch (view.getId()){
            case R.id.activity_to_home:
                final ValueAnimator anim2 = ValueAnimator.ofFloat(1f, 1.05f);
                anim2.setDuration(300);
                anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mHome.setScaleX((Float) animation.getAnimatedValue());
                        mHome.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim2.setRepeatCount(1);
                anim2.setRepeatMode(ValueAnimator.REVERSE);
                anim2.start();
                 intent=new Intent(ActivitiesActivity.this,ChildeDashboardNew.class);
                startActivity(intent);
                finish();
//                mediaPlayer.stop();
                break;
            case R.id.activity_festival_button:
//                soundPool.play(a,1f,1f,1,0,1);
                mFestivalButton .startAnimation(AnimationUtils.loadAnimation(ActivitiesActivity.this, R.anim.shake));
                editor.putString("ACTIVITY_START","valuebase");
                editor.commit();
                intent=new Intent(ActivitiesActivity.this,FestivalSession.class);
                startActivity(intent);
                break;
            case R.id.activity_festival:
//                soundPool.play(a,1f,1f,1,0,1);
                mFestivalImage.startAnimation(AnimationUtils.loadAnimation(ActivitiesActivity.this, R.anim.shake));
                editor.putString("ACTIVITY_START","valuebase");
                editor.commit();
                intent=new Intent(ActivitiesActivity.this,FestivalSession.class);
                startActivity(intent);
                break;
            case R.id.activity_weekly:
//                soundPool.play(b,1f,1f,1,0,1);
                mActivitiesWeekly.startAnimation(AnimationUtils.loadAnimation(ActivitiesActivity.this, R.anim.shake));
                editor.putString("ACTIVITY_START","weekly");
                editor.commit();
                intent=new Intent(ActivitiesActivity.this,FestivalSession.class);
                startActivity(intent);
                break;
            case R.id.activity_weekly_button:
//                soundPool.play(b,1f,1f,1,0,1);
                mActivitiesWeeklyButton.startAnimation(AnimationUtils.loadAnimation(ActivitiesActivity.this, R.anim.shake));
                editor.putString("ACTIVITY_START","weekly");
                editor.commit();
                intent=new Intent(ActivitiesActivity.this,FestivalSession.class);
                startActivity(intent);
                break;

            case R.id.story_activity:
                editor.putString("STORY_OR_CRAFT","STORY");
                editor.commit();
                mStoryImage.startAnimation(AnimationUtils.loadAnimation(ActivitiesActivity.this, R.anim.shake));
                intent=new Intent(ActivitiesActivity.this,VideoSessionStoryCraft.class);
                startActivity(intent);
                break;
            case R.id.story_activity_button:
                editor.putString("STORY_OR_CRAFT","STORY");
                editor.commit();
                mStoryButton.startAnimation(AnimationUtils.loadAnimation(ActivitiesActivity.this, R.anim.shake));
                intent=new Intent(ActivitiesActivity.this,VideoSessionStoryCraft.class);
                startActivity(intent);
                break;
            case R.id.craft_activity:
                editor.putString("STORY_OR_CRAFT","CRAFT");
                editor.commit();
                mCraftImage.startAnimation(AnimationUtils.loadAnimation(ActivitiesActivity.this, R.anim.shake));
                intent=new Intent(ActivitiesActivity.this,VideoSessionStoryCraft.class);
                startActivity(intent);
                break;
            case R.id.craft_activity_button:
                editor.putString("STORY_OR_CRAFT","CRAFT");
                editor.commit();
                mCraftButton.startAnimation(AnimationUtils.loadAnimation(ActivitiesActivity.this, R.anim.shake));
                intent=new Intent(ActivitiesActivity.this,VideoSessionStoryCraft.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ActivitiesActivity.this,ChildeDashboardNew.class);
        startActivity(intent);
        finish();
//        mediaPlayer.stop();
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
    private void setImages() {
        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(0.5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Glide.with(mActivitiesWeekly.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/activity_act.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mActivitiesWeekly);

        Glide.with(mActivitiesWeeklyButton.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/activity_act_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mActivitiesWeeklyButton);

        Glide.with(mFestivalImage.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/f_s_bunny.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mFestivalImage);

        Glide.with(mFestivalButton.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/activity_frstival_button.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mFestivalButton);

        Glide.with(mStoryImage.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/stories_icon.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mStoryImage);

        Glide.with(mStoryButton.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/story_button_new.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mStoryButton);

        Glide.with(mCraftImage.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/art_c_image.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mCraftImage);

        Glide.with(mCraftButton.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/arts_craft_button_new.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mCraftButton);

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

}