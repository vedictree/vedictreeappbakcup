package com.vedictree.preschool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import org.jetbrains.annotations.NotNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.POJO.AccessPojo;
import com.vedictree.preschool.POJO.PromoCodePojo;
import com.vedictree.preschool.POJO.Register;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoCode extends AppCompatActivity implements View.OnClickListener {

    String mMobileString;
    Button mSendPromoCode;
    EditText mPromoCodeText;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    String student_id;
    ImageView mBottomImage;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_code);
        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (tabletSize) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();
        student_id=preferences.getString("STUDENT_ID","");
        mPromoCodeText =findViewById(R.id.PromoCodeText);
        mSendPromoCode =findViewById(R.id.sendPromoCodeButton);
        mBottomImage=findViewById(R.id.verify_otp_image);
        mSendPromoCode.setOnClickListener(this);
        mPromoCodeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                APIInterface service = RetrofitSignleton.getAPIInterface();
                Call<Register> call = service.checkPromoCode(mPromoCodeText.getText().toString());
                call.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(@NotNull Call<Register> call, @NotNull Response<Register> response) {
                        if (response.body() != null) {
                            Toast.makeText(getApplicationContext(),response.body().getMsg(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Register> call, @NotNull Throwable t) {
                        Log.i("Error", String.valueOf(t.getMessage()));
                    }
                });
            }
        });


        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(2.0f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();
        Glide.with(mBottomImage.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/ic_verify_otp_two.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mBottomImage);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.sendPromoCodeButton:
                sendPromoCode();
                break;
            default:
                break;
        }
    }

    private void sendPromoCode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        APIInterface service= RetrofitSignleton.getAPIInterface();
        Call<PromoCodePojo> call=service.sendPromoCode(mPromoCodeText.getText().toString(),student_id);
        call.enqueue(new Callback<PromoCodePojo>() {
            @Override
            public void onResponse(@NotNull Call<PromoCodePojo> call, @NotNull Response<PromoCodePojo> response) {
                if (response.body()!=null)
                {
                    logout();
                    PromoCodePojo promoCodePojo=response.body();
                    Log.i("OTP",String.valueOf(promoCodePojo.getError().getError()));
                    Toast.makeText(getApplicationContext(),promoCodePojo.getError().getError() ,Toast.LENGTH_SHORT).show();
                    Intent login_intent=new Intent(PromoCode.this, LoginActivity.class);
                    login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(login_intent);
                    finish();
                    alertDialog_onCreate.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PromoCodePojo> call, @NotNull Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage() ,Toast.LENGTH_SHORT).show();
                alertDialog_onCreate.dismiss();
            }
        });
    }

    private void logout() {
        String mobile_str= preferences.getString("MOBILE","");
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<AccessPojo> call = service.deniedMobileStatus(mobile_str);
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
    }


}