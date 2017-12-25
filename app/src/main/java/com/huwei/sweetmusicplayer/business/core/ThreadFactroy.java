package com.huwei.sweetmusicplayer.business.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ezio
 * @date 2017/12/25
 */

public class ThreadFactroy {

    private static ThreadFactroy mInstance = new ThreadFactroy();

    private ExecutorService mMusicOperateExecutor;

    private ThreadFactroy() {

    }

    public static ThreadFactroy get() {
        return mInstance;
    }

    public ExecutorService getMusicOperateExecutor() {
        if (mMusicOperateExecutor == null) {
            mMusicOperateExecutor = Executors.newSingleThreadExecutor();
        }
        return mMusicOperateExecutor;
    }
}
