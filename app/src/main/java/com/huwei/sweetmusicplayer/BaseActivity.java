package com.huwei.sweetmusicplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import static com.huwei.sweetmusicplayer.Permission.CODE_READ_EXTERNAL_STORAGE;
import static com.huwei.sweetmusicplayer.Permission.PERMISSIONS;


/**
 * 项目中Activity基类，用于对activity的整体控制
 *
 * @author Jayce
 * @date 2015/6/19
 */
public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    private boolean hasAdjustActionBar;

    protected String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //状态栏透明 需要在创建SystemBarTintManager 之前调用。
//            setTranslucentStatus(true);
//        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        //使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
//        tintManager.setStatusBarTintResource(R.color.primary_dark);
//
//        // 设置状态栏的文字颜色
//        tintManager.setStatusBarDarkMode(true, this);
//
//        tintManager.setStatusBarAlpha(60);

        //权限处理
        for (String permiss : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(mContext, permiss) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{permiss}, CODE_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        adjustActionBarHeight();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 调整actionBar的高度 满足状态栏沉浸
     */
    public void adjustActionBarHeight() {
        if (!hasAdjustActionBar) {
            View actionBar = findViewById(R.id.actionbar);
            if (actionBar != null) {
                ViewGroup.LayoutParams params = actionBar.getLayoutParams();
                params.height = params.height + getStatusBarHeight();
                actionBar.setLayoutParams(params);
                Log.i(TAG, "adjustActionBarHeight actionBar");
            }
            hasAdjustActionBar = true;

            Log.i(TAG, "adjustActionBarHeight");
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void onBackClicked(View view) {
        onBackPressed();
    }
}
