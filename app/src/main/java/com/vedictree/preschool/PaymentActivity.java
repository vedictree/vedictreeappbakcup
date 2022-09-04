package com.vedictree.preschool;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.vedictree.preschool.POJO.verify_otp;
import com.vedictree.preschool.Utils.APIInterface;
import com.vedictree.preschool.Utils.RetrofitSignleton;

import org.json.JSONObject;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentActivity extends Activity implements PaymentResultWithDataListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    String OREDER_ID;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String order_amount;
    String final_amount;
    String email_final;
    String final_mobile;
    String final_name;
    Integer amount_int;
    String planStr;
    String packageStr;
    String firstNameStr;
    String lastNameStr;
    String studentId;
    String payment_status;
    String payment_type;
    String paymentStatusId;
    final PaymentActivity activity2 = this;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean tabletSize=(getApplicationContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK ) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (tabletSize) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
         preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         this.editor=preferences.edit();
         payment_type="1";
         paymentStatusId="1";
         order_amount=preferences.getString("ORDER_AMOUNT","");
         email_final=preferences.getString("EMAIL","");
         final_mobile=preferences.getString("MOBILE","");
         final_name=preferences.getString("NAME","");
         planStr=preferences.getString("PALN_ID","");
         packageStr=preferences.getString("PACKEAGE_NAME","");
         firstNameStr=preferences.getString("USER_FIRST_NAME","");
         lastNameStr=preferences.getString("USER_LAST_NAME","");
         studentId=preferences.getString("STUDENT_ID","");

        Log.i ("order_amount: ",order_amount);
        Log.i ("email_final: ",email_final);
        Log.i ("final_mobile: ",final_mobile);
        Log.i ("planStr: ",planStr);
        Log.i ("packageStr: ",packageStr);
        Log.i ("firstNameStr: ",final_name);
        Log.i ("lastNameStr: ",lastNameStr);
        Log.i ("studentId: ",studentId);


        Double rupees=Double.parseDouble(order_amount);
         final_amount=String.valueOf(rupees*100);
         amount_int= rupees.intValue()*100;
        Checkout.preload(getApplicationContext());
//        orderGenerate();
        new MyTask().execute();
//        startPayment();

    }

    private void orderGenerate() {
        try {
            RazorpayClient razorpay = new RazorpayClient("rzp_live_x90EawgbGwgJ18", "ZJyP9F00ZY1PkOrP7NYcPjIW");
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount_int);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_11");
            Order order = razorpay.Orders.create(orderRequest);
            JSONObject jsonObject = new JSONObject( order.toString());
            Log.i("Amount is:",jsonObject.getString("amount"));
            String amount_is=jsonObject.getString("amount");
            String order_detail=order.get("id");
//
            OREDER_ID=order.get("id");
            editor.putString("ORDER_ID",OREDER_ID);
            editor.putString("ORDER_LAST_AMOUNT",amount_is);
            editor.commit();
            Log.i("ORDER:", String.valueOf(order));
            startPayment();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            URL url;
            final Checkout co = new Checkout();

            try {
                RazorpayClient razorpay = new RazorpayClient("rzp_live_x90EawgbGwgJ18", "ZJyP9F00ZY1PkOrP7NYcPjIW");
                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount", amount_int);
                orderRequest.put("currency", "INR");
                orderRequest.put("receipt", "order_rcptid_11");
                Order order = razorpay.Orders.create(orderRequest);
                JSONObject jsonObject = new JSONObject( order.toString());
                Log.i("Amount is:",jsonObject.getString("amount"));
                String amount_is=jsonObject.getString("amount");
                String order_detail=order.get("id");
//
                OREDER_ID=order.get("id");
                editor.putString("ORDER_ID",OREDER_ID);
                editor.putString("ORDER_LAST_AMOUNT",amount_is);
                editor.commit();
                Log.i("ORDER:", String.valueOf(order));


                JSONObject options = new JSONObject();
                options.put("name", final_name);
                options.put("description", "Vedic tree Charges");
                options.put("order_id", order.get("id"));
                options.put("send_sms_hash",true);
                options.put("allow_rotation", true);
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                options.put("currency", "INR");
                options.put("amount", order.get("amount"));
                JSONObject preFill = new JSONObject();
                preFill.put("email", email_final);
                preFill.put("contact", final_mobile);
                options.put("prefill", preFill);

                co.open(activity2, options);

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    private void startPayment() {
        String order_id=preferences.getString("ORDER_ID","");
        String pay_amount=preferences.getString("ORDER_LAST_AMOUNT","");
//        Integer amount_final_int=Integer.parseInt(pay_amount);
        Log.i("Amount is:",pay_amount);

            final Activity activity = this;
            final Checkout co = new Checkout();

            try {
                JSONObject options = new JSONObject();
                options.put("name", final_name);
                options.put("description", "Vedic tree Charges");
                options.put("order_id", order_id);
                options.put("send_sms_hash",true);
                options.put("allow_rotation", true);
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                options.put("currency", "INR");
                options.put("amount", order_amount);
                JSONObject preFill = new JSONObject();
                preFill.put("email", email_final);
                preFill.put("contact", final_mobile);
                options.put("prefill", preFill);

                co.open(activity, options);
            } catch (Exception e) {
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        payment_status="success";
        Log.i ("Payment Successful: ",s);
        Log.i ("sign: ", String.valueOf(paymentData.getSignature()));
        Log.i ("order: ",paymentData.getOrderId());
        Log.i ("pay id: ", String.valueOf(paymentData.getPaymentId()));
//        Intent intent=new Intent(PaymentActivity.this,ReceiptActivity.class);
//        startActivity(intent);

        APIInterface service= RetrofitSignleton.getAPIInterface();
        Call<verify_otp> call=service.submitPayment(final_name,final_name,email_final,final_mobile,order_amount,studentId,paymentData.getOrderId(),paymentData.getPaymentId(),paymentData.getSignature(),planStr,payment_type,payment_status,paymentStatusId);
        call.enqueue(new Callback<verify_otp>() {
            @Override
            public void onResponse(Call<verify_otp> call, Response<verify_otp> response) {
//                Log.i("Error:",response.errorBody().source().toString());

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getCode()==200){
//                        Toast.makeText(getApplicationContext(),response.body().getMsg() ,Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(getApplicationContext(), VideoSplash.class);
//                        startActivity(intent);
                    }
                    else if (response.body().getCode()==404){
                        Toast.makeText(getApplicationContext(),response.body().getMsg() ,Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==505){
                        Toast.makeText(getApplicationContext(),response.body().getMsg() ,Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<verify_otp> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage() ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.i("Error of Payment",s);
        Log.i ("pay id: ", String.valueOf(paymentData.getPaymentId()));
        Toast.makeText(getApplicationContext()," Payment fail.Please try again", Toast.LENGTH_SHORT).show();
        finish();
    }
}
