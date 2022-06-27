package com.example.textualreaddemo.homepage.searchpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.textualreaddemo.MainActivity;
import com.example.textualreaddemo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里编写搜索主页
 * 最后更改时间：2022-6-26 20：00
 * @author luffe
 */

public class SearchPageFragment1 extends AppCompatActivity  {

    // Initialize variable
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page_fragment1);

        // Assign variable
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.news_list_viewpager);

        // Initialize array list
        ArrayList<String> arrayList = new ArrayList<>();



        // Add title in array list
        arrayList.add("社会新闻");
        arrayList.add("国内新闻");
        arrayList.add("国际新闻");
        arrayList.add("娱乐新闻");
        arrayList.add("体育新闻");
        arrayList.add("NBA新闻");
        arrayList.add("足球新闻");

        // Prepare view paper
        prepareViewPage(viewPager, arrayList);

        // Setup with view paper
        tabLayout.setupWithViewPager(viewPager);

        // Set the director mode scroll
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

    private void prepareViewPage(ViewPager viewPager, ArrayList<String> arrayList) {
        // Initialize main adapter
        TabMainAdapter adapter = new TabMainAdapter(getSupportFragmentManager());
        // Initialize main fragment
        TabMainFragment fragment = new TabMainFragment();
        // Use for loop
        for (int i = 0; i < arrayList.size(); i++) {
            // Initialize bundle
            Bundle bundle = new Bundle();
            // put String
            bundle.putString("title", arrayList.get(i));
            // Set argument
            fragment.setArguments(bundle);
            // Add fragment
            adapter.addFragment(fragment, arrayList.get(i));
            // Define new fragment
            fragment = new TabMainFragment();
        }
        // Set adapter
        viewPager.setAdapter(adapter);
    }

    private class TabMainAdapter extends FragmentPagerAdapter {
        // Initialize array list
        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        // Create constructor
        public void addFragment(Fragment fragment, String title) {
            // Add title
            arrayList.add(title);
            // Add fragment
            fragmentList.add(fragment);
        }

        public TabMainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // Return fragment postion
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Return fragment list size
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            // Return array list position
            return arrayList.get(position);
        }
    }
}

