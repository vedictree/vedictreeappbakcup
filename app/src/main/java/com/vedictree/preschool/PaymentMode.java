package com.vedictree.preschool;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.vedictree.preschool.POJO.Emiplan;
import com.vedictree.preschool.POJO.emi;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;
import com.vedictree.preschool.Utils.VedicConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMode extends AppCompatActivity implements View.OnClickListener {

    RadioButton mRazorPay;
    RadioButton mEmi;
    RadioButton mOfflineButton;
    LinearLayout emil_month_layout;
    RadioButton mThreeMonth;
    RadioButton mSixMonth;
    LinearLayout mSixMnthLayout;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    String pakage_name;
    TextView mTutionFeesThree;
    TextView mThreeEmiCharge;
    TextView mThreeTotelFee;

    TextView mTutionFeeSixMonth;
    TextView mSixEmiCharge;
    TextView mSixTotalFee;

    String plan_id;
    private List<emi> emiList;
    private emi emiArray;
    private List<Emiplan> emiplanList;
    private Emiplan emiplan;
    ArrayList<String> amount;
    ArrayList<String> tenture_name;
    ArrayList<String> monthlyFess;
    ArrayList<String> monthlyEmiFee;
    ArrayList<String> emiChergesFee;
    ArrayList<String> finalEmiAmmount;
    ArrayList<String> plan_id_array;
    Button mPayNow;
    TextView mTotalamounThree;
    TextView mTotalamounSix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_mode);
        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (tabletSize) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor = preferences.edit();
        amount = new ArrayList<>();
        tenture_name = new ArrayList<>();
        monthlyFess = new ArrayList<>();
        plan_id_array = new ArrayList<>();
        monthlyEmiFee = new ArrayList<>();
        emiChergesFee = new ArrayList<>();
        finalEmiAmmount=new ArrayList<>();

        pakage_name = preferences.getString("PKG_NAME", "");
        plan_id = preferences.getString("PALN_ID", "");

        mRazorPay = findViewById(R.id.razorpay_btn);
        mEmi = findViewById(R.id.emi_btn);
        mTotalamounThree=findViewById(R.id.threeMonthlyTotalFeesTotal);
        mTotalamounSix=findViewById(R.id.sixMonthlyTotalFeesAmount);
        mTutionFeesThree = findViewById(R.id.three_month_tutuion_fee);
        mThreeEmiCharge = findViewById(R.id.threeEmiCharge);
        mThreeTotelFee = findViewById(R.id.threeMonthlyTotalFees);
        mPayNow = findViewById(R.id.finalPayNowButton);

        mSixEmiCharge = findViewById(R.id.sixEmiCharge);
        mSixTotalFee = findViewById(R.id.sixMonthlyTotalFees);
        mTutionFeeSixMonth = findViewById(R.id.six_month_tution_fee);

        mOfflineButton = findViewById(R.id.offline_btn);
        mThreeMonth = findViewById(R.id.three_month);
        mSixMonth = findViewById(R.id.six_month);
        emil_month_layout = findViewById(R.id.emi_layout);
        emil_month_layout.setVisibility(View.GONE);
        mSixMnthLayout = findViewById(R.id.six_month_layout);

        mRazorPay.setOnClickListener(this);
        mEmi.setOnClickListener(this);
        mOfflineButton.setOnClickListener(this);
        mThreeMonth.setOnClickListener(this);
        mSixMonth.setOnClickListener(this);
        mPayNow.setOnClickListener(this);

        getAllDetail();
    }

    private void getAllDetail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialog = LayoutInflater.from(this).inflate(R.layout.progress_layout, viewGroup, false);
        builder.setView(dialog);
        AlertDialog alertDialog_onCreate= builder.create();
        alertDialog_onCreate.show();

        APIInterface service = RetrofitSignleton.getAPIInterface();
        Call<emi> call = service.getEmiPlan();
        call.enqueue(new Callback<emi>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<emi> call, Response<emi> response) {
                if (response.isSuccessful()) {
                    emiArray = response.body();
                    emiplanList = emiArray.getEmiplan();
                    if (response.body().getCode() == 200) {
                        if (emiplanList != null) {
                            for (int i = 0; i < emiplanList.size(); i++) {
                                emiplan = emiplanList.get(i);
                                amount.add(emiplan.getFinalFees());
                                tenture_name.add(emiplan.getTenureName());
                                monthlyFess.add(emiplan.getMonthlyFess());
                                emiChergesFee.add(emiplan.getEmicharges());
                                monthlyEmiFee.add(emiplan.getFinalFeesEmi());
                                plan_id_array.add(emiplan.getFeesId());
                                finalEmiAmmount.add(emiplan.getCheckoutEmiAmt());
                            }
                            if (plan_id_array != null) {
                                for (int j = 0; j < plan_id_array.size(); j++) {
                                    if (plan_id.equals(plan_id_array.get(j))) {
                                        if (tenture_name.get(j).equals("3")) {
                                            mTutionFeesThree.setText("RS: " + monthlyEmiFee.get(j));
                                            mThreeEmiCharge.setText("RS: " + emiChergesFee.get(j));
                                            mThreeTotelFee.setText("RS: " + monthlyFess.get(j));
                                            mTotalamounThree.setText("RS: " + finalEmiAmmount.get(j));

                                        } else if (tenture_name.get(j).equals("6")) {
                                            mTutionFeeSixMonth.setText("RS: " + monthlyEmiFee.get(j));
                                            mSixEmiCharge.setText("RS: " + emiChergesFee.get(j));
                                            mSixTotalFee.setText("RS: " + monthlyFess.get(j));
                                            mTotalamounSix.setText("RS: " + finalEmiAmmount.get(j));

                                        }
                                    }
                                    alertDialog_onCreate.dismiss();
                                }
                            }
                        }
                    }
                } else {
                    alertDialog_onCreate.dismiss();
                }
            }

            @Override
            public void onFailure(Call<emi> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                alertDialog_onCreate.dismiss();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.razorpay_btn:
                if (mRazorPay.isChecked()) {
                    mEmi.setChecked(false);
                    mOfflineButton.setChecked(false);
                    emil_month_layout.setVisibility(View.GONE);
                    editor.putString("RAZORPAY_FINAL", VedicConstants.RAZORPAY_PAYMENT);
                    editor.commit();
                }

                break;
            case R.id.emi_btn:
                if (mEmi.isChecked()) {
                    emil_month_layout.setVisibility(View.VISIBLE);
                    mRazorPay.setChecked(false);
                    mOfflineButton.setChecked(false);
                    editor.putString("RAZORPAY_FINAL", VedicConstants.EMI_PAYMENT);
                    editor.commit();
                }
                break;
            case R.id.offline_btn:
                if (mOfflineButton.isChecked()) {
                    emil_month_layout.setVisibility(View.GONE);
                    mEmi.setChecked(false);
                    mRazorPay.setChecked(false);
                    editor.putString("RAZORPAY_FINAL", VedicConstants.OFFLINE_PAYMENT);
                    editor.commit();
                }

                break;
            case R.id.three_month:
                if (mThreeMonth.isChecked()) {
                    mSixMonth.setChecked(false);
                    editor.putString("EMI_MODE", "3 Month");
                    editor.putString("RAZORPAY_FINAL", VedicConstants.EMI_THREE_PAYMENT);
                    editor.commit();
                }

                break;
            case R.id.six_month:
                if (mSixMonth.isChecked()) {
                    mThreeMonth.setChecked(false);
                    editor.putString("EMI_MODE", "6 Month");
                    editor.putString("RAZORPAY_FINAL", VedicConstants.EMI_SIX_PAYMENT);
                    editor.commit();
                }
                break;
            case R.id.finalPayNowButton:
                finalPaymentProcess();
                break;
            default:
                break;
        }
    }

    private void finalPaymentProcess() {

        String payment_mode = preferences.getString("RAZORPAY_FINAL", "");
        if (payment_mode.equals(VedicConstants.RAZORPAY_PAYMENT)) {
            Intent payment_intent = new Intent(PaymentMode.this, PaymentActivity.class);
            startActivity(payment_intent);
        }
        else if (payment_mode.equals(VedicConstants.EMI_PAYMENT)) {
            Toast.makeText(getApplicationContext(), R.string.TC12, Toast.LENGTH_LONG).show();
//            if (mSixMonth.isChecked()) {
//                if (payment_mode.equals(VedicConstants.EMI_SIX_PAYMENT)) {
//                    Intent payment_intent = new Intent(PaymentMode.this, PaymentEMI.class);
//                    startActivity(payment_intent);
//                }
//            }
//            else if (mThreeMonth.isChecked()) {
//                if (payment_mode.equals(VedicConstants.EMI_THREE_PAYMENT)) {
//                    Intent payment_intent = new Intent(PaymentMode.this, PaymentEMI.class);
//                    startActivity(payment_intent);
//                }
//            }
//            else {
//                Toast.makeText(getApplicationContext(), "Select emi mode 3 or 6 month", Toast.LENGTH_LONG).show();
//            }

        }else if (payment_mode.equals(VedicConstants.OFFLINE_PAYMENT)){
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentMode.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialog = LayoutInflater.from(PaymentMode.this).inflate(R.layout.offline_payment_pop_up, viewGroup, false);
            builder.setView(dialog);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}