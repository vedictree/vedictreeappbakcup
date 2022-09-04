package com.vedictree.preschool;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vedictree.preschool.POJO.Login_token;
import com.vedictree.preschool.POJO.SubmitTestPojo;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.ApiClientBasic;
import com.vedictree.preschool.Utils.ApiService;
import com.vedictree.preschool.Utils.FileUtil;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadTestNew extends AppCompatActivity {
    Button mAddPdf;
    private final int select_IdProof = 2; // request code fot Id proof intent
    Uri businessId_uri;
    String real_Path_id;
    private static final int CREATE_FILE = 1;
    public static final int PICKFILE_RESULT_CODE = 1;
    EditText mUploadFile;
    TableLayout table;
    ImageButton delete_row;
    TextView upload_file_text;
//    ImageView upload_img;
    ImageView mUploadButton;
    Button mSubmit;
    ArrayList<Uri> image_array;
    ArrayList<String> image_name;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";
    String student_id;
    String packege_id;
    String homework_id;
    String home_work_title;
    String start_time;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String class_id_str;
    TextView mSubjectName;
    TextView mSubjectDate;

    List<Uri> files = new ArrayList<>();
    ArrayList<String> image_pdf_array;
    ArrayList<String> upload_not_upload;
    String image_pdf_str;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView mBack;
    ImageView mLogo;

    //    ActivityResultLauncher<Intent> activityResultLauncher;
    String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
//    String[] perms = {"android.permission.FINE_LOCATION", "android.permission.CAMERA"};
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_test_pop_up_new);
        mAddPdf = findViewById(R.id.add_pdf_new);
        mLogo=findViewById(R.id.upload_test_logo);
        image_array=new ArrayList<>();
        image_name=new ArrayList<>();
        image_pdf_array=new ArrayList<>();
        upload_not_upload=new ArrayList<>();
        image_pdf_str="";
//        mUploadFile=findViewById(R.id.test_file);

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
                        Intent login_intent=new Intent(UploadTestNew.this, LoginActivity.class);
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


//        recyclerView=findViewById(R.id.test_upload_recyclerview);

        table = findViewById(R.id.tb_new);
        mSubjectName=findViewById(R.id.subject_name_new);
        mSubjectDate=findViewById(R.id.subject_date_new);

        student_id=preferences.getString("STUDENT_ID","");
        start_time=preferences.getString("START_TIME","");
        if (!start_time.isEmpty()){
            try {
                String inputPattern = "dd-MM-yyyy";
                String outputPattern = "yyyy-MM-dd";
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                Date date = inputFormat.parse(start_time);
                start_time = outputFormat.format(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        homework_id=preferences.getString("HOMEWORK_ID","");
        packege_id=preferences.getString("FEES_ID","");
        class_id_str=preferences.getString("CLASS_ID","");
        home_work_title=preferences.getString("HOME_WORK_TITLE","");
        Log.i("STUDENT_ID",student_id+" "+start_time+" "+homework_id+ " "+packege_id+"" +class_id_str+" "+home_work_title);
        if (home_work_title.equals("")){

        }else {
            mSubjectName.setText("Subjet of test:-"+" "+home_work_title);
            mSubjectDate.setText("Date of test:-"+" "+start_time);
        }

        addRow(getApplicationContext());
        mAddPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addRow(view.getContext());
            }

        });
        mSubmit=findViewById(R.id.submit_test_data_new);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (files.isEmpty()){

                }else {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(view.getContext(), R.style.CustomAlertDialog);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialog = LayoutInflater.from(view.getContext()).inflate(R.layout.progress_layout, viewGroup, false);
                    builder2.setView(dialog);
                    AlertDialog alertDialog_onCreate = builder2.create();
                    alertDialog_onCreate.show();

                ApiService service = ApiClientBasic.createService(ApiService.class);
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                    builder.addFormDataPart("studId", student_id);
                    builder.addFormDataPart("start_time",start_time);
                    builder.addFormDataPart("homework_id", homework_id);
                    builder.addFormDataPart("fk_feesId", packege_id);
                    builder.addFormDataPart("class_id", class_id_str);
                    builder.addFormDataPart("home_title", home_work_title);


                for (int i = 0; i <files.size() ; i++) {
                    File file = new File(getRealPathFromURI2(files.get(i),getApplicationContext()));
                    Log.i("File",String.valueOf(file.getName()));
                    Log.i("File name",String.valueOf(files.get(i)));
                    RequestBody requestImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    builder.addFormDataPart("file_allocated[]", image_name.get(i),requestImage);
                }

                MultipartBody requestBody = builder.build();
                Call<SubmitTestPojo> call = service.event_store(requestBody);
                call.enqueue(new Callback<SubmitTestPojo>() {
                    @Override
                    public void onResponse(Call<SubmitTestPojo> call, Response<SubmitTestPojo> response) {
                        if(response.isSuccessful()){
                            SubmitTestPojo submitTestPojo=response.body();

                            Log.i("Success", String.valueOf(submitTestPojo.getMsg()));
                                alertDialog_onCreate.dismiss();
                                Toast.makeText(getApplicationContext(), "Test submit successfully.!!!", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            alertDialog_onCreate.dismiss();
                            Log.i("Error 2",String.valueOf(response.code()));
                            Toast.makeText(getApplicationContext(), "Please try again.!!!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<SubmitTestPojo> call, Throwable t) {
                        Log.i("Fail",String.valueOf(t));
                        Toast.makeText(getApplicationContext(), String.valueOf(t), Toast.LENGTH_SHORT).show();
                    }
                });

//                ArrayList<File> tempFilesList = new ArrayList<>();
//                ArrayList<MultipartBody.Part> images = new ArrayList<>();
//                for(int j=0;j<image_array.size();j++) {
//                    File image_file2 = new File(getFilePathFromURI(getApplicationContext(),image_array.get(j)));
//                    tempFilesList.add(image_file2);
//                }
//                for (int k=0;k<tempFilesList.size();k++){
//                    images.add(prepareImageFilePart("file_allocated[]", tempFilesList.get(k)));
//
////                    images.add(prepareImageFilePart("file_allocated" + "["+(k)+"]", tempFilesList.get(k)));
//                }
                    List<MultipartBody.Part> list = new ArrayList<>();
                    List<String> list_image_name = new ArrayList<>();
                    for (int l=0;l<files.size();l++){
                        list_image_name.add("Image"+String.valueOf(l));
                    }
                    int index=0;
                    for (Uri uri : files) {
//                        Log.i("uris", uri.getPath());
                        list.add(prepareFilePart("file_allocated[]",list_image_name.get(index), uri));
                        index++;
                    }

//                    Log.i("File name",String.valueOf(list_image_name));

//                    for (int k=0;k<files.size();k++){
//                        Uri uri=files.get(k);
//                        Log.i("uris", uri.getPath());
//                        list.add(prepareFilePart("file_allocated[]",list_image_name.get(k), uri));
//
//                    }

//                    Log.i("Image file", String.valueOf(list));
                    RequestBody student_body = RequestBody.create(MediaType.parse("multipart/form-data"), student_id);
                    RequestBody start_time_body = RequestBody.create(MediaType.parse("multipart/form-data"), start_time);
                    RequestBody home_body = RequestBody.create(MediaType.parse("multipart/form-data"), homework_id);
                    RequestBody home_title_body = RequestBody.create(MediaType.parse("multipart/form-data"), home_work_title);
                    RequestBody packege_body = RequestBody.create(MediaType.parse("multipart/form-data"), packege_id);
                    RequestBody class_body = RequestBody.create(MediaType.parse("multipart/form-data"), class_id_str);

//                    APIInterface service = RetrofitSignleton.getAPIInterface();
//                    Call<SubmitTestPojo> call = service.uploadTestImage(student_body, start_time_body, home_body, packege_body, class_body, home_title_body, list);
//
//                    call.enqueue(new Callback<SubmitTestPojo>() {
//                        @Override
//                        public void onResponse(Call<SubmitTestPojo> call, Response<SubmitTestPojo> response) {
//                            if (response.body() != null) {
//                                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
//                                Log.i("Image upload", "Image Upload");
//                                files.clear();
//                                table.removeAllViews();
//                                addRow(view.getContext());
//
//                                alertDialog_onCreate.dismiss();
//                                if (response.isSuccessful()) {
//
//                                } else {
//                                    Log.i("Image Not upload", "Image Not  Upload");
//
//                                }
//                            }
//                            alertDialog_onCreate.dismiss();
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<SubmitTestPojo> call, Throwable t) {
//                            Log.i("Image Not upload", t.getMessage());
//
////                            Toast.makeText(getApplicationContext(), "Use Image and pdf file ", Toast.LENGTH_LONG).show();
//
////                            Toast.makeText(getApplicationContext(), "Enable permissions to upload image. Go to Settings -> Application Manager -> Your App -> Permissions -> Enable Storage.", Toast.LENGTH_LONG).show();
//                            alertDialog_onCreate.dismiss();
//                        }
//                    });
                }
            }
        });

        mBack=findViewById(R.id.back_upload_homework_new);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(UploadTestNew.this,ChildeDashboardNew.class);
//                startActivity(intent);
                finish();
            }
        });

//        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//
//                if (result.getResultCode()== Activity.RESULT_OK){
//                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
//                        if (Environment.isExternalStorageManager()){
//                            //Granted
//                        }else {
//                            //Denied
//                        }
//                    }
//                }
//            }
//        });

//        allowPermissionPopUp();
        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(2.0f);
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

    @Override
    protected void onResume() {
        super.onResume();
        if(isMyServiceRunning(MusicBackground.class)){
        }else {
            startService(new Intent(this,MusicBackground.class));
        }

    }



    private void addRow(Context  context) {
        if (table.getChildCount()==files.size()) {
            final TableRow row = (TableRow) LayoutInflater.from(context).inflate(R.layout.row_element, null);
            upload_file_text = row.findViewById(R.id.test_file_row);
            mUploadButton=row.findViewById(R.id.upload_icon);
            upload_file_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.i("Upload image",upload_file_text.getText().toString());
                    requestPermission();
//                    if (requestPermission()) {
                        if (upload_file_text.getText().toString().isEmpty()) {
                            selectImage(UploadTestNew.this);
                        } else {
//                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////                                Intent intent = new Intent(Intent.ACTION_PICK);
//                                intent.addCategory(Intent.CATEGORY_OPENABLE);
////                                intent.setType("image/*");
//                                intent.setType("*/*");
//                                startActivityForResult(intent, 7);

//                                Intent intent = new Intent();
//                                intent.setType("image/*");
//                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                                intent.setAction(Intent.ACTION_GET_CONTENT);
//                                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 7);
//                                selectImage(UploadTest.this);
                        }
//                    }else {
//                        requestforPermission();
//                    }
                }
            });
            mUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder alrt_builder = new AlertDialog.Builder(UploadTestNew.this,R.style.CustomAlertDialog);
                    ViewGroup viewGroup = findViewById(android.R.id.content);
                    View dialog = LayoutInflater.from(UploadTestNew.this).inflate(R.layout.progress_layout, viewGroup, false);
                    alrt_builder.setView(dialog);
                    AlertDialog alertDialog_onCreate= alrt_builder.create();
                    alertDialog_onCreate.show();

                    TableRow row2 = (TableRow) view.getParent();
                    int index = table.indexOfChild(row2);
                    Log.i("Index of Uplaod",String.valueOf(index));
                    if (files.size()==0) {
                        Toast.makeText(UploadTestNew.this, "Upload  file then try to upload ", Toast.LENGTH_LONG).show();
                        alertDialog_onCreate.dismiss();
                    }else {
                        if (upload_not_upload.get(index).equals("Upload")){
                            Toast.makeText(UploadTestNew.this, "File Already upload ", Toast.LENGTH_LONG).show();
                            alertDialog_onCreate.dismiss();

                        }else {
                            ApiService service = ApiClientBasic.createService(ApiService.class);
                            MultipartBody.Builder builder = new MultipartBody.Builder();
                            builder.setType(MultipartBody.FORM);
                            builder.addFormDataPart("studId", student_id);
                            builder.addFormDataPart("start_time", start_time);
                            builder.addFormDataPart("homework_id", homework_id);
                            builder.addFormDataPart("fk_feesId", packege_id);
                            builder.addFormDataPart("class_id", class_id_str);
                            builder.addFormDataPart("home_title", home_work_title);

                            File file = new File(FileUtil.getPath(getApplicationContext(), files.get(index)));

                            RequestBody requestImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            builder.addFormDataPart("file_allocated[]", image_name.get(index), requestImage);

                            MultipartBody requestBody = builder.build();
                            Call<SubmitTestPojo> call = service.event_store(requestBody);
                            call.enqueue(new Callback<SubmitTestPojo>() {
                                @Override
                                public void onResponse(Call<SubmitTestPojo> call, Response<SubmitTestPojo> response) {
                                    if (response.isSuccessful()) {
                                        SubmitTestPojo submitTestPojo = response.body();
                                        alertDialog_onCreate.dismiss();

                                        upload_not_upload.set(index,"Upload");
                                        Toast.makeText(getApplicationContext(), "Test submit successfully.!!!", Toast.LENGTH_SHORT).show();
                                        ImageView upload_button = ((ImageView) ((TableRow) table.getChildAt(index)).getChildAt(1));
                                        upload_button.setBackgroundResource(R.color.app_green);

                                    } else {
                                        Log.i("Error 2", String.valueOf(response.code()));
                                        Toast.makeText(getApplicationContext(), "Please try again.!!!", Toast.LENGTH_SHORT).show();
                                        alertDialog_onCreate.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SubmitTestPojo> call, Throwable t) {
                                    Log.i("Fail", String.valueOf(t));
//                                    allowPermissionPopUp();
                                    alertDialog_onCreate.dismiss();

                                }
                            });
                        }

                    }


                }
            });

            table.addView(row);
        }
        else {
            Toast.makeText(UploadTestNew.this, "Upload empty file then try for another file ", Toast.LENGTH_LONG).show();
        }
    }



    private void submitHomework(int index) {

        ApiService service = ApiClientBasic.createService(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("studId", student_id);
        builder.addFormDataPart("start_time",start_time);
        builder.addFormDataPart("homework_id", homework_id);
        builder.addFormDataPart("fk_feesId", packege_id);
        builder.addFormDataPart("class_id", class_id_str);
        builder.addFormDataPart("home_title", home_work_title);

//        for (int i = 0; i <files.size() ; i++) {
            File file = new File(getRealPathFromURI2(files.get(index),getApplicationContext()));
            Log.i("File",String.valueOf(file.getName()));
            Log.i("File name",String.valueOf(files.get(index)));
            RequestBody requestImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file_allocated[]", image_name.get(index),requestImage);
//        }

        MultipartBody requestBody = builder.build();
        Call<SubmitTestPojo> call = service.event_store(requestBody);
        call.enqueue(new Callback<SubmitTestPojo>() {
            @Override
            public void onResponse(Call<SubmitTestPojo> call, Response<SubmitTestPojo> response) {
                if(response.isSuccessful()){
                    SubmitTestPojo submitTestPojo=response.body();
                    mUploadButton.setVisibility(View.GONE);
                    Log.i("Success", String.valueOf(submitTestPojo.getMsg()));
                    Toast.makeText(getApplicationContext(), "Test submit successfully.!!!", Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.i("Error 2",String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "Please try again.!!!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SubmitTestPojo> call, Throwable t) {
                Log.i("Fail",String.valueOf(t));
                Toast.makeText(getApplicationContext(), String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });


//            String teacher_id=preferences.getString("TEACHER_ID","");
//            String plan_id=preferences.getString("PLAN_ID","");
//            File image_file = new File(getRealPathFromURI(files.get(index),getApplicationContext()));
//
//            RequestBody attachment_img = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
//            RequestBody student_body=RequestBody.create(MediaType.parse("multipart/form-data"),"132");
//            RequestBody date_body=RequestBody.create(MediaType.parse("multipart/form-data"),"2021-10-12");
//            RequestBody planid_body=RequestBody.create(MediaType.parse("multipart/form-data"),"13");
//        RequestBody cl_body=RequestBody.create(MediaType.parse("multipart/form-data"),"3");
//        RequestBody homeid_body=RequestBody.create(MediaType.parse("multipart/form-data"),"26");
//        RequestBody title_body=RequestBody.create(MediaType.parse("multipart/form-data"),"J HOMEWORK TEST 12/10");
//
//            student_id=preferences.getString("STUDENT_ID","");
//            APIInterface service = RetrofitSignleton.getAPIInterface();
////        Call<UploadChatattachment> call = service.uploadSingleHomework(student_body,teacher_body, MultipartBody.Part.createFormData("chatimgup", chatText.getText().toString(), attachment_img),planid_body);
//
//            Call<UploadChatattachment> call = service.uploadSingleHomework(student_body,date_body,homeid_body,planid_body,cl_body,title_body, MultipartBody.Part.createFormData("file_allocated[]", "Homweork_image", attachment_img));
//            call.enqueue(new Callback<UploadChatattachment>() {
//                @Override
//                public void onResponse(Call<UploadChatattachment> call, Response<UploadChatattachment> response) {
//                    if (response.body() != null) {
//                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
//                        mUploadButton.setVisibility(View.GONE);
//                        Log.i("Image upload","Image Upload");
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<UploadChatattachment> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"Enable permissions to upload image. Go to Settings -> Application Manager -> Your App -> Permissions -> Enable Storage.",Toast.LENGTH_LONG).show();
//                }
//            });
        }
    private String getRealPathFromURI(Uri contentUri, Context c) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(c, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName,String img_name, Uri fileUri) {

        File file = new File(fileUri.getPath());
        Log.i("here is error",file.getPath());
        // create RequestBody instance from file

//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse("image/*"),
//                        file);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file);


        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);


    }
    private void selectImage(Context context) {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(intent, 1);
//        final CharSequence[] options = {"Camera","Choose image","Select PDF", "Cancel"};

        final CharSequence[] options = {"Camera","Choose image", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Choose a Media");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Camera")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose image")) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1);
                    image_pdf_str="Image";

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
//                else if (options[item].equals("Select PDF")){
//
//                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.setType("application/pdf");
//                        intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 1);
//                        image_pdf_str = "Pdf";
//
//                }

            }
        });
        builder.show();
    }


    private MultipartBody.Part prepareImageFilePart(String partName, File file) {
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("*/*"),
                        file
                );
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private String getRealPathFromURI2(Uri contentUri,Context c) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        CursorLoader loader = new CursorLoader(c, contentUri, proj, null, null, null);
//        Cursor cursor = loader.loadInBackground();
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String result = cursor.getString(column_index);
//        cursor.close();
//        return result;



//        Uri uri = contentUri;
//        String yourRealPath = null;
//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
//        if(cursor.moveToFirst()){
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//             yourRealPath= cursor.getString(columnIndex);
//        } else {
//            Log.i("No image here","No image here.");
//        }
//        cursor.close();
//        return yourRealPath;


        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(c, contentUri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(contentUri)) {
                final String docId = DocumentsContract.getDocumentId(contentUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(contentUri)) {

                final String id = DocumentsContract.getDocumentId(contentUri);
                final Uri contentUri2 = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(c, contentUri2, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(contentUri)) {
                final String docId = DocumentsContract.getDocumentId(contentUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri3 = null;
                if ("image".equals(type)) {
                    contentUri3 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri3 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri3 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(c, contentUri3, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(contentUri.getScheme())) {
            return getDataColumn(c, contentUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(contentUri.getScheme())) {
            return contentUri.getPath();
        }

        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    @SuppressLint("Range")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap img=null;
                        if (data.getData()==null) {
                             img = (Bitmap) data.getExtras().get("data");
                        }else {
                            try {
                                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
//                        upload_img.setImageBitmap(img);
                        mUploadButton.setVisibility(View.VISIBLE);
//                        files.add(data.getData());
                        files.add(getImageUri(UploadTestNew.this,img));
                        upload_not_upload.add("Not_upload");
//                        Picasso.get().load(getImageUri(UploadTest.this,img)).into(upload_img);
                        String imgPath = FileUtil.getPath(UploadTestNew.this,getImageUri(UploadTestNew.this,img));
//                        files.add(Uri.parse(imgPath));
                        String filename = imgPath.substring(imgPath.lastIndexOf("/") + 1);
                        upload_file_text.setText(filename);
                        image_name.add(filename);
                        image_pdf_array.add("Image");
                        Log.e("image", imgPath);

                    }
                    addRow(getApplicationContext());

                    break;
                case 1:
//                    if (resultCode==RESULT_OK&& data!=null) {
//                        Uri selectedImage = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = getContentResolver().query(selectedImage,
//                                filePathColumn, null, null, null);
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String picturePath = cursor.getString(columnIndex);
//                        Log.i("picturePath", picturePath);
//                        files.add(Uri.parse(picturePath));
////                    String filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
//                        upload_file_text.setText(picturePath);
//                        cursor.close();
//                    }

                    if (resultCode == RESULT_OK && data != null) {
                        Uri img = data.getData();
                        Log.i("Image_uri", String.valueOf(img));
                        if (img.toString().contains("externalstorage")){
                            Toast.makeText(getApplicationContext(),"Select file from internal storage only!",Toast.LENGTH_LONG).show();
                        }else {
                            files.add(img);
                            upload_not_upload.add("Not_upload");
                            mUploadButton.setVisibility(View.VISIBLE);
//                            if (image_pdf_str.equals("Image")) {
                                String imgPath = FileUtil.getPath(UploadTestNew.this, img);
                                String filename2 = imgPath.substring(imgPath.lastIndexOf("/") + 1);
                                upload_file_text.setText(String.valueOf(filename2));
                                image_name.add(filename2);
                                image_pdf_array.add("Image");
                                addRow(getApplicationContext());
//                            }
//                            else {
//                                Uri selectedImage = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = getContentResolver().query(selectedImage,
//                                filePathColumn, null, null, null);
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String picturePath = cursor.getString(columnIndex);
//                        Log.i("picturePath", picturePath);
//                        files.add(Uri.parse(picturePath));
////                    String filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
//                        upload_file_text.setText(picturePath);
//                        cursor.close();
//                            }
                        }
                    }
                    break;
                case 2:
                    Uri uri = data.getData();
                    String uriString = uri.toString();
//                    File file = new File(uri.getPath());//create path from uri
//                    final String[] split = file.getPath().split(":");//split the path.
//                    String filePath = split[1];
//                    files.add(Uri.fromFile(new File(uri.getPath())));
//                    upload_file_text.setText(filePath);


//                    File myFile = new File(uriString);
                    File myFile =new File( FileUtil.getPath(UploadTestNew.this, uri));

                    String path = myFile.getAbsolutePath();

                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.i("image1", displayName);
                                upload_file_text.setText(displayName);
                                files.add(uri);
                                image_name.add(displayName);
                                image_pdf_array.add("Pdf");
//                                upload_img.setBackgroundResource(R.drawable.pdf_image);

                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        Log.i("image", displayName);
                        upload_file_text.setText(displayName);
                        image_name.add(displayName);
                        image_pdf_array.add("Pdf");
//                        upload_img.setBackgroundResource(R.drawable.pdf_image);

                    }
                    addRow(getApplicationContext());
                    break;

            }

        }

//        switch (requestCode) {
//            case 7:
//                if (resultCode == RESULT_OK) {
//                    String PathHolder = data.getData().getPath();
//                    businessId_uri = data.getData();
////                    if (businessId_uri.toString().contains("png")||businessId_uri.toString().contains("jpg")||businessId_uri.toString().contains("jpeg")){
////                        String url_str = getRealPathFromUri(getApplicationContext(), businessId_uri);
//                    String url_str = getRealPathFromURI2(businessId_uri,getApplicationContext());
//
////                    if (url_str==null){
////                            Toast.makeText(UploadTest.this, "File path not found!! Please move file into  download or picture folder", Toast.LENGTH_LONG).show();
////                        }else {
////                            Log.i("Uri main", getRealPathFromUri(getApplicationContext(), businessId_uri));
//                            String filename = url_str.substring(PathHolder.lastIndexOf("/") + 1);
//                            Log.i("Real Path:", filename);
////                             image_array.add(url_str);
//                    if (image_array!=null)
//                    if (image_array.contains(businessId_uri)){
//                        Log.i("File selected","File selected");
//                    }else {
//                        image_array.add(businessId_uri);
//                        image_name.add(filename);
//                        upload_file_text.setText(filename);
//                    }
//
////                        }
////                    }
////                    else {
////                        Uri uri = data.getData();
////                        String uriString = uri.toString();
////                        File myFile = new File(uriString);
////                        String path = getFilePathFromURI2(UploadTest.this,uri);
//////                        String path = getRealPathFromURI2(uri,getApplicationContext());
////                        if (path==null){
////                            Toast.makeText(UploadTest.this, "File path not found!! Please move file into download or picture folder", Toast.LENGTH_LONG).show();
////                        }else {
////                            Log.d("ioooo", path);
////                            image_array.add(path);
////                            String filename = path.substring(path.lastIndexOf("/") + 1);
////                            Log.i("Real Path:", filename);
////                            image_name.add(filename);
////                            upload_file_text.setText(filename);
////                        }
////
////                    }
//                }
//
//                break;
//            default:break;
//
//        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
                cursor.close();
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;

//        Cursor cursor = null;
//        try {
//            String[] proj = { MediaStore.Images.Media.DATA };
//            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "intuenty", null);
        Log.d("image uri",path);
        return Uri.parse(path);
    }

    private boolean requestPermission(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UploadTestNew.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERMISSIONS);
            return true;
        }else {
            return false;
        }
    }

    public static String getRealPathFromURI_API19(Uri uri,Context context){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }
    public static String getRealPath(Uri uri,Context context) {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(":");
        String type = split[0];
        Uri contentUri;
        switch (type) {
            case "image":
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                break;
            case "video":
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                break;
            case "audio":
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                break;
            default:
                contentUri = MediaStore.Files.getContentUri("external");
        }
        String selection = "_id=?";
        String[] selectionArgs = new String[]{
                split[0]
        };

        return getDataColumn2(context, contentUri, selection, selectionArgs);
    }

    public static String getDataColumn2(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                String value = cursor.getString(column_index);
                if (value.startsWith("content://") || !value.startsWith("/") && !value.startsWith("file://")) {
                    return null;
                }
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public void onRequestPermissionResult(int requestCode,String[] permissions,int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case 30:
                if (grantResults.length>0){
                    boolean reader=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writer=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if (reader & writer){
                        Log.i("Granted","Granted");
                    }else {
                        //
                        Log.i("Denied","Denied");

                    }
                }else {
                    //Denied
                    Log.i("Denied","Denied");

                }
                break;

        }

    }

}
