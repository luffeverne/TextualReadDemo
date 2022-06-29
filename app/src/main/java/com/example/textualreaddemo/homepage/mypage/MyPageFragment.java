package com.example.textualreaddemo.homepage.mypage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.textualreaddemo.DetailActivity;
import com.example.textualreaddemo.LoginActivity;
import com.example.textualreaddemo.MyApplication;
import com.example.textualreaddemo.R;
import com.example.textualreaddemo.room.CollectedNews;
import com.example.textualreaddemo.room.HistoryNews;
import com.example.textualreaddemo.room.User;
import com.example.textualreaddemo.room.manager.DBEngine;

import java.util.ArrayList;
import java.util.List;

public class MyPageFragment extends Fragment implements View.OnClickListener{


    TextView my_realName,personal_introduction,attention_author,collection_news,history_news,current_tv,switch_account,log_out;
    ImageView my_head_photo;
    MyApplication myApplication;
    User user;

    RecyclerView recyclerView;
    MyPageRecyclerViewAdapter adapter;
    DBEngine dbEngine;

    List<CollectedNews> collectedNews = new ArrayList<>();
    List<HistoryNews> historyNews = new ArrayList<>();
    List<String> itemList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
        user = myApplication.getUser();

        dbEngine = new DBEngine(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_page,container,false);
        my_head_photo = rootView.findViewById(R.id.my_head_photo);
        my_realName = rootView.findViewById(R.id.my_realName);
        personal_introduction = rootView.findViewById(R.id.personal_introduction);
        if (user.getHeadshot() != null) {
            Glide.with(this).load(user.getHeadshot()).into(my_head_photo);
        }
        my_realName.setText(user.getName());
        if (user.getPersonalData() != null) {
            personal_introduction.setText(user.getPersonalData());
        }

        attention_author = rootView.findViewById(R.id.attention_author);
        attention_author.setOnClickListener(this);
        collection_news = rootView.findViewById(R.id.collection_news);
        collection_news.setOnClickListener(this);
        history_news = rootView.findViewById(R.id.history_news);
        history_news.setOnClickListener(this);
        current_tv = collection_news;

        recyclerView = rootView.findViewById(R.id.rv_myPage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyPageRecyclerViewAdapter(getContext());

        switch_account = rootView.findViewById(R.id.switch_account);
        switch_account.setOnClickListener(this);
        log_out = rootView.findViewById(R.id.log_out);
        log_out.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (current_tv == collection_news) {
            loadCollectedNews();
        }
        if (current_tv == history_news) {
            loadHistoryNews();
        }
        if (current_tv == attention_author) {
            loadAttentionAuthor();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.attention_author:
                current_tv.setBackgroundResource(R.color.transparent);
                current_tv = attention_author;
                current_tv.setBackgroundResource(R.color.teal_200);
                loadAttentionAuthor();
                break;
            case R.id.collection_news:
                current_tv.setBackgroundResource(R.color.transparent);
                current_tv = collection_news;
                current_tv.setBackgroundResource(R.color.teal_200);
                loadCollectedNews();
                break;
            case R.id.history_news:
                current_tv.setBackgroundResource(R.color.transparent);
                current_tv = history_news;
                current_tv.setBackgroundResource(R.color.teal_200);
                loadHistoryNews();
                break;
            case R.id.switch_account:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.log_out:
                AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                        .setTitle("是否退出资讯阅读APP")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getActivity(), "已经取消退出", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alertDialog2.show();
                break;
            default:
                break;
        }
    }

    private void loadAttentionAuthor() {
//        historyNews = dbEngine.getAllHistoryNews();
        itemList.clear();
//        for (HistoryNews h:
//                historyNews) {
//            newsItemList.add(h.getNewsName());
//        }
        itemList.add("testAuthor1");
        itemList.add("testAuthor2");
        itemList.add("testAuthor3");
        adapter.setData(itemList);
        //adapter.notifyDataSetChanged();
        adapter.setOnRecyclerItemClickListen(new MyPageRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnRecyclerItemClick(int position) {
//                Log.e("lance", "OnRecyclerItemClick: " + historyNews.get(position).getNewsID());
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                intent.putExtra("newsId",historyNews.get(position).getNewsID());
//                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadHistoryNews() {
        historyNews = dbEngine.getAllHistoryNews();
        itemList.clear();
        for (HistoryNews h:
                historyNews) {
            itemList.add(h.getNewsName());
        }
        adapter.setData(itemList);
        //adapter.notifyDataSetChanged();
        adapter.setOnRecyclerItemClickListen(new MyPageRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnRecyclerItemClick(int position) {
                Log.e("lance", "OnRecyclerItemClick: " + historyNews.get(position).getNewsID());
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("newsId",historyNews.get(position).getNewsID());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadCollectedNews() {
        collectedNews = dbEngine.getAllCollectedNews();
        itemList.clear();
        for (CollectedNews c:
                collectedNews) {
            itemList.add(c.getNewsName());
        }
        adapter.setData(itemList);
        //adapter.notifyDataSetChanged();
        adapter.setOnRecyclerItemClickListen(new MyPageRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnRecyclerItemClick(int position) {
                Log.e("lance", "OnRecyclerItemClick: " + collectedNews.get(position).getNewsID());
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("newsId",collectedNews.get(position).getNewsID());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
