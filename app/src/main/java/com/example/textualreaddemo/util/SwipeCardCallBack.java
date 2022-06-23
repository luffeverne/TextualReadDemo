package com.example.textualreaddemo.util;

import android.graphics.Canvas;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.textualreaddemo.basebean.newsdata.NewsListData;
import com.example.textualreaddemo.homepage.NewsListRecyclerViewAdapter;

import java.util.List;

/**
 * 更新时间：2022-6-9 17：49
 * @author houdeng
 */
public class SwipeCardCallBack extends ItemTouchHelper.SimpleCallback {

    private List<NewsListData> mDatas;
    private NewsListRecyclerViewAdapter adapter;

    /**
     * 左右滑动：swipeDirs参数为ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
     * 上下滑动：swipeDirs参数为ItemTouchHelper.UP | ItemTouchHelper.DOWN
     * 0表示从不关心。
     */
    public SwipeCardCallBack(List<NewsListData> mDatas, NewsListRecyclerViewAdapter adapter) {
        super(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.UP | ItemTouchHelper.DOWN
        );
        this.mDatas = mDatas;
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //当已经滑动删除了的时候会被回掉--删除数据，循环的效果
        switch (direction){
            case ItemTouchHelper.UP:
                NewsListData removeDown = mDatas.remove(viewHolder.getLayoutPosition());
                mDatas.add(0, removeDown);
                break;
            case ItemTouchHelper.DOWN:
                NewsListData removeUp = mDatas.remove(0);
                mDatas.add(removeUp);
                break;
            default:
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}
