package com.huwei.sweetmusicplayer.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huwei.sweetmusicplayer.business.BaseFragment;
import com.huwei.sweetmusicplayer.ui.widgets.auto.AutoListView;

/**
 * 一个AutoListView视图的Fragment
 * @author jerry
 * @date 2016/01/04
 */
public abstract class AutoListFragment extends BaseFragment{

    protected AutoListView mAutoListView;
    protected int mPageNo;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAutoListView = new AutoListView(mAct);
        mAutoListView.setRefreshEnable(false);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mAutoListView.setLayoutParams(params);

        return mAutoListView;
    }
}
