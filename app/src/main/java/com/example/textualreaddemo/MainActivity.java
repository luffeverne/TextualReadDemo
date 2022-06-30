package com.example.textualreaddemo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.textualreaddemo.homepage.INewsListView;
import com.example.textualreaddemo.homepage.MainViewAdapter;
import com.example.textualreaddemo.homepage.NewsListPageFragment;
import com.example.textualreaddemo.homepage.model.CallBack;
import com.example.textualreaddemo.homepage.mypage.MyPageFragment;
import com.example.textualreaddemo.homepage.presenter.NewsListViewPresenter;
import com.example.textualreaddemo.homepage.searchpage.SearchPageFragment;
import com.example.textualreaddemo.service.UpdateNewsService;

/**
 * 主页登录跳转
 * 最后更改时间：2022-6-20 9:10
 * author: houdeng
 */

public class MainActivity extends AppCompatActivity implements INewsListView {

    private ViewPager2 mainView;
    private MainViewAdapter adapter;
    private NewsListViewPresenter newsListViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //取消状态栏
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainView = findViewById(R.id.main_view_container);

        newsListViewPresenter = new NewsListViewPresenter(this);
        newsListViewPresenter.getNewsListData(this);

        // 给viewpager2控件设置适配器
        setViewPager2Adapter();

        //启动服务
        Intent intent = new Intent(this, UpdateNewsService.class);
        startService(intent);
    }

    private void setViewPager2Adapter() {

        adapter = new MainViewAdapter(this);
        mainView.setAdapter(adapter);
        adapter.addFragment(new SearchPageFragment());
        adapter.addFragment(new NewsListPageFragment());
        adapter.addFragment(new MyPageFragment());
        mainView.setCurrentItem(1);

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void getDataSuccess() {

    }
}