package com.vedictree.preschool;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.Adapters.testAdapterNew;
import com.vedictree.preschool.POJO.Homework_notification;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.TestPojo;
import com.vedictree.preschool.POJO.TestPojoResponse;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeworkNew extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LinearLayout linearLayout;

    testAdapterNew testAdapter;

    ArrayList<String> question_text;
    ArrayList<String> test_date;
    ArrayList<String> test_download;
    ArrayList<String> test_Status;
    ArrayList<String> homewwork_id;
    ArrayList<String> fees_id_array;
    ArrayList<String> class_array;
    ArrayList<String> new_test_attachment;
//    ArrayList<Integer> session_background;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private List<TestPojoResponse> testPojoResponseList;
    ImageView mBack;
    TextView mNoHomework;
    Timer t;
//    MediaPlayer mediaPlayer;
    ImageView mLogo;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_new);

//        stopService(new Intent(this, MusicBackground.class));
//        startService(new Intent(this, MusicBackground.class));

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
//        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
//        mediaPlayer.start();

        recyclerView=findViewById(R.id.homework_recyclerview_2);
        mBack=findViewById(R.id.homework_back_button);
        mNoHomework=findViewById(R.id.no_homework);
        mLogo=findViewById(R.id.homework_logo);
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
                        Intent login_intent=new Intent(HomeworkNew.this, LoginActivity.class);
                        login_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login_intent);
                        finish();
//                        mediaPlayer.stop();
                        Toast.makeText(getApplicationContext(),"User Already Login in another device.Please login to continue", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Login_token> call, Throwable t) {
            }
        });

        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        getTestData(tabletSize);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeworkNew.this,ChildeDashboardNew.class);
                startActivity(intent);
                finish();
//                mediaPlayer.stop();

            }
        });
        t= new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      ((MyApplication)getApplication()).cancelNotification(R.integer.notificationId_home);
                                      Log.i("Timer home","Timer home");
                                  }
                              },
                0,
                10);

        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(0.5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();
        Glide.with(mLogo.getContext())
                .load("https://www.vedictreeschool.com/vtmobapp/images/logo_ui_two.png")
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

    private void getTestData(Boolean tablet_size) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
//        ViewGroup viewGroup = findViewById(android.R.id.content);
//        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
//        builder.setView(dialog);
//        AlertDialog alertDialog_onCreate= builder.create();
//        alertDialog_onCreate.show();

        test_date=new ArrayList<>();
        question_text=new ArrayList<>();
        test_download=new ArrayList<>();
        test_Status=new ArrayList<>();
        homewwork_id=new ArrayList<>();
        fees_id_array=new ArrayList<>();
        class_array=new ArrayList<>();
        new_test_attachment=new ArrayList<>();
        ArrayList<String> newList=new ArrayList<>();
        ArrayList<String> newListDate=new ArrayList<>();

        ArrayList<String> newListHomeworkId=new ArrayList<>();
        ArrayList<String> newListFeesId=new ArrayList<>();
        ArrayList<String> newListClassId=new ArrayList<>();
        ArrayList<String> newListQuestion=new ArrayList<>();

        String plan_id=preferences.getString("PLAN_ID","");
        String classString=preferences.getString("CLASS","");
        String student_id_str=preferences.getString("STUDENT_ID","");

        APIInterface service = RetrofitSignletonTeacher.getLiveIntrface();
//        Call<TestPojo> call = service.getTestHistory(classString,plan_id,"2");
        Call<TestPojo> call = service.getTestHistory("3","13","2");

        call.enqueue(new Callback<TestPojo>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<TestPojo> call, Response<TestPojo> response) {
                if (response.body() != null) {
                    TestPojo testPojo=response.body();
                    testPojoResponseList=testPojo.getGetfeesdata();
                    if (testPojoResponseList!=null){
                        for (int i=0;i<testPojoResponseList.size();i++) {
                            Log.i("Student id",testPojoResponseList.get(i).getFk_studentId());
//                            if (testPojoResponseList.get(i).getFk_studentId().equals(student_id_str)){
                                if (testPojoResponseList.get(i).getFk_studentId().equals("226")){
                                test_date.add(testPojoResponseList.get(i).getStartTime());
                                test_download.add(testPojoResponseList.get(i).getAllocatedFile());
                                question_text.add(testPojoResponseList.get(i).getHomeTitle());
                                test_Status.add("Not Expire");

                                homewwork_id.add(testPojoResponseList.get(i).getHomeworkId());
                                fees_id_array.add(testPojoResponseList.get(i).getFkFeesId());
                                class_array.add(testPojoResponseList.get(i).getClassId());
                            }
                        }
                    }
                    ArrayList<String> url_str=new ArrayList<>();

                    if (!test_date.isEmpty()){
                        for (String  element : test_date) {
                            if (!newList.contains(element)) {
                                newList.add(element);
                            }
                        }
                        ArrayList<String> homework_id_last=new ArrayList<>();
                        ArrayList<String> class_last=new ArrayList<>();
                        ArrayList<String> fees_id_last=new ArrayList<>();
                        ArrayList<String> question_last=new ArrayList<>();

                        for (int i=0;i<newList.size();i++){
                            for (int j=0;j<test_date.size();j++){
                                String new_str=newList.get(i);
                                String new_test_str=test_date.get(j);
                                Log.i("Test ",new_str);
                                Log.i("Test ",new_test_str);
                                if(new_str.equals(new_test_str)){
                                    url_str.add(test_download.get(j));
                                    homework_id_last.add(homewwork_id.get(j));
                                    class_last.add(class_array.get(j));
                                    fees_id_last.add(fees_id_array.get(j));
                                    question_last.add(question_text.get(j));
                                }
                            }
                            new_test_attachment.add(url_str.toString());
                            newListHomeworkId.add(homework_id_last.get(0));
                            newListFeesId.add(fees_id_last.get(0));
                            newListClassId.add(class_last.get(0));
                            newListQuestion.add(question_last.get(0));
                            url_str.clear();
                            homework_id_last.clear();
                            fees_id_last.clear();
                            class_last.clear();
                            question_last.clear();
                        }
                        if (!newList.isEmpty()){
                            for (int k=0;k<newList.size();k++) {
                                try {
                                    String inputPattern = "yyyy-MM-dd";
                                    String outputPattern = "dd-MM-yyyy";
                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                                    Date date = inputFormat.parse(newList.get(k));
                                    String  str_d = outputFormat.format(date);
                                    newListDate.add(str_d);

                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                        Log.i("Date are",String.valueOf(newListDate));
                        Log.i("Date are",String.valueOf(newListClassId));
                        Log.i("Date are",String.valueOf(newListFeesId));
                        Log.i("Date are",String.valueOf(newListHomeworkId));
                        Log.i("Date are",String.valueOf(newListQuestion));
                        Log.i("Date are",String.valueOf(new_test_attachment));

                    }


                    if (!test_date.isEmpty()){
                        recyclerView.setVisibility(View.VISIBLE);
                        mNoHomework.setVisibility(View.GONE);
                        if (tablet_size){
                            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(mLayoutManager);
                            testAdapter = new testAdapterNew(getApplicationContext(),newListDate,newListQuestion,new_test_attachment,newListHomeworkId,newListFeesId,newListClassId);
                            recyclerView.setAdapter(testAdapter);
                        }else {
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            testAdapter = new testAdapterNew(getApplicationContext(),newListDate,newListQuestion,new_test_attachment,newListHomeworkId,newListFeesId,newListClassId);
                            recyclerView.setAdapter(testAdapter);
//
                        }

//                            alertDialog_onCreate.dismiss();
                        setHomeworkRead(newList);
                    }
                    else {
                        recyclerView.setVisibility(View.GONE);
                        mNoHomework.setVisibility(View.VISIBLE);
//                        alertDialog_onCreate.dismiss();
                        Toast.makeText(getApplicationContext(), "No homework available.", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    recyclerView.setVisibility(View.GONE);
                    mNoHomework.setVisibility(View.VISIBLE);
//                    alertDialog_onCreate.dismiss();
//                    Toast.makeText(getApplicationContext(), "No homework available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TestPojo> call, Throwable t) {
//                alertDialog_onCreate.dismiss();
                recyclerView.setVisibility(View.GONE);
                mNoHomework.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setHomeworkRead(ArrayList<String> date_array) {
        Log.i("DateArray",String.valueOf(date_array));
        if (date_array.isEmpty()){

        }else {
            for (int i = 0; i < date_array.size(); i++) {
                String student_id=preferences.getString("STUDENT_ID","");
                APIInterface service = RetrofitSignleton.getAPIInterface();
                Call<Homework_notification> call = service.readHomeworkNotification(student_id,date_array.get(i));
                call.enqueue(new Callback<Homework_notification>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(Call<Homework_notification> call, Response<Homework_notification> response) {
                        if (response.body()!=null){

                        }
                    }
                    @Override
                    public void onFailure(Call<Homework_notification> call, Throwable t) {

                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(HomeworkNew.this,ChildeDashboardNew.class);
        startActivity(intent);
        finish();
//        mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        t.cancel();

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
        t.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        t.cancel();
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
}