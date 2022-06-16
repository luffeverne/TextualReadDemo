package com.example.textualreaddemo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.textualreaddemo.beanRetrofit.NewsList;
import com.example.textualreaddemo.beanRetrofit.NewsTypes;
import com.example.textualreaddemo.detailpage.DetailContentFragment;
import com.example.textualreaddemo.detailpage.DetailContentFragmentAdapter;
import com.example.textualreaddemo.detailpage.IFragmentCallback;
import com.example.textualreaddemo.networkRetrofit.NewsUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DetailActivity extends AppCompatActivity {
    //合格新闻id长度,VQ83OGT2N,S1655357985168(不合格），H9U4JBKA0552NSSC,EJA5MJQ30001875N（合格）
    final static int RIGHT_DETAIL_NEWS_LENGTH = 15;
    //管理新闻详情
    ViewPager2 viewPager2;
    //viewPager2 的 DetailContentFragment
    List<DetailContentFragment> detailContentFragmentList = new ArrayList<>();
    DetailContentFragmentAdapter adapter;
    //新闻详情页面底部控件
    LinearLayout btns_bottom_detail;
    //新闻详情页面控制上一页，下一页的按钮
    ImageButton btn_up,btn_down;
    //记录当前的 DetailContentFragment
    DetailContentFragment currentFragment;
    //接收 NewsListPageFragment 传过来的新闻详情id
    String newsDetailDataFromHomepageID;
    //新闻详情id列表
    List<String> newsDetailDataIDs = new ArrayList<>();
    //是否加载新新闻
    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //接收 NewsListPageFragment 传过来的新闻详情id
        Intent intent = getIntent();
        newsDetailDataFromHomepageID = intent.getStringExtra("newsId");

        viewPager2 = findViewById(R.id.vp_detail);
        btns_bottom_detail = findViewById(R.id.btns_bottom_detail);
        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);

        //单例模式创建对应数量的 DetailContentFragment ,getNewsDetailDataID(): 获取合格的随机新闻id列表
        newsDetailDataIDs = getNewsDetailDataID();
        for (int i = 0; i < newsDetailDataIDs.size(); i++) {
            detailContentFragmentList.add(DetailContentFragment.newInstance(newsDetailDataIDs.get(i),"1"));
        }

        adapter = new DetailContentFragmentAdapter(DetailActivity.this,detailContentFragmentList);
        viewPager2.setAdapter(adapter);

        //设置 viewPager2 页面监听 DetailContentFragment
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentFragment = detailContentFragmentList.get(position);
                //通信接收当前 DetailContentFragment 发来其的滚动状态，然后对控件是否隐藏进行操作
                currentFragment.setFragmentCallback(new IFragmentCallback() {
                    @Override
                    public void sendMsgToActivity(String msg) {
                        if (msg.equals("scrolling")) {
                            viewPager2.setUserInputEnabled(false);
                            btn_up.setVisibility(View.GONE);
                            btn_down.setVisibility(View.GONE);
                            btns_bottom_detail.setVisibility(View.GONE);
                        }
                        if (msg.equals("stopScrolling")) {
                            viewPager2.setUserInputEnabled(true);
                            btn_up.setVisibility(View.VISIBLE);
                            btn_down.setVisibility(View.VISIBLE);
                            btns_bottom_detail.setVisibility(View.VISIBLE);
                        }
                    }
                });
                System.out.println(position);
                System.out.println(detailContentFragmentList.size());
            }
        });
    }

    private List<String> getNewsDetailDataID() {
        //获取新闻详情id
        NewsTypes newsTypeData = null;
        for (int i = 0; i < 10; i++) {
            newsTypeData = NewsUtility.getNewsTypes();
            if (newsTypeData != null) break;
        }
        if (newsTypeData == null)
            Toast.makeText(DetailActivity.this,"新闻类型数据请求出现问题，请刷新",Toast.LENGTH_LONG).show();
        List<String> newsTypeIDs = new ArrayList<>();
        for (int i = 0; i < newsTypeData.getData().size(); i++) {
            newsTypeIDs.add(newsTypeData.getData().get(i).typeId);
        }
        String newsListDataIDRandom;
        Random random = new Random();
        newsListDataIDRandom = newsTypeIDs.get(random.nextInt(16));
        //非具体新闻列表
        List<NewsList.Data> newsListData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            newsListData = NewsUtility.getNewsList(newsListDataIDRandom).getData();
            if (newsListData != null) break;
        }
        if (newsListData == null)
            Toast.makeText(DetailActivity.this,"新闻列表数据请求出现问题，请刷新",Toast.LENGTH_LONG).show();
        //新闻详情id列表
        List<String> newsDetailDataID = new ArrayList<>();
        if (!newsDetailDataFromHomepageID.equals(newsListData.get(0).newsId))
            newsDetailDataID.add(newsDetailDataFromHomepageID);
        for (int i = 0; i < newsListData.size(); i++) {
            if (newsListData.get(i).newsId.length() > RIGHT_DETAIL_NEWS_LENGTH)
                newsDetailDataID.add(newsListData.get(i).newsId);
        }
        return newsDetailDataID;
    }
}

