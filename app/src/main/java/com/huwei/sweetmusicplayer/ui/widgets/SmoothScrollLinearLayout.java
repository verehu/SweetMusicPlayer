package com.huwei.sweetmusicplayer.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 可以平滑滚动的LinearLayout
 *
 * @author jayce
 * @date 2015/11/20
 */
public class SmoothScrollLinearLayout extends LinearLayout {

    private Scroller mScroller;
    private int mHeight;

    private boolean mExpanded = false;  //是否展开

    private static final int ANIMATION_DURATION = 1000;   //  从底部到上面需要1s
    public static final int DURATION = 1500;   //满屏滑动时间
    public static final int OPEN_ANIM_DURATION = 1000;
    public static int SNAP_VELOCITY = 600;  //最小的滑动速率

    public SmoothScrollLinearLayout(Context context) {
        this(context, null);
    }

    public SmoothScrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        updateExpanded(t);
    }

    /**
     * 更新mExpanded状态
     */
    private void updateExpanded(int t) {
        if (t == 0) {
            mExpanded = true;
        } else if(t == - mHeight){
            mExpanded = false;
        }
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    //    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - getScrollX();
        int dy = fy - getScrollY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, Math.abs(dy * DURATION / mHeight));
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

}
