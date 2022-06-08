package com.example.textualreaddemo.basebean.newsdata;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 这个类表示每一条新闻简讯数据
 * 更改时间：2022-6-7
 * @author houdeng
 */

public class NewsListBean {

    private int code;
    //确认信息
    private String msg;

    @SerializedName("data")
    private List<NewsListData> data;

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setData(List<NewsListData> data) {
        this.data = data;
    }
    public List<NewsListData> getData() {
        return data;
    }
}
