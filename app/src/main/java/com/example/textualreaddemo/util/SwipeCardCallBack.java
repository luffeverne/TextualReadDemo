package com.example.textualreaddemo.util;

import android.graphics.Canvas;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 更新时间：2022-6-9 17：49
 * @author houdeng
 */
public class SwipeCardCallBack extends ItemTouchHelper.SimpleCallback {

    /**
     * 左右滑动：swipeDirs参数为ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
     * 上下滑动：swipeDirs参数为ItemTouchHelper.UP | ItemTouchHelper.DOWN
     * 0表示从不关心。
     */
    public SwipeCardCallBack() {
        super(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                ItemTouchHelper.UP | ItemTouchHelper.DOWN
        );
    }

    public SwipeCardCallBack(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }


    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                         int direction) {
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX,
                dY, actionState, isCurrentlyActive);
    }

}
