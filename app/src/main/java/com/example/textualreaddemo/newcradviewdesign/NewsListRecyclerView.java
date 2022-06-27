package com.example.textualreaddemo.newcradviewdesign;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListRecyclerView extends RecyclerView {

    /**
     * 相邻卡片之间的间隔
     */
    private int mIntervalDistance = 0;
    private float mSelectedScale = 0.2f;
    private static final String TAG = "NewsListRecyclerView";

    public NewsListRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public NewsListRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NewsListRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //开启顺序重新排列
    private void init(){
        setChildrenDrawingOrderEnabled(true);
    }

    public int getIntervalDistance() {
       return mIntervalDistance;
    }

    /**
     * 设置子视图的缩放系数/旋转角度
     */
    @Override
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        int childWidth = child.getWidth() - child.getPaddingLeft() - child.getPaddingRight();
        int childHeight = child.getHeight() - child.getPaddingTop() - child.getPaddingBottom();
        int width = getWidth();
        if(width <= child.getWidth()){
            return super.drawChild(canvas, child, drawingTime);
        }
        int pivot = (width - childWidth)/2;
        int x = child.getLeft();
        float scale , alpha;
        alpha = 1 - 0.6f*Math.abs(x - pivot)/pivot;
        if(x <= pivot){
            scale = 2f*(1-mSelectedScale)*(x+childWidth) / (width+childWidth) + mSelectedScale;
        }else{
            scale = 2f*(1-mSelectedScale)*(width - x) / (width+childWidth) + mSelectedScale;
        }
        child.setPivotX(childWidth / 2);
        child.setPivotY(childHeight*2 / 5);
        child.setScaleX(scale);
        child.setScaleY(scale);
        //设置子视图的3D旋转
        float rotationY = calculateRotationY(x - pivot);
        if(Math.abs(x - pivot) < 5){
            child.setRotationY(0);
            rotationY = 0;
        }else {
            child.setRotationY(rotationY);
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    /**
     * 重写视图布置顺序:前半顺序绘制，后半倒序绘制，中间位置
     * 中间位置最后一个绘制count-1
     * 中间位置之前的视图绘制顺序为i
     * 中间位置之后的视图绘制顺序为center+count-1-i
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        NewsListLayoutManager layoutManager = (NewsListLayoutManager) getLayoutManager();
        //计算出中间位置，即当前显示视图的位置
        int center = layoutManager.getCenterVisiblePosition() - layoutManager.getFirstVisiblePosition();
        //序号为i的视图的绘制序号
        int order;
        if(i == center){
            order = childCount - 1;
        }else if(i < center){
            order = i;
        }else{
            order = center + childCount - 1 - i;
        }
        Log.d(TAG,"childCount = "+childCount+",center = "+center+",order = "+order+",i = "+i);
        return order;
    }

    private final float MAX_ROTATION_Y = 20.0f;
    //根据与中心点的距离计算y轴旋转角度，距离越远旋转越大
    private float calculateRotationY(int offsetX){
        float rotation = -MAX_ROTATION_Y * offsetX / mIntervalDistance;
        if(rotation < -MAX_ROTATION_Y){
            rotation = -MAX_ROTATION_Y;
        }else if(rotation > MAX_ROTATION_Y){
            rotation = MAX_ROTATION_Y;
        }
        return rotation;
    }

}
