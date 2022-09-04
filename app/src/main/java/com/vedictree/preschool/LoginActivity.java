package com.vedictree.preschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.vedictree.preschool.POJO.CalenderPojo;
import com.vedictree.preschool.POJO.CalenderPojoResponse;
import com.vedictree.preschool.POJO.LoginPojo;
import com.vedictree.preschool.POJO.LoginPojoResponse;
import com.vedictree.preschool.POJO.SocialLogin;
import com.vedictree.preschool.POJO.SocialloginDatum;
import com.vedictree.preschool.POJO.StudentHistory;
import com.vedictree.preschool.POJO.UserHistory;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RequiredFieldUtils;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.VedicConstants;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button mLogin;
    EditText mUsername;
    EditText mPassword;
    TextView mForgotPassword;
    ImageButton mGoogle;
    ImageButton mFacebook;
    LinearLayout back_ll;
    RequestBody emailRequest;
    RequestBody passRequest;
    private LoginPojo loginPojo;
    private List<LoginPojoResponse> loginPojoResponseList;
    private LoginPojoResponse loginPojoResponse;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    String mName;

    Calendar calendar;
    SimpleDateFormat dateFormat;
    private List<CalenderPojoResponse> calenderPojoResponseList;

    //Google
    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;

    //Firebase
    private FirebaseAuth mAuth;
    AppEventsLogger logger;

    //Facebook
    CallbackManager callbackManager;
    AccessToken accessToken;


    String mSocialUserEmail;
    String mSocialUserName;
    private SocialLogin socialLogin;
    private List<SocialloginDatum> socialloginDatumList;
    private SocialloginDatum socialloginDatum;
    Button mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        stopService(new Intent(this,MusicBackground.class));

        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);

        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION){
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
       }else {
           if (tabletSize) {
               setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
           } else {
               setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
           }
       }

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor = preferences.edit();

        mName=preferences.getString("NAME","");
        if(mName.equals("")){

        }
        else {
            Intent login_intent=new Intent(LoginActivity.this, VideoSplash.class);
            startActivity(login_intent);
            finish();
        }
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){
        }
        else {
            setLogoutPopUp(this);
        }
        mLogin = findViewById(R.id.login_loginButton);
        mUsername = findViewById(R.id.login_username);
        mPassword = findViewById(R.id.login_password);
        mForgotPassword = findViewById(R.id.new_forgotPassword);
        back_ll = findViewById(R.id.new_login_back);
        mGoogle = findViewById(R.id.new_google_login);
        mFacebook = findViewById(R.id.new_fb_login);
        mSignUp=findViewById(R.id.new_login_goto_signup);
        mGoogle.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
        back_ll.setOnClickListener(this);
        mSignUp.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FacebookSdk.sdkInitialize(LoginActivity.this);
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext(), new FacebookSdk.InitializeCallback() {
            @Override
            public void onInitialized() {
                if(AccessToken.getCurrentAccessToken() == null){
                    accessToken = AccessToken.getCurrentAccessToken();
                    Log.i("Accesstoken", String.valueOf(accessToken));
                } else {
                    accessToken = AccessToken.getCurrentAccessToken();
                    Log.i("Accesstoken2", String.valueOf(accessToken));
                }
            }
        });

        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        logger = AppEventsLogger.newLogger(this);

        Log.i("Accesstoken3f", String.valueOf(accessToken));
        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator f_anim = ValueAnimator.ofFloat(1f, 1.05f);
                f_anim.setDuration(300);
                f_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mFacebook.setScaleX((Float) animation.getAnimatedValue());
                        mFacebook.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                f_anim.setRepeatCount(1);
                f_anim.setRepeatMode(ValueAnimator.REVERSE);
                f_anim.start();

                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile","email"));
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile","email"));

                }
            }
        });


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.i("Log Result", String.valueOf(loginResult));
                        Log.i("F_S", loginResult.getAccessToken().getSource().name());

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(final JSONObject object, GraphResponse response) {
                                        try {
                                            Log.i("ID:", object.optString("id"));
                                            Log.i("name:", object.optString("name"));
                                            Log.i("Email_id", object.optString("email"));

                                            try {
                                                Log.i("Email_id", object.getString("email"));
                                                mSocialUserEmail=object.getString("email");
                                                mSocialUserName=object.getString("name");
                                                String fb_email = object.optString("email");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        editor.putString("REGISTER_MODE", VedicConstants.FACEBOOK_LOGIN);
                                        editor.commit();
                                        logSentFriendRequestEvent();
                                        LoginToSocial();
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Facebook Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.i("Success", exception.getMessage());

                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });




    }

    public void logSentFriendRequestEvent () {
        logger.logEvent("Login");
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (tokenTracker == null) {
                Toast.makeText(LoginActivity.this, "User Logged out", Toast.LENGTH_LONG).show();
            }
            else {
                Log.i("tokenTracker", String.valueOf(tokenTracker));
            }
        }
    };
    @Override
    public void onClick(View view) {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {

            switch (view.getId()) {
                case R.id.login_loginButton:
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
                    login(view);

                    break;

                case R.id.new_forgotPassword:
                    final ValueAnimator forgot_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    forgot_anim.setDuration(300);
                    forgot_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mForgotPassword.setScaleX((Float) animation.getAnimatedValue());
                            mForgotPassword.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    forgot_anim.setRepeatCount(1);
                    forgot_anim.setRepeatMode(ValueAnimator.REVERSE);
                    forgot_anim.start();
                    Intent forgot_intent = new Intent(LoginActivity.this, ForgotPasswordNew.class);
                    startActivity(forgot_intent);
                    break;

                case R.id.new_google_login:
                    final ValueAnimator g_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    g_anim.setDuration(300);
                    g_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mGoogle.setScaleX((Float) animation.getAnimatedValue());
                            mGoogle.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    g_anim.setRepeatCount(1);
                    g_anim.setRepeatMode(ValueAnimator.REVERSE);
                    g_anim.start();

                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);

                    break;

                case R.id.new_login_back:
                    final ValueAnimator back_anim = ValueAnimator.ofFloat(1f, 1.05f);
                    back_anim.setDuration(300);
                    back_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            back_ll.setScaleX((Float) animation.getAnimatedValue());
                            back_ll.setScaleY((Float) animation.getAnimatedValue());
                        }
                    });
                    back_anim.setRepeatCount(1);
                    back_anim.setRepeatMode(ValueAnimator.REVERSE);
                    back_anim.start();
                    finish();
                    break;

                case R.id.new_login_goto_signup:
                    Intent register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(register_intent);
                    break;

//            case R.id.login_username:
//                final ValueAnimator name_anim = ValueAnimator.ofFloat(1f, 1.05f);
//                name_anim.setDuration(300);
//                name_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mUsername.setScaleX((Float) animation.getAnimatedValue());
////                        mUsername.setScaleY((Float) animation.getAnimatedValue());
//                    }
//                });
//                name_anim.setRepeatCount(1);
//                name_anim.setRepeatMode(ValueAnimator.REVERSE);
//                name_anim.start();
//                break;
            }
        }else {
            setLogoutPopUp(view.getContext());
        }
    }

    private void login(View view) {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){

        final ArrayList<EditText> editTextArrayList = new ArrayList<>();
        editTextArrayList.add(mPassword);
        editTextArrayList.add(mUsername);

        if (RequiredFieldUtils.isRequiredFieldEmpty(editTextArrayList)) {
            Toast.makeText(getApplicationContext(), "The required Field can not be blank", Toast.LENGTH_LONG).show();
            return;
        } else if (mUsername.getText().toString().length() != 10) {
            Toast.makeText(getApplicationContext(), "Enter valid mobile number", Toast.LENGTH_LONG).show();
        }
        else {


            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.CustomAlertDialog);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialog = LayoutInflater.from(LoginActivity.this).inflate(R.layout.progress_layout, viewGroup, false);
            builder.setView(dialog);
            AlertDialog alertDialog_onCreate = builder.create();
            alertDialog_onCreate.show();

            emailRequest = RequestBody.create(MediaType.parse("multipart/form-data"), mUsername.getText().toString());
            passRequest = RequestBody.create(MediaType.parse("multipart/form-data"), mPassword.getText().toString());

            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<LoginPojo> call = service.userLogin(emailRequest, passRequest);
            call.enqueue(new Callback<LoginPojo>() {
                @Override
                public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                    Log.i("Login Response:", String.valueOf(response.body()));

                    if (response.body() != null) {

                        loginPojo = response.body();
//                        Toast.makeText(getApplicationContext(), loginPojo.getMsg(), Toast.LENGTH_SHORT).show();
                        if (!loginPojo.getRes().isEmpty()) {
                            loginPojoResponseList = loginPojo.getRes();
                            if (!loginPojoResponseList.isEmpty()) {
                                for (int i = 0; i < loginPojoResponseList.size(); i++) {


                                    loginPojoResponse = loginPojoResponseList.get(i);
                                    if (loginPojoResponse != null) {
                                        {
                                            editor.putString("EMAIL", loginPojoResponse.getStudentEmail());
                                            editor.putString("MOBILE", loginPojoResponse.getStudentMobile());
                                            editor.putString("CLASS", loginPojoResponse.getStudentClass());
                                            editor.putString("GENDER", loginPojoResponse.getStudentGender());
                                            editor.putString("CODE", loginPojoResponse.getRefferalCode());
                                            editor.putString("DATA_SOURCE", "normal_data");
                                            editor.putString("USER_FIRST_NAME", loginPojoResponse.getUsrFirstname());
                                            editor.putString("USER_LAST_NAME", loginPojoResponse.getUsrLastname());
                                            editor.putString("STUDENT_ID", loginPojoResponse.getStudId());
                                            editor.putString("PARENT_PIN_LOGIN", loginPojoResponse.getPinNumber());
                                            editor.putString("REFFERAL_CODE", loginPojoResponse.getNewrefferalCode());
                                            editor.putString("PROMO_CODE", loginPojoResponse.getPromoCode());
                                            editor.putString("PROFILE_PICTURE", loginPojoResponse.getUsrProfile());
                                            editor.putString("LOG_STRING", loginPojoResponse.getLogRandomId());
                                            editor.putString("COURSE_PERIOD", loginPojoResponse.getFk_coursePeriod());
                                            editor.commit();

                                            Log.i("LOG_STRING", loginPojoResponse.getLogRandomId());
                                            getStudentHistory(loginPojoResponse.getStudId(), loginPojoResponse.getUnlockdayId());

                                            if (loginPojoResponse.getUsrFirstname() == null) {
                                                editor.putString("NAME", loginPojoResponse.getStudentName());
                                            } else {
                                                editor.putString("NAME", loginPojoResponse.getUsrFirstname() + " " + loginPojoResponse.getUsrLastname());
                                            }
                                            editor.commit();
                                            alertDialog_onCreate.dismiss();

                                        }
                                    }
                                    ;
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Mobile number or Password is wrong!", Toast.LENGTH_SHORT).show();
                            alertDialog_onCreate.dismiss();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Mobile number or Password is wrong!", Toast.LENGTH_SHORT).show();
                        alertDialog_onCreate.dismiss();
                    }
                    alertDialog_onCreate.dismiss();

                }


                @Override
                public void onFailure(Call<LoginPojo> call, Throwable t) {
                    alertDialog_onCreate.dismiss();
                }
            });

        }
        }
        else {
            setLogoutPopUp(this);
        }
    }

    private void setHolidayLogic() {
        String course_str = preferences.getString("COURSE_PERIOD", "");
        Log.i("Couser is", course_str);
        if (course_str.equals("3") || course_str.equals("7")) {
            callCourseCalender();
        } else {
            callNormalCalender();
        }
//        Intent intent = new Intent(LoginActivity.this, VideoSplash.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();

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
//                        Log.i("Current date:", date);
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
                                                                    editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i - 9).getDays());
                                                                    editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i - 9).getMonths());
                                                                    editor.commit();
                                                                }
                                                            } else {
                                                                day_int = Integer.parseInt(calenderPojoResponseList.get(i - 8).getDays());
                                                                editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 8).getDays());
                                                                editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 8).getMonths());
                                                                editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i - 8).getDays());
                                                                editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i - 8).getMonths());
                                                                editor.commit();
                                                            }
                                                        } else {
                                                            day_int = Integer.parseInt(calenderPojoResponseList.get(i - 7).getDays());
                                                            editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 7).getDays());
                                                            editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 7).getMonths());
                                                            editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i - 7).getDays());
                                                            editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i - 7).getMonths());
                                                            editor.commit();
                                                        }
                                                    } else {
                                                        day_int = Integer.parseInt(calenderPojoResponseList.get(i - 6).getDays());
                                                        editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 6).getDays());
                                                        editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 6).getMonths());
                                                        editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i - 6).getDays());
                                                        editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i - 6).getMonths());
                                                        editor.commit();
                                                    }
                                                } else {
                                                    day_int = Integer.parseInt(calenderPojoResponseList.get(i - 5).getDays());
                                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 5).getDays());
                                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 5).getMonths());
                                                    editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i - 5).getDays());
                                                    editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i - 5).getMonths());
                                                    editor.commit();
                                                }
                                            } else {
                                                day_int = Integer.parseInt(calenderPojoResponseList.get(i - 4).getDays());
                                                editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 4).getDays());
                                                editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 4).getMonths());
                                                editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i - 4).getDays());
                                                editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i - 4).getMonths());
                                                editor.commit();
                                            }
                                        } else {
                                            day_int = Integer.parseInt(calenderPojoResponseList.get(i - 3).getDays());
                                            editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 3).getDays());
                                            editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 3).getMonths());
                                            editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i - 3).getDays());
                                            editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i - 3).getMonths());
                                            editor.commit();
                                        }

                                    } else {
                                        day_int = Integer.parseInt(calenderPojoResponseList.get(i - 2).getDays());
                                        editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 2).getDays());
                                        editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 2).getMonths());
                                        editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i - 2).getDays());
                                        editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i - 2).getMonths());
                                        editor.commit();
                                    }
                                } else {
                                    day_int = Integer.parseInt(calenderPojoResponseList.get(i - 1).getDays());
                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i - 1).getDays());
                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i - 1).getMonths());
                                    editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i - 1).getDays());
                                    editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i - 1).getMonths());
                                    editor.commit();

                                }
                            } else {

                                if (day_int > 7) {
                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i).getDays());
                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i).getMonths());
                                    editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i).getDays());
                                    editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i).getMonths());

                                    editor.commit();
                                } else {
                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i).getDays());
                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i).getMonths());
                                    editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i).getDays());
                                    editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i).getMonths());
                                    editor.commit();
                                }

                            }

                        }
//                        Intent intent = new Intent(LoginActivity.this, VideoSplash.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
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
        Log.i("Current date:", date);
        checkUnlockCourseDayAndMonth(date);
//        Intent intent=new Intent(LoginScreen.this,VideoSessionActivity.class);
//        startActivity(intent);
//        finish();
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
                            if (date.equals(calenderPojoResponseList.get(i).getCalDate())) {
                                if (calenderPojoResponseList.get(i).getDays().equals("0")) {
                                    calendar.add(Calendar.DATE, -1);
                                    System.out.println("Updated------- " + calendar.getTime());
                                    String new_date = dateFormat.format(calendar.getTime());
                                    System.out.println("Date------ = " + new_date);
                                    checkUnlockCourseDayAndMonth(new_date);
                                } else {
                                    Log.i("DayMonth", calenderPojoResponseList.get(i).getDays() + "," + calenderPojoResponseList.get(i).getMonths());
                                    editor.putString("FINAL_DAY", calenderPojoResponseList.get(i).getDays());
                                    editor.putString("FINAL_MONTH", calenderPojoResponseList.get(i).getMonths());
                                    editor.putString("LOCK_FINAL_DAY", calenderPojoResponseList.get(i).getDays());
                                    editor.putString("LOCK_FINAL_MONTH", calenderPojoResponseList.get(i).getMonths());
                                    editor.putString("SELECT_FINAL_DAY", calenderPojoResponseList.get(i).getDays());
                                    editor.putString("SELECT_FINAL_MONTH", calenderPojoResponseList.get(i).getMonths());
                                    editor.commit();
//                                    Intent intent = new Intent(LoginActivity.this, VideoSplash.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                    finish();
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

    private void getStudentHistory(String studId, String final_day_str) {
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<UserHistory> call = service.studentHistory(studId);
        call.enqueue(new Callback<UserHistory>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<UserHistory> call, Response<UserHistory> response) {
                if (response.body() != null) {
                    UserHistory userHistory = response.body();
                    List<StudentHistory> studentHistoryList = userHistory.getStudentHistory();
                    if (!studentHistoryList.isEmpty()) {

                        editor.putString("PLAN_ID", studentHistoryList.get(0).getPlanId());
                        editor.putString("PAYMENT_DATE", studentHistoryList.get(0).getPaymentDate());
                        editor.putString("ACCOUNT_PAID", "Yes");
                        editor.commit();
//                        if (final_day_str.equals("0")) {
                            setHolidayLogic();
                        Intent intent = new Intent(LoginActivity.this, VideoSplash.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
//                        finish();
//                        }
//                        else {
//                            editor.putString("FINAL_DAY", loginPojoResponse.getUnlockdayId());
//                            editor.putString("FINAL_MONTH", loginPojoResponse.getUnlock_monthId());
//                            editor.putString("SELECT_FINAL_DAY", loginPojoResponse.getUnlockdayId());
//                            editor.putString("SELECT_FINAL_MONTH", loginPojoResponse.getUnlock_monthId());
//                            editor.commit();
//                            Intent intent = new Intent(LoginActivity.this, VideoSplash.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                            finish();
//                        }

                    } else if (studentHistoryList.isEmpty()) {
//                        unpaid
                        editor.putString("ACCOUNT_PAID", "No");
                        editor.putString("FINAL_DAY", "3");
                        editor.putString("FINAL_MONTH", "1");
                        editor.putString("SELECT_FINAL_DAY", "1");
                        editor.putString("SELECT_FINAL_MONTH", "1");
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, VideoSplash.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
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

    public void ShowHidePass(View view) {

        if (view.getId() == R.id.show_pass_btn_new) {

            if (mPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.invisible);
                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.eye);
                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
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

    @Override
    public void onBackPressed() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i("firebaseAuthWithGoogle:", account.getId());
//                firebaseAuthWithGoogle(account.getIdToken());
                mSocialUserEmail = account.getEmail();
                mSocialUserName = account.getDisplayName();
                editor.putString("REGISTER_MODE", VedicConstants.GOOGLE_LOGIN);
                editor.commit();
                LoginToSocial();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.i("Google sign in failed", String.valueOf(e));
            }

//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            assert result != null;
//            handleSignInResult(result);
        } else {
            //Facebook login here
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("signInWithCredential", "success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i("User name: ", user.getDisplayName());
                            Log.i("Email: ", user.getEmail());
                            mSocialUserEmail = user.getEmail();
                            mSocialUserName = user.getDisplayName();
                            editor.putString("REGISTER_MODE", VedicConstants.GOOGLE_LOGIN);
                            editor.commit();
                            LoginToSocial();

//                            user.getEmail();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("signInWithCredential:failure", String.valueOf(task.getException()));
//                            updateUI(null);
                        }
                    }
                });
    }

    private void LoginToSocial() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        APIInterface service= RetrofitSignleton.getAPIInterface();
        Call<SocialLogin> call=service.socialLogin(mSocialUserEmail);
        call.enqueue(new Callback<SocialLogin>() {
            @Override
            public void onResponse(Call<SocialLogin> call, Response<SocialLogin> response) {

                if(response.body()!=null)
                {
                    socialLogin=response.body();
                    if (socialLogin.getSocialloginData()!=null) {
                        socialloginDatumList = socialLogin.getSocialloginData();
                        if (!socialloginDatumList.isEmpty()) {
                            for (int i = 0; i < socialloginDatumList.size(); i++) {
                                socialloginDatum = socialloginDatumList.get(i);
                                if (socialloginDatum!=null)
                                {
                                    editor.putString("EMAIL", socialloginDatum.getStudentEmail());
                                    editor.putString("MOBILE", socialloginDatum.getStudentMobile());
                                    editor.putString("CLASS", socialloginDatum.getStudentClass());
                                    editor.putString("GENDER", socialloginDatum.getStudentGender());
                                    editor.putString("CODE", socialloginDatum.getRefferalCode());
                                    editor.putString("DATA_SOURCE", "normal_data");
                                    editor.putString("STUDENT_ID", socialloginDatum.getStudId());
                                    editor.putString("USER_FIRST_NAME", socialloginDatum.getUsrFirstname());
                                    editor.putString("USER_LAST_NAME", socialloginDatum.getUsrLastname());
                                    editor.putString("PARENT_PIN_LOGIN", socialloginDatum.getPinNumber());
                                    editor.putString("REFFERAL_CODE", socialloginDatum.getNewrefferalCode());
                                    editor.putString("PROMO_CODE", socialloginDatum.getPromoCode());
                                    editor.putString("FINAL_MONTH", socialloginDatum.getUnlock_monthId());
                                    editor.putString("FINAL_DAY", socialloginDatum.getUnlockdayId());
                                    editor.putString("LOCK_MONTH", socialloginDatum.getUnlock_monthId());
                                    editor.putString("LOCK_DAY", socialloginDatum.getUnlockdayId());
                                    editor.putString("DAY_LOGIN_LOCK", socialloginDatum.getUnlockdayId());
                                    editor.putString("MONTH_LOGIN_LOCK", socialloginDatum.getUnlock_monthId());
                                    editor.putString("PROFILE_PICTURE",socialloginDatum.getUsrProfile());
                                    editor.putString("DAY_MONTH_SOURCE","login");
                                    editor.putString("LOG_STRING",socialloginDatum.getLogRandomId());
                                    getStudentHistory(socialloginDatum.getStudId(),socialloginDatum.getUnlockdayId());
                                    if (socialloginDatum.getUsrFirstname() == null) {
                                        editor.putString("NAME", socialloginDatum.getStudentName());
                                    } else {
                                        editor.putString("NAME", socialloginDatum.getUsrFirstname() + " " + socialloginDatum.getUsrLastname());
                                    }
                                    editor.commit();
                                    alertDialog_onCreate.dismiss();
                                }
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Email id or Password is wrong !" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Email id or Password is wrong!" ,Toast.LENGTH_SHORT).show();
                    }
                }
                alertDialog_onCreate.dismiss();
            }
            @Override
            public void onFailure(Call<SocialLogin> call, Throwable t) {
                alertDialog_onCreate.dismiss();
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            final GoogleSignInAccount account = result.getSignInAccount();
            assert account != null;
            Log.i("User name: ", account.getDisplayName());
            Log.i("Email: ", account.getEmail());
            Log.i("Id: ", account.getId());
            mSocialUserEmail=account.getEmail();
            mSocialUserName=account.getDisplayName();
            editor.putString("REGISTER_MODE", VedicConstants.GOOGLE_LOGIN);
            editor.commit();
            LoginToSocial();
        }
        else {
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }
    }
}