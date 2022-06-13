package com.example.textualreaddemo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.textualreaddemo.homepage.MainViewAdapter;
import com.example.textualreaddemo.homepage.MyPageFragment;
import com.example.textualreaddemo.homepage.NewsListPageFragment;
import com.example.textualreaddemo.homepage.searchpage.SearchPageFragment;

/**
 * 主页登录跳转
 * 最后更改时间：2022-6-10 13:58
 * author: houdeng
 */

public class MainActivity extends AppCompatActivity{

    private ViewPager2 mainView;
    private MainViewAdapter adapter;

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

        // 给viewpager2控件设置适配器
        adapter = new MainViewAdapter(this);
        mainView.setAdapter(adapter);
        adapter.addFragment(new MyPageFragment());
        adapter.addFragment(new NewsListPageFragment());
        adapter.addFragment(new SearchPageFragment());
        mainView.setCurrentItem(1);
    }

}