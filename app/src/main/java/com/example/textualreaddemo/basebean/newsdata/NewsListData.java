package com.example.textualreaddemo.basebean.newsdata;

import java.util.List;
import java.util.Date;

/**
 * 这个类表示每一条新闻简讯数据
 * 更改时间：2022-6-7
 * @author houdeng
 */
public class NewsListData {

    private String title;
    private List<String> imgList;
    private String source;
    private String newsId;
    private String digest;
    private String postTime;
    private String videoList;

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
    public List<String> getImgList() {
        return imgList;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
    public String getNewsId() {
        return newsId;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
    public String getDigest() {
        return digest;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
    public String getPostTime() {
        return postTime;
    }

    public void setVideoList(String videoList) {
        this.videoList = videoList;
    }
    public String getVideoList() {
        return videoList;
    }

}
