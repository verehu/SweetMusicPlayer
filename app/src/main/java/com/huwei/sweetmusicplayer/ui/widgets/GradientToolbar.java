package com.huwei.sweetmusicplayer.ui.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.huwei.sweetmusicplayer.R;

/**
 * @author jerry
 * @date 2015-12-16
 */
public class GradientToolbar extends FrameLayout implements AbsListView.OnScrollListener {
    public static final String TAG = "GradientToolbar";

    private Context mContext;

    private ImageView mIv_toolbarBg;
    private int mGradientHeight; //渐变高度
    private Bitmap mToolbarBgBitmap; //toolbar背景


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

    }

    void initView() {
        mIv_toolbarBg = (ImageView) findViewById(R.id.iv_toolbar_bg);
    }

    /**
     * 设置渐变的高度   就是头部减去toolbar高度
     *
     * @param mGradientHeight
     */
    public void setGradientHeight(int mGradientHeight) {
        this.mGradientHeight = mGradientHeight;
    }

    /**
     * @param bitmap
     */
    public void setToolbarBg(Bitmap bitmap) {
        if (mIv_toolbarBg != null) {
            mIv_toolbarBg.setImageBitmap(bitmap);
            mIv_toolbarBg.setAlpha(0);
        }
    }

    /**
     * 绑定ListView
     */
    public void bindListView(ListView view) {
        if (view != null) {
            view.setOnScrollListener(this);
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int scrollY = view.getScrollY();

        if (mGradientHeight != 0 && mIv_toolbarBg != null) {
            Log.i(TAG, "toolbar bg alpha:" + scrollY / (float) mGradientHeight);
            mIv_toolbarBg.setAlpha(scrollY / (float) mGradientHeight);
        }
    }
}
