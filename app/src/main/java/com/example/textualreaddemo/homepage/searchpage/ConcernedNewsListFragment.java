package com.example.textualreaddemo.homepage.searchpage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.newsdata.NewsListData;
import com.example.textualreaddemo.network.NewsDataUtility;

import java.util.List;

/**
 * 关注的人所发布的资讯
 * 最后更改时间：2022-6-11 23:20
 * @author houdeng
 */
public class ConcernedNewsListFragment extends Fragment {

    private RecyclerView listView;
    private ConcernedNewsListListViewAdapter adapter;
    private List<NewsListData> newsDataList;

    public static final String CONCERNED_PEOPLE_ID = "author_id";
    public static final String CONCERNED_PEOPLE_NAME = "author_name";

    /**
     * 创建关注人新闻展示页（暂时没用）
     */
    public static ConcernedNewsListFragment newsFragment(int channelId, String channelName){
        ConcernedNewsListFragment newsListFragment = new ConcernedNewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CONCERNED_PEOPLE_ID,channelId);
        bundle.putString(CONCERNED_PEOPLE_NAME,channelName);
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_concerned_news_list,container,false);

        //放入模拟数据
        setDataList();
        adapter = new ConcernedNewsListListViewAdapter(getActivity());
        listView = view.findViewById(R.id.concerned_news_list_view);
        adapter.setConcernedPeopleNews(newsDataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setAdapter(adapter);
        listView.setLayoutManager(linearLayoutManager);
        return view;
    }

    /**
     * 模拟数据
     */
    private void setDataList(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        newsDataList = NewsDataUtility.handleNewsListResponse(sp.getString("NewsList",null)).getData();
    }

}
