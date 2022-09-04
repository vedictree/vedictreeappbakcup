package com.vedictree.preschool;

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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.Receipt_poj;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportCardNew extends AppCompatActivity {

    LinearLayout mSemOne;
    LinearLayout mSemTwo;
//    LinearLayout mSem;
    WebView mWebView;
    WebView mWebViewTwo;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    ImageView mDownload;
    ImageView mDownloadTwo;
    TextView mNoReport;
    ImageView mLogo;

    PrintJob printJob;
    boolean printBtnPressed=false;
    String sem_str;
//    MusicManager mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_card_new);
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
                        Intent login_intent=new Intent(ReportCardNew.this, LoginActivity.class);
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

        mSemOne=findViewById(R.id.new_sem_one);
        mSemTwo=findViewById(R.id.new_sem_two);
        mDownload=findViewById(R.id.download_report_one);
        mDownloadTwo=findViewById(R.id.download_report_two);
        mNoReport=findViewById(R.id.no_report_card);
        mLogo=findViewById(R.id.report_card_logo);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){

        final WebView webView=(WebView)findViewById(R.id.new_report_webview);
        final WebView webView2=(WebView)findViewById(R.id.new_report_webview_two);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        if (Build.VERSION.SDK_INT<=Build.VERSION_CODES.M) {
            webView.setInitialScale(50);
            Log.i("50","50");
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){

                webView.setInitialScale(150);
                Log.i("100", "100");


        }else {
            webView.setInitialScale(100);
        }

        webView2.getSettings().setBuiltInZoomControls(true);
        webView2.getSettings().setDisplayZoomControls(true);
        if (Build.VERSION.SDK_INT<=Build.VERSION_CODES.M) {
            webView2.setInitialScale(50);
            Log.i("50","50");
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                webView2.setInitialScale(150);
                Log.i("100", "100");

        }else {
            webView2.setInitialScale(100);
        }


        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //initializing the printWeb Object
                mWebView=webView;
            }
        });

        webView2.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //initializing the printWeb Object
                mWebViewTwo=webView2;
            }
        });

        ImageView mBack=findViewById(R.id.report_card_back_to_home_button);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String student_id=preferences.getString("STUDENT_ID","");
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<Receipt_poj> receipt_call = service.semOneReportCard(student_id);
        receipt_call.enqueue(new Callback<Receipt_poj>() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onResponse(Call<Receipt_poj> call, Response<Receipt_poj> response) {
                                    if (response.body() != null) {
                                        String payment_utl=response.body().getPaymentUrl();
                                        if (!payment_utl.equals("")) {
                                            if (!payment_utl.isEmpty()) {
                                                webView.setVisibility(View.VISIBLE);
                                                webView.loadUrl(payment_utl);
                                                webView2.setVisibility(View.GONE);
                                                mNoReport.setVisibility(View.GONE);

                                            }
                                        }else {
                                            webView.setVisibility(View.GONE);
                                            webView2.setVisibility(View.GONE);
                                            mNoReport.setVisibility(View.VISIBLE);

                                        }
                                    }
                                    else {
                                        webView.setVisibility(View.GONE);
                                        webView2.setVisibility(View.GONE);
                                        mNoReport.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Receipt_poj> call, Throwable t) {
                                    Log.i("Error", String.valueOf(t.getMessage()));

                                }
                            });

         sem_str="Sem I";
        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mWebView!=null)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        Calling createWebPrintJob()
                        if (webView.getVisibility()==View.VISIBLE){
                            sem_str=" Sem I";
                            PrintTheWebPage(mWebView,sem_str );
                        }else {
                            sem_str=" Sem II";
                            PrintTheWebPage(mWebViewTwo,sem_str );
                        }

                    }else
                    {
                        //Showing Toast message to user
                        Toast.makeText(ReportCardNew.this, "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //Showing Toast message to user
                    Toast.makeText(ReportCardNew.this, "Check internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSemTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.clearHistory();
                String student_id=preferences.getString("STUDENT_ID","");
                APIInterface service = RetrofitSignleton.getAPIInterface();
                Call<Receipt_poj> receipt_call_two = service.semTwoReportCard(student_id);
                receipt_call_two.enqueue(new Callback<Receipt_poj>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(Call<Receipt_poj> call, Response<Receipt_poj> response) {
                        if (response.body() != null) {
                            String payment_utl=response.body().getPaymentUrl();
                            if (!payment_utl.equals("")) {
                                if (!payment_utl.isEmpty()) {
                                    webView2.setVisibility(View.VISIBLE);
                                    webView2.loadUrl(payment_utl);
//                                    webView2.loadUrl("https://www.google.com/");
                                    webView.setVisibility(View.GONE);
                                    mNoReport.setVisibility(View.GONE);
                                }
                            }else {
                                webView.setVisibility(View.GONE);
                                webView2.setVisibility(View.GONE);
                                mNoReport.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            webView.setVisibility(View.GONE);
                            webView2.setVisibility(View.GONE);
                            mNoReport.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "No report available", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Receipt_poj> call, Throwable t) {
                        Log.i("Error", String.valueOf(t.getMessage()));

                    }
                });

            }
        });

        mSemOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String student_id=preferences.getString("STUDENT_ID","");
                APIInterface service = RetrofitSignleton.getAPIInterface();
                Call<Receipt_poj> receipt_call = service.semOneReportCard(student_id);
                receipt_call.enqueue(new Callback<Receipt_poj>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(Call<Receipt_poj> call, Response<Receipt_poj> response) {
                        if (response.body() != null) {
                            String payment_utl=response.body().getPaymentUrl();
                            if (!payment_utl.equals("")) {
                                if (!payment_utl.isEmpty()) {
                                    webView.setVisibility(View.VISIBLE);
                                    webView.loadUrl(payment_utl);
                                    webView2.setVisibility(View.GONE);
                                    mNoReport.setVisibility(View.GONE);

                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "No report available", Toast.LENGTH_LONG).show();

                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "No report available", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Receipt_poj> call, Throwable t) {
                        Log.i("Error", String.valueOf(t.getMessage()));

                    }
                });

            }
        });

        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mWebView!=null)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        Calling createWebPrintJob()
                        if (webView.getVisibility()==View.VISIBLE){
                            sem_str=" Sem I";
                            PrintTheWebPage(mWebView,sem_str );
                        }else {
//                            sem_str=" Sem II";
//                            PrintTheWebPage(mWebViewTwo,sem_str );
                        }

                    }else
                    {
                        //Showing Toast message to user
                        Toast.makeText(ReportCardNew.this, "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //Showing Toast message to user
                    Toast.makeText(ReportCardNew.this, "Check internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDownloadTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mWebView!=null)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        Calling createWebPrintJob()
                        if (webView2.getVisibility()==View.VISIBLE){
                            sem_str=" Sem II";
                            PrintTheWebPage(mWebViewTwo,sem_str );
                        }else {
                        }

                    }else
                    {
                        //Showing Toast message to user
                        Toast.makeText(ReportCardNew.this, "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //Showing Toast message to user
                    Toast.makeText(ReportCardNew.this, "Check internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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


    private void PrintTheWebPage(WebView mWebView,String sem_str) {
        printBtnPressed=true;

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = getString(R.string.app_name) + sem_str;

        PrintDocumentAdapter printAdapter = mWebView.createPrintDocumentAdapter(jobName);

        assert printManager != null;
        printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

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