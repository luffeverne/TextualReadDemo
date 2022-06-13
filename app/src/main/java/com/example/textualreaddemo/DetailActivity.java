package com.example.textualreaddemo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.textualreaddemo.beanRetrofit.NewsDetail;
import com.example.textualreaddemo.beanRetrofit.NewsList;
import com.example.textualreaddemo.detailpage.DetailContentFragment;
import com.example.textualreaddemo.detailpage.DetailContentFragmentAdapter;
import com.example.textualreaddemo.detailpage.IFragmentCallback;
import com.example.textualreaddemo.networkRetrofit.NewsUtility;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    List<DetailContentFragment> detailContentFragmentList = new ArrayList<>();
    DetailContentFragmentAdapter adapter;
    LinearLayout btns_bottom_detail;
    ImageButton btn_up,btn_down;

    DetailContentFragment currentFragment;

    List<NewsList.Data> newsListData;
    List<String> newsDetailDataID = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewPager2 = findViewById(R.id.vp_detail);

        btns_bottom_detail = findViewById(R.id.btns_bottom_detail);
        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);
        newsListData = NewsUtility.getNewsList().getData();
        while (newsListData == null) {
            newsListData = NewsUtility.getNewsList().getData();
        }

        for (int i = 0; i < newsListData.size(); i++) {
            newsDetailDataID.add(newsListData.get(i).newsId);
        }

        for (int i = 0; i < newsDetailDataID.size(); i++) {
            detailContentFragmentList.add(DetailContentFragment.newInstance(newsDetailDataID.get(i),"1"));
        }

        adapter = new DetailContentFragmentAdapter(this,detailContentFragmentList);
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentFragment = detailContentFragmentList.get(position);
                currentFragment.setFragmentCallback(new IFragmentCallback() {
                    @Override
                    public void sendMsgToActivity(String msg) {
                        if (msg.equals("scrolling")) {
                            btn_up.setVisibility(View.GONE);
                            btn_down.setVisibility(View.GONE);
                            btns_bottom_detail.setVisibility(View.GONE);
                        }
                        if (msg.equals("stopScrolling")) {
                            btn_up.setVisibility(View.VISIBLE);
                            btn_down.setVisibility(View.VISIBLE);
                            btns_bottom_detail.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public String getMsgFromActivity(String msg) {
                        return null;
                    }
                });
            }
        });



    }



}
