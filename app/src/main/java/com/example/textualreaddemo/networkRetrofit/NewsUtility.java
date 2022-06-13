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
    //根据域名创建对应的Retrofit 和 其对应的 NetbingService 网络请求接口
    public static Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.mxnzp.com/api/news/").build();
    public static NetbingService netbingService = retrofit.create(NetbingService.class);
    //解析返回的json数据
    public static com.google.gson.Gson gson = new com.google.gson.Gson();
    //向该服务器申请数据时需要个人申请的 app_id 和 app_secret
    public static final String APP_ID = "nfvilyqvqqnklscs";
    public static final String APP_SECRET = "L2xacGh5U1B4WGFXeGdTWXQ3dVhsZz09";
    //记录返回的bean数据
    public static NewsTypes newsTypes = new NewsTypes();
    public static NewsList newsList = new NewsList();
    public static NewsDetail newsDetail = new NewsDetail();

    //返回新闻类型数据
    public static NewsTypes getNewsTypes() {
        //网络请求需要开启子线程
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                retrofit2.Call<ResponseBody> newsTypesCall = netbingService.getNewsTypes(APP_ID, APP_SECRET);
                Response<ResponseBody> response = null;
                try {
                    response = newsTypesCall.execute();
                    //这里注意对 response 只能处理一次，否则会造成数据流的断流
                    newsTypes = gson.fromJson(response.body().string(),NewsTypes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            //等待该线程完成，保证拿到数据
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newsTypes;
    }

    //返回非详情新闻的列表数据
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

    //返回新闻详情数据
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
