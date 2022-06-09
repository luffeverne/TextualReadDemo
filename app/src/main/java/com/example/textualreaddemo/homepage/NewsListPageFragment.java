package com.example.textualreaddemo.homepage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.NewsListBean;
import com.example.textualreaddemo.homepage.model.NewsListViewPresenter;
import com.example.textualreaddemo.network.NewsDataUtility;
import com.example.textualreaddemo.util.CardConfig;
import com.example.textualreaddemo.util.SwipeCardCallBack;
import com.example.textualreaddemo.util.SwipeCardLayoutManager;

/**
 * 这里编写新闻简讯列表主页
 * 最后更改时间：2022-6-8 21：30
 * @author houdeng
 */
public class NewsListPageFragment extends Fragment implements INewsListView{

    private NewsListViewPresenter newsListViewPresenter;
    private RecyclerView recyclerView;
    private NewsListRecyclerViewAdapter adapter;
    private ProgressDialog progressDialog;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list_page,container,false);
        recyclerView = view.findViewById(R.id.news_list_recycler_view);

        activity = getActivity();//获取activity对象，充当上下文

        testD();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍后...");

        newsListViewPresenter = new NewsListViewPresenter(this);//创建P层对象

        load();
        return view;
    }

    private void load() {
        newsListViewPresenter.getNewsListData(activity);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void getDataSuccess() {
        Toast.makeText(activity, "加载成功", Toast.LENGTH_SHORT).show();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        String temp = sp.getString("NewsList",null);
        if (temp != null){
            NewsListBean newsListBean = NewsDataUtility.handleNewsListResponse(temp);
            adapter.setData(newsListBean.getData());
        }
    }

    @Override
    public void getDataFailure() {
        Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
    }

    private void testD(){

        SwipeCardLayoutManager swmanamger = new SwipeCardLayoutManager(activity);
        recyclerView.setLayoutManager(swmanamger);
        adapter = new NewsListRecyclerViewAdapter(activity);
        recyclerView.setAdapter(adapter);
        CardConfig.initConfig(activity);
        ItemTouchHelper.Callback callback=new SwipeCardCallBack();
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        adapter = new NewsListRecyclerViewAdapter(activity);
//        recyclerView.setAdapter(adapter);
    }
}
