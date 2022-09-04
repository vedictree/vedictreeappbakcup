package com.vedictree.preschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
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
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.vedictree.preschool.POJO.EmailCheck;
import com.vedictree.preschool.POJO.Register;
import com.vedictree.preschool.POJO.SocialLogin;
import com.vedictree.preschool.POJO.SocialloginDatum;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RequiredFieldUtils;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.VedicConstants;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button mSignUp;
    ImageButton mGoogle;
    ImageButton mFacebook;
    Spinner spinner;
    Spinner spinnerClass;
    LinearLayout back_ll;

    EditText mStudentName;
    EditText mMobile;
    EditText mPassword;
    EditText mEmail;
    String gender_string;
    String class_string;
    String class_int;
    Boolean isMobileValid;

    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    AccessToken accessToken;
    AppEventsLogger logger;
    String mSocialUserEmail;
    String mSocialUserName;
    String mSignUpMobileStr;

    private SocialLogin socialLogin;
    private List<SocialloginDatum> socialloginDatumList;
    CheckBox mTermcondition;
    TextView mTermconditionText;
    CheckBox mMobileSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

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

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){
        }
        else {
            setLogoutPopUp(this);
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();
        mSignUp=findViewById(R.id.register_signupButton);
        mGoogle=findViewById(R.id.google_login_regis);
        mFacebook=findViewById(R.id.fb_login_regis);
        spinner=findViewById(R.id.classSpinnerSignUp);
        spinnerClass=findViewById(R.id.classSpinnerSignUp_new);
        back_ll=findViewById(R.id.new_sign_up_back);

        mStudentName=findViewById(R.id.register_student_name);
        mMobile=findViewById(R.id.register_mobile);
        mEmail=findViewById(R.id.register_email);
        mPassword=findViewById(R.id.register_password);
        mTermcondition=findViewById(R.id.t_c_checkbox);
        mTermconditionText=findViewById(R.id.t_c_checkbox_text);
        mMobileSignUp=findViewById(R.id.sign_mobile_checkbox);
//        mClass=findViewById(R.id.register_class_str);


        mSignUp.setOnClickListener(this);
        mGoogle.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
        back_ll.setOnClickListener(this);
        mTermconditionText.setOnClickListener(this);

        ArrayList<String> genderArray=new ArrayList<>();
        genderArray.add("  Gender");
        genderArray.add("  Boy");
        genderArray.add("  Girl");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_string =spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> genderAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_view,genderArray);
        spinner.setAdapter(genderAdapter);

        ArrayList<String> classArray=new ArrayList<>();
        classArray.add("  Class ");
        classArray.add("  Nursery");
        classArray.add("  Senior Kg");
        classArray.add("  Junior Kg");
        classArray.add("  Nursery Physical");
        classArray.add("  Junior Kg Physical");
        classArray.add("  Senior Kg Physical");

        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                class_string=spinnerClass.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> classAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_view,classArray);
        spinnerClass.setAdapter(classAdapter);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                logSentFriendRequestEvent();
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList("public_profile","email"));
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList("public_profile","email"));

                }
            }
        });

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
                                                editor.putString("REGISTER_EMAIL",mSocialUserEmail);
                                                editor.putString("REGISTER_MODE", VedicConstants.FACEBOOK_LOGIN);
                                                editor.commit();


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        proceedToRegister();
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(RegisterActivity.this, "Facebook Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.i("Success", exception.getMessage());
                        Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_signupButton:
                final ValueAnimator f_anim = ValueAnimator.ofFloat(1f, 1.05f);
                f_anim.setDuration(300);
                f_anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mSignUp.setScaleX((Float) animation.getAnimatedValue());
                        mSignUp.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                f_anim.setRepeatCount(1);
                f_anim.setRepeatMode(ValueAnimator.REVERSE);
                f_anim.start();
//                Intent intent = new Intent(SignUpActivity.this, VerifyOtpNew.class);
//                startActivity(intent);

                registerUser();
                break;

            case R.id.google_login_regis:
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
                String exist_user=preferences.getString("NAME","");
                if (exist_user.equals(""))
                {
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Already another account exist.First sign out that then continue to sign up", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.fb_login_regis:
                final ValueAnimator f_anim2 = ValueAnimator.ofFloat(1f, 1.05f);
                f_anim2.setDuration(300);
                f_anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mFacebook.setScaleX((Float) animation.getAnimatedValue());
                        mFacebook.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                f_anim2.setRepeatCount(1);
                f_anim2.setRepeatMode(ValueAnimator.REVERSE);
                f_anim2.start();
                break;
            case R.id.new_sign_up_back:
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

            case R.id.t_c_checkbox_text:
                editor.putString("T_C_FROM_REGISTER","register");
                editor.commit();
                Intent tc=new Intent(RegisterActivity.this, RegisterTermAndCondition.class);
                startActivity(tc);
                break;
        }
    }

    private void registerUser() {

        if (mMobileSignUp.isChecked()){
            mSignUpMobileStr="1";
        }else {
            mSignUpMobileStr="0";
        }
        if(mTermcondition.isChecked()) {
            final ArrayList<EditText> editTextArrayList = new ArrayList<>();
            editTextArrayList.add(mPassword);
            editTextArrayList.add(mStudentName);
            editTextArrayList.add(mEmail);
            editTextArrayList.add(mMobile);


            if (RequiredFieldUtils.isRequiredFieldEmpty(editTextArrayList)) {
                Toast.makeText(getApplicationContext(), "The required Field can not be blank", Toast.LENGTH_LONG).show();
                return;
            } else if (gender_string.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
            } else if (class_string.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Select Class", Toast.LENGTH_SHORT).show();
            } else if (mMobile.getText().toString().length() < 10) {
                mMobile.setError(getResources().getString(R.string.error_invalid_mobile));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
                mEmail.setError("Email is not valid");
            } else {

                if (gender_string.equals(" Boy")) {
                    gender_string = "Male";
                } else if (gender_string.equals(" Girl")) {
                    gender_string = "Female";
                }
                Log.i("Gender", gender_string);
                if (class_string.equals("  Senior Kg")) {
                    class_int = "3";
                } else if (class_string.equals("  Junior Kg")) {
                    class_int = "2";
                } else if (class_string.equals("  Nursery")) {
                    class_int = "1";
                } else if (class_string.equals("  Nursery Physical")) {
                    class_int = "4";
                } else if (class_string.equals("  Junior Kg Physical")) {
                    class_int = "5";
                } else if (class_string.equals("  Senior Kg Physical")) {
                    class_int = "6";
                }

                Log.i("Gender class", class_int);


                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
                builder.setView(dialog);
                AlertDialog alertDialog_onCreate = builder.create();
                alertDialog_onCreate.show();

                APIInterface service = RetrofitSignleton.getAPIInterface();
                Call<EmailCheck> call = service.emailExistCheck(mEmail.getText().toString());
                call.enqueue(new Callback<EmailCheck>() {
                    @Override
                    public void onResponse(@NotNull Call<EmailCheck> call, @NotNull Response<EmailCheck> response) {
                        if (response.body() != null) {
                            EmailCheck em = response.body();
                            if (em.getCode() == 200) {
                                checkMobileValidOrNot();
                            } else {
                                mEmail.setError("Email is exist");
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<EmailCheck> call, @NotNull Throwable t) {
                        Log.i("Error", String.valueOf(t.getMessage()));
                    }
                });


            }
        }else {
            Toast.makeText(getApplicationContext(), "Please Check Terms and conditions", Toast.LENGTH_LONG).show();
        }
        }

    private void checkMobileValidOrNot() {
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<EmailCheck> call = service.mobileNumberExist(mMobile.getText().toString());
        call.enqueue(new Callback<EmailCheck>() {
            @Override
            public void onResponse(@NotNull Call<EmailCheck> call, @NotNull Response<EmailCheck> response) {
                if (response.body() != null) {
                    EmailCheck em=response.body();
                    if (em.getCode()==200){
                        registerationOfUser();
                    }else {
                        mMobile.setError("Mobile is exist");
                    }
                    Toast.makeText(getApplicationContext(), em.getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            //                        @Override
            public void onFailure(@NotNull Call<EmailCheck> call, @NotNull Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
            }
        });
    }

    private void registerationOfUser() {
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<Register> call = service.addUser(mStudentName.getText().toString().trim(), mEmail.getText().toString().trim(), gender_string, class_int, mMobile.getText().toString().trim(), mPassword.getText().toString().trim(),mPassword.getText().toString().trim(),"","",mSignUpMobileStr);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(@NotNull Call<Register> call, @NotNull Response<Register> response) {
                Log.i("Url:", String.valueOf(call.request().url()));
                Log.i("Succsess:", String.valueOf(response.isSuccessful()));
                if (response.body()!=null) {
                    Register register=response.body();
                    if(register.getCode()==200) {
                        Toast.makeText(getApplicationContext(), register.getMsg(), Toast.LENGTH_LONG).show();
                        editor.putString("REGISTER_MOBILE", mMobile.getText().toString());
                        editor.commit();
                        Intent intent = new Intent(RegisterActivity.this, VerifyOtpNew.class);
                        startActivity(intent);
                    }else {

                        Toast.makeText(getApplicationContext(), "User already register.Please login to continue", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<Register> call, @NotNull Throwable t) {
//                        Toast.makeText(getApplicationContext(), "Please check network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (tokenTracker == null) {
                Toast.makeText(RegisterActivity.this, "User Logged out", Toast.LENGTH_LONG).show();
            }
            else {
                Log.i("tokenTracker", String.valueOf(tokenTracker));
            }
        }
    };

    private void proceedToRegister() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        String name_str=preferences.getString("REGISTER_EMAIL","");

        if (!name_str.equals("")) {
            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<com.vedictree.preschool.POJO.SocialLogin> call = service.socialLogin(name_str);
            call.enqueue(new Callback<com.vedictree.preschool.POJO.SocialLogin>() {
                @Override
                public void onResponse(Call<com.vedictree.preschool.POJO.SocialLogin> call, Response<com.vedictree.preschool.POJO.SocialLogin> response) {
                    if (response.body() != null) {
                        socialLogin = response.body();
                        if (socialLogin.getSocialloginData() != null) {
                            socialloginDatumList = socialLogin.getSocialloginData();
                            if (!socialloginDatumList.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "User exist.Please login to continue", Toast.LENGTH_LONG).show();
                            } else {
                                mEmail.setText(name_str);
                                Toast.makeText(getApplicationContext(), "Student does not exist.Please provide above information to register", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            mEmail.setText(name_str);
                            Toast.makeText(getApplicationContext(), "Student does not exist.Please provide above information to register", Toast.LENGTH_LONG).show();
                        }
                        alertDialog_onCreate.dismiss();
//                        if (response.body().getCode() == 200) {
//
//                            Toast.makeText(getApplicationContext(), "User exist.Please login to continue", Toast.LENGTH_LONG).show();
//                            alertDialog_onCreate.dismiss();
//                        } else if (response.body().getCode() == 404) {
//                            mEmail.setText(name_str);
//                            Toast.makeText(getApplicationContext(), "Student does not exist.Please provide above information to register", Toast.LENGTH_LONG).show();
//                            alertDialog_onCreate.dismiss();
//                        }
                    }
                }

                @Override
                public void onFailure(Call<com.vedictree.preschool.POJO.SocialLogin> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    alertDialog_onCreate.dismiss();
                }
            });
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            assert result != null;
            handleSignInResult(result);
        } else {
            //Facebook login here
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }
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
            editor.putString("REGISTER_EMAIL",mSocialUserEmail);
            editor.putString("REGISTER_MODE", VedicConstants.GOOGLE_LOGIN);
            editor.commit();
            proceedToRegister();

//            LoginToSocial();
        }
        else {
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }
    }

}