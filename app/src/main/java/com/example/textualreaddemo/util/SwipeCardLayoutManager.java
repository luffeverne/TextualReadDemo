package com.example.textualreaddemo.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 编写 LayoutManager工具
 * 更新时间：2022-6-15 20：27
 * @author houdeng
 */
public class SwipeCardLayoutManager extends RecyclerView.LayoutManager {

    Context context;
    int TRANS_Y_GAP;

    public SwipeCardLayoutManager(Context context){
        TRANS_Y_GAP= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                15,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //分离回收附属视图
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        int bottomPosition;
        if (itemCount < 5) {
            bottomPosition = 0;
        } else {
            bottomPosition = itemCount - 5;
        }
        for (int i = bottomPosition; i < itemCount; i++) {

            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            //摆放cardView
            layoutDecorated(view,
                    widthSpace / 2,
                    getHeight() / 7 ,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    getHeight() - getHeight() / 7 );
            //层叠效果--Scale+TranslationY
            int level = itemCount - i - 1;
            if(level>0){
                if(level<CardConfig.MAX_SHOW_COUNT){
                    view.setTranslationY(TRANS_Y_GAP*level*1.5f);
                    view.setScaleX(1-CardConfig.SCALE_GAP*level);
                    view.setScaleY(1-CardConfig.SCALE_GAP*level);
                }
            }else {
                view.setTranslationY(TRANS_Y_GAP*(level));
                view.setScaleX(1-CardConfig.SCALE_GAP*(level));
                view.setScaleY(1-CardConfig.SCALE_GAP*(level));
            }
//            view.setTranslationY(CardConfig.MAX_SHOW_COUNT*level*5f);
        }
//        设置前一个新闻card，但实现效果不好
        View view = recycler.getViewForPosition(0);
        addView(view);
        measureChildWithMargins(view, 0, 0);
        int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
        int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
        //摆放cardView
        layoutDecorated(view,
                widthSpace / 2,
                -getDecoratedMeasuredHeight(view)+heightSpace ,
                widthSpace / 2 + getDecoratedMeasuredWidth(view),
                heightSpace);
        view.setTranslationY(-1*TRANS_Y_GAP);
    }


}
