package com.example.textualreaddemo.basebean.newsdata;

import java.util.List;

/**
 * 这个类表示每一条新闻详情页所有数据
 * 更改时间：2022-6-7
 * @author houdeng
 */
public class NewsDetailsData {

    //新闻详情图片列表
    private List<NewsDetailImages> images;
    //新闻详情标题
    private String title;
    //新闻详情内容
    private String content;
    //新闻来源
    private String source;
    //新闻发布时间
    private NewsDetailsData ptime;
    //新闻唯一id
    private String docid;
    //新闻封面图片
    private String cover;

    public void setImages(List<NewsDetailImages> images) {
        this.images = images;
    }
    public List<NewsDetailImages> getImages() {
        return images;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }

    public void setPtime(NewsDetailsData ptime) {
        this.ptime = ptime;
    }
    public NewsDetailsData getPtime() {
        return ptime;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
    public String getDocid() {
        return docid;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    public String getCover() {
        return cover;
    }

}