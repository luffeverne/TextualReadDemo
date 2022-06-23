package com.example.textualreaddemo;

import android.app.Application;

import com.example.textualreaddemo.room.User;

public class MyApplication extends Application {
    private com.example.textualreaddemo.room.User user = new User();

    @Override
    public void onCreate() {
        //假装已经登录该写死的账号，将User作为全局变量
        user.setUserID("1");
        user.setName("kun");
        user.setPassword("123");
        super.onCreate();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
