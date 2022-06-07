package com.example.textualreaddemo.network;

import android.text.TextUtils;

import com.example.textualreaddemo.basebean.newsdata.NewsDetailsBean;
import com.example.textualreaddemo.basebean.newsdata.NewsListBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 借用Gson框架来解析json数据
 * 更改时间：2022-6-7
 * @author houdeng
 */
public class NewsDataUtility {

    /**
     * 解析和处理服务器返回的简讯列表数据
     */
    public static NewsListBean handleNewsListResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject allNewsData = new JSONObject(response);
                return new Gson().fromJson(allNewsData.toString(),NewsListBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 解析和处理服务器返回的新闻详情数据
     */
    public static NewsDetailsBean handleNewsDetailResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject allNewsData = new JSONObject(response);
                return new Gson().fromJson(allNewsData.toString(),NewsDetailsBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
