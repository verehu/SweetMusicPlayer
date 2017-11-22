package com.huwei.sweetmusicplayer.util;

public class LogUtil {
    // Use BuildConfig.DEBUG cause lots of headache. Stupid.
    public static boolean DEBUG = true;

    /**
     * TAG标签
     */
    private final static String TAG = LogUtil.class.getSimpleName();

    public static void i(String msg) {
        if (DEBUG)
            android.util.Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (DEBUG)
            android.util.Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (DEBUG)
            android.util.Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (DEBUG)
            android.util.Log.v(TAG, msg);
    }


    public static void d(final String tag, final String info) {
        if (DEBUG) {
            android.util.Log.d(tag, info);
        }
    }

//    public static void d(final String tag, final JSONObject json) {
//        if (DEBUG) {
//            android.util.Log.d(tag, json.toString());
//        }
//    }

    public static void v(final String tag, final String info) {
        if (DEBUG) {
            android.util.Log.v(tag, info);
        }
    }
    public static void i(final String tag, final String info) {
        if (DEBUG) {
            android.util.Log.i(tag, info);
        }
    }

    public static void e(final String tag, final String info) {
        android.util.Log.e(tag, info);
    }

    public static void w(final String tag, final String info) {
        android.util.Log.w(tag, info);
    }
}