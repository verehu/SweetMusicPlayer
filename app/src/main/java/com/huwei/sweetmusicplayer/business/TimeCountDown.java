package com.huwei.sweetmusicplayer.business;

import android.os.Handler;
import android.os.Process;

import com.huwei.sweetmusicplayer.business.enums.TimeContants;

/**
 * @author Ezio
 * @date 2018/01/08
 */

public class TimeCountDown implements TimeContants {
    private Handler mHandler = new Handler();
    private int[] mItemTimes = new int[]{0, 10 * MINUTE, 20 * MINUTE, 30 * MINUTE, 45 * MINUTE, 60 * MINUTE};
    private int mItem;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Process.killProcess(Process.myPid());
        }
    };

    public void setCountDownItem(int item) {
        mItem = item;

        mHandler.removeCallbacks(mRunnable);
        if (mItem != 0) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, mItemTimes[mItem]);
        }
    }

    public int getSelectItem() {
        return mItem;
    }
}
