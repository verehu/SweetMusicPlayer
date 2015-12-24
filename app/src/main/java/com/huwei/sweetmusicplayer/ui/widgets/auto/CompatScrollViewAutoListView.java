package com.huwei.sweetmusicplayer.ui.widgets.auto;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 兼容ScrollView的AutoListView 仅仅支持下拉刷新    外部嵌套的ScrollView 需要在初始化时滚动到头部
 *
 * @author jerry
 * @date 2015-12-24
 */
public class CompatScrollViewAutoListView extends AutoListView {
    public CompatScrollViewAutoListView(Context context) {
        this(context, null);
    }

    public CompatScrollViewAutoListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompatScrollViewAutoListView(Context context, AttributeSet attrs, int defStyle) {
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

    /**
     * 禁止下拉刷新
     * @param refreshEnable
     */
    @Override
    public void setRefreshEnable(boolean refreshEnable) {
        super.setRefreshEnable(false);
    }
}
