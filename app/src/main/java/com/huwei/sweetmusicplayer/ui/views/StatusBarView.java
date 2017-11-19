package com.huwei.sweetmusicplayer.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huwei.sweetmusicplayer.BaseActivity;
import com.huwei.sweetmusicplayer.util.Utils;

/**
 * Created by huwei on 17-4-27.
 */
public class StatusBarView extends View{

    private static final int[] ANDROID_ATTR = { android.R.attr.background };
    private static final int ATTR_ANDROID_BACKCOLOR= 0;

    private int mBackgroundColor;

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, ANDROID_ATTR);
        mBackgroundColor = ta.getColor(ATTR_ANDROID_BACKCOLOR, Color.TRANSPARENT);
        ta.recycle();

        setBackgroundColor(mBackgroundColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), Utils.getStatusBarHeight());
    }
}
