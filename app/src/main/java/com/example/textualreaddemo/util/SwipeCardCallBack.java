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
        //监听话滑动的距离--控制动画的执行程度
        //灵界点
        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX);
        //动画执行的百分比
        double fraction = distance / maxDistance;
        if (fraction > 1) {
            fraction = 1;
        }
        int itemcount = recyclerView.getChildCount();
        for (int i = 0; i < itemcount; i++) {
            //执行
            View view = recyclerView.getChildAt(i);
            //几个view层叠的效果，错开的效果--便宜动画+缩放动画
            int level = itemcount - i - 1;
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY((float) (1 - CardConfig.TRANS_V_GAP * level + fraction * CardConfig.TRANS_V_GAP));
                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    view.setTranslationY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                }
            }
        }
    }

}
