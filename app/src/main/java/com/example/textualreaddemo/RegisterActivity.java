package com.example.textualreaddemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textualreaddemo.data.UserData;


/**
 * 注册界面
 * 最后更改时间：2022-6-11 17:00
 * author: luffe
 */


public class RegisterActivity extends AppCompatActivity {
    private TextView mButtonRegister;
    private TextView mButtonCancel;
    private EditText mEditUsername;
    private EditText mEditPassword;
    private EditText mEditConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 调用 layout 里面的 activity_register.xml
        setContentView(R.layout.activity_register);

        // 设置点击事件
        initClickEvent();
    }

    private void initClickEvent() {
        // 点击 注册 按钮
        mButtonRegister = this.findViewById(R.id.btn_register);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取账号名、密码、确认密码
                mEditUsername = findViewById(R.id.text_username);
                mEditPassword = findViewById(R.id.text_password);
                mEditConfirmPassword = findViewById(R.id.text_confirmPassword);

                String username = mEditUsername.getText().toString();
                String password = mEditPassword.getText().toString();
                String confirmPassword = mEditConfirmPassword.getText().toString();
                UserData user = new UserData();

                if (password.equals(confirmPassword)) {
                    // 存储账户密码
                    user.setUserName(username);
                    user.setPassword(password);

                    // 传回账号
                    Intent intent = new Intent();
                    intent.putExtra("mEditUsername", username);
                    setResult(RESULT_OK, intent);

                    // 返回登录页
                    Intent loginPage = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(loginPage);
                    onDestroy();

                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this, "两次密码不一样", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 点击 取消 按钮
        mButtonCancel = this.findViewById(R.id.btn_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回登录页
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                onDestroy();
            }
        });

    }
}
