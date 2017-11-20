package com.huwei.sweetmusicplayer.business.ui.widgets.auto;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * 为了屏蔽一些方法的抽象
 * @author jerry
 * @date 2015-11-23
 */
public abstract class AbsSwipeRefershLayout extends SwipeRefreshLayout {
    public AbsSwipeRefershLayout(Context context) {
        super(context);
    }

    public AbsSwipeRefershLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public final void setOnRefreshListener(OnRefreshListener listener) {
        super.setOnRefreshListener(listener);
    }

    @Override
    public final void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }
}
