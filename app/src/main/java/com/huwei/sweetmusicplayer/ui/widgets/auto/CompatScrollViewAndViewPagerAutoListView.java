package com.huwei.sweetmusicplayer.ui.widgets.auto;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 兼容ScrollView ViewPager的AutoListView 仅仅支持下拉刷新    外部嵌套的ScrollView 需要在初始化时滚动到头部  可以在刷新的时候动态调整ViewPager的高度
 *
 * @author jerry
 * @date 2015-12-24
 */
public class CompatScrollViewAndViewPagerAutoListView extends AutoListView {

    private ViewPager mViewPager;

    public CompatScrollViewAndViewPagerAutoListView(Context context) {
        this(context, null);
    }

    public CompatScrollViewAndViewPagerAutoListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompatScrollViewAndViewPagerAutoListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //禁止下拉刷新
        setRefreshEnable(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public void requestLayout() {
//        int height = adjustListViewHeight(this);
//        if (mViewPager != null) {
//
//            ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
//            params.height = height;
//            mViewPager.setLayoutParams(params);
//        }
        super.requestLayout();
        if (mViewPager != null) {
            int height = getMeasuredHeight();
            ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
            params.height = height;
            mViewPager.setLayoutParams(params);
        }
    }

    /**
     * 禁止下拉刷新
     *
     * @param refreshEnable
     */
    @Override
    public void setRefreshEnable(boolean refreshEnable) {
        super.setRefreshEnable(false);
    }

    /**
     * 绑定需要动态调整高度的ViewPager
     *
     * @param mViewPager
     */
    public void bindViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }
}
