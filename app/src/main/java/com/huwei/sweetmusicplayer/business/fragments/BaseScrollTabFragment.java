package com.huwei.sweetmusicplayer.business.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.huwei.sweetmusicplayer.business.BaseFragment;
import com.huwei.sweetmusicplayer.business.interfaces.IAdjustListView;
import com.huwei.sweetmusicplayer.business.interfaces.IListViewScroll;
import com.huwei.sweetmusicplayer.ui.widgets.auto.AutoListView;

/**
 * @author jerry
 * @date 2015-12-28
 */
public class BaseScrollTabFragment extends BaseFragment implements IAdjustListView {
    protected AutoListView mAutoListView;
    protected IListViewScroll mIListViewScroll;
    protected int mPageNo;

    private int mPaddingTopHeight;
    private int mlowLimitHeight;  //头部可滚动的高度

    /**
     * ListView滚动 后的监听
     *
     * @param mIListViewScroll
     */
    public void setIListViewScroll(IListViewScroll mIListViewScroll) {
        this.mIListViewScroll = mIListViewScroll;
    }

    public void setFlowHeight(int height) {
        this.mPaddingTopHeight = height;
    }

    public void setlowLimitHeight(int height) {
        this.mlowLimitHeight = height;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAutoListView = new AutoListView(mAct);
        mAutoListView.setRefreshEnable(false);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mAutoListView.setLayoutParams(params);

        //添加一个空白的View 作为header
        View blankView = new View(mAct);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mPaddingTopHeight);
        blankView.setLayoutParams(layoutParams);

        mAutoListView.addHeaderView(blankView);

        mAutoListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mIListViewScroll != null) {
                    View child0 = view.getChildAt(0);
                    if (child0 != null) {
                        int scrollY;
                        //blankView是第一个可见View的情况
                        if (firstVisibleItem == 0) {
                            scrollY = -child0.getTop();
                        } else {
                            scrollY = -child0.getTop() + (firstVisibleItem - 1) * child0.getHeight() + mPaddingTopHeight;
                        }

                        if (scrollY > mlowLimitHeight) {
                            scrollY = mlowLimitHeight;
                        }
                        Log.i(TAG, "scrollY:" + scrollY);
                        mIListViewScroll.scrollY(scrollY);

                        Log.i(TAG, "child0.getTop():" + child0.getTop() + "flowHeight:" + mPaddingTopHeight);
                    }
                }
            }
        });
        return mAutoListView;
    }

    @SuppressLint("NewApi")
    @Override
    public void adjustListView(int offsetY) {
        if (mAutoListView != null) {
            mAutoListView.setSelectionFromTop(mAutoListView.getHeaderViewsCount(),offsetY);
        }
    }
}
