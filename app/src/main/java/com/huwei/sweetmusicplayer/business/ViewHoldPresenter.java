package com.huwei.sweetmusicplayer.business;

import android.content.Context;

/**
 * Created by huwei on 18-1-24.
 */

public abstract class ViewHoldPresenter<T extends BaseView> {
    protected final String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected T mView;

    public ViewHoldPresenter(Context context, T view) {
        this.mContext = context;
        this.mView = view;

        mView.setPresenter(this);
    }
}
