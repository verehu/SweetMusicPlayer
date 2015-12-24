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

import org.apache.commons.lang.StringUtils;

/**
 * 渐变Toolbar
 *
 * @author jerry
 * @date 2015-12-16
 */
public class GradientToolbar extends FrameLayout implements AbsListView.OnScrollListener, View.OnScrollChangeListener {
    public static final String TAG = "GradientToolbar";

    public static final int ALPHA = 255;

    private Context mContext;

    private ImageView mIv_toolbarBg;
    private View mHeaderView;
    private int mGradientHeight; //渐变高度
    private Drawable mToolbarBgDrawable; //toolbar背景
    private Toolbar mToolbar;
    private String mGradientTitle; //渐变过程中的title

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
        mToolbar = (Toolbar) findViewById(R.id.actionbar);
    }

    void initData() {
        mToolbar.setTitle("专辑");
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

    /**
     * 绑定ListView
     */
    public void bindListView(ListView view) {
        if (view != null) {
            view.setOnScrollListener(this);
        }
    }

    /**
     * 绑定ScrollView
     * @param view
     */
    public void bindScrollView(VerticalScrollView view) {
        if (view != null) {
            view.setOnScrollChangeListener(this);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        adjustHeaderViewAndTitle();
    }

    @Override
    public void onScrollChange(View view, int i, int i2, int i3, int i4) {
        adjustHeaderViewAndTitle();
    }

    private void adjustHeaderViewAndTitle() {
        if (mHeaderView != null) {
            mGradientHeight = mHeaderView.getMeasuredHeight() - getMeasuredHeight();
            if (mGradientHeight > 0 && mIv_toolbarBg != null) {
                int toolbarLoc[] = new int[2];
                int headerLoc[] = new int[2];
                mHeaderView.getLocationOnScreen(toolbarLoc);
                getLocationOnScreen(headerLoc);

                int toolbarY = toolbarLoc[1];
                int headerY = headerLoc[1];

                int offsetY = headerY - toolbarY;
                float alpha = offsetY / (float) mGradientHeight;
                if (alpha <= 0) {
                    alpha = 0;
                    mToolbar.setTitle("专辑");
                } else if (alpha >= 1) {
                    alpha = 1;
                    if (StringUtils.isNotEmpty(mGradientTitle)) {
                        mToolbar.setTitle(mGradientTitle);
                    }
                } else {
                    mToolbar.setTitle("专辑");
                }

                if (mToolbarBgDrawable != null) {
                    Log.i(TAG, "setBitmap");
                    mToolbarBgDrawable.mutate().setAlpha((int) (ALPHA * alpha));
                    mIv_toolbarBg.setImageDrawable(mToolbarBgDrawable);
                }

                mHeaderView.setAlpha(1 - alpha);
                Log.i(TAG, offsetY + ":" + mGradientHeight + " = toolbar bg alpha:" + alpha);
            }
        }
    }


}
