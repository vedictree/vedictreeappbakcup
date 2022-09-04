package com.vedictree.preschool;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCodeActivityNew extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    Button mShareApp;
    SharedPreferences preferences;
    String mCode;
    Bitmap qr_bitmap;
    ImageView mBack;
    SharedPreferences.Editor editor;
//    MusicManager mm;
    ImageView mLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_new);

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
                        Intent login_intent=new Intent(QRCodeActivityNew.this, LoginActivity.class);
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

        imageView = findViewById(R.id.imageView_new);
        mShareApp=findViewById(R.id.share_app_button_new);
        mLogo=findViewById(R.id.qrcode_logo);
        mBack=findViewById(R.id.qrcode_back_to_home_button);
        mCode=preferences.getString("REFFERAL_CODE","");

        String shareMessage="I’m inviting you to download and use Vedic Tree Kids Learning App which is Naye Zamane ki Nayi School.";
        shareMessage=shareMessage +"\n\nHere’s my :-"+mCode;
        shareMessage=shareMessage+ "\nJust enter it in Referral/ promo code field during Sign up /Registration.";
        shareMessage=shareMessage+ "\nhttps://play.google.com/store/apps/details?id=com.vedictree.preschool";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(shareMessage, BarcodeFormat.QR_CODE, 200, 200);
            qr_bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
            for (int x = 0; x<200; x++){
                for (int y=0; y<200; y++){
                    qr_bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(qr_bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mShareApp.setOnClickListener(this);
        mBack.setOnClickListener(this);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){
        }
        else {
            setLogoutPopUp(this);
        }
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


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share_app_button_new:
                sharePromoCodeAndQr();
                break;

            case R.id.qrcode_back_to_home_button:
                finish();
                break;

            default:break;
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

    private void sharePromoCodeAndQr() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String shareMessage="I’m inviting you to download and use Vedic Tree Kids Learning App which is Naye Zamane ki Nayi School.";
        shareMessage=shareMessage +"\n\nHere’s my Referral code:-"+mCode;
        shareMessage=shareMessage+ "\nJust enter it in Referral/ promo code field during Sign up /Registration.";
        shareMessage=shareMessage+ "\nhttps://play.google.com/store/apps/details?id=com.vedictree.preschool";
//        shareMessage=shareMessage + mReferralCode.getText().toString();
//        shareMessage = shareMessage + "\n\nDownload Vedic Tree Kids Learning app:-- " + "https://play.google.com/store/apps/details?id=" + "com.vedictree.preschool" + "\n\n";
//        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//        sendIntent.setType("text/plain");
//        Intent.createChooser(sendIntent, "Share via");
//        startActivity(sendIntent);
        Bitmap decodedByte = BitmapFactory.decodeResource(getResources(), R.drawable.share_file);
        Uri imageToShare = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), decodedByte, "Share app", null));   // in case of fragment use [context].getContentResolver()

        Bitmap decodedByte2 = BitmapFactory.decodeResource(getResources(),R.drawable.cloud );
        Uri imageToShare2 = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), qr_bitmap, "Share app", null));   // in case of fragment use [context].getContentResolver()
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TEXT, shareMessage);
        share.putExtra(Intent.EXTRA_STREAM, imageToShare);
        startActivity(Intent.createChooser(share, "Share via"));
        }
        else {
            setLogoutPopUp(this);
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