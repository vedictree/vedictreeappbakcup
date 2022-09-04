package com.vedictree.preschool;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivityNew extends AppCompatActivity implements View.OnClickListener {
//    VideoView mVideoView;

//    ImageView  mPandaGif;
    Button mLogin;
    Button mSignUp;
    SharedPreferences preferences;
    String mName;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
//        mPandaGif=findViewById(R.id.panda_video);
//        Glide.with(this).load(R.raw.panda_image_gif).into(mPandaGif);

        mLogin=findViewById(R.id.new_loginButton);
        mSignUp=findViewById(R.id.new_signUpButton);
        mLogin.setOnClickListener(this);
        mSignUp.setOnClickListener(this);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){


        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();
        mName=preferences.getString("NAME","");
        if(mName.equals("")){

        }
        else {
            Intent login_intent=new Intent(LoginActivityNew.this, ChildeDashboardNew.class);
            startActivity(login_intent);
            finish();
        }
        }
        else {
            setLogoutPopUp(this);
        }


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_loginButton:
                final ValueAnimator video_day_anim = ValueAnimator.ofFloat(1f, 1.05f);
                video_day_anim.setDuration(300);
                video_day_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mLogin.setScaleX((Float) animation.getAnimatedValue());
                        mLogin.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                video_day_anim.setRepeatCount(1);
                video_day_anim.setRepeatMode(ValueAnimator.REVERSE);
                video_day_anim.start();
                Intent intent=new Intent(LoginActivityNew.this, LoginActivity.class);
                startActivity(intent);
            break;

            case R.id.new_signUpButton:
                final ValueAnimator sign_anim = ValueAnimator.ofFloat(1f, 1.05f);
                sign_anim.setDuration(300);
                sign_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mSignUp.setScaleX((Float) animation.getAnimatedValue());
                        mSignUp.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                sign_anim.setRepeatCount(1);
                sign_anim.setRepeatMode(ValueAnimator.REVERSE);
                sign_anim.start();
                Intent intent2=new Intent(LoginActivityNew.this, RegisterActivity.class);
                startActivity(intent2);
            break;


        }
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