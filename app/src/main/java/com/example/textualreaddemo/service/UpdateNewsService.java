package com.example.textualreaddemo.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.textualreaddemo.basebean.newsdata.NewsListBean;
import com.example.textualreaddemo.homepage.model.NewsListModel;
import com.example.textualreaddemo.network.HttpUtil;
import com.example.textualreaddemo.network.NewsDataUtility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UpdateNewsService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateNewsList();
        //设置定时器
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 2 * 60 * 60 * 1000;        //2 小时
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, UpdateNewsService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新新闻信息
     */
    private void updateNewsList() {
        HttpUtil.sendOkHttpRequest(NewsListModel.NEWS_URL, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseText = response.body().string();
                final NewsListBean newsListBean = NewsDataUtility.handleNewsListResponse(responseText);
                if (newsListBean != null && "数据返回成功！".equals(newsListBean.getMsg())){
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(UpdateNewsService.this).edit();
                    editor.putString("NewsList",responseText);
                    editor.apply();
                }else {
                    updateNewsList();
                }
            }
        });
    }
}
