package com.example.textualreaddemo.network;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 联网工具类
 * 更改时间：2022-6-7
 * @author houdeng
 */
public class HttpUtil {
    /**
     * 实现与服务器交互
     */
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
