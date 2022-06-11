package com.example.textualreaddemo;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.textualreaddemo.detailpage.DetailContentFragment;
import com.example.textualreaddemo.detailpage.DetailContentFragmentAdapter;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    List<DetailContentFragment> detailContentFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewPager2 = findViewById(R.id.vp_detail);
        detailContentFragmentList.add(DetailContentFragment.newInstance("1","1"));
        detailContentFragmentList.add(DetailContentFragment.newInstance("1","1"));
        detailContentFragmentList.add(DetailContentFragment.newInstance("1","1"));
        viewPager2.setAdapter(new DetailContentFragmentAdapter(this,detailContentFragmentList));
    }
}
=======
import android.app.Activity;

public class DetailActivity extends Activity {
}
>>>>>>> 063d22fcc6074c2a5c4f154e756453f04f590b33
