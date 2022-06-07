package com.example.textualreaddemo.basebean.newsdata;

/**
 * 这个类表示每一条新闻详情页内加载图片的信息
 * 更改时间：2022-6-7
 * @author houdeng
 */
public class NewsDetailImages {

    private String position;
    private String imgSrc;
    private String size;
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return position;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    public String getImgSrc() {
        return imgSrc;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public String getSize() {
        return size;
    }

}
