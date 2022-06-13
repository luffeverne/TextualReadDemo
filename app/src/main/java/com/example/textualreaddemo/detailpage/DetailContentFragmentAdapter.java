package com.example.textualreaddemo.detailpage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class DetailContentFragmentAdapter extends FragmentStateAdapter {
    private List<DetailContentFragment> detailContentFragmentList;

    public DetailContentFragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<DetailContentFragment> detailContentFragmentList) {
        super(fragmentActivity);
        this.detailContentFragmentList = detailContentFragmentList;
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


}
