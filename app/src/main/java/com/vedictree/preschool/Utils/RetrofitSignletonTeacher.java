package com.vedictree.preschool.Utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSignletonTeacher
{
    public static Retrofit retrofit;
    public static final String BASE_URL ="http://vedictreeschool.com/api/";
    synchronized public static Retrofit getRetrofitTeacher(){
        if(retrofit == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://teacher.vedictreeschool.com/api/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    synchronized public static APIInterface getLiveIntrface(){
        return getRetrofitTeacher().create(APIInterface.class);
    }

}
