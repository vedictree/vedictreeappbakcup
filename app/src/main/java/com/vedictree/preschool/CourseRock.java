package com.vedictree.preschool;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRock extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences preferences;
    String course_name;
    TextView mAnnualFee;
    TextView mGST;
    TextView mbook;
    TextView mTotalFee;
    TextView mTotalAmount;
    Button mPayNow;
    TextView mCourse;
    SharedPreferences.Editor editor;
    String amount_str;
    TextView showVideo;
    TextView showPdf;
    String package_name_str;
    String planId;
    String file_name;
    AlertDialog alert_video;
    LinearLayout mBack;
    String mMyCourseFrom;
    String mClass;
    ImageView mTopImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_rock);
        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (tabletSize) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor=preferences.edit();
        String token_str=preferences.getString("LOG_STRING","");
        Log.i("LOG_STRING",token_str);
        mPayNow=findViewById(R.id.rock_pay_now);
        mMyCourseFrom=preferences.getString("MY_COURSE_FROM","");
        mTopImage=findViewById(R.id.rock_top_image);

        String plan_id=preferences.getString("PLAN_ID","");
        if (plan_id.equals("4")||plan_id.equals("14")|plan_id.equals("9")){
            mPayNow.setText("PAID");
        }else {
            mPayNow.setText("PAY NOW");
        }

//        if (mMyCourseFrom.equals("profile")){
//            mPayNow.setText("PAID");
//        }else {
//            mPayNow.setText("PAY NOW");
//        }

        mClass=preferences.getString("CLASS","");
        if(mClass.equals("1")){
            course_name="Nursery";

        }else if(mClass.equals("2")){
            course_name="Junior";

        }else if(mClass.equals("3")){
            course_name="Senior";

        }
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
                        Intent login_intent=new Intent(CourseRock.this, LoginActivity.class);
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

        mAnnualFee=findViewById(R.id.rock_annual_fee);
        mGST=findViewById(R.id.rock_gst);
        mbook=findViewById(R.id.rock_book);
        mTotalFee=findViewById(R.id.rock_total_fee);
        mTotalAmount=findViewById(R.id.rock_total_amount);
        mCourse=findViewById(R.id.course_rock);
        showVideo=(TextView)findViewById(R.id.show_rock);
        showPdf=findViewById(R.id.pdf_rock);
        mBack=findViewById(R.id.course_rock_back);
        mBack.setOnClickListener(this);
        showPdf.setOnClickListener(this);
        showVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialog = LayoutInflater.from(view.getContext()).inflate(R.layout.progress_layout, viewGroup, false);
                builder.setView(dialog);
                alert_video= builder.create();
                alert_video.show();

                if (course_name.equals("Nursery")){
                    String video_id="562685657";
                    String url="https://player.vimeo.com/video/"+video_id+"/config";
                    getUrl(url);

                }else if (course_name.equals("Junior")){
                    String video_id="562686082";
                    String url="https://player.vimeo.com/video/"+video_id+"/config";
                    getUrl(url);
                }else if (course_name.equals("Senior")){
                    String video_id="562686466";
                    String url="https://player.vimeo.com/video/"+video_id+"/config";
                    getUrl(url);
                }
            }
        });

//        course_name=preferences.getString("COURSE_NAME","");
        mCourse.setText(course_name);

        if (course_name.equals("Nursery")){
            amount_str="21999";
            mAnnualFee.setText("41,698/-");
            mbook.setText("2,300/-");
            mGST.setText("21,999/-");
            mTotalAmount.setText("21,999/-");
            mTotalFee.setText("43,998/-");
            package_name_str="Rock Star (Nursery)";
            planId="4";
        }
        else if (course_name.equals("Senior")){
            amount_str="23999";
            mAnnualFee.setText("45,698/-");
            mbook.setText("2,300/-");
            mGST.setText("23,999/-");
            mTotalFee.setText("47,998/-");
            mTotalAmount.setText("23,999/-");
            package_name_str="Rock Star (Senior)";
            planId="14";
        }
        else if (course_name.equals("Junior")){
            amount_str="22999";
            mAnnualFee.setText("43,698/-");
            mbook.setText("2,300/-");
            mGST.setText("22,999/-");
            mTotalAmount.setText("22,999/-");
            mTotalFee.setText("45,998/-");
            package_name_str="Rock Star (Junior)";
            planId="9";
        }
        mPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMyCourseFrom.equals("profile")){

                }else {
                    Log.i("Amount:", amount_str);
                    editor.putString("ORDER_AMOUNT", amount_str);
                    editor.putString("PACKEAGE_NAME", package_name_str);
                    editor.putString("PKG_NAME", "rock");
                    editor.putString("PALN_ID", planId);
                    editor.commit();
                    Intent intent = new Intent(CourseRock.this, PaymentMode.class);
                    startActivity(intent);
                }
            }
        });

        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(2.0f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();
        Glide.with(mTopImage.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/ic_rock_top_new.png")
                .placeholder(circularProgressDrawable)
                .listener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mTopImage);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mm.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mm.stop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.show_rock:
//                showVideoView();
//                break;
            case R.id.pdf_rock:
                editor.putString("PACKAGE_NAME","ROCK");
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), PdfView.class);
                startActivity(intent);
//                CopyReadPDFFromAssets();
                break;
            case R.id.course_rock_back:
                finish();
                break;
            default:break;
        }
    }


    private void copyPdfFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }

    private void showVideoView() {
//        String video_id="548825438";
//        String url="https://player.vimeo.com/video/"+video_id+"/config";
//        getUrl(url);

    }
    private void getUrl(String url) {


        StringRequest str=new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject resuest=jsonObject.getJSONObject("request");
                    JSONObject files=resuest.getJSONObject("files");
                    JSONArray progress_arr=files.getJSONArray("progressive");
                    Log.i("Json from volley",String.valueOf(progress_arr));
//                    String str_url;
//                    if (course_name.equals("Junior"))
//                    {
//                        JSONObject url_array=progress_arr.getJSONObject(0);
//                        str_url=url_array.getString("url");
//                        Log.i("Url1",str_url);
//                    }
//                    else {
//                        JSONObject url_array=progress_arr.getJSONObject(1);
//                        str_url=url_array.getString("url");
//                        Log.i("Url",str_url);
//                    }

                    String str_url="";
                    for (int i=0;i<progress_arr.length();i++){
                        JSONObject url_array=progress_arr.getJSONObject(i);
                        String str_quality=url_array.getString("quality");
                        if (str_quality.equals("720p")){
                            str_url=url_array.getString("url");
                            Log.i("Url",str_url);

                        }else {

                        }
                        Log.i("Quality",str_quality);

                    }
                    Intent intent=new Intent(CourseRock.this, VideoPlayerDemoVideoPlayer.class);
                    editor.putString("VIDEO_VIMEO",str_url);
                    editor.putString("FROM_DASHBOARD","empty");

                    editor.commit();
                    startActivity(intent);
                    alert_video.dismiss();
                }catch (JSONException e){
                    e.printStackTrace();
                    alert_video.dismiss();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Please try again", Toast.LENGTH_LONG).show();
                alert_video.dismiss();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(str);
    }


}