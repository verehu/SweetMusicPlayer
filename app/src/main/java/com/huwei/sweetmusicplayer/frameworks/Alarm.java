package com.huwei.sweetmusicplayer.frameworks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;

import com.huwei.sweetmusicplayer.AppContextHolder;
import com.huwei.sweetmusicplayer.util.LogUtils;

/**
 * Created by huwei on 18-1-10.
 */

public class Alarm {
    private static String TAG = "Alarm";

    private AlarmManager mAlarmManager;

    private static Alarm mInstance;

    private Alarm() {
        mAlarmManager = (AlarmManager) AppContextHolder.getAppContext().getSystemService(Context.ALARM_SERVICE);
    }

    public static Alarm get() {
        synchronized (Alarm.class) {
            if (mInstance == null) {
                mInstance = new Alarm();
            }
        }
        return mInstance;
    }

    /**
     * 设置精准alarm计时
     *
     * @param pendingIntent
     */
    public void set(long intervalMillis, PendingIntent pendingIntent) {
        mAlarmManager.cancel(pendingIntent);

        long triggerAtMillis = SystemClock.elapsedRealtime() + intervalMillis;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
        } else {
            mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);
        }

        long elapsedRealtime = SystemClock.elapsedRealtime();
        LogUtils.d(TAG, "triggerAtMillis:" + triggerAtMillis + "     elapsedRealtime:" + elapsedRealtime + "     diff:" + (triggerAtMillis - elapsedRealtime));
        //LogUtils.d(LogUtils.HEARTBEAT_TAG, "  dump alarm:" + PushUtils.getAlarm());
    }
}
