package com.example.textualreaddemo.basebean.newsdata;

import java.util.List;
import java.util.Date;

/**
 * 这个类表示每一条新闻简讯数据
 * 更改时间：2022-6-7
 * @author houdeng
 * title	字符串	新闻标题
 * imgList	字符串	新闻描述图片列表
 * source	字符串	新闻来源
 * newsId	字符串	新闻唯一id，后面查询新闻详情需要
 * digest	字符串	新闻摘要
 * postTime	字符串	新闻发布时间
 * videoList	字符串	新闻视频列表
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
