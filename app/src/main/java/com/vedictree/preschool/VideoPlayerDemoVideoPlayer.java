package com.vedictree.preschool;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.mediarouter.app.MediaRouteButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chaos.view.PinView;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLoadRequestData;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.images.WebImage;
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
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayerDemoVideoPlayer extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoPlayerDemoVideoMediaController.MediaPlayerControl {
    SurfaceView videoSurface;
    MediaPlayer player;
    VideoPlayerDemoVideoMediaController controller;
    SharedPreferences preferences;
    TextView mVideoName;
    LinearLayout listLinear;
    Handler handler;
    RecyclerView mRecyclerView;
    String video_string;
    SharedPreferences.Editor editor;
    ImageView mLockUnlock;
    Boolean    lock_flag;
    EditText mParentPin;
    AlertDialog alertDialog_onCreate;
    AlertDialog alertDialog;
    ImageView mPlayPause;
    String video_current_position;
    Button video_quality;
    String video_id;
    String from_dashboard;
    String parent_pin;
    PinView pinView;
    ImageView mBack;
    List<parentPinPojoResponse> parentPinPojoResponseList;
    private List<Get_live_session_detail> get_live_session_detailList;
    String package_name;
//    LinearLayout video_image_ll;
//    ImageView video_image;

    private CastSession mCastSession;
    private CastContext mCastContext;
    private SessionManager mSessionManager;
    private final SessionManagerListener<CastSession> mSessionManagerListener =
            new MySessionManagerListener();
    private List<MediaInfo> videos;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"CommitPrefEdits", "InvalidWakeLockTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        stopService(new Intent(this,MusicBackground.class));

        stopService(new Intent(this,MusicBackground.class));
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
                        Intent login_intent=new Intent(VideoPlayerDemoVideoPlayer.this, LoginActivity.class);
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

        listLinear=findViewById(R.id.list_view);
        mRecyclerView=findViewById(R.id.you_tube_more_video);

        String image_url=preferences.getString("VIDEO_THUMB","");
        String  video_title=preferences.getString("VIDEO_TITLE","");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();
        alertDialog_onCreate.setCancelable(false);


        video_quality=findViewById(R.id.qqq);
        mLockUnlock=findViewById(R.id.lock_unlock_image);
        mPlayPause=findViewById(R.id.video_play_pause);
        lock_flag=true;
        mLockUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lock_flag){

                    mLockUnlock.setBackgroundResource(R.drawable.lock_image);
                    controller.setVisibility(View.GONE);
                    lock_flag=false;
                }
                else {
                    player.pause();
                    setStopTime();

                    Dialog alertDialog = new Dialog(view.getContext());
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.setContentView(R.layout.child_parent_pin_pop_up);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    String parent_pin=preferences.getString("PARENT_PIN_LOGIN","");

                    TextView parent_pin_label=alertDialog.findViewById(R.id.parent_pin_label);
                    Button forgot_pin=alertDialog.findViewById(R.id.forgot_pin_new);
                    Button set_pin=alertDialog.findViewById(R.id.ok_pin_new);

                    if (parent_pin.equals("0")){
                        parent_pin_label.setText("SET PIN");
                        forgot_pin.setVisibility(View.GONE);
                        set_pin.setVisibility(View.VISIBLE);
                    }else {
                        parent_pin_label.setText("ENTER PIN");
                        forgot_pin.setVisibility(View.GONE);
                        set_pin.setVisibility(View.GONE);
                    }
                    pinView=alertDialog.findViewById(R.id.firstPinView);

                    ImageView imageView=alertDialog.findViewById(R.id.eye_view);
                    imageView.setImageResource(R.drawable.ic_baseline_remove_white_eye_24);


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
                                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_white_24);
                                pinView.setText(pin_str);
                            }else {
                                pinView.setPasswordHidden(true);
                                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_remove_white_eye_24);
                                pinView.setText(pin_str);

                            }
                        }
                    });

                    ImageView one=alertDialog.findViewById(R.id.child_key_one);
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
                    CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(view.getContext());
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

                                }
                                else {
                                    String student_id=preferences.getString("STUDENT_ID","");

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
                        public void onClick(View view) {final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
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

                            if (pinView.getText().equals(""))
                            {
                                pinView.setText("1");
                            }
                            pinView.setText(pinView.getText().toString()+"1");


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
                            if (pinView.getText().equals(""))
                            {
                                pinView.setText("2");
                            }
                            pinView.setText(pinView.getText().toString()+"2");
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
                            if (pinView.getText().equals(""))
                            {
                                pinView.setText("3");
                            }
                            pinView.setText(pinView.getText().toString()+"3");
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
                            if (pinView.getText().equals(""))
                            {
                                pinView.setText("4");
                            }
                            pinView.setText(pinView.getText().toString()+"4");
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
                            if (pinView.getText().equals(""))
                            {
                                pinView.setText("5");
                            }
                            pinView.setText(pinView.getText().toString()+"5");
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
                            if (pinView.getText().equals(""))
                            {
                                pinView.setText("6");
                            }
                            pinView.setText(pinView.getText().toString()+"6");
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
                                    player.start();
                                    controller.setVisibility(View.VISIBLE);
                                    mLockUnlock.setBackgroundResource(R.drawable.unlick_image);

                                } else {
                                    pinView.setError("Enter correct parent pin");
                                }
                            }
                        }
                    });
                    alertDialog.show();
                    lock_flag=true;
                }
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


        handler=new Handler();

        parent_pin=preferences.getString("PARENT_PIN_LOGIN","");
        Log.i("Parent pi",parent_pin);
        String str = preferences.getString("VIDEO_NAME", "");
        video_string=preferences.getString("VIDEO_VIMEO","");
        video_id=preferences.getString("VIMEO_ID","");
        video_current_position=preferences.getString("LAST_POSITION","00");
        from_dashboard=preferences.getString("FROM_DASHBOARD","");

        Log.i("URL_STRING_TIME:",video_current_position);

        videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
        mVideoName=findViewById(R.id.video_label);

        SurfaceHolder videoHolder = videoSurface.getHolder();
        videoHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        videoHolder.setKeepScreenOn(true);
        videoHolder.addCallback(this);


        player = new MediaPlayer();
        controller = new VideoPlayerDemoVideoMediaController(this);

        try {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(String.valueOf(Uri.parse(video_string)));
            player.setOnPreparedListener(this);
//            video_image_ll.setVisibility(View.VISIBLE);
            alertDialog_onCreate.show();

        }
        catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
            Log.i("Media issue",e.getMessage());
            e.printStackTrace();
        }
        mBack=findViewById(R.id.video_back_image);
        mBack.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                if (lock_flag){
                    Log.i("Video_time", String.valueOf(player.getDuration()));
                    Log.i("Video_time2", String.valueOf(player.getCurrentPosition()));
                    if(String.valueOf(player.getCurrentPosition()).equals(String.valueOf(player.getDuration()))) {
//                player.stop();
                        setStopTime();
                        Log.i("Video current position back 2:", String.valueOf(player.getCurrentPosition()));
                        editor.putString("LAST_POSITION", String.valueOf(player.getCurrentPosition()));
                        editor.commit();
                        finish();
                    }
                    else {

                        Double video_duration_str=Double.parseDouble(String.valueOf(player.getDuration()));
                        Double video_current_str=Double.parseDouble(String.valueOf(player.getCurrentPosition()));
                        Double video_pause_time=(video_current_str/video_duration_str);
                        video_pause_time=video_pause_time*100;
                        Log.i("Video Pause time",String.valueOf(video_pause_time));
                        if (video_pause_time>98.00){
                            setStopTime();
                            finish();
                        }else {

                            Dialog alertDialog = new Dialog(view.getContext());
                            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            alertDialog.setContentView(R.layout.stop_video_pop_up);
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            Button no_button = alertDialog.findViewById(R.id.stop_video_no_button);
                            no_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    final ValueAnimator no_button_anim = ValueAnimator.ofFloat(1f, 1.05f);
                                    no_button_anim.setDuration(300);
                                    no_button_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            no_button.setScaleX((Float) animation.getAnimatedValue());
                                            no_button.setScaleY((Float) animation.getAnimatedValue());
                                        }
                                    });
                                    no_button_anim.setRepeatCount(1);
                                    no_button_anim.setRepeatMode(ValueAnimator.REVERSE);
                                    no_button_anim.start();
                                    alertDialog.dismiss();
                                    alertDialog.cancel();
                                }
                            });

                            Button yes_button = alertDialog.findViewById(R.id.stop_video_yes_button);
                            yes_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    final ValueAnimator yes_button_anim = ValueAnimator.ofFloat(1f, 1.05f);
                                    yes_button_anim.setDuration(300);
                                    yes_button_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            yes_button.setScaleX((Float) animation.getAnimatedValue());
                                            yes_button.setScaleY((Float) animation.getAnimatedValue());
                                        }
                                    });
                                    yes_button_anim.setRepeatCount(1);
                                    yes_button_anim.setRepeatMode(ValueAnimator.REVERSE);
                                    yes_button_anim.start();
                                    alertDialog.dismiss();
                                    player.stop();
                                    setStopTime();
                                    finish();
                                }
                            });
                            alertDialog.show();
                        }

                    }
                }else {

                }
            }
        });

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
                                        Window window = VideoPlayerDemoVideoPlayer.this.getWindow();
                                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerDemoVideoPlayer.this);
                                        ViewGroup viewGroup = findViewById(android.R.id.content);
                                        View dialog = LayoutInflater.from(VideoPlayerDemoVideoPlayer.this).inflate(R.layout.live_sessions_pop_up, viewGroup, false);
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
                                                        Intent webIntent1 = new Intent(VideoPlayerDemoVideoPlayer.this, YouTubeVideo.class);
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

        MediaRouteButton mMediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), mMediaRouteButton);
        mCastContext = CastContext.getSharedInstance(this);
        mSessionManager = CastContext.getSharedInstance(this).getSessionManager();

        MediaMetadata mediaMetadata=new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
        mediaMetadata.putString(MediaMetadata.KEY_SUBTITLE,"");
        mediaMetadata.putString(MediaMetadata.KEY_TITLE,video_title);
        mediaMetadata.addImage(new WebImage(Uri.parse(image_url)));
        final MediaInfo item =new MediaInfo.Builder(video_string)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType("video/mp4")
                .setMetadata(mediaMetadata)
                .build();
        mCastSession = mSessionManager.getCurrentCastSession();
//        mSessionManager.addSessionManagerListener(mSessionManagerListener, CastSession.class);

        if(mCastSession!=null) {
            RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
            assert remoteMediaClient != null;
            remoteMediaClient.load(new MediaLoadRequestData.Builder().setMediaInfo(item).build());
        }

        mMediaRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCastSession!=null) {
                    RemoteMediaClient remoteMediaClient = mCastSession.getRemoteMediaClient();
                    assert remoteMediaClient != null;
                    remoteMediaClient.load(new MediaLoadRequestData.Builder().setMediaInfo(item).build());
                }
            }
        });
    }

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {

        @Override
        public void onSessionEnded(CastSession session, int error) {
            if (session == mCastSession) {
                mCastSession = null;
            }
            invalidateOptionsMenu();
            Log.i("onSessionEnded","onSessionEnded");
        }

        @Override
        public void onSessionResumed(CastSession session, boolean wasSuspended) {
            mCastSession = session;
            invalidateOptionsMenu();
            Log.i("onSessionEnded","onSessionEnded");
        }

        @Override
        public void onSessionStarted(CastSession session, String sessionId) {
            mCastSession = session;
            invalidateOptionsMenu();
            Log.i("onSessionEnded","onSessionEnded");
        }

        @Override
        public void onSessionStarting(CastSession session) {
            Log.i("onSessionStarting","onSessionStarting");
        }

        @Override
        public void onSessionStartFailed(CastSession session, int error) {
            Log.i("onSessionStartFailed","onSessionStartFailed");
        }

        @Override
        public void onSessionEnding(CastSession session) {
            Log.i("onSessionEnding","onSessionEnding");
        }

        @Override
        public void onSessionResuming(CastSession session, String sessionId) {
            Log.i("onSessionResuming","onSessionResuming");
        }

        @Override
        public void onSessionResumeFailed(CastSession session, int error) {
            Log.i("onSessionResumeFailed","onSessionResumeFailed");
        }

        @Override
        public void onSessionSuspended(CastSession session, int reason) {
            Log.i("onSessionSuspended","onSessionSuspended");
        }
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
                            Dialog alertDialog = new Dialog(VideoPlayerDemoVideoPlayer.this);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.show();
        mBack.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // hide your button here
                mBack.setVisibility(View.GONE);
            }
        }, 3000);
        return false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setDisplay(holder);
        try {
//            player.prepareAsync();
            player.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        Log.i("Pause","Pause");
        player.pause();
        super.onPause();
        mSessionManager.removeSessionManagerListener(mSessionManagerListener, CastSession.class);
        mCastSession = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    private void checkParentPin() {
        {
            String student_id=preferences.getString("STUDENT_ID","");
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
//                                Intent login_intent = new Intent(ChildeDashboardNew.this, ChildeDashboardNew.class);
//                                startActivity(login_intent);

                            } else {
                                player.start();
                                    controller.setVisibility(View.VISIBLE);
                                    mLockUnlock.setBackgroundResource(R.drawable.unlick_image);
                                    mBack.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        // hide your button here
                                        mBack.setVisibility(View.GONE);
                                    }
                                }, 3000);
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
    protected void onResume() {
        stopService(new Intent(this,MusicBackground.class));
        if (player.isPlaying()) {
            Log.i("Pause", "Resume");
            player.start();
            player.seekTo(player.getCurrentPosition());
            controller.setVisibility(View.VISIBLE);
        }
        controller.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // hide your button here
                mBack.setVisibility(View.GONE);
            }
        }, 3000);
        super.onResume();
//        mCastSession = mSessionManager.getCurrentCastSession();
//        mSessionManager.addSessionManagerListener(mSessionManagerListener, CastSession.class);

        mCastSession = mSessionManager.getCurrentCastSession();
        mSessionManager.addSessionManagerListener(mSessionManagerListener, CastSession.class);
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        controller.setMediaPlayer(this);
        controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
        player.start();
//        player.pause();
        alertDialog_onCreate.dismiss();
//        video_image_ll.setVisibility(View.GONE);
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @SuppressLint("LongLogTag")
    @Override
    public int getCurrentPosition() {
        Log.i("Video CurrentPosition:",String.valueOf(player.getCurrentPosition()));
        setStopTime();
        if(player.getCurrentPosition()==player.getDuration()){
            player.stop();
            setStopTime();
            Log.i("Video current position back 2:",String.valueOf(player.getCurrentPosition()));
            editor.putString("LAST_POSITION",String.valueOf(player.getCurrentPosition()));
            editor.commit();
            finish();
        }else {
            setStopTime();
        }
        return player.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return player.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        player.pause();
        setStopTime();
    }

    @Override
    public void seekTo(int i) {
        player.seekTo(i);
    }

    @Override
    public void start() {
        player.start();
    }

    @SuppressLint("LongLogTag")
    public void stop() {
        Double video_duration_str=Double.parseDouble(String.valueOf(player.getDuration()));
        Double video_current_str=Double.parseDouble(String.valueOf(player.getCurrentPosition()));
        Double video_pause_time=(video_current_str/video_duration_str);
        video_pause_time=video_pause_time*100;
        if (video_pause_time>98.00){
            controller.hide();
            editor.putString("RESUME_TIME", String.valueOf(player.getCurrentPosition()));
            editor.commit();
            Log.i("Video stop", "Video Stop");
            player.stop();
            setStopTime();
            finish();
        }else {
            controller.hide();
            editor.putString("RESUME_TIME", String.valueOf(player.getCurrentPosition()));
            editor.commit();
            Log.i("Video stop", "Video Stop");
            player.stop();
            setStopTime();
        }

    }

    private void setStopTime() {
        if (from_dashboard.equals("dashboard")) {
            String student_string = preferences.getString("STUDENT_ID", "");
            String class_string = preferences.getString("CLASS", "");
            String vimeo_id = preferences.getString("VIMEO_ID", "");
            String session_id_str = preferences.getString("VIMEO_SESSION_ID", "");

            String month_str = preferences.getString("FINAL_MONTH", "");
            String day_str = preferences.getString("FINAL_DAY", "");

            DateFormat formatter = new SimpleDateFormat("mm:ss", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String total_time = formatter.format(new Date(player.getDuration()));
            String current_time = formatter.format(new Date(player.getCurrentPosition()));

            String[] units = total_time.split(":"); //will break the string up into an array
            int minutes = Integer.parseInt(units[0]); //first element
            int seconds = Integer.parseInt(units[1]); //second element
            Float duration =Float.parseFloat( String.valueOf(60 * minutes + seconds));

            String[] units_track = current_time.split(":"); //will break the string up into an array
            int minutes_track = Integer.parseInt(units_track[0]); //first element
            int seconds_track = Integer.parseInt(units_track[1]); //second element
            Float duration_track = Float.parseFloat(String.valueOf(60 * minutes_track + seconds_track));

            Log.i("Video detail", duration + "  " + vimeo_id + "  " + session_id_str + "  " + duration_track);

            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<ResponseBody> call = service.getTracking(duration, duration_track, vimeo_id, month_str, day_str, student_string, session_id_str, class_string);
            call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() != null) {
                                Log.i("Video detail", "Video detail upload successfully");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
        }
        else {

        }

    }

    @Override
    public void onResumeVideo() {

    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void toggleFullScreen() {

    }
    @SuppressLint("LongLogTag")
    @Override
    public void onBackPressed(){
        if (lock_flag){
            Log.i("Video_time", String.valueOf(player.getDuration()));
            Log.i("Video_time2", String.valueOf(player.getCurrentPosition()));
            if(String.valueOf(player.getCurrentPosition()).equals(String.valueOf(player.getDuration()))) {
//                player.stop();
                setStopTime();
                Log.i("Video current position back 2:", String.valueOf(player.getCurrentPosition()));
                editor.putString("LAST_POSITION", String.valueOf(player.getCurrentPosition()));
                editor.commit();
                finish();
            }
            else {

                Double video_duration_str=Double.parseDouble(String.valueOf(player.getDuration()));
                Double video_current_str=Double.parseDouble(String.valueOf(player.getCurrentPosition()));
                Double video_pause_time=(video_current_str/video_duration_str);
                video_pause_time=video_pause_time*100;
                Log.i("Video Pause time",String.valueOf(video_pause_time));
                if (video_pause_time>98.00){
                    setStopTime();
                    finish();
                }else {

                    Dialog alertDialog = new Dialog(getApplicationContext());
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.setContentView(R.layout.stop_video_pop_up);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Button no_button = alertDialog.findViewById(R.id.stop_video_no_button);
                    no_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final ValueAnimator no_button_anim = ValueAnimator.ofFloat(1f, 1.05f);
                            no_button_anim.setDuration(300);
                            no_button_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    no_button.setScaleX((Float) animation.getAnimatedValue());
                                    no_button.setScaleY((Float) animation.getAnimatedValue());
                                }
                            });
                            no_button_anim.setRepeatCount(1);
                            no_button_anim.setRepeatMode(ValueAnimator.REVERSE);
                            no_button_anim.start();
                            alertDialog.dismiss();
                            alertDialog.cancel();
                        }
                    });

                    Button yes_button = alertDialog.findViewById(R.id.stop_video_yes_button);
                    yes_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final ValueAnimator yes_button_anim = ValueAnimator.ofFloat(1f, 1.05f);
                            yes_button_anim.setDuration(300);
                            yes_button_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    yes_button.setScaleX((Float) animation.getAnimatedValue());
                                    yes_button.setScaleY((Float) animation.getAnimatedValue());
                                }
                            });
                            yes_button_anim.setRepeatCount(1);
                            yes_button_anim.setRepeatMode(ValueAnimator.REVERSE);
                            yes_button_anim.start();
                            alertDialog.dismiss();
                            player.stop();
                            setStopTime();
                            finish();
                        }
                    });
                    alertDialog.show();
                }

            }
        }else {

        }
    }
//    {
//        if (lock_flag){
//            if(player.getCurrentPosition()==player.getDuration()) {
////                player.stop();
//                setStopTime();
//                Log.i("Video current position back 2:", String.valueOf(player.getCurrentPosition()));
//                editor.putString("LAST_POSITION", String.valueOf(player.getCurrentPosition()));
//                editor.commit();
//                finish();
//            }else {
//                AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
//                alertdialog.setMessage("Are you sure you want to stop video???");
//                alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        player.stop();
//                        setStopTime();
//
//                        Log.i("Video current position back:", String.valueOf(player.getCurrentPosition()));
//                        editor.putString("LAST_POSITION", String.valueOf(player.getCurrentPosition()));
//                        editor.commit();
//                        finish();
//
//                    }
//                });
//                alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog alert = alertdialog.create();
//                alertdialog.show();
//            }
//        }else {
//
//        }
//    }

    @SuppressLint("SetTextI18n")
    public void ShowHidePass(View view){

        if(view.getId()==R.id.eye_view){

            if(mParentPin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.invisible);
                mParentPin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.eye);
                mParentPin.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
        else if (view.getId()==R.id.child_key_one){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("1");
            }
            mParentPin.setText(mParentPin.getText().toString()+"1");
        }
        else if (view.getId()==R.id.child_key_two){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("2");
            }
            mParentPin.setText(mParentPin.getText().toString()+"2");
        }
        else if (view.getId()==R.id.child_key_three){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("3");
            }
            mParentPin.setText(mParentPin.getText().toString()+"3");
        }
        else if (view.getId()==R.id.child_key_four){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("4");
            }
            mParentPin.setText(mParentPin.getText().toString()+"4");
        }
        else if (view.getId()==R.id.child_key_five){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("5");
            }
            mParentPin.setText(mParentPin.getText().toString()+"5");
        }
        else if (view.getId()==R.id.child_key_six){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("6");
            }
            mParentPin.setText(mParentPin.getText().toString()+"6");
        }
        else if (view.getId()==R.id.child_key_seven){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("7");
            }
            mParentPin.setText(mParentPin.getText().toString()+"7");
        }
        else if (view.getId()==R.id.child_key_eight){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("8");
            }
            mParentPin.setText(mParentPin.getText().toString()+"8");
        }
        else if (view.getId()==R.id.child_key_nine){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("9");
            }
            mParentPin.setText(mParentPin.getText().toString()+"9");
        }
        else if (view.getId()==R.id.child_key_zero){
            if (mParentPin.getText().equals(""))
            {
                mParentPin.setText("0");
            }
            mParentPin.setText(mParentPin.getText().toString()+"0");
        }
        else if (view.getId()==R.id.ok_pin_new){
            if (mParentPin.getText().toString().equals(parent_pin)){
                alertDialog.dismiss();
                player.start();
                controller.setVisibility(View.VISIBLE);
                mLockUnlock.setBackgroundResource(R.drawable.unlick_image);
                mBack.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // hide your button here
                        mBack.setVisibility(View.GONE);
                    }
                }, 3000);
            }
        }
        else if (view.getId()==R.id.child_key_backspace){
            int length = mParentPin.getText().length();
            if (length > 0) {
                mParentPin.getText().delete(length - 1, length);
            }
        }

    }
}
