package com.example.textualreaddemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.textualreaddemo.homepage.MainViewAdapter;
import com.example.textualreaddemo.homepage.MyPageFragment;
import com.example.textualreaddemo.homepage.NewsListPageFragment;
import com.example.textualreaddemo.homepage.SearchPageFragment;

/**
 * 主页登录跳转
 * 最后更改时间：2022-6-10 13:58
 */

public class MainActivity extends AppCompatActivity{

    private ViewPager2 mainView;
    private MainViewAdapter adapter;

    private TextView mButtonLogin;
    private TextView mButtonRegister;
    private TextView mButtonForgetPassword;

    private TextView mEditUserName;

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

        // 找控件
        initView();
        
        // 设置点击事件
        initClickEvent();

    }

    private void initClickEvent() {
//        mEditUserName = findViewById(R.id.username);


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mEditUserName.getText());
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了新用户注册");
                // 跳转到新用户注册界面
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        mButtonForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了忘记密码");
                // 跳转到重置密码界面
            }
        });

    }

    private void initView() {
        mButtonLogin = this.findViewById(R.id.btn_login);
        mButtonRegister = this.findViewById(R.id.btn_register);
        mButtonForgetPassword = this.findViewById(R.id.btn_forgetPassword);
        mEditUserName = this.findViewById(R.id.username);

    }

}