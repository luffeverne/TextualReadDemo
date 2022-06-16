package com.example.textualreaddemo;

import com.example.textualreaddemo.beanRetrofit.NewsDetail;
import com.example.textualreaddemo.networkRetrofit.NewsUtility;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetDataToBean {
    @Test
    public void a() {
        System.out.println(NewsUtility.getNewsTypes().getData().size());
        for (int i = 0; i < NewsUtility.getNewsTypes().getData().size(); i++) {
            System.out.println(NewsUtility.getNewsTypes().getData().get(i).typeId);
        }
        Random random = new Random();
        for (int i = 0; i < NewsUtility.getNewsTypes().getData().size(); i++) {
            System.out.println(random.nextInt(16));
        }
    }
    @Test
    public void b() {
        System.out.println(NewsUtility.getNewsList("509").getData());
    }
    @Test
    public void c() {
        System.out.println(NewsUtility.getNewsDetail("EJA5MJQ30001875N").getData().getImages().size());
        List<NewsDetail.Data.Images> images = NewsUtility.getNewsDetail("EJA5MJQ30001875N").getData().getImages();
        //List<String> imgSrcs = new ArrayList<>();
        //List<String> imgSrcPosition = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            //imgSrcs.add(images.get(i).imgSrc);
            //imgSrcPosition.add(images.get(i).position);
        }
        //System.out.println(imgSrcs);
        //System.out.println(imgSrcPosition);
    }

}
