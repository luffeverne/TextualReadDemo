package com.example.textualreaddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * 主页登录跳转
 * 最后更改时间：2022-6-8 22:00
 * @author luffe
 */
public class LoginActivity extends AppCompatActivity {

    private TextView login;
    private TextView register;
    private TextView forgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        login = this.findViewById(R.id.btn_login);
        register = this.findViewById(R.id.btn_register);
        forgetPassword = this.findViewById(R.id.btn_forgetPassword);
    }
}