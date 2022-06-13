package com.example.textualreaddemo.networkRetrofit;


import com.example.textualreaddemo.beanRetrofit.NewsDetail;
import com.example.textualreaddemo.beanRetrofit.NewsList;
import com.example.textualreaddemo.beanRetrofit.NewsTypes;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsUtility {
    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.mxnzp.com/api/news/").build();
    public static NetbingService netbingService = retrofit.create(NetbingService.class);
    public static com.google.gson.Gson gson = new com.google.gson.Gson();
    public static final String APP_ID = "nfvilyqvqqnklscs";
    public static final String APP_SECRET = "L2xacGh5U1B4WGFXeGdTWXQ3dVhsZz09";

    public static NewsTypes newsTypes = new NewsTypes();
    public static NewsList newsList = new NewsList();
    public static NewsDetail newsDetail = new NewsDetail();

    public static NewsTypes getNewsTypes() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                retrofit2.Call<ResponseBody> newsTypesCall = netbingService.getNewsTypes(APP_ID, APP_SECRET);
                Response<ResponseBody> response = null;
                try {
                    response = newsTypesCall.execute();
                    newsTypes = gson.fromJson(response.body().string(),NewsTypes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newsTypes;
    }

    public static NewsList getNewsList() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Call<ResponseBody> newsListCall = netbingService.getNewsList("509", "1", APP_ID, APP_SECRET);
                Response<ResponseBody> response = null;
                try {
                    response = newsListCall.execute();
                    newsList = gson.fromJson(response.body().string(),NewsList.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    public static NewsDetail getNewsDetail(String detailNewsID) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Call<ResponseBody> newsDetailCall = netbingService.getNewsDetail(detailNewsID, APP_ID, APP_SECRET);
                try {
                    Response<ResponseBody> response = newsDetailCall.execute();
                    newsDetail = gson.fromJson(response.body().string(),NewsDetail.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newsDetail;
    }


}
