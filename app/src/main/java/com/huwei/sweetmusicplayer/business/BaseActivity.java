package com.huwei.sweetmusicplayer.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.util.ImmersiveUtil;
import com.hwangjr.rxbus.RxBus;

/**
 * 项目中Activity基类，用于对activity的整体控制
 *
 * @author Jayce
 * @date 2015/6/19
 */
public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    private View mStatusView;
    private ViewGroup mRootView;

    protected final String TAG;

    public BaseActivity() {
        TAG = getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImmersiveUtil.immersive(this);

        mContext = this;

        if (isActivityNeedBus()) {
            RxBus.get().register(this);
        }

        if (isNeedWindowBGTransparent()) {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isActivityNeedBus()) {
            RxBus.get().unregister(this);
        }
    }

    protected boolean isNeedWindowBGTransparent() {
        return false;
    }

    public int getStatusBarColor() {
        return getResources().getColor(R.color.status_color);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = LayoutInflater.from(mContext).inflate(layoutResID, null, false);
        handleContentView(view,null);
    }

    @Override
    public void setContentView(View view) {
        handleContentView(view,null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        handleContentView(view, params);
    }

    /**
     * 处理contentView 不要直接复写setcontentView之后调用super
     * @param view
     * @param params
     */
    private void handleContentView(View view, ViewGroup.LayoutParams params) {
        if (isNeedStatusView()) {
            if (view instanceof LinearLayout && ((LinearLayout) view).getOrientation() == LinearLayout.VERTICAL) {
                mRootView = (ViewGroup) view;
            } else {
                mRootView = new LinearLayout(this);
                ((LinearLayout)mRootView).setOrientation(LinearLayout.VERTICAL);

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mRootView.addView(view, layoutParams);
            }

            mStatusView = ImmersiveUtil.createStatusView(this, getStatusBarColor());
            mRootView.addView(mStatusView, 0);
            checkAndSetContentView(mRootView, params);
        } else {
            checkAndSetContentView(view, params);
        }
    }

    private void checkAndSetContentView(View view, ViewGroup.LayoutParams params){
        if (params != null) {
            super.setContentView(view, params);
        } else {
            super.setContentView(view);
        }
    }

    protected boolean isNeedStatusView() {
        return true;
    }

    public void onBackClicked(View view) {
        onBackPressed();
    }

    protected boolean isActivityNeedBus() {
        return false;
    }

    @Override
    public void startActivity(Intent intent) {
        if (this instanceof BottomPlayActivity) {
            try {
                if (BottomPlayActivity.class.isAssignableFrom(Class.forName(intent.getComponent().getClassName()))) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.startActivity(intent);
    }
}
