package com.huwei.sweetmusicplayer.fragments.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author Jayce
 * @date 2015/8/17
 */
public class BaseFragment extends Fragment {

    protected String TAG = getClass().getSimpleName();

    protected Activity mAct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAct = getActivity();
    }
}
