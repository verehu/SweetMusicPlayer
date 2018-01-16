package com.huwei.sweetmusicplayer.business.fragments.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.huwei.sweetmusicplayer.business.BottomPlayActivity;

/**
 * @author Jayce
 * @date 2015/8/17
 */
public class BaseFragment extends Fragment {

    protected final String TAG;

    protected Activity mAct;
    protected Context mContext;

    public BaseFragment() {
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAct = getActivity();
        mContext = getContext();
    }

    @Override
    public void startActivity(Intent intent) {
        if (this.getActivity() instanceof BottomPlayActivity) {
            try {
                if (BottomPlayActivity.class.isAssignableFrom(Class.forName(intent.getComponent().getClassName()))) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.startActivity(intent);
    }
}
