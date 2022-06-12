package com.example.textualreaddemo.homepage.searchpage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textualreaddemo.R;
import com.example.textualreaddemo.basebean.concernedpeople.ConcernedPeopleBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConcernedViewAdapter extends FragmentPagerAdapter {

    private List<ConcernedPeopleBean> concernPeopleList;

    public ConcernedViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    public void setData(List<ConcernedPeopleBean> concernPeopleList ){
        this.concernPeopleList = concernPeopleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //创建一个newsfragment并把参数传进去
        return ConcernedNewsListFragment.newsFragment(concernPeopleList.get(position).getId(),
                concernPeopleList.get(position).getName());
    }

    @Override
    public int getCount() {
        return concernPeopleList == null ? 0 : concernPeopleList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return concernPeopleList.get(position).getName();
    }

}
