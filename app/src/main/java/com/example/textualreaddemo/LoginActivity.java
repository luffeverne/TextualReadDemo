package com.example.textualreaddemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textualreaddemo.data.DBOpenHelper;
import com.example.textualreaddemo.data.User;

import java.util.ArrayList;

/**
 * 主页登录跳转
 * 最后更改时间：2022-6-16 17:00
 * @author luffe
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mButtonLogin;
    private TextView mButtonRegister;
    private TextView mButtonForgetPassword;
    private TextView mEditUserName;
    private TextView mEditPassword;
    private DBOpenHelper mDBOpenHelper;
    private CheckBox mCheckBoxRememberPass;
    boolean isRemember;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;  // editor将数据写入SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);
    }

    private void initView() {
        mButtonLogin = this.findViewById(R.id.btn_login);
        mButtonRegister = this.findViewById(R.id.btn_register);
        mButtonForgetPassword = this.findViewById(R.id.btn_forgetPassword);
        mEditUserName = findViewById(R.id.text_username);
        mEditPassword = findViewById(R.id.text_password);
        mCheckBoxRememberPass = findViewById(R.id.checkBox_rememberPassword);

        // 设置点击事件监听器
        mButtonLogin.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);
        mButtonForgetPassword.setOnClickListener(this);

        // 记住密码功能
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        isRemember  = pref.getBoolean("remember_password", false); // 读取上次登陆时存入"remember_password"的信息，没有读取到则默认为false
        if (isRemember) { // 如果读取为true, 则将 name, password, checkbox 的信息写入文本框
            String name = pref.getString("name", "");
            String password = pref.getString("password", "");
            mEditUserName.setText(name);
            mEditPassword.setText(password);
            mCheckBoxRememberPass.setChecked(true);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            //  跳转到注册界面
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.btn_forgetPassword:
                startActivity(new Intent(this, ResetPassword.class));
                finish();
                break;
            case R.id.btn_login:
                String strName = mEditUserName.getText().toString().trim();
                String strPassword = mEditPassword.getText().toString().trim();

                // 账户登录验证
                if (!TextUtils.isEmpty(strName) && !TextUtils.isEmpty(strPassword)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (strName.equals(user.getName()) && strPassword.equals(user.getPassword())) {
                            match = true;
                            break;
                        }
                    }
                    if (match) {
                        editor = pref.edit();
                        if (mCheckBoxRememberPass.isChecked()) { //如果勾选了 checkbox 框，则将 name，password，checkbox信息写入
                            editor.putBoolean("remember_password", true);
                            editor.putString("name", strName);
                            editor.putString("password", strPassword);
                        } else {
                            editor.clear();  //若没有，清除SharedPreferences存储的信息
                        }
                        editor.apply();

                        // 登录成功, 跳转到新闻卡片布局主页
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish(); // 销毁此 Activity
                    } else {
                        Toast.makeText(LoginActivity.this, "账号或者密码错误！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}