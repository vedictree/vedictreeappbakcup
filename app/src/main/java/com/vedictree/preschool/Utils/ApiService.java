package com.vedictree.preschool.Utils;

import com.vedictree.preschool.POJO.SubmitTestPojo;
import com.vedictree.preschool.POJO.ValueBaseEducationPojo;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("student/Uploadhomework")
    Call<SubmitTestPojo> event_store(@Body RequestBody file);

}
