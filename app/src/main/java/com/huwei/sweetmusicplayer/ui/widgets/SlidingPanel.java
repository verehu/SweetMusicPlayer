package com.huwei.sweetmusicplayer.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.huwei.sweetmusicplayer.R;


/**
 * Created by huwei on 14-12-19.
 */
public class SlidingPanel extends FrameLayout implements GestureDetector.OnGestureListener {
    private static final String TAG = "SlidingPanel";
    private static final String GTAG = "GTAG";    //手势TAG

    private View mHandle;
    private SmoothScrollLinearLayout mContent;

    public static int SNAP_VELOCITY = 600;  //最小的滑动速率
    private int mContentRangeTop;   //content在父布局的移动范围
    private int mContentRangeBottom;
    private int mDownY;     //ACTION_DOWN时y的坐标
    private int mMaxFlingVelocity;

    public static boolean mTracking = false;    //是否正在滑动

    private GestureDetector mGestureDetector;   //检测手势辅助类
    private VelocityTracker mVelocityTracker = null;
    boolean isInit;


    public SlidingPanel(Context context) {
        this(context, null);
    }

    public SlidingPanel(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGestureDetector = new GestureDetector(context, this);
        mMaxFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHandle = findViewById(R.id.bottom_bar);

        if (mHandle == null) {
            throw new IllegalArgumentException("The handle attribute is required and must refer "
                    + "to a valid child.");
        }

        mContent = (SmoothScrollLinearLayout) findViewById(R.id.content);

        if (mContent == null) {
            throw new IllegalArgumentException("The content attribute is required and must refer "
                    + "to a valid child.");
        }

        Log.i(TAG, "***********handle:" + mHandle + "************content:" + mContent + "**********");

        mHandle.setFocusable(true);
        mHandle.setClickable(true);
        mHandle.setOnTouchListener(touchListener);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        View handle = mHandle;

        measureChild(handle, widthMeasureSpec, heightMeasureSpec);

        mContentRangeBottom = heightSpecSize;
        measureChild(mContent, MeasureSpec.makeMeasureSpec(widthSpecSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mContentRangeBottom, MeasureSpec.EXACTLY));

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!isInit) {
            mContent.scrollTo(0, -mContentRangeBottom);
            isInit = true;
        }

        long drawingTime = getDrawingTime();

        final View handle = mHandle;
        drawChild(canvas, handle, drawingTime);

        final View content = mContent;
        drawChild(canvas, content, drawingTime);
    }

    /**
     * 点击时打开Content*
     */
    private void open() {
        mContent.smoothScrollTo(0, 0);
    }

    /**
     * 关闭Content*
     */
    public void close() {
        mContent.smoothScrollTo(0, -mContentRangeBottom);
    }

    /**
     * content是否展开*
     */
    public boolean isExpanded() {
        return mContent.isExpanded();
    }

    /**
     * ACTION_UP时 contentView不是停靠在屏幕边缘（在屏幕中间）时，调整contentView的位置*
     * @param vY y方向上的速率
     */
    private void adjustContentView(float vY) {
        //根据速率判断
        if (vY < -SNAP_VELOCITY) {
            mContent.smoothScrollTo(0, 0);
        }else {
            //根据现在的位置调整
            final int top = -mContent.getScrollY();

            //切割父容器，分成3等份
            final int perRange = (mContentRangeBottom - mContentRangeTop) / 3;
            if (isExpanded()) {
                //小于1/3
                if (top < perRange + mContentRangeTop) {
                    mContent.smoothScrollTo(0, 0);
                } else {
                    mContent.smoothScrollTo(0, -mContentRangeBottom);
                }
            } else {
                //小于2/3
                if (top < mContentRangeTop + perRange * 2) {
                    mContent.smoothScrollTo(0, 0);
                } else {
                    mContent.smoothScrollTo(0, -mContentRangeBottom);
                }
            }
        }

    }

    /**
     * 按下时触发*
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(GTAG, "onDown:" + e.getY());
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(GTAG, "onShowPress");
    }

    /**
     * 轻轻点击*
     *
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(GTAG, "onSingleTapUp---mExpanded:" + isExpanded());
        if (!isExpanded()) {
            open();
        }
        return false;
    }

    /**
     * 滚动时触发
     *
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        int touchY = (int) e2.getY();
        Log.i(GTAG, "onScroll:" + mContent.getY());
        scrollTo(touchY - mDownY + (isExpanded() ? mContentRangeTop : mContentRangeBottom));
        return false;
    }

    /**
     * 长按*
     *
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(GTAG, "onLongPress");
    }

    /**
     * 瞬时滑动*
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(GTAG, "onFling");
        return false;
    }

    /**
     * 获取速度追踪器
     *
     * @return
     */
    private VelocityTracker getVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        return mVelocityTracker;
    }

    /**
     * 回收速度追踪器
     */
    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 滚动到某个位置
     * @param fY    和正常的scrollTo（）方法相反
     */
    public void scrollTo(int fY) {
        if (fY < 0) {
            mContent.scrollTo(0, 0);
        } else if (fY > mContentRangeBottom) {
            mContent.scrollTo(0, -mContentRangeBottom);
        } else {
            mContent.scrollTo(0, -fY);
        }
    }

    OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final View handle = mHandle;
            //1.获取速度追踪器
            getVelocityTracker();
            //2.将当前事件纳入到追踪器中
            mVelocityTracker.addMovement(event);

            int pointId = -1;

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mDownY = (int) event.getY();
                pointId = event.getPointerId(0);
//                Log.i(GTAG, "mDownY:" + mDownY);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mTracking = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mTracking = false;

                //3.计算当前速度
                mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                //获取x y方向上的速度
                float vY = mVelocityTracker.getYVelocity(pointId);

                adjustContentView(vY);
            }
//            Log.i(GTAG, "onTouch:" + event.getAction() + " y:" + event.getY());
            return mGestureDetector.onTouchEvent(event);
        }

    };
}
