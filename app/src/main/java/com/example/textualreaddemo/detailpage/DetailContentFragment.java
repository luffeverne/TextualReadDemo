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
    private TextView title_detail,content_detail;
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
        content_detail = rootView.findViewById(R.id.content_detail);

        if (newsDetailData != null) {
            title_detail.setText(newsDetailData.getTitle());
            content_detail.setText(newsDetailData.getContent());
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