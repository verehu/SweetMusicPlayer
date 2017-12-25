package com.huwei.sweetmusicplayer.business.core;

/**
 * @author Ezio
 * @date 2017/12/25
 */

public abstract class Task implements Runnable {

    private boolean mIsCancel;

    @Override
    public void run() {
        if (!mIsCancel) {
            onWork();
        }
    }

    protected abstract void onWork();

    private boolean isCanceled() {
        return mIsCancel;
    }

    public void cancel() {
        mIsCancel = true;
    }
}
