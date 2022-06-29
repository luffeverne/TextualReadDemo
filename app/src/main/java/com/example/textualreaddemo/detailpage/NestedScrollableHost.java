/*
 * 版权所有 2019 安卓开源项目
 *
 * 根据 Apache 许可证 2.0 版（“许可证”）进行许可;
 * 除非符合许可证，否则您不得使用此文件。
 * 您可以在以下位置获取许可证副本：
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * 除非适用法律要求或书面同意，否则软件
 * 根据许可证分发的按“原样”分发，
 * 不提供任何明示或暗示的保证或条件。
 * 请参阅许可证，了解管理权限和
 * 许可证下的限制。
 */
package com.example.textualreaddemo.detailpage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.viewpager2.widget.ViewPager2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * 用于将可滚动组件包装在 ViewPager2 中的布局。作为问题的解决方案提供
 * 其中 ViewPager2 的页面具有嵌套的可滚动元素，这些元素的滚动方向与
 * ViewPager2.可滚动元素必须是此主机布局的直接且唯一的子元素。
 *
 * 此解决方案在使用多级嵌套可滚动元素时存在限制
 * （例如，水平回收器视图在垂直回收器视图中在水平视图Pager2中）。
 */
public final class NestedScrollableHost extends FrameLayout {
    private int touchSlop;
    private float initialX;
    private float initialY;

    private final ViewPager2 getParentViewPager() {
        ViewParent viewParent = this.getParent();
        if (!(viewParent instanceof View)) {
            viewParent = null;
        }

        View v;
        for(v = (View)viewParent; v != null && !(v instanceof ViewPager2); v = (View)viewParent) {
            viewParent = v.getParent();
            if (!(viewParent instanceof View)) {
                viewParent = null;
            }
        }

        View v2 = v;
        if (!(v instanceof ViewPager2)) {
            v2 = null;
        }

        return (ViewPager2)v2;
    }

    //只对第一个滚动view进行处理
    private final View getChild() {
        return this.getChildCount() > 0 ? this.getChildAt(0) : null;
    }

    private final boolean canChildScroll(int orientation, float delta) {
        int direction = -((int)Math.signum(delta));
        View viewChild;
        boolean flag = false;
        switch(orientation) {
            case 0:
                viewChild = this.getChild();
                flag = viewChild != null ? viewChild.canScrollHorizontally(direction) : false;
                break;
            case 1:
                viewChild = this.getChild();
                flag = viewChild != null ? viewChild.canScrollVertically(direction) : false;
                break;
            default:
                try {
                    throw (Throwable)(new IllegalArgumentException());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
        }

        return flag;
    }

    //这个办法不行，重写下面那个方法分发view事件
//    @Override
//    public boolean onInterceptTouchEvent(@NotNull MotionEvent e) {
//        Intrinsics.checkNotNullParameter(e, "e");
//        this.handleInterceptTouchEvent(e);
//        Log.e("lance", "onInterceptTouchEvent: ");
//        return true;
//    }


    //重写下面方法分发事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "e");
        this.handleInterceptTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private final void handleInterceptTouchEvent(MotionEvent e) {
        ViewPager2 parentViewPager = this.getParentViewPager();
        if (parentViewPager != null) {
            int orientation = parentViewPager.getOrientation();
            // Early return if child can't scroll in same direction as parent
            if (this.canChildScroll(orientation, -1.0F) || this.canChildScroll(orientation, 1.0F)) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    this.initialX = e.getX();
                    this.initialY = e.getY();
                    // Child can scroll, disallow all parents to intercept
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
                    float dx = e.getX() - this.initialX;
                    float dy = e.getY() - this.initialY;
                    boolean isVpHorizontal = orientation == ViewPager2.ORIENTATION_HORIZONTAL;
                    // assuming ViewPager2 touch slop is 2x touch slop of child
                    float scaledDx = Math.abs(dx) * (isVpHorizontal ? 0.5F : 1.0F);
                    float scaledDy = Math.abs(dy) * (isVpHorizontal ? 1.0F : 0.5F);
                    if (scaledDx > (float)this.touchSlop || scaledDy > (float)this.touchSlop) {
                        if (isVpHorizontal == scaledDy > scaledDx) {
                            // Gesture is perpendicular, allow all parents to intercept
                            this.getParent().requestDisallowInterceptTouchEvent(false);
                        } else if (this.canChildScroll(orientation, isVpHorizontal ? dx : dy)) {
                            // Child can scroll, disallow all parents to intercept
                            this.getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            // Child cannot scroll, allow all parents to intercept
                            this.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }

            }
        }
    }

    public NestedScrollableHost(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        ViewConfiguration var10001 = ViewConfiguration.get(this.getContext());
        Intrinsics.checkNotNullExpressionValue(var10001, "ViewConfiguration.get(context)");
        this.touchSlop = var10001.getScaledTouchSlop();
    }

    public NestedScrollableHost(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        ViewConfiguration var10001 = ViewConfiguration.get(this.getContext());
        Intrinsics.checkNotNullExpressionValue(var10001, "ViewConfiguration.get(context)");
        this.touchSlop = var10001.getScaledTouchSlop();
    }
}

