package com.vedictree.preschool.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSignleton
{
    public static Retrofit retrofit;
//    public static final String BASE_URL ="http://www.vedictreeschool.com/api/";
    synchronized public static Retrofit getRetrofit(){
        if(retrofit == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.vedictreeschool.com/api/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    synchronized public static APIInterface getAPIInterface(){
        return getRetrofit().create(APIInterface.class);
    }

//    static public boolean isURLReachable(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        if (netInfo != null && netInfo.isConnected()) {
//            try {
//                URL url = new URL("https://vedictreeschool.com/api/");   // Change to "http://google.com" for www  test.
//                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
//                urlc.setConnectTimeout(10 * 1000);          // 10 s.
//                urlc.connect();
//                if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
//                    Log.wtf("Connection", "Success !");
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (MalformedURLException e1) {
//                return false;
//            } catch (IOException e) {
//                return false;
//            }
//        }
//        return false;
//    }
}
