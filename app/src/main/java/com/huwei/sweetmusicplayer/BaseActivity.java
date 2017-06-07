package com.huwei.sweetmusicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huwei.sweetmusicplayer.util.ImmersiveUtil;

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

    protected String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImmersiveUtil.immersive(this);

        mContext = this;
    }

    public int getStatusBarColor() {
        return getResources().getColor(R.color.status_color);
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
        if (isNeedStausView()) {
            if (view instanceof LinearLayout && ((LinearLayout) view).getOrientation() == LinearLayout.VERTICAL) {
                mRootView = (ViewGroup) view;
                checkAndsetContentView(mRootView, params);
            } else {
                mRootView = new LinearLayout(this);
                ((LinearLayout)mRootView).setOrientation(LinearLayout.VERTICAL);

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mRootView.addView(view, layoutParams);
                super.setContentView(mRootView);
            }

            mStatusView = ImmersiveUtil.createStatusView(this, getStatusBarColor());
            mRootView.addView(mStatusView, 0);
        } else {
            checkAndsetContentView(view, params);
        }
    }

    private void checkAndsetContentView(View view, ViewGroup.LayoutParams params){
        if (params != null) {
            super.setContentView(view, params);
        } else {
            super.setContentView(view);
        }
    }

    protected boolean isNeedStausView() {
        return true;
    }

    public void onBackClicked(View view) {
        onBackPressed();
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
