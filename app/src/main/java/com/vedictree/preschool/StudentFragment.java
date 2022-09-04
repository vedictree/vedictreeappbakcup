package com.vedictree.preschool;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.vedictree.preschool.POJO.AllStateList;
import com.vedictree.preschool.POJO.InformationDetail;
import com.vedictree.preschool.POJO.InformationPojo;
import com.vedictree.preschool.POJO.Profile_pojo;
import com.vedictree.preschool.POJO.StateList;
import com.vedictree.preschool.POJO.StatesAll;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RequiredFieldUtils;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StudentFragment extends Fragment implements View.OnClickListener{

    View mRootView;
    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    private final int select_photo = 1;
    public static final int RESULT_OK = -1;
    private final int select_IdProof = 2; // request code fot Id proof intent

    String uriPath;
    String real_Path;
    Uri imageuri;
    Uri businessId_uri;
    String real_Path_id;

    EditText mStudentName;
    EditText mStudentFather;
    EditText mSurname;
    EditText mReligion;
    EditText mStudentCasteName;
    EditText mStudentSubCasteName;
    EditText mStudentPreviousSchoolName;
    EditText mStudentMotherToungeName;
    EditText mStudentGenderName;
    EditText mStudentBirthDate;
    EditText mCity;
    EditText mStudentMobile;
    EditText mBirthCertificate;
    Button mSaveInfo;
    String studentId;
    SharedPreferences preferences;
    ImageView browse_certificate;
    EditText mCountry;
    EditText mEmail;
    EditText mAddress1;
    EditText mAddress2;
    EditText mAdhare;

    RadioButton male;
    RadioButton female;
    String genderString;
    String state_string;
    String state_edit;
    private InformationPojo informationPojo;
    private InformationDetail informationDetail;
    private List<InformationDetail> informationDetailList;
    Spinner state_spinner;
    private List<StateList> stateLists;

    RequestBody first_name;
    RequestBody last_name;
    RequestBody father_name;
    RequestBody email_id;
    RequestBody mobile_no;
    RequestBody adhar_no;
    RequestBody religion_str;
    RequestBody cast_str;
    RequestBody sub_cast_str;
    RequestBody school_str;
    RequestBody mother_tongue_str;
    RequestBody add1_str;
    RequestBody add2_str;
    RequestBody gender_str;
    RequestBody date_str;
    RequestBody city_str;
    RequestBody state_str;
    RequestBody country_str;
    RequestBody profile_img;
    RequestBody birth_img;
    RequestBody stud_str_id;
    RequestBody stud_role;
    RequestBody land_no;
    RequestBody about_info;
    RequestBody dob_2;
    RequestBody profile_2;
    File mUserProfilePictureFromServer;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView= inflater.inflate(R.layout.fragment_student, container, false);


        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        this.editor=preferences.edit();
        studentId=preferences.getString("STUDENT_ID","");
        genderString="";
        circleImageView=mRootView.findViewById(R.id.profile_image_student);
        circleImageView.setOnClickListener(this);
        mSaveInfo=mRootView.findViewById(R.id.student_save);
        mStudentName=mRootView.findViewById(R.id.studentName);
        mStudentFather=mRootView.findViewById(R.id.studentFatherName);
        mSurname=mRootView.findViewById(R.id.studentSurnameName);
        mReligion=mRootView.findViewById(R.id.studentReligionName);
        mStudentCasteName=mRootView.findViewById(R.id.studentCasteName);
        mStudentSubCasteName=mRootView.findViewById(R.id.studentSubCasteName);
        mStudentPreviousSchoolName=mRootView.findViewById(R.id.studentPreviousSchoolName);
        mStudentMotherToungeName=mRootView.findViewById(R.id.studentMotherToungeName);
        mStudentGenderName=mRootView.findViewById(R.id.studentGenderName);
        mStudentBirthDate=mRootView.findViewById(R.id.studentBirthDate);
        mStudentMobile=mRootView.findViewById(R.id.studentMobile);
        browse_certificate=mRootView.findViewById(R.id.browse_certificate);
        mBirthCertificate=mRootView.findViewById(R.id.student_certificate);
        mCountry=mRootView.findViewById(R.id.studentCountry);
        mAddress1=mRootView.findViewById(R.id.studentAdd1);
        mAddress2=mRootView.findViewById(R.id.studentAdd2);
        mEmail=mRootView.findViewById(R.id.studentEmail);
        mAdhare=mRootView.findViewById(R.id.studentAdhar);
        state_spinner=mRootView.findViewById(R.id.stateSpinner);

        browse_certificate.setOnClickListener(this);
        mStudentBirthDate.setOnClickListener(this);
        mCity =mRootView.findViewById(R.id.studentCity);
        male=mRootView.findViewById(R.id.radioM);
        female=mRootView.findViewById(R.id.radioF);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
        mSaveInfo.setOnClickListener(this);

        getStudentProfile(getContext());

        ArrayList stateArray=new ArrayList();
        ArrayList stateId=new ArrayList();

        stateArray.add("Select State");
        stateId.add("00");
        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<StatesAll> call = service.getState();
        call.enqueue(new Callback<StatesAll>() {
            @Override
            public void onResponse(@NotNull Call<StatesAll> call, @NotNull Response<StatesAll> response) {
                if (response.body() != null) {
                    StatesAll statesAll=response.body();
                    AllStateList allStateList=statesAll.getError();
                    stateLists=allStateList.getStates();
                    for (int i=0;i<stateLists.size();i++){
                        Log.i("State",stateLists.get(i).getStateName());
                        Log.i("State",stateLists.get(i).getStateId());
                        stateArray.add(stateLists.get(i).getStateName());
                        stateId.add(stateLists.get(i).getStateId());

                    }
                    Log.i("State array", String.valueOf(stateArray));
                    ArrayAdapter<String> classAdapter=new ArrayAdapter<String>(mRootView.getContext(),R.layout.spinner_textview,stateArray);
                    state_spinner.setAdapter(classAdapter);
//                    state_spinner.setSelection(classAdapter.getPosition(state_edit));

                }
            }

            @Override
            public void onFailure(@NotNull Call<StatesAll> call, @NotNull Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
            }
        });

        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mStateString=state_spinner.getSelectedItem().toString();
                state_spinner.getSelectedItemPosition();
                Log.i("Class is:",String.valueOf(state_spinner.getSelectedItem()));
                int i=state_spinner.getSelectedItemPosition();
                Log.i("Class id",String.valueOf(stateId.get(i)));
                state_string=String.valueOf(stateId.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return mRootView;
    }

    private void getStudentProfile(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        ViewGroup viewGroup =mRootView.findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(getContext()).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();


        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<InformationPojo> call = service.getStudentDetail(studentId);
        call.enqueue(new Callback<InformationPojo>() {
            @Override
            public void onResponse(Call<InformationPojo> call, Response<InformationPojo> response) {
                if (response.body() != null) {
                    informationPojo= response.body();
                    informationDetailList=informationPojo.getUserinfo();
                    if (!informationDetailList.isEmpty()) {
                        informationDetail = informationDetailList.get(0);
                        if (informationDetail.getUsrFirstname()==null){
                            mStudentName.setText(informationDetail.getStudentName());
                        }else {
                            mStudentName.setText(informationDetail.getUsrFirstname());
                        }
                        mStudentFather.setText(informationDetail.getUsrMiddlename());
                        mSurname.setText(informationDetail.getUsrLastname());
                        mStudentMobile.setText(informationDetail.getStudentMobile());
                        mReligion.setText(informationDetail.getStudentReligion());
                        mStudentCasteName.setText(informationDetail.getStudentCaste());
                        mStudentSubCasteName.setText(informationDetail.getStudentSubcast());
                        mStudentPreviousSchoolName.setText(informationDetail.getPreschool());
                        mStudentMotherToungeName.setText(informationDetail.getMothertoungue());
                        if (informationDetail.getStudentGender().equals("Male")){
                            male.setChecked(true);
                            genderString="Male";
                        }else if (informationDetail.getStudentGender().equals("Female")){
                            female.setChecked(true);
                            genderString="Female";
                        }
                        mStudentBirthDate.setText(informationDetail.getUsrDob());
                        mCity.setText(informationDetail.getUsrCity());
                        mCountry.setText(informationDetail.getUsrCountry());
                        mAdhare.setText(informationDetail.getAdharno());
                        mEmail.setText(informationDetail.getStudentEmail());
                        mAddress1.setText(informationDetail.getUsrAdd2());
                        mAddress2.setText(informationDetail.getUsrAdd1());
//                        state_edit=informationDetail.getUsrState();
                        editor.putString("PROFILE_PICTURE",informationDetail.getUsrProfile());
                        editor.commit();

                        if (informationDetail.getUsrProfile()==null||informationDetail.getUsrProfile().equals("")){
                            circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.parent_dashboard_girl));

                        }else {
                            Retrofit retrofit2 = new Retrofit.Builder()
                                    .baseUrl("https://vedictreeschool.com/uploads/studetprofile/")
                                    .build();

                            APIInterface service2 = retrofit2.create(APIInterface.class);
                          Call<ResponseBody> call2 = service2.profilePicture(informationDetail.getUsrProfile());
                            Log.i("PROFILE", String.valueOf(call2.request().url()));

                            call2.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(@NotNull Call<ResponseBody> call2, @NotNull Response<ResponseBody> response) {
                                    if (response.body() != null) {
                                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                        circleImageView.setImageBitmap(bmp);
//                                        ProfilePojo profilePojo=response.body();

                                        mUserProfilePictureFromServer = new File(context.getCacheDir(), "temp");
                                        try {
                                            mUserProfilePictureFromServer.createNewFile();
                                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                            bmp.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
                                            byte[] bitmapdata = bos.toByteArray();
                                            FileOutputStream fos = null;
                                            fos = new FileOutputStream(mUserProfilePictureFromServer);
                                            fos.write(bitmapdata);
                                            fos.flush();
                                            fos.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(@NotNull Call<ResponseBody> call2, @NotNull Throwable t) {
                                    Log.i("Error", String.valueOf(t.getMessage()));
                                }
                            });

//                            APIInterface service = RetrofitSignleton.getAPIInterface();
//                            Call<ProfilePojo> call2 = service.getImageUploaded(informationDetail.getUsrProfile());
//                            Log.i("PROFILE", String.valueOf(call2.request().url()));
//
//                            call2.enqueue(new Callback<ProfilePojo>() {
//                                @Override
//                                public void onResponse(@NotNull Call<ProfilePojo> call2, @NotNull Response<ProfilePojo> response) {
//                                    if (response.body() != null) {
//
//                                        ProfilePojo profilePojo=response.body();
//                                        Glide.with(context).load(profilePojo.getError().getData()).into(circleImageView);
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(@NotNull Call<ProfilePojo> call2, @NotNull Throwable t) {
//                                    Log.i("Error", String.valueOf(t.getMessage()));
//                                }
//                            });
                        }



                        alertDialog_onCreate.dismiss();

                    }
                    alertDialog_onCreate.dismiss();


                }
            }
            @Override
            public void onFailure(Call<InformationPojo> call, Throwable t) {
                alertDialog_onCreate.dismiss();

            }
        });
    }

    public void onClickedBMW(View view) {
        Toast.makeText(getContext(), "BMW is selected for a drive!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_image_student:
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, select_photo);
                break;
            case R.id.student_save:
                studentInfo();
                break;
            case R.id.studentBirthDate:
                setDate(view);
                break;
            case R.id.radioM:
                if (male.isChecked()){
                    female.setChecked(false);
                    genderString="Male";
                }
                break;
            case R.id.radioF:
                if (female.isChecked()){
                    male.setChecked(false);
                    genderString="Female";
                }
                break;
            case R.id.browse_certificate:
                Intent businessId_intent = new Intent(Intent.ACTION_PICK);
                businessId_intent.setType("image/*");
                startActivityForResult(businessId_intent, select_IdProof);//
                break;
            default:break;
        }
    }

    private void setDate(View view){
                final Calendar myCalendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "yy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        mStudentBirthDate.setText(sdf.format(myCalendar.getTime()));
                        Log.i("Date is:",String.valueOf(sdf.format(myCalendar.getTime())));

                    }
                };
                new DatePickerDialog(view.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private String getRealPathFromURI(Uri contentUri,Context c) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(c, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    private void studentInfo() {
        final ArrayList<EditText> editTextArrayList = new ArrayList<>();
        editTextArrayList.add(mSurname);
        editTextArrayList.add(mStudentName);
        editTextArrayList.add(mStudentFather);
        editTextArrayList.add(mStudentMotherToungeName);
        editTextArrayList.add(mStudentBirthDate);
        editTextArrayList.add(mCity);
        editTextArrayList.add(mCountry);
        editTextArrayList.add(mStudentMobile);
        editTextArrayList.add(mEmail);
        editTextArrayList.add(mReligion);
        editTextArrayList.add(mStudentCasteName);
        editTextArrayList.add(mAddress1);
        editTextArrayList.add(mBirthCertificate);


        if (mStudentSubCasteName.getText().equals("")){
            mStudentSubCasteName.setText("  ");
        }
        if (mStudentPreviousSchoolName.getText().equals("")){
            mStudentPreviousSchoolName.setText("  ");
        }
        if (mAddress2.getText().equals("")){
            mAddress2.setText("  ");
        }
        if (mAdhare.getText().equals("")){
            mAdhare.setText("  ");
        }

        if(RequiredFieldUtils.isRequiredFieldEmpty(editTextArrayList)){
            return;
        }
        else if (mStudentMobile.getText().toString().length()<10){
            Toast.makeText(getContext(), "Mobile number should be 10 digit", Toast.LENGTH_LONG).show();
        }
        else if (genderString.equals("")){
            Toast.makeText(getContext(), "Please select gender", Toast.LENGTH_LONG).show();
        }
        else {
             first_name=RequestBody.create(MediaType.parse("multipart/form-data"), mStudentName.getText().toString());
            last_name=RequestBody.create(MediaType.parse("multipart/form-data"), mSurname.getText().toString());
             father_name=RequestBody.create(MediaType.parse("multipart/form-data"), mStudentFather.getText().toString());
             email_id=RequestBody.create(MediaType.parse("multipart/form-data"), mEmail.getText().toString());
             mobile_no=RequestBody.create(MediaType.parse("multipart/form-data"), mStudentMobile.getText().toString());
             adhar_no=RequestBody.create(MediaType.parse("multipart/form-data"), mAdhare.getText().toString());
             religion_str=RequestBody.create(MediaType.parse("multipart/form-data"), mReligion.getText().toString());
             cast_str=RequestBody.create(MediaType.parse("multipart/form-data"), mStudentCasteName.getText().toString());
             sub_cast_str=RequestBody.create(MediaType.parse("multipart/form-data"), mStudentSubCasteName.getText().toString());
             school_str=RequestBody.create(MediaType.parse("multipart/form-data"), mStudentPreviousSchoolName.getText().toString());
             mother_tongue_str=RequestBody.create(MediaType.parse("multipart/form-data"), mStudentMotherToungeName.getText().toString());
             add1_str=RequestBody.create(MediaType.parse("multipart/form-data"), mAddress1.getText().toString());
             add2_str=RequestBody.create(MediaType.parse("multipart/form-data"), mAddress2.getText().toString());
             gender_str=RequestBody.create(MediaType.parse("multipart/form-data"), mStudentGenderName.getText().toString());
             date_str=RequestBody.create(MediaType.parse("multipart/form-data"), mStudentBirthDate.getText().toString());
             city_str=RequestBody.create(MediaType.parse("multipart/form-data"), mCity.getText().toString());
             state_str=RequestBody.create(MediaType.parse("multipart/form-data"), state_string);
             country_str=RequestBody.create(MediaType.parse("multipart/form-data"), mCountry.getText().toString());
            stud_str_id =RequestBody.create(MediaType.parse("multipart/form-data"), studentId);
             stud_role =RequestBody.create(MediaType.parse("multipart/form-data"), "student");
             land_no =RequestBody.create(MediaType.parse("multipart/form-data"), " ");
             about_info =RequestBody.create(MediaType.parse("multipart/form-data"), " ");
             dob_2 =RequestBody.create(MediaType.parse("multipart/form-data"), " ");
             profile_2 =RequestBody.create(MediaType.parse("multipart/form-data"), " ");

             if(imageuri!=null) {
                 File image_file = new File(getRealPathFromURI(imageuri, getContext()));
                 profile_img = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
             }else{
                 profile_img = RequestBody.create(MediaType.parse("multipart/form-data"),mUserProfilePictureFromServer);
             }

            File birth_file = new File(getRealPathFromURI(businessId_uri,getContext()));
            birth_img = RequestBody.create(MediaType.parse("multipart/form-data"), birth_file);

            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<Profile_pojo> call = service.updateProfile2(mobile_no,land_no,first_name,last_name,about_info,add1_str,add2_str,city_str,state_str,country_str,email_id,stud_str_id,stud_role,profile_img,adhar_no,religion_str,cast_str,sub_cast_str,school_str,mother_tongue_str,father_name,birth_img,profile_2,dob_2,date_str);
//            Call<Profile_pojo> call = service.updateProfile(mStudentMobile.getText().toString()," ",mStudentName.getText().toString(),mSurname.getText().toString()," ",mAddress1.getText().toString(),mAddress2.getText().toString(),mCity.getText().toString(),state_string,mCountry.getText().toString(),mEmail.getText().toString(),studentId,"student",image,mAdhare.getText().toString(),mReligion.getText().toString(),mStudentSubCasteName.getText().toString(),mStudentCasteName.getText().toString(),mStudentPreviousSchoolName.getText().toString(),mStudentMotherToungeName.getText().toString(),mStudentFather.getText().toString(),image2," "," ",mStudentBirthDate.getText().toString());
            Log.i("URL:", String.valueOf(call.request().url()));
            Log.i("URL Headers:", String.valueOf(call.request().headers()));
            call.enqueue(new Callback<Profile_pojo>() {
                @Override
                public void onResponse(Call<Profile_pojo> call, Response<Profile_pojo> response) {
                    if (response.body() != null) {
                        Toast.makeText(getContext(), response.body().getError().getError(), Toast.LENGTH_LONG).show();
                        getStudentProfile(getContext());

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Profile_pojo> call, Throwable t) {
                    Log.i("Error", String.valueOf(t.getMessage()));
//                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] selected_media = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, selected_media, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public static Bitmap decodeUri(Context context, Uri uri,
                                   final int requiredSize) throws FileNotFoundException {
        BitmapFactory.Options bitmap_option = new BitmapFactory.Options();
        bitmap_option.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, bitmap_option);

        int width_tmp = bitmap_option.outWidth, height_tmp = bitmap_option.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options bitmap_option_2 = new BitmapFactory.Options();
        bitmap_option_2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, bitmap_option_2);
    }

    public void onActivityResult(int requestcode, int resultcode,
                                 Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);
        switch (requestcode) {
            case select_photo:
                if (resultcode == RESULT_OK) {
                    try {
                        imageuri = imagereturnintent.getData();
                        uriPath=("URI Path: " + imageuri.toString());
                        real_Path = getRealPathFromUri(getContext(),imageuri);
//                        realPath=("Real Path: " + real_Path);
                        Bitmap bitmap = decodeUri(getContext(), imageuri, 300);
                        if (bitmap != null)
                            circleImageView.setImageBitmap(bitmap);
                        else
                            Toast.makeText(getContext(),
                                    "Error while decoding image.",
                                    Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "File not found.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case select_IdProof:
                if (resultcode == RESULT_OK) {
                    businessId_uri = imagereturnintent.getData();
                     real_Path_id = getRealPathFromUri(getContext(),
                            businessId_uri);
                    String filename=real_Path_id.substring(real_Path_id.lastIndexOf("/")+1);
                    Log.i("Real Path:",filename);
                    mBirthCertificate.setText(filename);
                }
                break;
                default:break;
        }
    }

}