package com.huwei.sweetmusicplayer.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取屏幕高度和宽度
 * Created by huwei on 15-1-21.
 */
public class WindowTool {
    public static int getHeight(Context context){
        WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    
    public static int getWidth(Context context){
        WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getActivityHeight(Activity activity){
        return  activity.getWindow().getDecorView().getHeight();
    }
}
