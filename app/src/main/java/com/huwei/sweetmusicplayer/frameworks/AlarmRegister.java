package com.huwei.sweetmusicplayer.frameworks;

import android.app.PendingIntent;
import android.content.Intent;

import com.huwei.sweetmusicplayer.AppContextHolder;

import static com.huwei.sweetmusicplayer.business.core.MusicControllerService.PLAYPRO_EXIT;

/**
 * Created by huwei on 18-1-10.
 */

public class AlarmRegister {
    public static PendingIntent getStopIntent() {
        Intent exitIntent = new Intent(PLAYPRO_EXIT);
        PendingIntent exitPendingIntent = PendingIntent.getBroadcast(AppContextHolder.getAppContext(), 0, exitIntent, 0);
        return exitPendingIntent;
    }
}
