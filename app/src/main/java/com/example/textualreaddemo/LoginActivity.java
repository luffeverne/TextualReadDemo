package com.example.textualreaddemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
/**
 * 主页登录跳转
 * 最后更改时间：2022-6-11 17:00
 * @author luffe
 */
public class LoginActivity extends AppCompatActivity {

    private TextView mButtonLogin;
    private TextView mButtonRegister;
    private TextView mButtonForgetPassword;

    private TextView mEditUserName;
    private TextView mEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 点击 登录 按钮
        mButtonLogin = this.findViewById(R.id.btn_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了登录按钮");

                mEditUserName = findViewById(R.id.text_username);
                mEditPassword = findViewById(R.id.text_password);

                // 账户密码匹配
                if ("testName".equals(mEditUserName.getText().toString())
                        && "123".equals(mEditPassword.getText().toString())){
                    // 登录成功, 跳转到新闻卡片布局主页
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    onDestroy(); // 销毁当前登录页
                } else {
                    Toast.makeText(LoginActivity.this, "账号或者密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 点击 新用户注册 按钮
        mButtonRegister = this.findViewById(R.id.btn_register);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了新用户注册");
                // 跳转到新用户注册界面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                onDestroy();
            }
        });

        // 点击 忘记密码 按钮
        mButtonForgetPassword = this.findViewById(R.id.btn_forgetPassword);
        mButtonForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了忘记密码");
                // 跳转到重置密码界面
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//
//    // 接受返回来的账号
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    EditText mEditUserName = findViewById(R.id.text_username);
//                    mEditUserName.setText();
//                }
//        }
//    }
}