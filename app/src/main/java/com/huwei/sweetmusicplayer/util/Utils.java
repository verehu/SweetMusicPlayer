package com.huwei.sweetmusicplayer.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.huwei.sweetmusicplayer.SweetApplication;

import java.util.List;

/**
 * @author jerry
 * @date 2016-06-23
 */
public class Utils {
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = SweetApplication.get().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = SweetApplication.get().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static Resources getResources() {
        return SweetApplication.get().getResources();
    }

    /**
     * 获取没动画的intent
     *
     * @return
     */
    public static Intent getActIntent(Context context, Class actClass) {
        Intent intent = new Intent(context, actClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return intent;
    }

    /**
     * 隐藏软键盘
     *
     * @param act
     */
    public static void hideSoftInput(Activity act) {
        try {
            if (act == null) {
                return;
            }
            final View v = act.getWindow().peekDecorView();
            if (v != null && v.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) act
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context) {
        try {
            InputMethodManager m = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列表的数据 的第一个 可以被取出 并且 不是null
     *
     * @param list
     * @return
     */
    public static boolean canFetchFirst(List list) {
        return list != null && list.size() > 0 && list.get(0) != null;
    }

    public static void safeClose(Cursor cursor) {
        try {
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
