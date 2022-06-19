package com.example.textualreaddemo.homepage.searchpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.concernedpeople.ConcernedPeopleBean;
import com.google.android.material.tabs.TabLayout;

/**
 * 这里编写搜索主页
 * 最后更改时间：2022-6-11 20：34
 * @author houdeng
 */

public class SearchPageFragment extends Fragment {

    private TabLayout concernedTab;
    private ViewPager viewPager;
    private ConcernedViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_page,container,false);

        concernedTab = view.findViewById(R.id.concerned_tab_layout);
        viewPager = view.findViewById(R.id.concerned_news_list_viewpager);
        adapter = new ConcernedViewAdapter(getChildFragmentManager());
        adapter.setData(ConcernedPeopleBean.setConcernedPeople());
        viewPager.setAdapter(adapter);

        //设置放置方向为横向
        concernedTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab绑定viewpager
        concernedTab.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        return view;
    }

}
