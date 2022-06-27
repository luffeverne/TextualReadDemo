package com.example.textualreaddemo.newcradviewdesign;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * 为了点击item使相应的item居中显示，且实现RecyclerView的平缓滑动，重写LinearLayoutManager<P/>
 */
public class NewsListLayoutManager extends LinearLayoutManager {
    private static final String TAG = "NewsListLayoutManager";
    //相邻卡片之间的间距
    private int mChildIntervalWidth;
    /**
     * 用于存储子视图的在RecyclerView中的位置<P/>
     * key为子视图的序号，Rect为子视图的位置<P/>
     */
    private Map<Integer , Rect> mChildPositionRects = new HashMap<>();
    /**
     * 用于记录子视图是否已经添加至RecyclerView中
     * key为子视图的序号，value为为该子视图是否在可视区域，true表示已显示，false未显示
     */
    private Map<Integer , Boolean> mChildHasAttached = new HashMap<>();
    //子视图宽度，高度
    private int mChildWidth , mChildHeight;
    //卡片后移的距离
    private int mStartX;
    //卡片间距系数，为距离/卡片宽度
    private float OVERLYING_RATIO = 1.00f*1/3;
    public NewsListLayoutManager(Context context) {
        super(context);
    }

    public NewsListLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NewsListLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(getItemCount() == 0){
            detachAndScrapAttachedViews(recycler);
            return;
        }
        mChildHasAttached.clear();
        mChildPositionRects.clear();
        detachAndScrapAttachedViews(recycler);
        //计算子视图宽度，相邻子视图间距
        if(mChildIntervalWidth <= 0){
            View firstItem = recycler.getViewForPosition(0);
            measureChildWithMargins(firstItem, 0, 0);
            mChildWidth = getDecoratedMeasuredWidth(firstItem);
            mChildHeight = getDecoratedMeasuredHeight(firstItem);
            mChildIntervalWidth = (int) (mChildWidth*OVERLYING_RATIO);
        }
        //子视图水平方向的偏移量
        int offsetX = 0;
        mStartX = getWidth()/2 - mChildWidth/2;
        for(int i = 0 ; i < getItemCount() ; i++){
            Rect rect = new Rect(offsetX + mStartX , 0 ,
                    offsetX + mChildWidth + mStartX , mChildHeight);
            mChildPositionRects.put(i , rect);
            mChildHasAttached.put(i,false);
            offsetX += mChildIntervalWidth;
        }
        //添加可视区域的视图
        int visibleCount = getHorizontalSpace() / mChildIntervalWidth;
        Rect visibleRect = getVisibleArea();
        for(int i = 0; i < visibleCount; i++){
            insertView(i, visibleRect, recycler, false);
            Log.d(TAG,"the i ="+i+" visible count = "+visibleCount+",rect left = "+visibleRect.left);
        }
    }

    //横向移动的绝对距离
    private int mScrollDistanceX = 0;
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //如视图内无可显示的子视图，不累加
        if(getChildCount() <= 0){
            return dx;
        }
        int travel = dx;
        //左边缘
        if(mScrollDistanceX + dx < 0){
            Log.d(TAG,"到达左边缘");
            travel = -mScrollDistanceX;
        }else if(mScrollDistanceX + dx > ((getItemCount() -1)*mChildIntervalWidth)){
            //右边缘
            Log.d(TAG,"到达右边缘");
            travel = (getItemCount() -1)*mChildIntervalWidth - mScrollDistanceX;
        }
        mScrollDistanceX += travel;
        //回收非显示区域子视图，并且在可见区域放置子视图
        Rect visibleRect = getVisibleArea();
        for(int i = getChildCount()-1; i >=0 ; i--){
            View item = getChildAt(i);
            int position = getPosition(item);
            Rect rect = mChildPositionRects.get(position);
            //判断子视图与可见区域无交集时，移除并回收视图
            if(!Rect.intersects(rect,visibleRect)){
                removeAndRecycleView(item,recycler);
                mChildHasAttached.put(position,false);
                Log.d(TAG,"移除视图 位置："+position);
            }else{
                //可视区域放置子视图
                layoutDecoratedWithMargins(item , rect.left - mScrollDistanceX, rect.top ,
                        rect.right - mScrollDistanceX, rect.bottom);
                mChildHasAttached.put(position , true);
                Log.d(TAG,"放置视图 位置："+position);
            }
        }
        //RecyclerView头尾填充空白区域
        View firstItem = getChildAt(0);
        View lastItem = getChildAt(getChildCount() - 1);
        if(travel >= 0 ){
            //左滑：向底部滑动
            int minPos = getPosition(firstItem);
            //填充可视区域右侧的View
            for(int i = minPos; i < getItemCount(); i++){
                insertView(i, visibleRect, recycler, false);
            }
        }else{
            //右滑：向顶部滑动
            int maxPos = getPosition(lastItem);
            //填充可视区域左侧的View
            for(int i = maxPos; i >= 0; i--){
                insertView(i, visibleRect, recycler, true);
            }
        }
        return travel;
    }

    /**
     * 获取水平方向视图可用空间的宽度
     * @return
     */
    private int getHorizontalSpace(){
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * 获取显示的区域
     * @return
     */
    private Rect getVisibleArea(){
        Rect rect = new Rect(getPaddingLeft() + mScrollDistanceX, getPaddingTop() ,
                getWidth() - getPaddingRight() + mScrollDistanceX, getHeight() - getPaddingBottom());
        return rect;
    }

    //针对可视区域插入子视图
    private void insertView(int pos , Rect visibleRect , RecyclerView.Recycler recycler , boolean firstPos){
        Rect rect = mChildPositionRects.get(pos);
        if(Rect.intersects(visibleRect , rect) && !mChildHasAttached.get(pos)){
            //仅在可视区域其未显示的视图才执行插入视图
            View item = recycler.getViewForPosition(pos);
            if(firstPos){
                addView(item , 0);
            }else{
                addView(item);
            }
            measureChildWithMargins(item,0,0);
            layoutDecoratedWithMargins(item, rect.left - mScrollDistanceX, rect.top ,
                    rect.right - mScrollDistanceX, rect.bottom);
            mChildHasAttached.put(pos, true);
        }
    }

    /**
     * 计算显示的视图的中间视图的位置，基本思路是基于RecyclerView滑动的距离除以子视图间距
     */
    public int getCenterVisiblePosition(){
        int position = mScrollDistanceX / mChildIntervalWidth;
        int offset = mScrollDistanceX % mChildIntervalWidth;
        if(offset > mChildIntervalWidth/2){
            position++;
        }
        return position;
    }

    //计算显示的第一个视图的位置
    public int getFirstVisiblePosition(){
        if(getChildCount() < 0){
            return  0;
        }
        View item = getChildAt(0);
        return getPosition(item);
    }

}
