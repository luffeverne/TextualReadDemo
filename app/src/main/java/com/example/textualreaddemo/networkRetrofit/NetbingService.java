package com.example.textualreaddemo.networkRetrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetbingService {
    @GET("types")
    Call<ResponseBody> getNewsTypes(@Query("app_id") String app_id,@Query("app_secret") String app_secret);

    @GET("list")
    Call<ResponseBody> getNewsList(@Query("typeId") String typeId,@Query("page") String page,@Query("app_id") String app_id,@Query("app_secret") String app_secret);

    @GET("details")
    Call<ResponseBody> getNewsDetail(@Query("newsId") String newsId,@Query("app_id") String app_id,@Query("app_secret") String app_secret);
}
