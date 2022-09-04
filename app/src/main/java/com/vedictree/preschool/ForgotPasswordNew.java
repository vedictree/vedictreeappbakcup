package com.vedictree.preschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.UiModeManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vedictree.preschool.POJO.Forgot_password;
import com.vedictree.preschool.POJO.Register;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RequiredFieldUtils;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordNew extends AppCompatActivity implements View.OnClickListener {

    EditText mMobile;
    TextView mResendOtp;
    LinearLayout mSubmitll;
    Button mSubmit;
    LinearLayout mVerifyOtpll;
    EditText mEnterOtp;
    EditText mPassword;
    Button mForgotPassword;
    Boolean isMobileValid;
    LinearLayout mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_new);
        mMobile=findViewById(R.id.forgot_username);
        mResendOtp=findViewById(R.id.resend_otp_new);
        mSubmitll=findViewById(R.id.submit_forgot_password_new_ll);
        mSubmit=findViewById(R.id.submit_forgot_password_new);
        mVerifyOtpll=findViewById(R.id.verify_otp_ll_new);
        mEnterOtp=findViewById(R.id.enter_otp_new);
        mPassword=findViewById(R.id.forgot_password);
        mForgotPassword=findViewById(R.id.forgot_password_button);
        mSubmit.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
        mResendOtp.setOnClickListener(this);
        mBack=findViewById(R.id.new_forgot_passwoed_back);
        mBack.setOnClickListener(this);
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_forgot_password_new:
                sendOtp();
//                mSubmitll.setVisibility(View.GONE);
//                mVerifyOtpll.setVisibility(View.VISIBLE);
                break;

            case R.id.resend_otp_new:
                sendOtp();
                break;
            case R.id.forgot_password_button:
                resetPassword();
                break;
            case R.id.new_forgot_passwoed_back:
                finish();
                break;

            default:break;
        }

    }
    public void ShowHidePass(View view){

        if(view.getId()==R.id.show_pass_btn_forgot){

            if(mPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.invisible);
                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.eye);
                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
    private void resetPassword() {
        final ArrayList<EditText> editTextArrayList = new ArrayList<>();
        editTextArrayList.add(mMobile);
        editTextArrayList.add(mPassword);
        editTextArrayList.add(mEnterOtp);

        if(RequiredFieldUtils.isRequiredFieldEmpty(editTextArrayList)){
            return;
        } else if (mMobile.getText().toString().length()<10) {
            mMobile.setError(getResources().getString(R.string.error_invalid_mobile));
        }else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
            builder.setView(dialog);
            AlertDialog alertDialog_onCreate= builder.create();
            alertDialog_onCreate.show();

            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<Forgot_password> call = service.resetPassword(mEnterOtp.getText().toString(),mPassword.getText().toString(),mMobile.getText().toString());
            Log.i("URL:", String.valueOf(call.request().url()));
            Log.i("URL Headers:", String.valueOf(call.request().headers()));
            call.enqueue(new Callback<Forgot_password>() {
                @Override
                public void onResponse(Call<Forgot_password> call, Response<Forgot_password> response) {
                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Please login to continue!!!", Toast.LENGTH_LONG).show();
//                        preferences.edit().remove("NAME").apply();
//                        logout();
                        Intent login_intent = new Intent(ForgotPasswordNew.this, LoginActivity.class);
                        login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login_intent);
                        finish();
                        alertDialog_onCreate.dismiss();
                    }
                    else {
                        alertDialog_onCreate.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Forgot_password> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    alertDialog_onCreate.dismiss();
                }
            });
        }
    }


    private void sendOtp() {

        if (mMobile.getText().toString().isEmpty()) {
            mMobile.setError(getResources().getString(R.string.mobile_error));
            isMobileValid = false;
        } else if (mMobile.getText().toString().length()<10) {
            mMobile.setError(getResources().getString(R.string.error_invalid_mobile));
            isMobileValid = false;

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
            builder.setView(dialog);
            AlertDialog alertDialog_onCreate= builder.create();
            alertDialog_onCreate.show();

            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<Register> call = service.sendOtp(mMobile.getText().toString());
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(),response.body().getMsg(), Toast.LENGTH_LONG).show();
                        mVerifyOtpll.setVisibility(View.VISIBLE);
                        mSubmitll.setVisibility(View.GONE);
                        mSubmit.setVisibility(View.VISIBLE);
                        alertDialog_onCreate.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<Register> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    alertDialog_onCreate.dismiss();
                }
            });
        }
    }

}