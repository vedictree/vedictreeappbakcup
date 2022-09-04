package com.vedictree.preschool;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.Adapters.StudentProgressAdapter;
import com.vedictree.preschool.Adapters.StudentProgressAttendance;
import com.vedictree.preschool.Adapters.StudentProgressCategoriesAdapter;
import com.vedictree.preschool.POJO.Get_live_session_detail;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.Progress_report;
import com.vedictree.preschool.POJO.Progress_report_detail;
import com.vedictree.preschool.POJO.live_session;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.RetrofitSignletonTeacher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressReportNew extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView progressText;
    int i = 0;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    StudentProgressAdapter studentProgressAdapter;

    RecyclerView recyclerViewCategories;
    RecyclerView.LayoutManager categoriesLayout;
    StudentProgressCategoriesAdapter studentProgressCategoriesAdapter;

    RecyclerView recyclerViewAttendance;
    RecyclerView.LayoutManager layoutManagerAttendance;
    StudentProgressAttendance studentProgressAttendance;

    ArrayList<Integer> arrayList;
    ArrayList<String> session_name;
    ArrayList<String> session_percentage;
    ArrayList<String> category_name;
    ArrayList<String> category_percentage;
    Spinner progressReportSpinner;
    TextView mStudentName;
    TextView mClassName;
    SharedPreferences preferences;
    String studentNameString;
    String classString;
    ImageView mLogo;

    ArrayList<String> day_array;
    ArrayList<String> time_array;
    ArrayList<String> state_array;
    String student_id;

    private List<Get_live_session_detail> get_live_session_detailList;
    String package_name;

    private Progress_report progress_report;
    private Progress_report_detail progress_report_detail;
    private List<Progress_report_detail> progress_report_detailList;
    ArrayList<String> session_callback;
    ArrayList<String> session_image;
    ArrayList<String> start_time_callback;
    ArrayList<String> duration_time_callback;
    ArrayList<String> percentage_callback;
    String profile_str;
    ImageView mBack;
    SharedPreferences.Editor editor;

//    de.hdodenhof.circleimageview.CircleImageView circleImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report_new);

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
                        Intent login_intent=new Intent(ProgressReportNew.this, LoginActivity.class);
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


        recyclerView=findViewById(R.id.progress_report_sessions_new);
        progressBar = findViewById(R.id.progress_bar_new);
        progressText = findViewById(R.id.progress_text_new);
        progressReportSpinner=findViewById(R.id.progress_report_working_new);
        recyclerViewCategories=findViewById(R.id.progress_report_categories_new);
        recyclerViewAttendance=findViewById(R.id.progress_report_attendance_new);
        mStudentName=findViewById(R.id.progress_student_name);
        mClassName=findViewById(R.id.progress_course_name);
        mLogo=findViewById(R.id.progress_report_logo);
//        circleImageView=findViewById(R.id.profile_image_report);
        studentNameString=preferences.getString("NAME","");
        classString=preferences.getString("CLASS","");
        student_id=preferences.getString("STUDENT_ID","");
        profile_str=preferences.getString("PROFILE_PICTURE","");
        mBack=findViewById(R.id.progress_back_to_home_button);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ValueAnimator anim3 = ValueAnimator.ofFloat(1f, 1.05f);
                anim3.setDuration(300);
                anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mBack.setScaleX((Float) animation.getAnimatedValue());
                        mBack.setScaleY((Float) animation.getAnimatedValue());
                    }
                });
                anim3.setRepeatCount(1);
                anim3.setRepeatMode(ValueAnimator.REVERSE);
                anim3.start();
                finish();
            }
        });

//        setProfilePicture();

        if (classString.equals("1")){
            mClassName.setText("Nursery");
        }else if (classString.equals("2")){
            mClassName.setText("Junior kg");
        }else if (classString.equals("3")){
            mClassName.setText("Senior kg");
        }

        mStudentName.setText(studentNameString);

        arrayList=new ArrayList<>();
        session_name=new ArrayList<>();
        session_percentage=new ArrayList<>();
        category_name=new ArrayList<>();
        day_array=new ArrayList<>();
        time_array=new ArrayList<>();
        state_array=new ArrayList<>();
        session_callback=new ArrayList<>();
        session_image=new ArrayList<>();
        duration_time_callback=new ArrayList<>();
        start_time_callback=new ArrayList<>();
        percentage_callback=new ArrayList<>();
        category_percentage=new ArrayList<>();


        ArrayList<String> workingReportArray=new ArrayList<String>();
        workingReportArray.add("Today");
        workingReportArray.add("Last Week");
        workingReportArray.add("Last Month");

        progressReportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Class is:",progressReportSpinner.getSelectedItem().toString());
                if (progressReportSpinner.getSelectedItem().toString().equals("Today")){

                    setWeekData("today");
                }else  if (progressReportSpinner.getSelectedItem().toString().equals("Last Week")){
                    setWeekData("week");

                }else  if (progressReportSpinner.getSelectedItem().toString().equals("Last Month")){
                    setWeekData("month");
                }
                else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_working_hours,workingReportArray);
        progressReportSpinner.setAdapter(stringArrayAdapter);

        day_array.add("Today");
        day_array.add("05/05/2021");
        day_array.add("05/05/2021");
        day_array.add("05/05/2021");

        time_array.add("05:00 PM");
        time_array.add("05:00 PM");
        time_array.add("05:00 PM");
        time_array.add("05:00 PM");

        state_array.add("Present");
        state_array.add("Absent");
        state_array.add("Present");
        state_array.add("Absent");

        layoutManagerAttendance= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAttendance.setLayoutManager(layoutManagerAttendance);
        studentProgressAttendance = new StudentProgressAttendance(getApplicationContext(),day_array,time_array,state_array);
        recyclerViewAttendance.setAdapter(studentProgressAttendance);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected){
        }
        else {
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

        Call<live_session> call2 = service2.getLiveSession(classString,student_id);
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
                                        Window window = ProgressReportNew.this.getWindow();
                                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ProgressReportNew.this);
                                        ViewGroup viewGroup = findViewById(android.R.id.content);
                                        View dialog = LayoutInflater.from(ProgressReportNew.this).inflate(R.layout.live_sessions_pop_up, viewGroup, false);
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
                                                        Intent webIntent1 = new Intent(ProgressReportNew.this, YouTubeVideo.class);
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

//        setProgressDetail();

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

//    private void setProfilePicture() {
//        if (profile_str.equals("")){
//            circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.parent_dashboard_girl));
//        }else {
//            APIInterface service = RetrofitSignleton.getAPIInterface();
//            Call<ProfilePojo> call2 = service.getImageUploaded(profile_str);
//            Log.i("PROFILE", String.valueOf(call2.request().url()));
//
//            call2.enqueue(new Callback<ProfilePojo>() {
//                @Override
//                public void onResponse(@NotNull Call<ProfilePojo> call2, @NotNull Response<ProfilePojo> response) {
//                    if (response.body() != null) {
//
//                        ProfilePojo profilePojo=response.body();
//                        Glide.with(ProgressReportNew.this).load(profilePojo.getError().getData()).into(circleImageView);
//                    }
//                }
//
//                @Override
//                public void onFailure(@NotNull Call<ProfilePojo> call2, @NotNull Throwable t) {
//                    Log.i("Error", String.valueOf(t.getMessage()));
//                }
//            });
//        }
//    }

    private void setWeekData(String str) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ProgressReport.this,R.style.CustomAlertDialog);
//        ViewGroup viewGroup =findViewById(android.R.id.content);
//        View dialog = LayoutInflater.from(context).inflate(R.layout.progress_layout, viewGroup, false);
//        builder.setView(dialog);
//        AlertDialog alertDialog_onCreate= builder.create();
//        alertDialog_onCreate.show();

        session_callback.clear();
        start_time_callback.clear();
        duration_time_callback.clear();
        session_image.clear();

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<Progress_report> call = service.getFilterProgress(str,student_id);
        call.enqueue(new Callback<Progress_report>() {
            @Override
            public void onResponse(Call<Progress_report> call, Response<Progress_report> response) {
                if (response.body() != null) {
                    progress_report=response.body();
                    if (progress_report.getError().getSessionName()!=null) {
                        if (!progress_report.getError().getSessionName().isEmpty()) {

                            session_callback = progress_report.getError().getSessionName();
                            start_time_callback = progress_report.getError().getTrackEndTime();
                            duration_time_callback = progress_report.getError().getTrackDuration();
                            session_image=progress_report.getError().getSessionImages();
                            Log.i("Session are:", String.valueOf(progress_report.getError().getSessionName()));
                        }
                        else {
                            progressText.setText("0%");
                            progressBar.setProgress(0);
                        }
                        setWorkingHour(start_time_callback,duration_time_callback,str);
                        setStudentPercentage(session_callback, start_time_callback, duration_time_callback,session_image);
                        setCategoryData(session_callback,start_time_callback,duration_time_callback);
                    }
                }
//                alertDialog_onCreate.dismiss();
            }
            @Override
            public void onFailure(Call<Progress_report> call, Throwable t) {
                Log.i("Session error:",String.valueOf(t.getMessage()));
//                alertDialog_onCreate.dismiss();
            }
        });
    }

    private void setCategoryData(ArrayList<String> session_callback, ArrayList<String> start_time_callback, ArrayList<String> duration_time_callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProgressReportNew.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup =findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(ProgressReportNew.this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        ArrayList<String> filter_array=new ArrayList();
        category_percentage=new ArrayList<>();
        Integer avrage_per = 0;
        if (!start_time_callback.isEmpty()) {
            for (int i = 0; i < start_time_callback.size(); i++) {
                float total1 = Float.parseFloat(start_time_callback.get(i));
                float present1 = Float.parseFloat(duration_time_callback.get(i));
                float reamining_time=present1-total1;
                Log.i("Remaining time:",String.valueOf(reamining_time));
                if ((int)reamining_time<=20){
                    filter_array.add("100");
                }else {
                    float c = (total1 / 60);
                    int result = (int) Math.ceil(c);
                    filter_array.add(String.valueOf(result));
                }
            }

            for (int j = 0; j < filter_array.size(); j++) {
                avrage_per = avrage_per + (Integer.parseInt(filter_array.get(j)));
            }
            avrage_per = avrage_per / filter_array.size();
        }
        else {
            avrage_per=0;
        }

        category_name=new ArrayList<>();
        category_name.add("Curricular");
        category_name.add("Extra Curricular");
        category_name.add("Personality Development");
        category_name.add("Value Based Education");

        category_percentage.add(String.valueOf(avrage_per));
        category_percentage.add("20");
        category_percentage.add("20");
        category_percentage.add("20");

        Log.i("Cat %:",String.valueOf(category_percentage));
        Log.i("Cat %:",String.valueOf(category_name));


        categoriesLayout= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategories.setLayoutManager(categoriesLayout);
        studentProgressCategoriesAdapter = new StudentProgressCategoriesAdapter(getApplicationContext(),category_percentage,category_name);
        recyclerViewCategories.setAdapter(studentProgressCategoriesAdapter);
        alertDialog_onCreate.dismiss();

    }

    private void setWorkingHour(ArrayList<String> start_time_callback, ArrayList<String> duration_time_callback,String str) {
        ArrayList<String> filter_array=new ArrayList();
        Integer avrage_per = 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(ProgressReportNew.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup =findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(ProgressReportNew.this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();


        if (!start_time_callback.isEmpty()) {
            for (int i = 0; i < start_time_callback.size(); i++) {
                float total1 = Float.parseFloat(start_time_callback.get(i));
                float present1 = Float.parseFloat(duration_time_callback.get(i));
                float reamining_time=present1-total1;
                Log.i("Remaining time:",String.valueOf(reamining_time));
                if ((int)reamining_time<=20){
                    filter_array.add("100");
                }else {
                    float c = (total1 / 60);
                    int result = (int) Math.ceil(c);
                    filter_array.add(String.valueOf(result));
                }
            }

            for (int j = 0; j < filter_array.size(); j++) {
                avrage_per = avrage_per + (Integer.parseInt(filter_array.get(j)));
            }
            avrage_per = avrage_per / filter_array.size();
            int average_result = (int) Math.ceil(avrage_per);

            progressText.setText(String.valueOf(average_result) + "%");
            progressBar.setProgress(average_result);
        }
        else {
            progressText.setText("0%");
            progressBar.setProgress(0);
        }
        alertDialog_onCreate.dismiss();

    }

    private void setTodayProgress() {
        Log.i("Per_arr",String.valueOf(percentage_callback));
        if (!percentage_callback.isEmpty()) {
            Integer avrage_per = 0;
            for (int i = 0; i < percentage_callback.size(); i++) {
                avrage_per = avrage_per + (Integer.parseInt(percentage_callback.get(i)));
            }
            avrage_per = avrage_per / percentage_callback.size();
            Log.i("avg_arr", String.valueOf(avrage_per));
            progressText.setText(String.valueOf(avrage_per) + "%");
            progressBar.setProgress(avrage_per);
        }else {
            progressText.setText("0%");
            progressBar.setProgress(0);
//            setCategoryData(0);
        }
    }


    private void setProgressDetail() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProgressReportNew.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup =findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(ProgressReportNew.this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        session_callback.clear();
        start_time_callback.clear();
        duration_time_callback.clear();
        session_image.clear();

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<Progress_report> call = service.getProgressReport(student_id);
        call.enqueue(new Callback<Progress_report>() {
            @Override
            public void onResponse(Call<Progress_report> call, Response<Progress_report> response) {
                if (response.body() != null) {
                    alertDialog_onCreate.dismiss();
                    progress_report=response.body();
                    if (progress_report.getError().getSessionName()!=null) {
                        if (!progress_report.getError().getSessionName().isEmpty()) {

                            session_callback = progress_report.getError().getSessionName();
                            start_time_callback = progress_report.getError().getTrackEndTime();
                            duration_time_callback = progress_report.getError().getTrackDuration();
                            session_image=progress_report.getError().getSessionImages();
                            Log.i("Session are:", String.valueOf(progress_report.getError().getSessionName()));
                            for (int i = 0; i < session_callback.size(); i++) {
//                                Log.i("Session is:", session_callback.get(i));
                            }
                            setStudentPercentage(session_callback, start_time_callback, duration_time_callback,session_image);
                        }
                    }
                }
                alertDialog_onCreate.dismiss();
            }
            @Override
            public void onFailure(Call<Progress_report> call, Throwable t) {
                Log.i("Session error:",String.valueOf(t.getMessage()));
                alertDialog_onCreate.dismiss();
            }
        });
    }

    private void setStudentPercentage(ArrayList<String> session_callback, List<String> start_time_callback, List<String> duration_time_callback,ArrayList<String> session_image) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProgressReportNew.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup =findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(ProgressReportNew.this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        percentage_callback=new ArrayList<>();   //Percentage cha array ahe ha
        for (int i=0;i<session_callback.size();i++){ //for loop

            float total1 = Float.parseFloat(start_time_callback.get(i));
            float present1 = Float.parseFloat(duration_time_callback.get(i));

            Log.i("total1",String.valueOf(total1));//
            Log.i("present1",String.valueOf(present1));//

            float reamining_time=present1-total1;
            Log.i("Remaining time:",String.valueOf(reamining_time));
            if ((int)reamining_time<=20){
                percentage_callback.add("100");
            }else {
//                float per=(total1/60)*10;
                float per=(total1/60);

                int result = (int) Math.ceil(per);

                percentage_callback.add(String.valueOf(result));
                Log.i("Per arr:-",String.valueOf(result));
//                float per = ((total1/present1) * 100);
//                Log.i("Final",String.valueOf(per));
//                percentage_callback.add(String.valueOf((int)per));
            }
        }
        setDashboard(session_callback,percentage_callback,session_image);
        alertDialog_onCreate.dismiss();
//        setDataToUi(session_callback, percentage_callback );
    }

    private void setDataToUi(ArrayList<String> session_callback, ArrayList<String> percentage_callback,ArrayList<String> session_image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProgressReportNew.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup =findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(ProgressReportNew.this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        if (session_image.isEmpty()){
            recyclerView.setVisibility(View.GONE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            studentProgressAdapter = new StudentProgressAdapter(getApplicationContext(), session_callback, percentage_callback,session_image);
            recyclerView.setAdapter(studentProgressAdapter);
        }
        alertDialog_onCreate.dismiss();
    }

    private void setDashboard(ArrayList<String> session_callback,ArrayList<String> percentage_callback,ArrayList<String> session_image)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProgressReportNew.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup =findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(ProgressReportNew.this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();


//        ArrayList<Integer> image_array=new ArrayList<>();
//        if (!session_callback.isEmpty()) {
////            session_callback.remove(0);
////            percentage_callback.remove(0);
//            for (int i = 0; i < session_callback.size(); i++) {
//                String session_name_live = session_callback.get(i);
//                switch (session_name_live) {
//                    case "Morning Routine":
//                        image_array.add(R.drawable.prayer);
//                        break;
//                    case "Learning English":
//                        image_array.add(R.drawable.english);
//                        break;
//                    case "English - Part 2":
//                        image_array.add(R.drawable.english);
//                        break;
//                    case "English - Part 3":
//                        image_array.add(R.drawable.english);
//                        break;
//                    case "English - Part 4":
//                        image_array.add(R.drawable.english);
//                        break;
//                    case "Fun With Numbers":
//                        image_array.add(R.drawable.math);
//                        break;
//                    case "General Awareness":
//                        image_array.add(R.drawable.general_awarness);
//                        break;
//                    case "Story Time":
//                        image_array.add(R.drawable.story_time);
//                        break;
//                    case "Chalo Hindi Sikhe":
//                        image_array.add(R.drawable.hindi);
//                        break;
//                    case "Lets Dance":
//                        image_array.add(R.drawable.dance);
//                        break;
//                    case "Singing The Rhymes":
//                        image_array.add(R.drawable.rhymes);
//                        break;
//                    case "Special Celebrations":
//                        image_array.add(R.drawable.sp_image);
//                        break;
//                    case "Concepts":
//                        image_array.add(R.drawable.concept);
//                        break;
//                    case "Value Based Education":
//                        image_array.add(R.drawable.ic_baseline_image_search_24);
//                        break;
//                    case "Personality Development":
//                        image_array.add(R.drawable.ic_baseline_image_search_24);
//                        break;
//                    case "Art & Craft":
//                        image_array.add(R.drawable.art);
//                        break;
//                    case "Yoga":
//                        image_array.add(R.drawable.yoga);
//                        break;
//                    case "Physical Training":
//                        image_array.add(R.drawable.physical_education);
//                        break;
//                    case "Colour":
//                        image_array.add(R.drawable.colors);
//                        break;
//                    case "English Rhyme":
//                        image_array.add(R.drawable.english);
//                        break;
//                    case "Shape":
//                        image_array.add(R.drawable.shapes);
//                        break;
//                    case "Test":
//                        image_array.add(R.drawable.ic_baseline_image_search_24);
//                        break;
//                    case "National Anthem":
//                        image_array.add(R.drawable.anthem_n);
//                        break;
//                    default:
//                        image_array.add(R.drawable.ic_baseline_image_search_24);
//                        break;
//                }
//            }
//            Log.i("Image arr", String.valueOf(image_array));
//            Log.i("Image arr2", String.valueOf(session_callback));
//            Log.i("Image arr3", String.valueOf(percentage_callback));
//        }
//        else {
//
//        }
        setDataToUi( session_callback, percentage_callback,session_image);
        alertDialog_onCreate.dismiss();
    }

//    private void setDashboard(ArrayList<String> session_array,ArrayList<String> per_array)
//    {
//        ArrayList<String> per_arr=new ArrayList<>();
//        for (int i = 0; i < session_array.size(); i++) {
//            for (int j=0;j<session_name.size();j++) {
//                String all_session = session_name.get(j);
//                String session_name_live = session_array.get(i);
//
//                switch (all_session) {
//                    case "Morning Routine":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Learning English":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Fun With Numbers":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "General Awareness":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Story Time":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Chalo Hindi Sikhe":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Lets Dance":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Singing The Rhymes":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Special Celebrations":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Concepts":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Value Based Education":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Personality Development":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Art & Craft":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Yoga":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Physical Training":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Colour":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "English Rhyme":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    case "Shape":
//                        if (all_session.equals(session_name_live)) {
//                            per_arr.add(per_array.get(i));
//                        }
//                        per_arr.add("0");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//        Log.i("Per arr",String.valueOf(per_arr));
////        addImagesToView(image_array, videoArray, textArray,session_id_array);
//    }


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
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}