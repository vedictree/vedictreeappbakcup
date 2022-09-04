package com.vedictree.preschool;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.provider.MediaStore;
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
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.vedictree.preschool.POJO.AccessPojo;
import com.vedictree.preschool.POJO.Get_live_session_detail;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.ProfilePojo;
import com.vedictree.preschool.POJO.Receipt_poj;
import com.vedictree.preschool.POJO.StudentHistory;
import com.vedictree.preschool.POJO.TestPojo;
import com.vedictree.preschool.POJO.TestPojoResponse;
import com.vedictree.preschool.POJO.UserHistory;
import com.vedictree.preschool.POJO.live_session;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;
import com.vedictree.preschool.Utils.VedicConstants;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivityNew extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences preferences;
    String mName;
    String mEmail;
    String mMobile;
    String mClass;
    String mGender;
    String mCode;
    TextView mNameTextView;
    TextView mMobileTextView;
    TextView mEmailTextView;
    Button mReferralCode;
    TextView mProfileClass;
    TextView mPromoCode;
    ImageView mMyCourse;
    ImageView mEditProfile;
    ImageView mChangePassword;
    ImageView mTermAndCondition;
    ImageView show_receipt_button;
    ImageView mShareApp;
    ImageView mLogout;
    Button mPromoCodeButton;

    private UserHistory userHistory;
    private List<StudentHistory> studentHistoryList;
    private StudentHistory studentHistory;
    SharedPreferences.Editor editor;

    String student_id;
    String promo_code;
    LinearLayout promo_layout;
    RequestBody studentIdBody;
    String profile_str;
    de.hdodenhof.circleimageview.CircleImageView circleImageView;

    ImageView mBack;
    private List<Get_live_session_detail> get_live_session_detailList;
    String package_name;
    ImageView mLogo;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_new);

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
                        Intent login_intent=new Intent(ProfileActivityNew.this, LoginActivity.class);
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


        student_id=preferences.getString("STUDENT_ID","");
        studentIdBody= RequestBody.create(MediaType.parse("multipart/form-data"),student_id);

        mName=preferences.getString("NAME","");
        mEmail=preferences.getString("EMAIL","");
        mGender=preferences.getString("GENDER","");
        mMobile=preferences.getString("MOBILE","");
        mCode=preferences.getString("REFFERAL_CODE","");
        mClass=preferences.getString("CLASS","");
        promo_code=preferences.getString("PROMO_CODE","");
        profile_str=preferences.getString("PROFILE_PICTURE","");

        circleImageView=findViewById(R.id.profile_image_new);
        mNameTextView=findViewById(R.id.profile_student_name_new);
        mEmailTextView=findViewById(R.id.profile_email_new);
        mMobileTextView=findViewById(R.id.profile_mobile_new);
        mReferralCode=findViewById(R.id.profile_share_new);
        mProfileClass=findViewById(R.id.profile_course_new);
        mEditProfile=findViewById(R.id.profile_edit_new);
        mChangePassword=findViewById(R.id.profile_forgot_password_new);
        mTermAndCondition=findViewById(R.id.terms_and_conditions_new);
        show_receipt_button=findViewById(R.id.show_receipt_new);
        mShareApp=findViewById(R.id.share_app_link_new);
        mPromoCode=findViewById(R.id.promo_code_str_new);
        mPromoCodeButton=findViewById(R.id.add_promo_code_new);
        promo_layout=findViewById(R.id.promoLayout_new);
        promo_layout.setVisibility(View.GONE);
        mBack=findViewById(R.id.new_profile_back);
        mBack.setOnClickListener(this);
        mMyCourse=findViewById(R.id.profile_my_course);
        mMyCourse.setOnClickListener(this);
        mLogo=findViewById(R.id.profile_new_logo);
        mLogout=findViewById(R.id.profile_logout_new);
        mLogout.setOnClickListener(this);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            setProfilePicture();
//        addNotification();

            mChangePassword.setOnClickListener(this);
            mReferralCode.setOnClickListener(this);
            mEditProfile.setOnClickListener(this);
            mTermAndCondition.setOnClickListener(this);
            show_receipt_button.setOnClickListener(this);
            mShareApp.setOnClickListener(this);
            mPromoCodeButton.setOnClickListener(this);

            mNameTextView.setText(mName);
            mMobileTextView.setText(mMobile);
            mEmailTextView.setText(mEmail);
            mReferralCode.setText(" Referral Code : " + mCode);
            String promo_code = preferences.getString("PROMO_CODE", "");
            String account_status = preferences.getString("ACCOUNT_PAID", "");
            if (account_status.equals("Yes")) {
                promo_layout.setVisibility(View.GONE);
                mPromoCodeButton.setVisibility(View.INVISIBLE);
            } else if (account_status.equals("No")) {
                if (promo_code == null || promo_code.equals("")) {
                    promo_layout.setVisibility(View.GONE);
                    mPromoCodeButton.setVisibility(View.VISIBLE);
                } else {
                    promo_layout.setVisibility(View.VISIBLE);
                    mPromoCodeButton.setVisibility(View.INVISIBLE);
                    mPromoCode.setText(promo_code);

                }
            }

            if (mClass.equals("1")) {
                mProfileClass.setText("Nursery");
            } else if (mClass.equals("2")) {
                mProfileClass.setText("Junior");
            } else if (mClass.equals("3")) {
                mProfileClass.setText("Senior");
            }else if (mClass.equals("4")) {
                mProfileClass.setText("Nursery Physical");
            }else if (mClass.equals("5")) {
                mProfileClass.setText("Junior Physical");
            }else if (mClass.equals("6")) {
                mProfileClass.setText("Senior Physical");
            }
        }else {
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

        Call<live_session> call2 = service2.getLiveSession(mClass,student_id);
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
                                        Window window = ProfileActivityNew.this.getWindow();
                                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivityNew.this);
                                        ViewGroup viewGroup = findViewById(android.R.id.content);
                                        View dialog = LayoutInflater.from(ProfileActivityNew.this).inflate(R.layout.live_sessions_pop_up, viewGroup, false);
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
                                                        Intent webIntent1 = new Intent(ProfileActivityNew.this, YouTubeVideo.class);
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

        setImage();
    }

    private void setImage() {
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

        Glide.with(mEditProfile.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/editprofile.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mEditProfile);

        Glide.with(mShareApp.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/share_app.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mShareApp);

        Glide.with(mMyCourse.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/my_course.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mMyCourse);

        Glide.with(mTermAndCondition.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/terms_cond.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mTermAndCondition);

        Glide.with(mChangePassword.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/change_pwd.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mChangePassword);

        Glide.with(show_receipt_button.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/show_rec.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(show_receipt_button);


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
                            Dialog alertDialog = new Dialog(ProfileActivityNew.this);
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

//    private void addNotification() {
//        String class_string=preferences.getString("CLASS","");
//        APIInterface service = RetrofitSignletonTeacher.getLiveIntrface();
//        Call<Notice> call = service.getNotification(class_string);
//        call.enqueue(new Callback<Notice>() {
//            @Override
//            public void onResponse(Call<Notice> call, Response<Notice> response) {
//                if (response.body() != null) {
//                    Notice notice_object=response.body();
//                    noticeDescriptions=notice_object.getRes();
//                    if (notice_object.getRes()!=null){
//                        for (int i=0;i<noticeDescriptions.size();i++){
//                            Log.i("Notification","Notification");
//                            cartBadgeMyProfileText.setText(String.valueOf(noticeDescriptions.size()));
//                        }
//                    }else {
//                        cartBadgeMyProfileText.setText("0");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Notice> call, Throwable t) {
////                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
////        if (!notification_str.equals("")) {
//
//    }

    private void setProfilePicture() {
        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(0.5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();


        if (profile_str.equals("")){
            circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.parent_dashboard_girl));
        }else {

            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<ProfilePojo> call2 = service.getImageUploaded(profile_str);
            Log.i("PROFILE", String.valueOf(call2.request().url()));

            call2.enqueue(new Callback<ProfilePojo>() {
                @Override
                public void onResponse(@NotNull Call<ProfilePojo> call2, @NotNull Response<ProfilePojo> response) {
                    if (response.body() != null) {

                        ProfilePojo profilePojo=response.body();

                        Glide.with(ProfileActivityNew.this).load(profilePojo.getError().getData()).placeholder(circularProgressDrawable).into(circleImageView);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ProfilePojo> call2, @NotNull Throwable t) {
                    Log.i("Error", String.valueOf(t.getMessage()));
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.profile_sign_out:
//                signOut();
//                break;
            case R.id.new_profile_back:
                finish();
                break;
            case R.id.profile_share_new:
                shareAppCode();
                break;
            case R.id.profile_edit_new:
                Intent edit_profile_intent=new Intent(ProfileActivityNew.this, EditProfile.class);
                startActivity(edit_profile_intent);
                break;
            case R.id.profile_forgot_password_new:
                Intent forgot_intent=new Intent(ProfileActivityNew.this, ChangePasswordNew.class);
                startActivity(forgot_intent);
                break;
            case R.id.terms_and_conditions_new:
                Intent tc=new Intent(ProfileActivityNew.this, TermsAndConditionsNew.class);
                startActivity(tc);
                break;

            case R.id.show_receipt_new:
                showRecipt();
                break;
            case R.id.share_app_link_new:
                Intent qr_code_intent=new Intent(ProfileActivityNew.this,QRCodeActivityNew.class);
                startActivity(qr_code_intent);
//                sharePromoCodeAndQr();
                break;
            case  R.id.add_promo_code_new:
                Intent promo_code_intent=new Intent(ProfileActivityNew.this,PromoCode.class);
                startActivity(promo_code_intent);
                break;
            case R.id.profile_my_course:
                String account_paid_unpaid=preferences.getString("ACCOUNT_PAID","");
                if (account_paid_unpaid.equals("Yes")) {
                    if(mClass.equals("1")){
                        editor.putString("COURSE_NAME","Nursery");
                        editor.commit();
                    }else if(mClass.equals("2")){
                        editor.putString("COURSE_NAME","Junior");
                        editor.commit();
                    }else if(mClass.equals("3")){
                        editor.putString("COURSE_NAME","Senior");
                        editor.commit();
                    }

                    editor.putString("MY_COURSE_FROM","profile");
                    editor.commit();
                    selectCourseDetail();
                }else {
//                    editor.putString("MY_COURSE_FROM","");
//                    editor.commit();
                    Intent course_intent = new Intent(ProfileActivityNew.this, MyCoursesNew.class);
                    startActivity(course_intent);
                }
                break;
            case R.id.profile_logout_new:
                Logout();
                break;
            default:break;

        }
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

                Intent login_intent=new Intent(ProfileActivityNew.this, LoginActivity.class);
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


    private void selectCourseDetail() {
        String plan_id=preferences.getString("PLAN_ID","");
        String class_string=preferences.getString("CLASS","");

        if (class_string.equals("1") || class_string.equals("2")|| class_string.equals("3")){
            //        plan_id="13";
//        if (plan_id.equals("1")||plan_id.equals(F"11")|plan_id.equals("6")){
//            Intent intent = new Intent(ProfileActivityNew.this, CourseGold.class);
//            startActivity(intent);
//        }else if (plan_id.equals("2")||plan_id.equals("12")|plan_id.equals("7")){
//            Intent intent = new Intent(ProfileActivityNew.this, CourseTwinking.class);
//            startActivity(intent);
//
//        }else if (plan_id.equals("3")||plan_id.equals("13")|plan_id.equals("8")){
//            Intent intent = new Intent(ProfileActivityNew.this, CourseShining.class);
//            startActivity(intent);
//
//        }else if (plan_id.equals("4")||plan_id.equals("14")|plan_id.equals("9")){
            Intent intent = new Intent(ProfileActivityNew.this, CourseRock.class);
            startActivity(intent);

//        }else if (plan_id.equals("16")||plan_id.equals("15")|plan_id.equals("10")){
//            Intent intent = new Intent(ProfileActivityNew.this, CourseSuper.class);
//            startActivity(intent);
//
//        }
        }else {
            Intent intent = new Intent(ProfileActivityNew.this, MyCoursesNew.class);
            startActivity(intent);

        }


    }

    private void sharePromoCodeAndQr() {


        if(mCode.equals("")||preferences.getString("PROMO_CODE","")==null){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vedic Tree Kids Learning App");
            String shareMessage;
            shareMessage = "Download Vedic Tree Kids Learning app:-- " +"https://play.google.com/store/apps/details?id=" +"com.vedictree.preschool" +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        }
        else {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vedic Tree Kids Learning App");
            String shareMessage = "\nUse my Promo Code:-"  +promo_code;
            shareMessage = shareMessage + "Download Vedic Tree Kids Learning app:-- " + "https://play.google.com/store/apps/details?id=" + "com.vedictree.preschool" + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        }
    }

    private void showRecipt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        String student_id=preferences.getString("STUDENT_ID","");
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<UserHistory> call = service.studentHistory(student_id);
        call.enqueue(new Callback<UserHistory>() {
            @Override
            public void onResponse(Call<UserHistory> call, Response<UserHistory> response) {
                if (response.body() != null) {
                    userHistory=response.body();
                    studentHistoryList=userHistory.getStudentHistory();
                    if (!studentHistoryList.isEmpty()){
                        for (int i=0;i<studentHistoryList.size();i++){
                            studentHistory=studentHistoryList.get(i);
                            String log_id=studentHistory.getLogId();
                            Call<Receipt_poj> receipt_call = service.paymentReceipt(student_id,log_id);
                            receipt_call.enqueue(new Callback<Receipt_poj>() {
                                @Override
                                public void onResponse(Call<Receipt_poj> call, Response<Receipt_poj> response) {
                                    if (response.body() != null) {
                                        String payment_utl=response.body().getPaymentUrl();
//                                        String url="https://vedictreeschool.com/website/showpdfapp/19/24";
                                        if (!payment_utl.equals("")) {
                                            if (!payment_utl.isEmpty()) {
                                                Intent rec_intent = new Intent(ProfileActivityNew.this, ReceiptActivityNew.class);
                                                editor.putString("PAYMENT_URL", payment_utl);
                                                editor.commit();
                                                startActivity(rec_intent);
                                                alertDialog_onCreate.dismiss();
                                            }
                                        }else {
                                            Toast.makeText(getApplicationContext(), "No receipt available", Toast.LENGTH_LONG).show();

                                        }
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "No receipt available", Toast.LENGTH_LONG).show();
                                            alertDialog_onCreate.dismiss();
                                        }
                                }

                                @Override
                                public void onFailure(Call<Receipt_poj> call, Throwable t) {
                                    Log.i("Error", String.valueOf(t.getMessage()));
//                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                    alertDialog_onCreate.dismiss();
                                }
                            });

                        }
                    }
                    else if (studentHistoryList.isEmpty()){
                        Toast.makeText(getApplicationContext(), "No receipt available", Toast.LENGTH_LONG).show();
                        alertDialog_onCreate.dismiss();
                    }

                } else{
                    alertDialog_onCreate.dismiss();
                }
            }
            @Override
            public void onFailure(Call<UserHistory> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                alertDialog_onCreate.dismiss();
            }
        });
    }

    private void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        String register_mode=preferences.getString("REGISTER_MODE","");
        if (register_mode.equals(VedicConstants.NORMAL_LOGIN)){
            preferences.edit().remove("NAME").apply();
            preferences.edit().remove("REGISTER_EMAIL").apply();
            preferences.edit().remove("PREVIOUS_UNLOCK_MONTH").apply();
            preferences.edit().remove("SELECT_MONTH").apply();
            preferences.edit().remove("ENABLE_PREVIOUS_MONTH").apply();
            preferences.edit().remove("PRESS_PREVIOUS_MONTH_BUTTON").apply();
            preferences.edit().remove("FINAL_DAY").apply();
            preferences.edit().remove("FINAL_MONTH").apply();
            preferences.edit().remove("COURSE_PERIOD").apply();
            preferences.edit().remove("LOCK_ACCESS_GRAND").apply();

            preferences.edit().clear();
            Toast.makeText(getApplicationContext(), VedicConstants.NORMAL_SIGN_OUT, Toast.LENGTH_SHORT).show();
            Intent login_intent=new Intent(ProfileActivityNew.this, LoginActivity.class);
            login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login_intent);
            finish();
            alertDialog_onCreate.dismiss();

        }
        else if (register_mode.equals(VedicConstants.GOOGLE_LOGIN)){
            preferences.edit().remove("NAME").apply();
            preferences.edit().remove("REGISTER_EMAIL").apply();
            preferences.edit().remove("PREVIOUS_UNLOCK_MONTH").apply();
            preferences.edit().remove("SELECT_MONTH").apply();
            preferences.edit().remove("ENABLE_PREVIOUS_MONTH").apply();
            preferences.edit().remove("PRESS_PREVIOUS_MONTH_BUTTON").apply();
            preferences.edit().remove("FINAL_DAY").apply();
            preferences.edit().remove("FINAL_MONTH").apply();
            preferences.edit().clear();
            preferences.edit().remove("COURSE_PERIOD").apply();
            preferences.edit().remove("LOCK_ACCESS_GRAND").apply();



            GoogleSignInOptions gso = new GoogleSignInOptions.
                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                    build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
            googleSignInClient.signOut();
            FirebaseAuth.getInstance().signOut();

            Toast.makeText(getApplicationContext(), VedicConstants.GOOGLE_SIGN_OUT, Toast.LENGTH_SHORT).show();
            Intent login_intent=new Intent(ProfileActivityNew.this, LoginActivity.class);
            login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login_intent);
            finish();
            alertDialog_onCreate.dismiss();
        }
        else if (register_mode.equals(VedicConstants.FACEBOOK_LOGIN)){
            preferences.edit().remove("NAME").apply();
            preferences.edit().remove("REGISTER_EMAIL").apply();
            preferences.edit().remove("PREVIOUS_UNLOCK_MONTH").apply();
            preferences.edit().remove("SELECT_MONTH").apply();
            preferences.edit().remove("ENABLE_PREVIOUS_MONTH").apply();
            preferences.edit().remove("PRESS_PREVIOUS_MONTH_BUTTON").apply();
            preferences.edit().remove("FINAL_DAY").apply();
            preferences.edit().remove("FINAL_MONTH").apply();
            preferences.edit().clear();
            preferences.edit().remove("COURSE_PERIOD").apply();
            preferences.edit().remove("LOCK_ACCESS_GRAND").apply();
            preferences.edit().remove("YOU_TUBE").apply();



            LoginManager.getInstance().logOut();
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(getApplicationContext(), VedicConstants.FACEBOOK_SIGN_OUT, Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
            alertDialog_onCreate.dismiss();
        }
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<AccessPojo> call = service.deniedMobileStatus(mMobile);
        call.enqueue(new Callback<AccessPojo>() {
            @Override
            public void onResponse(@NotNull Call<AccessPojo> call, @NotNull Response<AccessPojo> response) {
                if (response.body() != null) {
                    AccessPojo accessPojo= response.body();
                    Toast.makeText(getApplicationContext(),accessPojo.getError().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<AccessPojo> call, @NotNull Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
            }
        });

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private void shareAppCode() {

        if (mCode.equals("") || preferences.getString("REFFERAL_CODE", "") == null) {
            Toast.makeText(getApplicationContext(), "No referral code available", Toast.LENGTH_LONG).show();

        } else {

            String shareMessage = "I’m inviting you to download and use Vedic Tree Kids Learning App which is Naye Zamane ki Nayi School.";
            shareMessage = shareMessage + "\n\nHere’s my " + mReferralCode.getText().toString();
            shareMessage = shareMessage + "\nJust enter it in Referral/ promo code field during Sign up /Registration.";
            shareMessage = shareMessage + "\nhttps://play.google.com/store/apps/details?id=com.vedictree.preschool";

//        String shareMessage = "Vedic Tree Kids Learning App.\n\n Use this referral code:";
//        shareMessage=shareMessage +"\n\nHere’s my "+mReferralCode.getText().toString();
//        shareMessage=shareMessage+ "\nJust enter it in Referral/ promo code field during Sign up /Registration.";
//        shareMessage=shareMessage+ "\nhttps://play.google.com/store/apps/details?id=com.vedictree.preschool";

            Bitmap decodedByte = BitmapFactory.decodeResource(getResources(), R.drawable.share_file);
            Uri imageToShare = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), decodedByte, "Share app", null));   // in case of fragment use [context].getContentResolver()
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.putExtra(Intent.EXTRA_TEXT, shareMessage);
            share.putExtra(Intent.EXTRA_STREAM, imageToShare);
            startActivity(Intent.createChooser(share, "Share via"));
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
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}