package com.example.textualreaddemo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.textualreaddemo.beanRetrofit.NewsList;
import com.example.textualreaddemo.beanRetrofit.NewsTypes;
import com.example.textualreaddemo.detailpage.DetailContentFragment;
import com.example.textualreaddemo.detailpage.DetailContentFragmentAdapter;
import com.example.textualreaddemo.detailpage.IFragmentCallback;
import com.example.textualreaddemo.networkRetrofit.NewsUtility;
import com.example.textualreaddemo.room.News;
import com.example.textualreaddemo.room.manager.DBEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
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
    ImageButton btn_isLove,btn_isCollected,btn_comments,btn_back,btn_up,btn_down,btn_refresh;
    //记录当前的 DetailContentFragment
    DetailContentFragment currentFragment;
    //接收 NewsListPageFragment 传过来的新闻详情id
    String newsDetailDataFromHomepageID;
    //新闻详情id列表
    List<String> newsDetailDataIDs = new ArrayList<>();
    Boolean isLove = false,isCollected = false;

    DBEngine dbEngine;

    EditText et_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        loadFragments();
    }
    private void initView() {
        //接收 NewsListPageFragment 传过来的新闻详情id
        Intent intent = getIntent();
        newsDetailDataFromHomepageID = intent.getStringExtra("newsId");

        dbEngine = new DBEngine(this);

        viewPager2 = findViewById(R.id.vp_detail);
        btn_back = findViewById(R.id.btn_back);
        btns_bottom_detail = findViewById(R.id.btns_bottom_detail);
        btn_isLove = findViewById(R.id.btn_isLove);
        btn_isCollected = findViewById(R.id.btn_isCollected);
        btn_comments = findViewById(R.id.btn_comments);
        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);
        btn_refresh = findViewById(R.id.refresh);
        btn_isLove.setOnClickListener(this);
        btn_isCollected.setOnClickListener(this);
        btn_comments.setOnClickListener(this);
        btn_refresh.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_up.setOnClickListener(this);
        btn_down.setOnClickListener(this);

        et_detail = findViewById(R.id.et_detail);
        et_detail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Toast.makeText(DetailActivity.this,"评论成功:" + et_detail.getText().toString(),Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void loadFragments() {
        //单例模式创建对应数量的 DetailContentFragment ,getNewsDetailDataID(): 获取合格的随机新闻id列表
        newsDetailDataIDs = getNewsDetailDataID();
        if (newsDetailDataIDs.size() <= 6) {
            newsDetailDataIDs.addAll(getNewsDetailDataID());
        }

        //暂时把数据写死测试和数据库的联系
        newsDetailDataIDs.clear();
        newsDetailDataIDs.add("HAJ5PN2B0526K1KN");
        newsDetailDataIDs.add("HAF1D0TP055229B6");
        newsDetailDataIDs.add("HAG7Q85305159TSH");
        //[, , , HAG7Q85305159TSH, HAJ5PN2B0526K1KN, HAI8A6NT003198EF, HAI5KA5N003198EF, HAFOFEG3003198EF, HAFO2FA5003198EF, HAFNU27B003198EF, HAFNQ9VG003198EF, HAFNEK8I003198EF, HAFM6BE1003198EF, HAIV6V0G0526K1KN]
        detailContentFragmentList.clear();
        for (int i = 0; i < newsDetailDataIDs.size(); i++) {
            detailContentFragmentList.add(DetailContentFragment.newInstance(newsDetailDataIDs.get(i)));
        }

        adapter = new DetailContentFragmentAdapter(DetailActivity.this);
        adapter.setData(detailContentFragmentList);
        viewPager2.setAdapter(adapter);

        //设置 viewPager2 页面监听 DetailContentFragment
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentFragment = detailContentFragmentList.get(position);
                //通信接收当前 DetailContentFragment 发来其的滚动状态，然后对控件是否隐藏进行操作

                News news = dbEngine.getNewsByNewsID(newsDetailDataIDs.get(position));
                if ("ture".equals(news.getIsLike()))
                    btn_isLove.setImageResource(R.drawable.ic_baseline_favorite_24);
                if ("false".equals(news.getIsLike()))
                    btn_isLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                if ("ture".equals(news.getIsCollected()))
                    btn_isCollected.setImageResource(R.drawable.ic_baseline_star_24);
                if ("false".equals(news.getIsCollected()))
                    btn_isCollected.setImageResource(R.drawable.ic_baseline_star_border_24);

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
        if (newsDetailDataFromHomepageID != null) {
            if (!newsDetailDataFromHomepageID.equals(newsListData.get(0).newsId)) {
                newsDetailDataID.add(newsDetailDataFromHomepageID);
                newsDetailDataFromHomepageID = null;
            }
        }
        for (int i = 0; i < newsListData.size(); i++) {
            if (newsListData.get(i).newsId.length() > RIGHT_DETAIL_NEWS_LENGTH)
                newsDetailDataID.add(newsListData.get(i).newsId);
        }
        return newsDetailDataID;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refresh:
                loadFragments();
                break;
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_up:
                viewPager2.setCurrentItem(detailContentFragmentList.indexOf(currentFragment) - 1);
                if (detailContentFragmentList.indexOf(currentFragment) - 1 == -1) {
                    Toast.makeText(this,"first",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_down:
                viewPager2.setCurrentItem(detailContentFragmentList.indexOf(currentFragment) + 1);
                if (detailContentFragmentList.indexOf(currentFragment) + 1 == detailContentFragmentList.size()) {
                    Toast.makeText(this,"last",Toast.LENGTH_LONG).show();
                }
            case R.id.btn_isLove:
            {
                String detailNewsID = currentFragment.getArguments().getString("detailNewsID");
                News news = new News();
                news.setNewsID(detailNewsID);
                if (!isLove) {
                    news.setIsLike("ture");
                    dbEngine.updateNews(news);
                    btn_isLove.setImageResource(R.drawable.ic_baseline_favorite_24);
                    Log.e("lance", "onClick: " + dbEngine.getNewsByNewsID(detailNewsID));
                }
                if (isLove) {
                    news.setIsLike("false");
                    dbEngine.updateNews(news);
                    btn_isLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
                isLove = !isLove;
                break;
            }
            case R.id.btn_isCollected:
            {
                String detailNewsID = currentFragment.getArguments().getString("detailNewsID");
                News news = new News();
                news.setNewsID(detailNewsID);
                if (!isCollected) {
                    news.setIsCollected("ture");
                    dbEngine.updateNews(news);
                    btn_isCollected.setImageResource(R.drawable.ic_baseline_star_24);
                    Log.e("lance", "onClick: " + dbEngine.getNewsByNewsID(detailNewsID));
                }
                if (isCollected) {
                    news.setIsCollected("false");
                    dbEngine.updateNews(news);
                    btn_isCollected.setImageResource(R.drawable.ic_baseline_star_border_24);
                }
                isCollected = !isCollected;
                break;
            }
            case R.id.btn_comments:
                Toast.makeText(this,"comments",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}

