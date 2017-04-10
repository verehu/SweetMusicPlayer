package com.huwei.sweetmusicplayer.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author jerry
 * @date 2016/01/17
 */
public class FragmentUtil {

    public static void replace(FragmentActivity act, int layoutId, Fragment fragment) {
        replace(act, layoutId, fragment, true);
    }

    public static void replace(FragmentActivity act, int layoutId, Fragment fragment, boolean isNeedBack) {
        FragmentManager fragmentManager = act.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (isNeedBack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(layoutId, fragment);
        transaction.commit();
    }
}
