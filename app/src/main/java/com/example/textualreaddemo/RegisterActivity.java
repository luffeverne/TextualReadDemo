package com.example.textualreaddemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.textualreaddemo.data.Code;
import com.example.textualreaddemo.data.DBOpenHelper;


/**
 * 注册界面
 * 最后更改时间：2022-6-14 17:00
 * author: luffe
 */


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String strShowCode;
    private DBOpenHelper mDBOpenHelper;
    private Button mButtonRegister;
    private Button mButtonCancel;
    private EditText mEditUsername;
    private EditText mEditPassword;
    private EditText mEditConfirmPassword;
    private ImageView mIvShowCode;
    private EditText mEditInputCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 调用 layout 里面的 activity_register.xml
        setContentView(R.layout.activity_register);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        // 将验证码用图片的形式显示出来
        mIvShowCode.setImageBitmap(Code.getInstance().createBitmap());
        strShowCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView() {
        // 初始化控件
        mButtonRegister = findViewById(R.id.btn_register);
        mButtonCancel = findViewById(R.id.btn_cancel);
        mIvShowCode = findViewById(R.id.btn_showCode);
        mEditUsername = findViewById(R.id.text_username);
        mEditPassword = findViewById(R.id.text_password);
        mEditConfirmPassword = findViewById(R.id.text_confirmPassword);
        mEditInputCode = findViewById(R.id.text_InputCode);

        // 设置点击事件监听器
        mButtonRegister.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel: // 取消注册，返回登录页
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_showCode: // 更新随机验证码生成
                mIvShowCode.setImageBitmap(Code.getInstance().createBitmap());
                strShowCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.btn_register: // 注册
                // 获取用户输入的用户名、密码
                String username = mEditUsername.getText().toString().trim();
                String password = mEditPassword.getText().toString().trim();
                String confirmPassword = mEditConfirmPassword.getText().toString().trim();
                String inputCode = mEditInputCode.getText().toString().trim();

                // 注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(inputCode)) {
                    if (!(password == confirmPassword)) {
                        // 将用户名和密码加入到数据库中
                        mDBOpenHelper.add(username, confirmPassword);
                        // 注册成功了，进入主页
                        Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "密码输入不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

}
