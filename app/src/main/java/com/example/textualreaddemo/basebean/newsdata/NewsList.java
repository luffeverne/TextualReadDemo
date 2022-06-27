package com.example.textualreaddemo.basebean.newsdata;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 这个类用来记录新闻列表
 * 更改时间：2022-6-25
 * @author luffe
 */

public class NewsList {

    public int code;

    public String msg;

    @SerializedName("newslist")
    public List<News> newsList;
}
