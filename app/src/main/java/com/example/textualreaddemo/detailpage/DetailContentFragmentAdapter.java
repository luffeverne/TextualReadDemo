package com.example.textualreaddemo.detailpage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * DetailContentFragmentAdapter 为 DetailActivity 里面 viewpager2 的适配器
 */
    public class DetailContentFragmentAdapter extends FragmentStateAdapter {
    private List<DetailContentFragment> detailContentFragmentList = new ArrayList<>();

    public DetailContentFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setData(List<DetailContentFragment> fragmentList){
        detailContentFragmentList.clear();
        this.detailContentFragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return detailContentFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return detailContentFragmentList.size();
    }

    @Override
    public long getItemId(int position) {
        return detailContentFragmentList.get(position).hashCode();
    }

    @Override
    public boolean containsItem(long itemId) {
        return super.containsItem(itemId);
    }

}
