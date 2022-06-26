package com.example.textualreaddemo.homepage.searchpage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.concernedpeople.ConcernedPeopleBean;
import com.google.android.material.tabs.TabLayout;

/**
 * 这里编写搜索主页
 * 最后更改时间：2022-6-19 20：00
 * @author houdeng、luffe
 */

public class SearchPageFragment extends Fragment  {

    private TabLayout concernedTab;
    private ViewPager viewPager;
    private ConcernedViewAdapter adapter;

    private SearchView mSearchView;
    private ListView mListView;

    private String[] mStrs = {"aaa", "bbb", "ccc", "airsaid"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_page,container,false);

        concernedTab = view.findViewById(R.id.concerned_tab_layout);
        viewPager = view.findViewById(R.id.concerned_news_list_viewpager);
        adapter = new ConcernedViewAdapter(getChildFragmentManager());
        adapter.setData(ConcernedPeopleBean.setConcernedPeople());
        viewPager.setAdapter(adapter);


        mSearchView = view.findViewById(R.id.searchView);
        mListView = view.findViewById(R.id.listView);
        mListView.setTextFilterEnabled(false);


        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    mListView.setFilterText(newText);
                } else {
                    mListView.clearTextFilter();
                }
                return false;
            }
        });

        //设置放置方向为横向滚动
        concernedTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab绑定viewpager
        concernedTab.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        return view;
    }

    private void initView() {

    }

}
