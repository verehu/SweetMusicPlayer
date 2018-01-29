package com.huwei.sweetmusicplayer.ui.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.util.BitmapUtil;
import com.huwei.sweetmusicplayer.util.LogUtils;

import org.apache.commons.lang.StringUtils;

/**
 * 渐变Toolbar
 *
 * @author jerry
 * @date 2015-12-16
 */
public class GradientToolbar extends FrameLayout implements AbsListView.OnScrollListener, VerticalScrollView.OnScrollChangeListener {
    public static final String TAG = "GradientToolbar";

    public static final int ALPHA = 255;

    private Context mContext;

    private ImageView mIv_toolbarBg;
    private View mHeaderView;
    private int mGradientHeight; //渐变高度
    private Drawable mToolbarBgDrawable; //toolbar背景
    private Toolbar mToolbar;
    private String mGradientTitle; //渐变过程中的title
    private String mTitle; //普通状态下的title

    public GradientToolbar(Context context) {
        this(context, null);
    }

    public GradientToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_gradient_toolbar, this);

        initView();
        initData();
    }

    void initView() {
        mIv_toolbarBg = (ImageView) findViewById(R.id.iv_toolbar_bg);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    void initData() {

    }

    /**
     * 生成渐变高度
     */
    public void bindHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    /**
     * @param bitmap
     */
    public void setToolbarBg(Bitmap bitmap) {
        if (mIv_toolbarBg != null) {
            mToolbarBgDrawable = BitmapUtil.bitmap2drawable(bitmap);
            mToolbarBgDrawable.mutate().setAlpha(0);
            mIv_toolbarBg.setImageDrawable(mToolbarBgDrawable);
        }
    }

    public void setGradientTitle(String mTitle) {
        this.mGradientTitle = mTitle;
    }

    public void setTitle(int resString) {
        setTitle(getResources().getString(resString));
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;

        if (mToolbar != null) {
            mToolbar.setTitle(mTitle);
        }
    }

    /**
     * 绑定ListView  直接调用adjustHeaderViewAndTitle()
     */
    @Deprecated
    public void bindListView(ListView view) {
        if (view != null) {
            view.setOnScrollListener(this);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * 当header是直接嵌套在ListView的时候才能设置这个监听
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        LogUtils.i(TAG, "firstVisibleItem:" + firstVisibleItem);
        adjustHeaderViewAndTitle(firstVisibleItem==0);
    }

    public void adjustHeaderViewAndTitle(){
        adjustHeaderViewAndTitle(true);
    }

    private void adjustHeaderViewAndTitle(boolean isHeadViewVisiable) {
        if (mHeaderView != null) {
            mGradientHeight = mHeaderView.getMeasuredHeight() - getMeasuredHeight();
            if (mGradientHeight > 0 && mIv_toolbarBg != null) {
                float alpha;
                int offsetY=0;
                if (isHeadViewVisiable) {
                    int toolbarLoc[] = new int[2];
                    int headerLoc[] = new int[2];
                    mHeaderView.getLocationOnScreen(toolbarLoc);
                    getLocationOnScreen(headerLoc);

                    int toolbarY = toolbarLoc[1];
                    int headerY = headerLoc[1];

                    offsetY = headerY - toolbarY;
                    alpha = offsetY / (float) mGradientHeight;
                } else {
                    alpha = 1;
                }
                if (alpha <= 0) {
                    alpha = 0;
                    mToolbar.setTitle(mTitle);
                } else
                    if (alpha >= 1) {
                    alpha = 1;
                    if (StringUtils.isNotEmpty(mGradientTitle)) {
                        mToolbar.setTitle(mGradientTitle);
                    }
                } else {
                    mToolbar.setTitle(mTitle);
                }

                if (mToolbarBgDrawable != null) {
                    Log.i(TAG, "setBitmap");
                    mToolbarBgDrawable.mutate().setAlpha((int) (ALPHA * alpha));
                    mIv_toolbarBg.setImageDrawable(mToolbarBgDrawable);
                }

                mHeaderView.setAlpha(1 - alpha);

                LogUtils.i(TAG, offsetY + ":" + mGradientHeight + " = toolbar bg alpha:" + alpha);
            }
        }
    }


    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        adjustHeaderViewAndTitle();
    }
}
