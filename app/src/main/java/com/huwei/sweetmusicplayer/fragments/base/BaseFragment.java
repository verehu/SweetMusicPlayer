package com.huwei.sweetmusicplayer.fragments.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.huwei.sweetmusicplayer.BottomPlayActivity;

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
