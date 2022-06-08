package com.example.textualreaddemo.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.textualreaddemo.R;

/**
 * 这里编写新闻简讯列表主页
 * 最后更改时间：2022-6-8 14：01
 * @author houdeng
 */
public class NewsListPageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list_page,container,false);

        return view;
    }

}
