package com.example.textualreaddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

/**
 * 主页登录跳转
 * 最后更改时间：2022-6-8 22:00
 * @author luffe
 */

public class MainActivity extends AppCompatActivity{

    private ViewPager2 mainView;
    private MainViewAdapter adapter;

    private TextView login;
    private TextView register;
    private TextView forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // 找控件
        initView();
        
        // 设置点击事件
        initClickEvent();

    }

    private void initClickEvent() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了登录按钮");
                // 跳转到登陆界面
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了新用户注册");
                // 跳转到新用户注册界面
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了忘记密码");
                // 跳转到重置密码界面
            }
        });

    }

    private void initView() {
        TextView login = this.findViewById(R.id.btn_login);
        TextView register = this.findViewById(R.id.btn_register);
        TextView forgetPassword = this.findViewById(R.id.btn_forgetPassword);
    }

}