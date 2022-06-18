package com.example.textualreaddemo.homepage.mypage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.textualreaddemo.R;

public class MyPageFragment extends Fragment{

    private ImageView headphoto;
    private TextView edit;
    private TextView historyNews;
    private TextView collectionNews;
    private TextView concernedAuthor;
    private TextView logout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_page,container,false);
        findAllView(rootView);
        setListener();
        return rootView;
    }

    private void setListener(){
        OnClick onClick = new OnClick();
        historyNews.setOnClickListener(onClick);
        headphoto.setOnClickListener(onClick);
        edit.setOnClickListener(onClick);
        logout.setOnClickListener(onClick);
        concernedAuthor.setOnClickListener(onClick);
        collectionNews.setOnClickListener(onClick);
    }

    class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.history_news:
                    Toast.makeText(getContext(), "查看历史记录", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.my_head_photo:
                    Toast.makeText(getContext(), "这是头像", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.log_out:
                    Toast.makeText(getContext(), "退出登录", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.my_edit_information:
                    Toast.makeText(getContext(), "编辑内容", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.concerned_author:
                    Toast.makeText(getContext(), "关注的人", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.collection_news:
                    Toast.makeText(getContext(), "查看收藏新闻", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }
    }

    private void findAllView(View inflate) {
        historyNews = inflate.findViewById(R.id.history_news);
        headphoto = inflate.findViewById(R.id.my_head_photo);
        edit = inflate.findViewById(R.id.my_edit_information);
        collectionNews =inflate.findViewById(R.id.collection_news);
        concernedAuthor = inflate.findViewById(R.id.concerned_author);
        logout = inflate.findViewById(R.id.log_out);
    }

}
