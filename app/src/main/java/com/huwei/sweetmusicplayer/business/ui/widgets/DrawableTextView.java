package com.huwei.sweetmusicplayer.business.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.util.LogUtils;


/**
 * 可以调整drawable 宽度和高度的textView   目前只支持有一个drawable的情况
 *
 * @author jerry
 * @date 2016/03/18
 */
public class DrawableTextView extends TextView {

    public static final String TAG = "DrawableTextView";

    private Context mContext;
    private int mDrawbleWidth;
    private int mDrawbleHeight;
    private int mDrawbleRes[] = new int[4];
    private boolean mIsDrawableCenter;

    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        initAttr(attrs);
        adjustDrawablesBound();
    }

    void initAttr(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);

        mDrawbleWidth = a.getDimensionPixelOffset(R.styleable.DrawableTextView_drawableWidth, 0);
        mDrawbleHeight = a.getDimensionPixelOffset(R.styleable.DrawableTextView_drawableHeight, 0);
        mIsDrawableCenter = a.getBoolean(R.styleable.DrawableTextView_isDrawableCenter, false);
        mDrawbleRes[0] = a.getResourceId(R.styleable.DrawableTextView_android_drawableLeft, 0);
        mDrawbleRes[1] = a.getResourceId(R.styleable.DrawableTextView_android_drawableTop, 0);
        mDrawbleRes[2] = a.getResourceId(R.styleable.DrawableTextView_android_drawableRight, 0);
        mDrawbleRes[3] = a.getResourceId(R.styleable.DrawableTextView_android_drawableBottom, 0);

        a.recycle();
    }

    void adjustDrawablesBound() {
        if (mDrawbleWidth > 0 && mDrawbleHeight > 0) {
//            Drawable[] d = getCompoundDrawables();
            Drawable[] t = new Drawable[4];

            for (int i = 0; i < mDrawbleRes.length; i++) {
                if (mDrawbleRes[i] != 0) {

                    Drawable drawable = getResources().getDrawable(mDrawbleRes[i]);

                    if (drawable != null) {

                        t[i] = drawable;
//                        t[i] = (ShapeDrawable) d[i];
//                    t[i] = BitmapUtil.zoomDrawable(drawable, mDrawbleWidth, mDrawbleHeight);
                        t[i].setBounds(0, 0, mDrawbleWidth, mDrawbleHeight);
//                        t[i].setIntrinsicWidth(mDrawbleWidth);
//                        t[i].setIntrinsicHeight(mDrawbleHeight);

                        LogUtils.i(TAG, "(mDrawbleWidth,mDrawbleWidth):" + mDrawbleWidth + "," + mDrawbleHeight + "   w,h:" + t[i].getIntrinsicWidth() + "," + t[i].getIntrinsicHeight());

                        setCompoundDrawables(t[0], t[1], t[2], t[3]);
                        return;
//                    drawable.setBounds(0, 0, mDrawbleWidth, mDrawbleHeight);
                    }
                }
            }
//            setCompoundDrawablesWithIntrinsicBounds(d[0], d[1], d[2], d[3]);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsDrawableCenter) {
            Drawable[] drawables = getCompoundDrawables();
            if (drawables != null) {
                Drawable drawableLeft = drawables[0];
                if (drawableLeft != null) {
                    float textWidth = getPaint().measureText(getText().toString());
                    int drawablePadding = getCompoundDrawablePadding();
                    int drawableWidth = 0;
                    drawableWidth = drawableLeft.getIntrinsicWidth();
                    float bodyWidth = textWidth + drawableWidth + drawablePadding;
                    canvas.translate((getWidth() - bodyWidth) / 2, 0);
                }
            }
        }
        super.onDraw(canvas);
    }
}
