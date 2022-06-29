package com.example.textualreaddemo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.textualreaddemo.beanRetrofit.NewsDetail;
import com.example.textualreaddemo.beanRetrofit.NewsList;
import com.example.textualreaddemo.beanRetrofit.NewsTypes;
import com.example.textualreaddemo.detailpage.DetailContentFragment;
import com.example.textualreaddemo.detailpage.DetailContentFragmentAdapter;
import com.example.textualreaddemo.detailpage.IFragmentCallback;
import com.example.textualreaddemo.networkRetrofit.NewsUtility;
import com.example.textualreaddemo.room.CollectedNews;
import com.example.textualreaddemo.room.manager.DBEngine;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kotlin.jvm.internal.Intrinsics;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener{


    int currentFragmentPosition;

    MyApplication myApplication;
    List<NewsDetail.Data> news = new ArrayList<>();
    //合格新闻id长度,VQ83OGT2N,S1655357985168(不合格），H9U4JBKA0552NSSC,EJA5MJQ30001875N（合格）
    //final static int RIGHT_DETAIL_NEWS_LENGTH = 15;
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
    //DetailContentFragment currentFragment;
    //接收 NewsListPageFragment 传过来的新闻详情id
    String newsIDFromOtherActivity;
    Boolean isLove = false;

    DBEngine dbEngine;

    EditText et_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //接收 NewsListPageFragment 传过来的新闻详情id
        Intent intent = getIntent();
        newsIDFromOtherActivity = intent.getStringExtra("newsId");
        Log.e("lance", "onCreate: " + newsIDFromOtherActivity);

        dbEngine = new DBEngine(this);

        myApplication = (MyApplication) getApplication();

        for (int i = 0; i < 20; i++) {
            news.addAll(getNews());
            if (news.size() >=6) {
                break;
            }
        }
        myApplication.setNews(news);

        initView();

        loadFragments();
    }


    private void initView() {

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

    private List<NewsDetail.Data> getNews() {
        List<String> newsIDs = new ArrayList<>();

        //新闻详情id列表

        for (int i = 0; i < 10; i++) {
            newsIDs.addAll(getNewsIDs());
            if (newsIDs.size() == 0)continue;
            if (!newsIDs.get(0).equals("此类型无详情id") && newsIDs.size() >= 6) {
                break;
            } else {
                newsIDs.clear();
            }
        }

        if (newsIDFromOtherActivity != null) {
            if (!newsIDFromOtherActivity.equals(newsIDs.get(0))) {
                newsIDs.add(0,newsIDFromOtherActivity);
            }
            newsIDFromOtherActivity = null;
        } else {
            Toast.makeText(DetailActivity.this,"其他 Activity 传来的 newsID 有问题或刷新",Toast.LENGTH_LONG).show();
        }


        List<NewsDetail.Data> news = new ArrayList<>();
        for (int i = 0; i < newsIDs.size(); i++) {
            NewsDetail newsDetail = null;
            for (int j = 0; j < 20; j++) {
                newsDetail = NewsUtility.getNewsDetail(newsIDs.get(i));
                if (newsDetail != null) break;
            }
            if (newsDetail == null) {
                Toast.makeText(this,"新闻为空，服务器异常",Toast.LENGTH_LONG).show();
            } else {
                if (newsDetail.getData() == null) {
                    Toast.makeText(this,"新闻 Data 为空，服务器异常",Toast.LENGTH_LONG).show();
                } else {
                    if (!newsDetail.getData().getTitle().equals("") && !newsDetail.getData().getContent().equals("") && !newsDetail.getData().getSource().equals("") && !newsDetail.getData().getPtime().equals("") && !newsDetail.getData().getDocid().equals("")) {
                        news.add(newsDetail.getData());
                    }
                }
            }
        }
        Log.e("lance", "getNews: " + news.size() );
        return news;
    }

    private void loadFragments() {
        //单例模式创建对应数量的 DetailContentFragment ,getNewsDetailDataID(): 获取合格的随机新闻id列表
        for (int i = 0; i < news.size(); i++) {
            detailContentFragmentList.add(DetailContentFragment.newInstance(String.valueOf(i)));
        }

        adapter = new DetailContentFragmentAdapter(DetailActivity.this);
        adapter.setData(detailContentFragmentList);
        viewPager2.setAdapter(adapter);

        //设置 viewPager2 页面监听 DetailContentFragment
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                currentFragmentPosition = position;

                if (dbEngine.getCollectedNewsByNewsID(news.get(position).getDocid()) != null)
                    btn_isCollected.setImageResource(R.drawable.ic_baseline_star_24);
                else
                    btn_isCollected.setImageResource(R.drawable.ic_baseline_star_border_24);
            }
        });
    }

    private List<String> getNewsIDs() {
        //获取新闻详情id
        NewsTypes newsTypeData = null;
        for (int i = 0; i < 20; i++) {
            newsTypeData = NewsUtility.getNewsTypes();
            if (newsTypeData != null) break;
        }

        if (newsTypeData == null) {
            Toast.makeText(DetailActivity.this,"新闻类型数据为空，服务器异常",Toast.LENGTH_LONG).show();
        } else {
            if (newsTypeData.getData() == null) {
                Toast.makeText(DetailActivity.this,"新闻类型数据 Data 为空，服务器异常",Toast.LENGTH_LONG).show();
            } else {
                if (newsTypeData.getData().size() == 0) {
                    Toast.makeText(DetailActivity.this,"新闻类型数据 Data 长度为0，服务器异常",Toast.LENGTH_LONG).show();
                } else {
                    List<String> newsTypeIDs = new ArrayList<>();
                    for (int i = 0; i < newsTypeData.getData().size(); i++) {
                        newsTypeIDs.add(newsTypeData.getData().get(i).typeId);
                    }
                    String newsListDataIDRandom;
                    Random random = new Random();
                    newsListDataIDRandom = newsTypeIDs.get(random.nextInt(newsTypeIDs.size()));

                    //非具体新闻列表
                    NewsList newsList = null;
                    for (int i = 0; i < 20; i++) {
                        newsList = NewsUtility.getNewsList(newsListDataIDRandom);
                        if (newsList != null) break;
                    }
                    if (newsList == null) {
                        Toast.makeText(DetailActivity.this,"列表新闻为空，服务器异常",Toast.LENGTH_LONG).show();
                    } else {
                        if (newsList.getData() == null) {
                            Toast.makeText(DetailActivity.this,"列表新闻 Data 为空，服务器异常",Toast.LENGTH_LONG).show();
                        } else {
                            if (newsList.getData().size() == 0) {
                                Toast.makeText(DetailActivity.this,"列表新闻 Data 长度为0，服务器异常",Toast.LENGTH_LONG).show();
                            } else {
                                //新闻详情id列表
                                List<String> newsIDs = new ArrayList<>();
                                for (int i = 0; i < newsList.getData().size(); i++) {
                                    newsIDs.add(newsList.getData().get(i).newsId);
                                }
                                return newsIDs;
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refresh:
                news.clear();
                for (int i = 0; i < 20; i++) {
                    news.addAll(getNews());
                    if (news.size() >=6) {
                        break;
                    }
                }
                myApplication.setNews(news);
                detailContentFragmentList.clear();
                loadFragments();
                break;
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_up:
                viewPager2.setCurrentItem(currentFragmentPosition - 1);
                if (currentFragmentPosition == 0) {
                    Toast.makeText(this,"first",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_down:
                viewPager2.setCurrentItem(currentFragmentPosition + 1);
                if (currentFragmentPosition == detailContentFragmentList.size() - 1) {
                    Toast.makeText(this,"last",Toast.LENGTH_LONG).show();
                }
            case R.id.btn_isLove:
            {
                if (!isLove) {
                    btn_isLove.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
                if (isLove) {
                    btn_isLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
                isLove = !isLove;
                break;
            }
            case R.id.btn_isCollected:
            {
                String detailNewsID = news.get(currentFragmentPosition).getDocid();
                if (dbEngine.getCollectedNewsByNewsID(detailNewsID) == null) {
                    NewsDetail newsDetail = null;
                    for (int i = 0; i < 20; i++) {
                        newsDetail = NewsUtility.getNewsDetail(detailNewsID);
                        if (newsDetail != null ) {
                            CollectedNews collectedNews = new CollectedNews(detailNewsID);
                            collectedNews.setNewsName(newsDetail.getData().getTitle());
                            dbEngine.insertCollectedNews(collectedNews);
                            btn_isCollected.setImageResource(R.drawable.ic_baseline_star_24);
                            break;
                        }
                    }
                } else {
                    dbEngine.deleteCollectedNewsByNewsID(detailNewsID);
                    btn_isCollected.setImageResource(R.drawable.ic_baseline_star_border_24);
                }
                break;
            }
            case R.id.btn_comments:
                Toast.makeText(this,"comments",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
//        viewPager2.setUserInputEnabled(true);
//        btn_up.setVisibility(View.VISIBLE);
//        btn_down.setVisibility(View.VISIBLE);
//        btns_bottom_detail.setVisibility(View.VISIBLE);
//        viewPager2.setUserInputEnabled(false);
//        btn_up.setVisibility(View.GONE);
//        btn_down.setVisibility(View.GONE);
//        btns_bottom_detail.setVisibility(View.GONE);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (myApplication.getCanDropLoad()) {
            int position = currentFragmentPosition;
            Log.e("lance", "dispatchTouchEvent: " + myApplication.getCanDropLoad());
            for (int i = 0; i < 20; i++) {
                news.addAll(getNews());
                if (news.size() >=6) {
                    break;
                }
            }
            myApplication.setNews(news);
            detailContentFragmentList.clear();
            loadFragments();
            viewPager2.setCurrentItem(position + 1);
        }
        myApplication.setCanDropLoad(false);
        return super.dispatchTouchEvent(ev);
    }
}

