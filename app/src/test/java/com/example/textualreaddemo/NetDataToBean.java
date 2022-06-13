package com.example.textualreaddemo;

import com.example.textualreaddemo.networkRetrofit.NewsUtility;

import org.junit.Test;

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
        System.out.println(NewsUtility.getNewsDetail("EJA5MJQ30001875N").getData());
    }

}
