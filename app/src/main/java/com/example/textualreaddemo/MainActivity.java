package com.example.textualreaddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.NewsListBean;
import com.example.textualreaddemo.homepage.MainViewAdapter;
import com.example.textualreaddemo.homepage.MyPageFragment;
import com.example.textualreaddemo.homepage.NewsListPageFragment;
import com.example.textualreaddemo.homepage.SearchPageFragment;
import com.example.textualreaddemo.network.HttpUtil;
import com.example.textualreaddemo.network.NewsDataUtility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{

    private ViewPager2 mainView;
    private MainViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainView = findViewById(R.id.main_view_container);

        //给viewpager2控件设置适配器
        adapter = new MainViewAdapter(this);
        mainView.setAdapter(adapter);
        adapter.addFragment(new MyPageFragment());
        adapter.addFragment(new NewsListPageFragment());
        adapter.addFragment(new SearchPageFragment());
        mainView.setCurrentItem(1);

    }

}