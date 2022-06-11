package com.example.textualreaddemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 调用 layout 里面的 register.xml
        setContentView(R.layout.register);
    }
}
