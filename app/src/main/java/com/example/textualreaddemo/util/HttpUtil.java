package com.example.textualreaddemo.util;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 网络请求响应工具
 * 更改时间：2022-6-25
 * @author luffe
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
