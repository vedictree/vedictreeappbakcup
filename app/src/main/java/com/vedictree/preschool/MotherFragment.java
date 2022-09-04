package com.vedictree.preschool;

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
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;

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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MotherFragment extends Fragment implements View.OnClickListener {
    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    private final int select_photo = 1;
    public static final int RESULT_OK = -1;
    String uriPath;
    String real_Path;
    Uri imageuri;
    String imageString;
    View mRootView;
    EditText mMotherName;
    EditText mMotherMobile;
    EditText mMotherEmail;
    EditText mMotherAdarNo;
    EditText madd1;//Address of mother
    String motherRole;
    Button SaveInfo;
    SharedPreferences preferences;
    String studentId;
    private InformationPojo informationPojo;
    private InformationDetail informationDetail;
    private List<InformationDetail> informationDetailList;
    EditText mCast;
    EditText mSubCast;
    EditText mReligion;
    EditText mCity;
    EditText mCountry;
    EditText mLastName;
    EditText mMotherTounge;
    EditText mAdd2;
    EditText mLandline;
    Spinner state_spinner;
    private List<StateList> stateLists;
    String state_string;

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
    String profile_str;
    File mUserProfilePictureFromServer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView= inflater.inflate(R.layout.fragment_mother, container, false);
        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        studentId=preferences.getString("STUDENT_ID","");
        circleImageView=mRootView.findViewById(R.id.profile_image_mother);
        mMotherName=mRootView.findViewById(R.id.motherName);
        mMotherMobile=mRootView.findViewById(R.id.motherMobile);
        mMotherEmail=mRootView.findViewById(R.id.motherEmail);
        mMotherAdarNo=mRootView.findViewById(R.id.motherAdhar);
        madd1 =mRootView.findViewById(R.id.motherAddress);
        SaveInfo=mRootView.findViewById(R.id.mother_save);
        motherRole="mother";
        mCast=mRootView.findViewById(R.id.motherCast);
        mSubCast=mRootView.findViewById(R.id.motherSubcast);
        mReligion=mRootView.findViewById(R.id.motherRelegion);
        mCity=mRootView.findViewById(R.id.motherCity);
        mCountry=mRootView.findViewById(R.id.motherCountry);
        mLastName=mRootView.findViewById(R.id.motherLastName);
        mMotherTounge=mRootView.findViewById(R.id.motherMotherTounge);
        mAdd2=mRootView.findViewById(R.id.motherAdd2);
        mLandline=mRootView.findViewById(R.id.motherEmergancy);
        state_spinner=mRootView.findViewById(R.id.stateMotherSpinner);

        circleImageView.setOnClickListener(this);
        SaveInfo.setOnClickListener(this);
        getMotherProfile(getContext());

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
                    ArrayAdapter<String> classAdapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_textview,stateArray);
                    state_spinner.setAdapter(classAdapter);
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
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_image_mother:
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, select_photo);
                break;
            case R.id.mother_save:
                saveMotherInfo();
                break;
            default:break;
        }
    }

    private void getMotherProfile(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        ViewGroup viewGroup =mRootView.findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(getContext()).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<InformationPojo> call = service.getMotherDetail(studentId);
        call.enqueue(new Callback<InformationPojo>() {
            @Override
            public void onResponse(Call<InformationPojo> call, Response<InformationPojo> response) {
                if (response.body() != null) {
                    informationPojo= response.body();
                    informationDetailList=informationPojo.getUserinfo();
                    if (!informationDetailList.isEmpty()) {
                        informationDetail = informationDetailList.get(0);
                        mMotherName.setText(informationDetail.getUsrFirstname());
                        mLastName.setText(informationDetail.getUsrLastname());
                        mMotherEmail.setText(informationDetail.getStudentEmail());
                        if(informationDetail.getStudentMobile()==null||informationDetail.getStudentMobile().equals("")){
                            mMotherMobile.setText(informationDetail.getStudentAltMobile());
                        }
                        else{
                            mMotherMobile.setText(informationDetail.getStudentMobile());
                        }
                        
                        mMotherAdarNo.setText(informationDetail.getAdharno());
                        mReligion.setText(informationDetail.getStudentReligion());
                        mCast.setText(informationDetail.getStudentCaste());
                        mSubCast.setText(informationDetail.getStudentSubcast());
                        mCity.setText(informationDetail.getUsrCity());
                        mCountry.setText(informationDetail.getUsrCountry());
                        mMotherTounge.setText(informationDetail.getMothertoungue());
                        mAdd2.setText(informationDetail.getUsrAdd2());
                        mLandline.setText(informationDetail.getUsrLandline());
                        madd1.setText(informationDetail.getUsrAdd1());
                        if (informationDetail.getUsrProfile().equals("")||informationDetail.getUsrProfile()==null){
                            circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.parent_dashboard_girl));
                            profile_str="";
                        }
                        else {
//                            String uri_image="https://vedictreeschool.com/uploads/motherprofile/"+informationDetail.getUsrProfile();
//                            Glide.with(context).load(uri_image).into(circleImageView);
                            profile_str=informationDetail.getUsrProfile();

//                            APIInterface service = RetrofitSignletonImage.getAPIInterface();
//                            String uri_imag2e="motherprofile/"+informationDetail.getUsrProfile();

                            Retrofit retrofit2 = new Retrofit.Builder()
                                    .baseUrl("https://vedictreeschool.com/uploads/motherprofile/")
                                    .build();

                            APIInterface service2 = retrofit2.create(APIInterface.class);
//                            service2.profilePicture("https://s3.amazon.com/profile-picture/path");

// https://s3.amazon.com/profile-picture/path
                            Call<ResponseBody> call2 = service2.profilePicture(informationDetail.getUsrProfile());
                            Log.i("PROFILE", String.valueOf(call2.request().url()));

                            call2.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(@NotNull Call<ResponseBody> call2, @NotNull Response<ResponseBody> response) {
                                    if (response.body() != null) {
                                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                        circleImageView.setImageBitmap(bmp);
//                                        ProfilePojo profilePojo=response.body();
                                        mUserProfilePictureFromServer = new File(getContext().getCacheDir(), "temp");
                                        try {
                                            mUserProfilePictureFromServer.createNewFile();
                                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                            bmp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
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
                        }
                        alertDialog_onCreate.dismiss();
                    }
                    alertDialog_onCreate.dismiss();

                }
                alertDialog_onCreate.dismiss();

            }
            @Override
            public void onFailure(Call<InformationPojo> call, Throwable t) {
                alertDialog_onCreate.dismiss();
            }
        });
    }
    private void saveMotherInfo() {
        final ArrayList<EditText> editTextArrayList = new ArrayList<>();
        editTextArrayList.add(mMotherMobile);
        editTextArrayList.add(mMotherName);
        editTextArrayList.add(madd1);
        editTextArrayList.add(mMotherEmail);
        editTextArrayList.add(mCity);
        editTextArrayList.add(mCountry);
        editTextArrayList.add(mLastName);
        editTextArrayList.add(mCast);
        editTextArrayList.add(mReligion);
        editTextArrayList.add(mMotherTounge);

        if (mSubCast.getText().equals("")){
            mSubCast.setText("  ");
        }

        if (mAdd2.getText().equals("")){
            mAdd2.setText("  ");
        }
        if (mLandline.getText().equals("")){
            mLandline.setText("  ");
        }

        if (mMotherAdarNo.getText().equals("")){
            mSubCast.setText("  ");
        }
        if(RequiredFieldUtils.isRequiredFieldEmpty(editTextArrayList)){
            return;
        }else if (mMotherMobile.getText().toString().length()<10){
            Toast.makeText(getContext(), "Mobile number should be 10 digit", Toast.LENGTH_LONG).show();

        }else if (mMotherAdarNo.getText().toString().length()<12){
            Toast.makeText(getContext(), "Aadhaar number should be 12 digit", Toast.LENGTH_LONG).show();

        }else {

            first_name=RequestBody.create(MediaType.parse("multipart/form-data"), mMotherName.getText().toString());
            last_name=RequestBody.create(MediaType.parse("multipart/form-data"), mLastName.getText().toString());
            father_name=RequestBody.create(MediaType.parse("multipart/form-data")," ");
            email_id=RequestBody.create(MediaType.parse("multipart/form-data"), mMotherEmail.getText().toString());
            mobile_no=RequestBody.create(MediaType.parse("multipart/form-data"), mMotherMobile.getText().toString());
            adhar_no=RequestBody.create(MediaType.parse("multipart/form-data"), mMotherAdarNo.getText().toString());
            religion_str=RequestBody.create(MediaType.parse("multipart/form-data"), mReligion.getText().toString());
            cast_str=RequestBody.create(MediaType.parse("multipart/form-data"), mCast.getText().toString());
            sub_cast_str=RequestBody.create(MediaType.parse("multipart/form-data"), mSubCast.getText().toString());
            school_str=RequestBody.create(MediaType.parse("multipart/form-data")," ");
            mother_tongue_str=RequestBody.create(MediaType.parse("multipart/form-data"), mMotherTounge.getText().toString());
            add1_str=RequestBody.create(MediaType.parse("multipart/form-data"), madd1.getText().toString());
            add2_str=RequestBody.create(MediaType.parse("multipart/form-data"), mAdd2.getText().toString());
            gender_str=RequestBody.create(MediaType.parse("multipart/form-data")," ");
            date_str=RequestBody.create(MediaType.parse("multipart/form-data")," ");
            city_str=RequestBody.create(MediaType.parse("multipart/form-data"), mCity.getText().toString());
            state_str=RequestBody.create(MediaType.parse("multipart/form-data"), state_string);
            country_str=RequestBody.create(MediaType.parse("multipart/form-data"), mCountry.getText().toString());
            stud_str_id =RequestBody.create(MediaType.parse("multipart/form-data"), studentId);
            stud_role =RequestBody.create(MediaType.parse("multipart/form-data"), "mother");
            land_no =RequestBody.create(MediaType.parse("multipart/form-data"), mLandline.getText().toString());
            about_info =RequestBody.create(MediaType.parse("multipart/form-data"), " ");
            dob_2 =RequestBody.create(MediaType.parse("multipart/form-data"), " ");
            profile_2 =RequestBody.create(MediaType.parse("multipart/form-data"), " ");

            if (imageuri!=null){
                File image_file = new File(getRealPathFromURI(imageuri, getContext()));
                profile_img = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
            }else {
                profile_img = RequestBody.create(MediaType.parse("multipart/form-data"), mUserProfilePictureFromServer);
                imageString="motherPic";
            }

            birth_img = RequestBody.create(MediaType.parse("multipart/form-data"), " ");

            APIInterface service = RetrofitSignleton.getAPIInterface();
            Call<Profile_pojo> call = service.updateProfile3(mobile_no,land_no,first_name,last_name,about_info,add1_str,add2_str,city_str,state_str,country_str,email_id,stud_str_id,stud_role, MultipartBody.Part.createFormData("usr_profile",imageString, profile_img),adhar_no,religion_str,cast_str,sub_cast_str,school_str,mother_tongue_str,father_name,MultipartBody.Part.createFormData("dob_certificate","", profile_img),profile_2,dob_2,date_str);

//            Call<Profile_pojo> call = service.uploadMotherProfile(mMotherMobile.getText().toString(),mLandline.getText().toString(),mMotherName.getText().toString(),mLastName.getText().toString()," ", madd1.getText().toString(),mAdd2.getText().toString(),mCity.getText().toString(),state_string,mCountry.getText().toString(),mMotherEmail.getText().toString(),studentId,motherRole,image_file,mMotherAdarNo.getText().toString(),mReligion.getText().toString(),mCast.getText().toString(),mSubCast.getText().toString()," ",mMotherTounge.getText().toString()," ", new File(" ")," "," "," ");
            Log.i("URL:", String.valueOf(call.request().url()));
            Log.i("URL Headers:", String.valueOf(call.request().headers()));
            call.enqueue(new Callback<Profile_pojo>() {
                @Override
                public void onResponse(Call<Profile_pojo> call, Response<Profile_pojo> response) {
                    if (response.body() != null) {
                        Toast.makeText(getContext(), response.body().getError().getError(), Toast.LENGTH_LONG).show();

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
                        if (bitmap != null) {
                            circleImageView.setImageBitmap(bitmap);
                            imageString=real_Path.substring(real_Path.lastIndexOf("/")+1);
                        }
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
        }
    }
}