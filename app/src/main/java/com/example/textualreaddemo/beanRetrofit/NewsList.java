package com.example.textualreaddemo.beanRetrofit;

import java.util.List;

public class NewsList {
    //json 数据格式采用String接收
    public String code;
    public String msg;
    public List<Data> data;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data {
        public String title;
        public List<String> imgList;
        public String source;
        public String newsId;
        public String digest;
        public String postTime;
        public String videoList;

        public String getTitle() {
            return title;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public String getSource() {
            return source;
        }

        public String getNewsId() {
            return newsId;
        }

        public String getDigest() {
            return digest;
        }

        public String getPostTime() {
            return postTime;
        }

        public String getVideoList() {
            return videoList;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "title='" + title + '\'' +
                    ", imgList=" + imgList +
                    ", source='" + source + '\'' +
                    ", newsId='" + newsId + '\'' +
                    ", digest='" + digest + '\'' +
                    ", postTime=" + postTime +
                    ", videoList='" + videoList + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsList{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
