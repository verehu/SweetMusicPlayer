package com.huwei.sweetmusicplayer.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.huwei.sweetmusicplayer.ui.widgets.auto.AutoListView;

/**
 * 只能获得垂直滑动手势的ScrollView
 *
 * @author jerry
 * @date 2015-12-24
 */
public class VerticalScrollView extends ScrollView {
    public static final String TAG = "VerticalScrollView";

    private GestureDetector mGestureDetector;
    private AutoListView mChildAutoListView;    //子控件

    private OnScrollChangeListener mScrollViewListener;

    public VerticalScrollView(Context context) {
        this(context, null);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mGestureDetector = new GestureDetector(context, new YScrollDetector());
    }

    /**
     * 绑定自动加载的ListView
     */
    public void bindAutoListView(AutoListView autoListView) {
        this.mChildAutoListView = autoListView;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        mScrollViewListener = listener;
    }

    /**
     * 监听垂直手势并且拦截
     */
    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceX) < Math.abs(distanceY);
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        int offsetY = getScrollY() + getHeight() - getChildAt(0).getHeight();
        Log.i(TAG, "offsetY:" + offsetY);

        if (mScrollViewListener != null) {
            mScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }

        if (offsetY == 0) {
            if (mChildAutoListView != null) {
                mChildAutoListView.ifNeedLoad();
            }
        }
    }

    public interface OnScrollChangeListener {

        void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}
