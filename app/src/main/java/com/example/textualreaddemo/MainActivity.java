package com.example.textualreaddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.NewsListBean;
import com.example.textualreaddemo.network.HttpUtil;
import com.example.textualreaddemo.network.NewsDataUtility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest = findViewById(R.id.test1);
        btnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String url = "https://www.mxnzp.com/api/news/list?typeId=509&page=1&app_id=hmrdfmfmsxjysxkj&app_secret=M0lwY0daVVB2Wi9MamljMDIybTh1UT09";

        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String temp = response.body().string();
                NewsListBean newsListBean = NewsDataUtility.handleNewsListResponse(temp);
                int a= 0;
            }
        });
    }
}