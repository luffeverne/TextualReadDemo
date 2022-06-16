package com.example.textualreaddemo;

import com.example.textualreaddemo.beanRetrofit.NewsDetail;
import com.example.textualreaddemo.networkRetrofit.NewsUtility;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NetDataToBean {
    @Test
    public void a() {
        System.out.println(NewsUtility.getNewsTypes().getData());
    }
    @Test
    public void b() {
        System.out.println(NewsUtility.getNewsList().getData());
    }
    @Test
    public void c() {
        System.out.println(NewsUtility.getNewsDetail("EJA5MJQ30001875N").getData().getImages().size());
        List<NewsDetail.Data.Images> images = NewsUtility.getNewsDetail("EJA5MJQ30001875N").getData().getImages();
        List<String> imgSrcs = new ArrayList<>();
        List<String> imgSrcPosition = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            imgSrcs.add(images.get(i).imgSrc);
            imgSrcPosition.add(images.get(i).position);
        }
        System.out.println(imgSrcs);
        System.out.println(imgSrcPosition);
    }

}
