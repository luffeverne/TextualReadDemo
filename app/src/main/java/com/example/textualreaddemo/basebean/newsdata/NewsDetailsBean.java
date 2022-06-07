package com.example.textualreaddemo.basebean.newsdata;

/**
 * 这个类表示每一条新闻详情页内所需要的数据
 * 更改时间：2022-6-7
 * @author houdeng
 */
public class NewsDetailsBean {

    private int code;
    private String msg;
    private NewsDetailsData data;

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

    public void setData(NewsDetailsData data) {
        this.data = data;
    }
    public NewsDetailsData getData() {
        return data;
    }

}
