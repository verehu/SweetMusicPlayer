package com.huwei.sweetmusicplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * Created by huwei on 17-4-11.
 */

public class Permission {
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final int CODE_READ_EXTERNAL_STORAGE = 0;

    public static boolean isPermissionGranted(String permission){
        return ActivityCompat.checkSelfPermission(SweetApplication.get(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isPermissionGranted(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        return permissions[requestCode].equals(requestCode)
                && grantResults[requestCode] == PackageManager.PERMISSION_GRANTED;
    }
}
