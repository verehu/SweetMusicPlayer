package com.huwei.sweetmusicplayer.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.huwei.sweetmusicplayer.R;


/**
 * Created by huwei on 14-12-19.
 */
public class SlidingPanel extends LinearLayout implements GestureDetector.OnGestureListener {
    private static final String TAG = "SlidingPanel";
    private static final String GTAG = "GTAG";    //手势TAG

    private View mHandle;
    private View mContent;

    private int mContentRangeTop;   //content在父布局的移动范围
    private int mContentRangeBottom;

    private int mMaxScrollY;
    private int mDownY;     //ACTION_DOWN时y的坐标
    private boolean mExpanded = false;  //是否展开
    public static boolean mTracking = false;    //是否正在滑动


    private static final int ANIMATION_DURATION = 1000;   //  从底部到上面需要1s
    public static final int DURATION = 1500;   //满屏滑动时间
    public static final int OPEN_ANIM_DURATION = 1000;
    public static int SNAP_VELOCITY = 600;  //最小的滑动速率

    private GestureDetector mGestureDetector;   //检测手势辅助类

    private Scroller mScroller;


    public SlidingPanel(Context context) {
        this(context, null);
    }

    public SlidingPanel(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGestureDetector = new GestureDetector(context, this);
        mScroller = new Scroller(context);
        setOrientation(LinearLayout.VERTICAL);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHandle = findViewById(R.id.bottom_bar);

        if (mHandle == null) {
            throw new IllegalArgumentException("The handle attribute is required and must refer "
                    + "to a valid child.");
        }

        mContent = findViewById(R.id.content);

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

        mMaxScrollY =   heightSpecSize;
        measureChild(mContent, MeasureSpec.makeMeasureSpec(widthSpecSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mMaxScrollY, MeasureSpec.EXACTLY));

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);


        //获取自身的宽高
        final int width = r - l;
        final int height = b - t;

        mContentRangeTop = 0;
        mContentRangeBottom = b - t;

        final View handle = mHandle;
        int childHeight = handle.getMeasuredHeight();
        int childWidth = handle.getMeasuredWidth();

        handle.layout(0, height - childHeight, childWidth, height);

        final View content = mContent;
        if (!mExpanded) {
            mContent.layout(0, mContentRangeBottom, content.getMeasuredWidth(), mContentRangeBottom + content.getMeasuredHeight());
        } else {
            mContent.layout(0, mContentRangeTop, content.getMeasuredWidth(), mContentRangeTop + content.getMeasuredHeight());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        long drawingTime = getDrawingTime();

        final View handle = mHandle;
        drawChild(canvas, handle, drawingTime);

        final View content = mContent;
        drawChild(canvas, content, drawingTime);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mTracking = true;
                int nowY = (int) event.getY();

                moveContent(nowY - mDownY);
                break;
            case MotionEvent.ACTION_UP:
                mTracking = false;
                adjustContentView();
                break;
        }
        return mExpanded;
    }


    /**
     * 停止滑动
     *
     * @param
     */
    private void stopTracking() {
        //判断content是展开还是收缩
        updateExpanded();
    }

    /**
     * 更新mExpanded状态
     */
    private void updateExpanded() {
        if (mContent.getTop() <= mContentRangeTop) {
            mExpanded = true;
        } else {
            mExpanded = false;
        }
    }

    /**
     * move content到指定位置
     *
     * @param position
     */
    private void moveContent(int position) {


        Log.i(GTAG, "*********move Content:position" + position + "********** content LastY:"+mContent.getTop()+"*******content getScrollY:"+mContent.getScrollY());
        //不能移出上边界
        if (position < mContentRangeTop) {
            position = mContentRangeTop;
        } else if (position > mContentRangeBottom) {
            position = mContentRangeBottom;
        }

        final View content = mContent;
        final int top = (int) mContent.getTop();
        final int deltaY = position - top;

//        content.layout(0, position, content.getWidth(), position + content.getHeight());
        content.scrollTo(0,position);
    }

    //移动Content
    private void doAnimation(int nowY, final int targetY) {
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
        //BounceInterpolator bounceInterpolator=new BounceInterpolator();
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, targetY - nowY);
        animation.setDuration(ANIMATION_DURATION * Math.abs(targetY - nowY) / mContentRangeBottom);
        animation.setInterpolator(accelerateInterpolator);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Log.i(TAG,"onAnimationEnd");
                mContent.clearAnimation();
                mContent.layout(0, targetY, mContent.getMeasuredWidth(), targetY + mContent.getMeasuredHeight());

                stopTracking();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mContent.startAnimation(animation);

    }

    /**
     * 点击时打开Content*
     */
    private void open() {
        doAnimation(mContentRangeBottom, 0);
        //stopTracking();
    }

    /**
     * 关闭Content*
     */
    public void close() {
        doAnimation(0, mContentRangeBottom);
        //stopTracking();
    }

    /**
     * content是否展开*
     */
    public boolean isExpanded() {
        return mExpanded;
    }

    /**
     * ACTION_UP时 contentView不是停靠在屏幕边缘（在屏幕中间）时，调整contentView的位置*
     */
    private void adjustContentView() {
        final int top = mContent.getTop();

        //切割父容器，分成3等份
        final int perRange = (mContentRangeBottom - mContentRangeTop) / 3;
        if (mExpanded) {
            //小于1/3
            if (top < perRange + mContentRangeTop) {
                doAnimation(top, 0);
            } else {
                doAnimation(top, mContentRangeBottom);
            }
        } else {
            //小于2/3
            if (top < mContentRangeTop + perRange * 2) {
                doAnimation(top, 0);
            } else {
                doAnimation(top, mContentRangeBottom);
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
        Log.i(GTAG, "onSingleTapUp");
        if (!mExpanded) {
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
        moveContent(touchY - mDownY + (mExpanded ? mContentRangeTop : mContentRangeBottom));
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

    public void smoothScrollTo(int fY) {
        if (fY < 0) {
            smoothScrollTo(0, 0);
        } else if (fY > mMaxScrollY) {
            smoothScrollTo(0, mMaxScrollY);
        } else {
            smoothScrollTo(0, mMaxScrollY);
        }
    }

    //    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - getScrollX();
        int dy = fy - getScrollY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, Math.abs(dx * DURATION / mMaxScrollY));
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final View handle = mHandle;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                final int top = handle.getTop();
                mDownY = (int) event.getY();

                Log.i(GTAG, "mDownY:" + mDownY);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mTracking = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mTracking = false;
                adjustContentView();
            }
            Log.i(GTAG, "onTouch:" + event.getAction() + " y:" + event.getY());
            return mGestureDetector.onTouchEvent(event);
        }

    };
}
