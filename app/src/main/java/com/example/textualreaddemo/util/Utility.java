package com.example.textualreaddemo.util;

import com.example.textualreaddemo.basebean.newsdata.NewsList;
import com.google.gson.Gson;

/**
 * 解析 JSON 工具
 * 更改时间：2022-6-25
 * @author luffe
 */
public class Utility {
    public static NewsList parseJsonWithGson(final String requestText) {
        Gson gson =  new Gson();
        return gson.fromJson(requestText, NewsList.class);
    }
}
