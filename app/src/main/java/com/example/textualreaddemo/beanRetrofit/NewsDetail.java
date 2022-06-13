package com.example.textualreaddemo.beanRetrofit;

import java.util.List;

/**
 * 新闻详情数据相关类
 */
public class NewsDetail {
    public String code;
    public String msg;
    public Data data;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return "NewsDetail{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        public List<Images> images;
        public String title;
        public String content;
        public String source;
        public String ptime;
        public String docid;
        public String cover;

        public List<Images> getImages() {
            return images;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getSource() {
            return source;
        }

        public String getPtime() {
            return ptime;
        }

        public String getDocid() {
            return docid;
        }

        public String getCover() {
            return cover;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "images=" + images +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", source='" + source + '\'' +
                    ", ptime='" + ptime + '\'' +
                    ", docid='" + docid + '\'' +
                    ", cover='" + cover + '\'' +
                    '}';
        }

        public static class Images {
            public String position;
            public String imgSrc;
            public String size;

            @Override
            public String toString() {
                return "Images{" +
                        "position='" + position + '\'' +
                        ", imgSrc='" + imgSrc + '\'' +
                        ", size='" + size + '\'' +
                        '}';
            }
        }
    }
}
