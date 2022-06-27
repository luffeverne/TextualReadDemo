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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textualreaddemo.DetailActivity;
import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.PictureBean;
import com.example.textualreaddemo.homepage.presenter.NewsListViewPresenter;
import com.example.textualreaddemo.newcradviewdesign.ItemAdapter;
import com.example.textualreaddemo.newcradviewdesign.NewsListLayoutManager;
import com.example.textualreaddemo.newcradviewdesign.NewsListRecyclerView;
import com.example.textualreaddemo.newcradviewdesign.SetPictureUtil;
import com.example.textualreaddemo.util.NetworkBroadcastUtils;

import java.util.List;

/**
 * 这里编写新闻简讯列表主页
 * 最后更改时间：2022-6-8 21：30
 * @author houdeng
 */
public class NewsListPageFragment extends Fragment implements INewsListView{

    private SetPictureUtil setPictureUtil;

    private NewsListViewPresenter newsListViewPresenter;
    private NewsListRecyclerView recyclerView;
    private ItemAdapter adapter;
    private ProgressDialog progressDialog;
    private Activity activity;
    private NetworkBroadcastUtils.MyConnectivityReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list_page,container,false);

        recyclerView = view.findViewById(R.id.news_list_recycler_view);
        activity = getActivity();//获取activity对象，充当上下文
        setPictureUtil = new SetPictureUtil(activity);//初始化工具类

        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍后...");

        newsListViewPresenter = new NewsListViewPresenter(this);//创建P层对象

        listenNetworkBroadcast();

        return view;
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
            initView(temp);
        }
    }

    private void getFromSP(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        String temp = sp.getString("NewsList",null);
        if (temp != null){
            initView(temp);
        }else {
            Toast.makeText(activity, "无缓存，请重新获取", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(String body){
        List<PictureBean> pictureList = setPictureUtil.getPictureList(body);
        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new NewsListLayoutManager(activity,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //滑动监听
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        /**
         * 点击屏幕中的非居中位置时，可以使点击的项目平滑移动至屏幕中央
         */
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                recyclerView.smoothScrollToPosition(pos);
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("newsId",pictureList.get(pos).getId());
                startActivity(intent);
            }
        });
        adapter.setDataList(pictureList);
    }

    private void listenNetworkBroadcast() {
        //实例化工具类
        NetworkBroadcastUtils utils=new NetworkBroadcastUtils();
        //实例化网络广播监听
        receiver= new NetworkBroadcastUtils.MyConnectivityReceiver();
        //调用初始化 注册广播的方法
        utils.GetNetWorkConnectivity(activity,receiver);
        //广播的回调方法
        receiver.setNetworkMethod(new NetworkBroadcastUtils.MyConnectivityReceiver.NetworkMethod() {
            @Override
            public void haveNetWork() {
                //从网络获取数据
                newsListViewPresenter.getNewsListData(activity);
            }
            @Override
            public void NoNetWork() {
                getFromSP();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(receiver);
    }
}
