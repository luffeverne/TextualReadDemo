package com.example.textualreaddemo.homepage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页viewpager2适配器
 * 最后更改时间：2022-6-8 14：01
 * @author houdeng
 */
public class MainViewAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public MainViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        if (fragments == null){
            fragments = new ArrayList<>();
        }
    }

    public void addFragment(Fragment fragment){
        if (fragments != null) {
            fragments.add(fragment);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments == null ? null : fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments == null ? 0 : fragments.size();
    }



}
