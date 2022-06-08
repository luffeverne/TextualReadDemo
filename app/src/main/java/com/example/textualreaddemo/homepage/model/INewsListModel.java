package com.example.textualreaddemo.homepage.model;

import android.app.Activity;

import com.example.textualreaddemo.basebean.newsdata.NewsListBean;

public interface INewsListModel {

    void getNewsListData(Activity activity , CallBack callBack);

}
