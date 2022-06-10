package com.example.textualreaddemo.homepage.presenter;

import android.app.Activity;

import com.example.textualreaddemo.homepage.INewsListView;
import com.example.textualreaddemo.homepage.model.CallBack;
import com.example.textualreaddemo.homepage.model.INewsListModel;
import com.example.textualreaddemo.homepage.model.NewsListModel;

/**
 * 简讯处理中的P层
 * 不知道这个放在presenter也不行
 * 最后更新时间：2022-6-8 21：30
 */
public class NewsListViewPresenter implements INewsListViewPresenter, CallBack {

    private INewsListView newsListView;
    private INewsListModel newsListModel;

    public NewsListViewPresenter(INewsListView newsListView) {
        this.newsListView = newsListView;
        this.newsListModel = new NewsListModel();
    }

    @Override
    public void getNewsListData(Activity activity) {
        newsListView.showProgress();
        newsListModel.getNewsListData(activity , this);
    }

    @Override
    public void onSuccess() {
        newsListView.hideProgress();
        newsListView.getDataSuccess();
    }

    @Override
    public void onFailure() {
        newsListView.hideProgress();
        newsListView.getDataFailure();
    }
}
