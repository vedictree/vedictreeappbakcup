package com.vedictree.preschool;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentEMI extends AppCompatActivity {

    EditText mModeOfEmi;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText mStudentNameEMI;
    EditText mStudentDOBEmi;
    EditText mBranchId;
    String course_id;
    EditText mAmountEmi;
    EditText mStudentReferenceNo;
    EditText mApplicantName;
    EditText mApplicantGender;
    EditText mApplicantDob;
    EditText mMobileNum;
    EditText emailIdEMI;
    EditText mAadharNo;
    EditText mMaritalStatus;
    EditText mPan_no;
    EditText professionId;
    EditText mEmployerName;
    EditText mAnnualIncome;
    EditText mRelationshipWithStudent;
    Button mPayNow;
    String emi_mode_str;
    String gender_str;
    String referrance_str;
    String institute_id;
    String student_id;
    String plan_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_e_m_i);
        mModeOfEmi=findViewById(R.id.emiMode);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        this.editor=preferences.edit();
        String emi_mode=preferences.getString("EMI_MODE","");
        Log.i("EMI_MODE",emi_mode);
        mModeOfEmi.setText(emi_mode);
        mStudentNameEMI=findViewById(R.id.studentNameEMI);
        mStudentDOBEmi=findViewById(R.id.studentDOBEmi);
        mBranchId=findViewById(R.id.branchId);
        mAmountEmi=findViewById(R.id.amountEmi);
        mStudentReferenceNo=findViewById(R.id.student_reference_no);
        mApplicantName=findViewById(R.id.applicant_name);
        mApplicantGender=findViewById(R.id.applicant_gender);
        mApplicantDob=findViewById(R.id.applicant_dob);
        mMobileNum=findViewById(R.id.mobileNum);
        emailIdEMI=findViewById(R.id.emailId);
        mAadharNo=findViewById(R.id.aadhar_no);
        mMaritalStatus=findViewById(R.id.marital_status);
        mPan_no=findViewById(R.id.pan_no);
        professionId=findViewById(R.id.profession);
        mEmployerName=findViewById(R.id.employer_name);
        mAnnualIncome=findViewById(R.id.annual_income);
        mRelationshipWithStudent=findViewById(R.id.relationship_with_student);
        mPayNow=findViewById(R.id.EmiPayNowButton);

        mStudentNameEMI.setText(preferences.getString("NAME",""));
        mAmountEmi.setText(preferences.getString("ORDER_LAST_AMOUNT",""));

        mPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payEmiAmount();

            }
        });



    }

    private void payEmiAmount() {
        if (mModeOfEmi.getText().toString().equals("3 Month")){
            emi_mode_str="3";
        }else  if (mModeOfEmi.getText().toString().equals("3 Month")){
            emi_mode_str="6";
        }
        if (mApplicantGender.getText().toString().equals("Male") ||mApplicantGender.getText().toString().equals("male")){
            gender_str="M";
        }else  if (mApplicantGender.getText().toString().equals("Female") ||mApplicantGender.getText().toString().equals("female")){
            gender_str="F";
        }

        referrance_str="1111";
        institute_id="1111";

        student_id=preferences.getString("STUDENT_ID","");
        plan_id=preferences.getString("PALN_ID","");
        course_id=preferences.getString("PALN_ID","");

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<ResponseBody> call = service.emiPayment(mStudentNameEMI.getText().toString(),mStudentDOBEmi.getText().toString(),course_id,mBranchId.getText().toString(),mAmountEmi.getText().toString(),emi_mode_str,referrance_str,mApplicantName.getText().toString(),gender_str,mApplicantDob.getText().toString(),mMobileNum.getText().toString(),emailIdEMI.getText().toString(),mAadharNo.getText().toString(),mMaritalStatus.getText().toString(),mPan_no.getText().toString(),professionId.getText().toString(),mEmployerName.getText().toString(),mAnnualIncome.getText().toString(),mRelationshipWithStudent.getText().toString(),institute_id,"",student_id,plan_id,"");
        Log.i("URL:", String.valueOf(call.request().url()));
        Log.i("URL Headers:", String.valueOf(call.request().headers()));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    Log.i("Respons body:",String.valueOf(response.body()));

                } else {


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}