package com.example.textualreaddemo.networkRetrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 对新闻数据提供的服务器进行访问的接口
 */
public interface NetbingService {
    //获取新闻类型数据
    @GET("types")
    Call<ResponseBody> getNewsTypes(@Query("app_id") String app_id,@Query("app_secret") String app_secret);
    //获取非详情新闻列表数据
    @GET("list")
    Call<ResponseBody> getNewsList(@Query("typeId") String typeId,@Query("page") String page,@Query("app_id") String app_id,@Query("app_secret") String app_secret);
    //获取详情新闻数据
    @GET("details")
    Call<ResponseBody> getNewsDetail(@Query("newsId") String newsId,@Query("app_id") String app_id,@Query("app_secret") String app_secret);
}
