package com.example.textualreaddemo.basebean.newsdata;

import com.example.textualreaddemo.basebean.concernedpeople.ConcernedPeopleBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个类用来记录新闻信息
 * 更改时间：2022-6-25
 * @author luffe
 */
public class News {

    @SerializedName("ctime")
    public String name;

    public String title;

    public String description;

    public String picUrl;

    public String url;
}
