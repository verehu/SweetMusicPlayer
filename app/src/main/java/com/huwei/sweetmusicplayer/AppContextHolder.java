package com.huwei.sweetmusicplayer;

import android.content.Context;

/**
 * Created by huwei on 18-1-10.
 */

public class AppContextHolder {
    private static Context sAppContext;

    public static Context getAppContext() {
        return sAppContext;
    }

    public static void setContext(Context context) {
        sAppContext = context;
    }
}
