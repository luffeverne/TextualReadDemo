package com.example.textualreaddemo.detailpage;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.textualreaddemo.MyApplication;
import com.example.textualreaddemo.R;
import com.example.textualreaddemo.beanRetrofit.NewsDetail;
import com.example.textualreaddemo.networkRetrofit.NewsUtility;
import com.example.textualreaddemo.room.HistoryNews;
import com.example.textualreaddemo.room.manager.DBEngine;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import java.util.ArrayList;
import java.util.List;

/**
 * DetailContentFragment 用于展示新闻具体内容
 */
public class DetailContentFragment extends Fragment {

    // DetailActivity 向 DetailContentFragment 通信（传递数据）时使用的参数
    private static final String ARG_POSITIONINVIEWPAGER2 = "positionInViewPager2";

    private int positionInViewPager2;

    private View rootView;

    // NestedScrollView 用于包裹新闻详情内容
    private NestedScrollView nestedScrollView;
    //新闻详情的标题，具体内容
    private TextView title_detail;
    private ImageView coverImage;
    private HtmlTextView htmlTextView;
    private WebView webView;
    private StringBuilder stringBuilder;

    private DBEngine dbEngine;

    MyApplication myApplication;

    NewsDetail.Data news;

    public DetailContentFragment() {
        // Required empty public constructor
    }

    //接口实现通信，Fragment 提供赋值接口，向 DetailActivity 传递数据
    private IFragmentCallback fragmentCallback;
    public void setFragmentCallback(IFragmentCallback callback) {
        fragmentCallback = callback;
    }

    //单例模式
    public static DetailContentFragment newInstance(String param1) {
        DetailContentFragment fragment = new DetailContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POSITIONINVIEWPAGER2, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            positionInViewPager2 = Integer.parseInt(getArguments().getString(ARG_POSITIONINVIEWPAGER2));
        }
        myApplication = (MyApplication) getActivity().getApplication();
        dbEngine = new DBEngine(context);
        news = myApplication.getNews().get(positionInViewPager2);

        if (dbEngine.getHistoryNewsByNewsID(news.getDocid()) == null) {
            HistoryNews historyNews = new HistoryNews(news.getDocid());
            historyNews.setNewsName(news.getTitle());
            dbEngine.insertHistoryNews(historyNews);
        }

        if (news.getImages() != null) {
            if (news.getImages().size() != 0) {
                //图片地址列表
                List<String> imgSrcs = new ArrayList<>();
                List<String> imgPositions = new ArrayList<>();
                for (int i = 0; i < news.getImages().size(); i++) {
                    imgSrcs.add(news.getImages().get(i).imgSrc);
                    imgPositions.add(news.getImages().get(i).position);
                }

                String content = news.getContent();
                Solution solution = new Solution();
                stringBuilder = new StringBuilder("");
                for (int i = 0; i < imgSrcs.size(); i++) {
                    int index = solution.strStr(content,imgPositions.get(i));
                    stringBuilder.append(content.substring(0,index))
                            .append("<img  src='" + imgSrcs.get(i) + "'/>");
                    content = content.substring(imgPositions.get(i).length() + index);
                }
            }
        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_content, container, false);

        nestedScrollView = rootView.findViewById(R.id.myNestedScrollView);

        title_detail = rootView.findViewById(R.id.title_detail);

        coverImage = rootView.findViewById(R.id.coverImage);

        //htmlTextView = rootView.findViewById(R.id.html_textview);
        webView = rootView.findViewById(R.id.webView);

        title_detail.setText(news.getTitle());

        if (news.getCover() != null) {
            if (news.getCover() != null) {
                Glide.with(this).load(news.getCover()).into(coverImage);
            }
        }

        if (news.getImages() != null) {
            if (news.getImages().size() != 0) {
                //这里出现过一次空指针异常，不知道原因。
                String content = stringBuilder.toString();
                content = content.replace("<img", "<img style='max-width:100%;height:auto'");
                webView.loadData(content,"text/html","UTF-8");
            } else {
                String content = news.getContent();
                content = content.replace("<img", "<img style='max-width:100%;height:auto'");
                webView.loadData(content,"text/html","UTF-8");
            }
        } else {
            String content = news.getContent();
            content = content.replace("<img", "<img style='max-width:100%;height:auto'");
            webView.loadData(content,"text/html","UTF-8");
        }

        //监听 nestedScrollView 的滑动和停止
        if (positionInViewPager2 == myApplication.getNews().size() - 1) {
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        //滑动底部
                        //fragmentCallback.sendMsgToActivity("stopScrolling");
                        Toast.makeText(getContext(),"最后一页的底部",Toast.LENGTH_LONG).show();
                        myApplication.setCanDropLoad(true);
                    }
                }
            });
        }
        return rootView;
    }
}

class Solution {
    // 获得前缀表
    public void getNext(int[] next, String s){
        int j = -1;
        next[0] = j;
        for (int i = 1; i<s.length(); i++){
            while(j>=0 && s.charAt(i) != s.charAt(j+1)){
                j=next[j];
            }

            if(s.charAt(i)==s.charAt(j+1)){
                j++;
            }
            next[i] = j;
        }
    }
    public int strStr(String haystack, String needle) {
        if(needle.length()==0){
            return 0;
        }

        int[] next = new int[needle.length()];
        getNext(next, needle);
        int j = -1;
        for(int i = 0; i<haystack.length();i++){
            while(j>=0 && haystack.charAt(i) != needle.charAt(j+1)){
                j = next[j];
            }
            if(haystack.charAt(i)==needle.charAt(j+1)){
                j++;
            }
            if(j==needle.length()-1){
                return (i-needle.length()+1);
            }
        }

        return -1;
    }
}