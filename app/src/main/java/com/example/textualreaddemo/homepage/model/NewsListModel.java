package com.example.textualreaddemo.homepage.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.textualreaddemo.basebean.newsdata.NewsListBean;
import com.example.textualreaddemo.network.HttpUtil;
import com.example.textualreaddemo.network.NewsDataUtility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 简讯处理中的M层 主要执行通过网络获取信息
 * 更新时间：2022-6-8 21：30
 * @author houdeng
 */
public class NewsListModel implements INewsListModel{

    public static final String NEWS_URL = "https://www.mxnzp.com/api/news/list?typeId=514&page=1&app_id=tqepknkfxkbi0njl&app_secret=eFFSL0xlSWV2bW5Ha2lHY2ZmQmJ2QT09";

    @Override
    public void getNewsListData(Activity activity , CallBack callBack) {
        HttpUtil.sendOkHttpRequest(NEWS_URL, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseText = response.body().string();
                final NewsListBean newsListBean = NewsDataUtility.handleNewsListResponse(responseText);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (newsListBean != null && "数据返回成功！".equals(newsListBean.getMsg())){
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(activity).edit();
                            editor.putString("NewsList",responseText);
                            editor.apply();
                            callBack.onSuccess();
                        }else {
                            callBack.onFailure(activity);
                        }
                    }
                });
            }
        });
    }
}
