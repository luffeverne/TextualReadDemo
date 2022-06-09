package com.example.textualreaddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class TestActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        imageView = findViewById(R.id.test_img);
        Glide.with(this)
                .load("http://cms-bucket.ws.126.net/2022/0609/84393547p00rd76a3002jc000s600e3c.png")
                .into(imageView);
    }
}