package com.vedictree.preschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vedictree.preschool.POJO.Register;
import com.vedictree.preschool.POJO.verify_otp;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpNew extends AppCompatActivity implements View.OnClickListener {

    String mMobileString;
    Button mSendOtp;
    EditText mOtpText;
    TextView mResendOtp;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_new);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();
        mMobileString = preferences.getString("REGISTER_MOBILE", "");
        Log.i("Mobile:",mMobileString);
        mOtpText=findViewById(R.id.otpTextNew);
        mSendOtp=findViewById(R.id.submit_verify_otp_new);
        mResendOtp=findViewById(R.id.verifyOtpResendOtpNew);
        mResendOtp.setOnClickListener(this);
        mSendOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.submit_verify_otp_new:
                sendOtp();
                break;
            case R.id.verifyOtpResendOtpNew:
                resendtp();
                break;
            default:
                break;
        }
    }

    private void resendtp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<Register> call = service.sendOtp(mMobileString);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(@NotNull Call<Register> call, @NotNull Response<Register> response) {
                if (response.body() != null) {

                    Register register=response.body();
                    Toast.makeText(getApplicationContext(),register.getMsg(), Toast.LENGTH_LONG).show();
                    alertDialog_onCreate.dismiss();
                }
                alertDialog_onCreate.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<Register> call, @NotNull Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                alertDialog_onCreate.dismiss();
            }
        });
    }

    private void sendOtp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        APIInterface service= RetrofitSignleton.getAPIInterface();
        Call<verify_otp> call=service.verifyOtp(mMobileString,mOtpText.getText().toString());
        call.enqueue(new Callback<verify_otp>() {
            @Override
            public void onResponse(@NotNull Call<verify_otp> call, @NotNull Response<verify_otp> response) {
                if (response.body()!=null)
                {
                    verify_otp verifyOtp=response.body();
                    Log.i("OTP",String.valueOf(verifyOtp.getCode()));
                    Log.i("OTP",String.valueOf(verifyOtp.getMsg()));

                    if (verifyOtp.getCode()==200){
                        Toast.makeText(getApplicationContext(),response.body().getMsg() ,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VerifyOtpNew.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),response.body().getMsg() ,Toast.LENGTH_SHORT).show();
                    }
                    alertDialog_onCreate.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<verify_otp> call, @NotNull Throwable t) {
                alertDialog_onCreate.dismiss();
            }
        });
    }

}