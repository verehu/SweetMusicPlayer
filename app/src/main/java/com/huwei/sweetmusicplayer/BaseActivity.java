package com.huwei.sweetmusicplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
    public static final boolean IMMERSE_SWITCH = true;

    protected Context mContext;
    private boolean hasAdjustActionBar;
    private View mStatusView;
    private ViewGroup mRootView;

    protected String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        mRootView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.activity_wrapper, null, true);

        mStatusView = new View(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        mStatusView.setLayoutParams(layoutParams);
        mStatusView.setBackgroundColor(getStatusBarColor());
    }

    public int getStatusBarColor() {
        return getResources().getColor(R.color.primary);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = LayoutInflater.from(mContext).inflate(layoutResID, null, true);
        setContentView(view, view.getLayoutParams());
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, view.getLayoutParams());
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        super.setContentView(mRootView);

//        mRootView.addView(mStatusView);

        if (params == null ) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        mRootView.addView(view, params);
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
