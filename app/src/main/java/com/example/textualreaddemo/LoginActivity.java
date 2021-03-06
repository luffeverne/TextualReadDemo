package com.example.textualreaddemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textualreaddemo.data.DBOpenHelper;
import com.example.textualreaddemo.data.User;
import com.example.textualreaddemo.room.manager.DBEngine;

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
    private EditText mEditPhoneNUmber;
    private EditText mEditUserName;
    private EditText mEditPassword;

    //private DBOpenHelper mDBOpenHelper;
    DBEngine dbEngine;
    private CheckBox mCheckBoxRememberPass;
    boolean isRemember;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;  // editor将数据写入SharedPreferences

    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myApplication = (MyApplication) this.getApplication();

        initView();

        //mDBOpenHelper = new DBOpenHelper(this);
        dbEngine= new DBEngine(this);
    }

    private void initView() {
        mButtonLogin = this.findViewById(R.id.btn_login);
        mButtonRegister = this.findViewById(R.id.btn_register);
        mButtonForgetPassword = this.findViewById(R.id.btn_forgetPassword);
        mEditPhoneNUmber = findViewById(R.id.text_userPhoneNumber);
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
            String phoneNumber = pref.getString("phoneNumber","");
            String name = pref.getString("name","");
            String password = pref.getString("password", "");

            mEditPhoneNUmber.setText(phoneNumber);
            mEditUserName.setText(name);
            mEditPassword.setText(password);
            mCheckBoxRememberPass.setChecked(true);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            //  跳转到注册界面
            /*
            kun:
            这里我先跳转到输入手机号界面先
             */
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.btn_forgetPassword:
                startActivity(new Intent(this, ResetPassword.class));
                finish();
                break;
            case R.id.btn_login:
                Log.e("lance", "onClick: " + dbEngine.getAllUsers());
                String strPhoneNUmber = mEditPhoneNUmber.getText().toString().trim();
                String strName = mEditUserName.getText().toString().trim();
                String strPassword = mEditPassword.getText().toString().trim();

                // 账户登录验证
                if (strPhoneNUmber.matches("\\d{11}") && !TextUtils.isEmpty(strName) && !TextUtils.isEmpty(strPassword)) {
//                    for (int i = 0; i < data.size(); i++) {
//                        User user = data.get(i);
//                        if (strName.equals(user.getName()) && strPassword.equals(user.getPassword())) {
//                            match = true;
//                            break;
//                        }
//                    }
                    if (dbEngine.getUserByUserID(strPhoneNUmber) != null) {
                        editor = pref.edit();
                        if (mCheckBoxRememberPass.isChecked()) { //如果勾选了 checkbox 框，则将 name，password，checkbox信息写入
                            editor.putBoolean("remember_password", true);
                            editor.putString("phoneNumber",strPhoneNUmber);
                            editor.putString("name", strName);
                            editor.putString("password", strPassword);
                        } else {
                            editor.clear();  //若没有，清除SharedPreferences存储的信息
                        }
                        editor.apply();

                        //为项目全局变量 User 赋值;
                        myApplication.setUser(dbEngine.getUserByUserID(strPhoneNUmber));

                        // 登录成功, 跳转到新闻卡片布局主页
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish(); // 销毁此 Activity
                    } else {
                        Toast.makeText(LoginActivity.this, "手机号或账号或者密码错误！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入手机号和账户密码不正确或为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}