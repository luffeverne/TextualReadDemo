package com.example.textualreaddemo;

import android.app.Application;

import com.example.textualreaddemo.beanRetrofit.NewsDetail;
import com.example.textualreaddemo.room.User;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private com.example.textualreaddemo.room.User user = new User();
    private List<NewsDetail.Data> news = new ArrayList<>();
    private boolean canDropLoad = false;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<NewsDetail.Data> getNews() {
        return news;
    }

    public boolean getCanDropLoad() {
        return canDropLoad;
    }

    public void setCanDropLoad(boolean canDropLoad) {
        this.canDropLoad = canDropLoad;
    }

    public void setNews(List<NewsDetail.Data> news) {
        this.news = news;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
