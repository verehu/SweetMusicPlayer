package com.huwei.sweetmusicplayer.business.recievers;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huwei.sweetmusicplayer.business.MainActivity;

import java.util.List;

/**
 * @author Jayce
 * @date 2015/6/19
 */
public class BringToFrontReceiver extends BroadcastReceiver {
    public static final String TAG = "BringToFrontReceiver";
    public static final String ACTION_BRING_TO_FRONT = "com.huwei.action.BringToFront";

    public BringToFrontReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "#######################recieved###########################");

        //获取ActivityManager
        ActivityManager mAm = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        //获得当前运行的task
        List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo rti : taskList) {
            //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
            if (rti.topActivity.getPackageName().equals(context.getPackageName())) {
                Log.i(TAG, "#######################front###########################");
                mAm.moveTaskToFront(rti.id, ActivityManager.MOVE_TASK_WITH_HOME);
                return;
            }
        }
        //若没有找到运行的task，用户结束了task或被系统释放，则重新启动mainactivity
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(resultIntent);
        Log.i(TAG, "#######################end###########################");
    }
}


