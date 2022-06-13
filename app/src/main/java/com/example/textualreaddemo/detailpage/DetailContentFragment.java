package com.example.textualreaddemo.detailpage;

import android.os.Bundle;

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
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailContentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;
    private NestedScrollView nestedScrollView;
    TextView title_detail,content_detail;
    String detailNewsID;
    NewsDetail.Data newsDetailData;



    public DetailContentFragment() {
        // Required empty public constructor
    }

    //接口实现通信，Fragment 提供赋值接口， Activity 赋值
    private IFragmentCallback fragmentCallback;
    public void setFragmentCallback(IFragmentCallback callback) {
        fragmentCallback = callback;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailContentFragment newInstance(String param1, String param2) {
        DetailContentFragment fragment = new DetailContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        detailNewsID = mParam1;
        newsDetailData = NewsUtility.getNewsDetail(detailNewsID).getData();
        while (newsDetailData == null) {
            newsDetailData = NewsUtility.getNewsDetail(detailNewsID).getData();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_detail_content, container, false);
        nestedScrollView = rootView.findViewById(R.id.myNestedScrollView);
        title_detail = rootView.findViewById(R.id.title_detail);
        content_detail = rootView.findViewById(R.id.content_detail);
        title_detail.setText(newsDetailData.getTitle());
        content_detail.setText(newsDetailData.getContent());
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (fragmentCallback != null)
                        fragmentCallback.sendMsgToActivity("scrolling");
                } else {
                    if (fragmentCallback != null)
                    fragmentCallback.sendMsgToActivity("stopScrolling");
                }

                return false;
            }
        });
        return rootView;
    }

}