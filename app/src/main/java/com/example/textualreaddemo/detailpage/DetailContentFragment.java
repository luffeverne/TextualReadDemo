package com.example.textualreaddemo.detailpage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.textualreaddemo.R;
import com.example.textualreaddemo.beanRetrofit.NewsDetail;
import com.example.textualreaddemo.beanRetrofit.NewsList;
import com.example.textualreaddemo.networkRetrofit.NewsUtility;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * DetailContentFragment 用于展示新闻具体内容
 */
public class DetailContentFragment extends Fragment {

    // DetailActivity 向 DetailContentFragment 通信（传递数据）时使用的参数
    private static final String ARG_DETAILNEWSID = "detailNewsID";

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String detailNewsID;
    private String mParam2;
    private View rootView;

    // NestedScrollView 用于包裹新闻详情内容
    private NestedScrollView nestedScrollView;
    //新闻详情的标题，具体内容
    private TextView title_detail;
    private HtmlTextView htmlTextView;
    private StringBuilder stringBuilder;
    //新闻详情的数据
    private NewsDetail.Data newsDetailData;

    public DetailContentFragment() {
        // Required empty public constructor
    }

    //接口实现通信，Fragment 提供赋值接口，向 DetailActivity 传递数据
    private IFragmentCallback fragmentCallback;
    public void setFragmentCallback(IFragmentCallback callback) {
        fragmentCallback = callback;
    }

    //单例模式
    public static DetailContentFragment newInstance(String param1, String param2) {
        DetailContentFragment fragment = new DetailContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DETAILNEWSID, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            detailNewsID = getArguments().getString(ARG_DETAILNEWSID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //DetailActivity 传过来的新闻详情id
        System.out.println(detailNewsID);
        for (int i = 0; i < 3; i++) {
            newsDetailData = NewsUtility.getNewsDetail(detailNewsID).getData();
            if (newsDetailData != null) break;
        }
        if (newsDetailData == null)
            Toast.makeText(getActivity(),"请求超时，该新闻不存在",Toast.LENGTH_SHORT).show();

        //图片地址列表
        List<String> imgSrcs = new ArrayList<>();
        List<String> imgPositions = new ArrayList<>();
        for (int i = 0; i < newsDetailData.getImages().size(); i++) {
            imgSrcs.add(newsDetailData.getImages().get(i).imgSrc);
            imgPositions.add(newsDetailData.getImages().get(i).position);
        }
        String Content = newsDetailData.getContent();
        Solution solution = new Solution();
        stringBuilder = new StringBuilder("");
        for (int i = 0; i < imgSrcs.size(); i++) {
            int index = solution.strStr(Content,imgPositions.get(i));
            stringBuilder.append(Content.substring(0,index))
                    .append("<img  src='" + imgSrcs.get(i) + "'/>");
            Content = Content.substring(imgPositions.get(i).length() + index);
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
        htmlTextView = rootView.findViewById(R.id.html_textview);

        if (newsDetailData != null) {
            title_detail.setText(newsDetailData.getTitle());
            if (newsDetailData.getImages().size() != 0) {
                htmlTextView.setHtml(stringBuilder.toString(),new HtmlHttpImageGetter(htmlTextView));
            } else {
                htmlTextView.setHtml(newsDetailData.getContent(),new HtmlHttpImageGetter(htmlTextView));
            }

        }


        //监听 nestedScrollView 的滑动和停止
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == 0 || scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())){
                    //滑动到顶部，底部
                    fragmentCallback.sendMsgToActivity("stopScrolling");
                } else {
                    fragmentCallback.sendMsgToActivity("scrolling");
                }
            }
        });
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