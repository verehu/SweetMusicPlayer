package com.huwei.sweetmusicplayer.ui.widgets.auto;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 带谷歌下拉刷新的AutoListView
 * @author jerry
 * @date 2015-11-23
 */
public class AutoSwipeRefreshListView extends AbsSwipeRefershLayout implements IPullRefershBase {
    public static final String TAG = "AutoSwipeRefreshLayout";
    private IPullRefershBase.OnRefreshListener mOnRefreshListener;
    private IPullRefershBase.OnLoadListener mOnLoadListener;
    private AutoListView mAutoListView;

    public AutoSwipeRefreshListView(Context context) {
        this(context, null);
    }

    public AutoSwipeRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mAutoListView = new AutoListView(context);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mAutoListView, layoutParams);
    }

    @Override
    public void setLoadEnable(boolean loadEnable) {
        mAutoListView.setLoadEnable(loadEnable);
    }

    @Override
    public void setOnRefreshListener(IPullRefershBase.OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AutoSwipeRefreshListView.this.onRefresh();
            }
        });
    }

    @Override
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
        mAutoListView.setOnLoadListener(onLoadListener);
    }

    @Override
    public void onRefreshComplete() {
        setRefreshing(false);
    }

    @Override
    public void onLoadComplete(boolean hasMore) {
        mAutoListView.onLoadComplete(hasMore);
    }

    @Override
    public void onRefresh() {
        if(mOnRefreshListener!=null){
            mAutoListView.preRefresh();
            mOnRefreshListener.onRefresh();
        }
    }

    @Override
    public void onLoad() {
        if(mOnLoadListener!=null){
            mOnLoadListener.onLoad();
        }
    }

    public ListView getListView(){
        return mAutoListView;
    }
}
