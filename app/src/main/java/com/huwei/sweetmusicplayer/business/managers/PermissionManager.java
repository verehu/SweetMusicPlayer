package com.huwei.sweetmusicplayer.business.managers;

/**
 * Created by huwei on 17-4-25.
 */

public class PermissionManager {
    private static PermissionManager mInstance;

    private PermissionManager() {

    }

    public static PermissionManager get(){
        if (mInstance == null) {
            mInstance = new PermissionManager();
        }
        return mInstance;
    }
}
