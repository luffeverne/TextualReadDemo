package com.example.textualreaddemo.homepage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.NewsListBean;
import com.example.textualreaddemo.DetailActivity;
import com.example.textualreaddemo.homepage.presenter.NewsListViewPresenter;
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

        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍后...");

        newsListViewPresenter = new NewsListViewPresenter(this);//创建P层对象
        load();
//        testD();
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
        Toast.makeText(activity, "网络加载成功", Toast.LENGTH_SHORT).show();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        String temp = sp.getString("NewsList",null);
        if (temp != null){
            testD(temp);
        }
    }

    @Override
    public void getDataFailure() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        String temp = sp.getString("NewsList",null);
        if (temp != null){
            testD(temp);
        }else {
            Toast.makeText(activity, "无缓存，请重新获取", Toast.LENGTH_SHORT).show();
        }
    }

    private void testD(String temp) {
        NewsListBean newsListBean = NewsDataUtility.handleNewsListResponse(temp);
        //判断数据是否正确
        if (newsListBean != null && "数据返回成功！".equals(newsListBean.getMsg())) {
            SwipeCardLayoutManager swmanamger = new SwipeCardLayoutManager(activity);
            recyclerView.setLayoutManager(swmanamger);
            adapter = new NewsListRecyclerViewAdapter(activity);

            //设置数据
            adapter.setData(newsListBean.getData());
            //设置点击事件
            adapter.setOnItemClickListener(new NewsListRecyclerViewAdapter.MyOnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, DetailActivity.class);
                            intent.putExtra("newsId", newsListBean.getData().get(position).getNewsId());
                            activity.startActivity(intent);
                        }
                    });
                }
            });
            recyclerView.setAdapter(adapter);
            //配置布局信息文件
            CardConfig.initConfig(activity);
            //设置触摸效果
            ItemTouchHelper.Callback callback = new SwipeCardCallBack(newsListBean.getData(), adapter);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(recyclerView);
        }

    }
}
