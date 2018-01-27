package com.huwei.sweetmusicplayer.business.ui.widgets

import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.contains.ILrcStateContain
import com.huwei.sweetmusicplayer.business.core.MusicManager
import com.huwei.sweetmusicplayer.business.models.LrcContent
import com.huwei.sweetmusicplayer.business.ui.listeners.OnLrcSearchClickListener
import com.huwei.sweetmusicplayer.util.DisplayUtil
import com.huwei.sweetmusicplayer.util.LogUtils
import com.huwei.sweetmusicplayer.util.TimeUtil

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.EXACTLY
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.ScrollView
import android.widget.FrameLayout
import com.huwei.sweetmusicplayer.business.BaseView
import com.huwei.sweetmusicplayer.business.playmusic.PlayMusicContract

class LrcView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : ScrollView(mContext, attrs, defStyle), OnScrollChangedListener, View.OnTouchListener, ILrcStateContain,
        GestureDetector.OnGestureListener, BaseView<PlayMusicContract.Presenter> {

    private var viewWidth: Int = 0    //歌词视图宽度
    private var viewHeight: Int = 0   //歌词视图高度
    private var currentPaint: Paint? = null        //当前画笔对象
    private var notCurrentPaint: Paint? = null        //非当前画笔对象
    private var tipsPaint: Paint? = null  //提示信息画笔

    private var lightTextSize: Float = 0.toFloat()        //高亮文本大小
    private var norTextSize: Float = 0.toFloat()        //非高亮文本大小
    private var tipsTextSize: Float = 0.toFloat()    //提示文本大小
    private var textHeight: Float = 0.toFloat()    //文本高度

    var index: Int = 0
        private set

    private var lrcState = -1
    private var lrcTextView: LrcTextView? = null

    var lrcLists: List<LrcContent>? = null
        private set

    private var canDrawLine = false
    private var pos = -1 //手指按下后歌词要到的位置
    private var linePaint: Paint? = null

    private var count = 0  //绘制加载点的次数

    private var onLrcSearchClickListener: OnLrcSearchClickListener? = null
    private val mGestureDeterctor: GestureDetector
    private var mIsScrolling = false

    private lateinit var presenter: PlayMusicContract.Presenter

    init {
        mGestureDeterctor = GestureDetector(mContext, this)
        this.setOnTouchListener(this)

        isFocusable = true    //设置该控件可以有焦点
        this.setWillNotDraw(false)

        norTextSize = DisplayUtil.sp2px(mContext, 16f).toFloat()
        lightTextSize = DisplayUtil.sp2px(mContext, 18f).toFloat()
        tipsTextSize = DisplayUtil.sp2px(mContext, 20f).toFloat()
        textHeight = norTextSize + DisplayUtil.dip2px(mContext, 10f)

        //高亮歌词部分
        currentPaint = Paint()
        currentPaint!!.isAntiAlias = true    //设置抗锯齿
        currentPaint!!.textAlign = Paint.Align.CENTER    //设置文本居中

        //非高亮歌词部分
        notCurrentPaint = Paint()
        notCurrentPaint!!.isAntiAlias = true
        notCurrentPaint!!.textAlign = Paint.Align.CENTER

        //提示信息画笔
        tipsPaint = Paint()
        tipsPaint!!.isAntiAlias = true
        tipsPaint!!.textAlign = Paint.Align.CENTER

        //
        linePaint = Paint()
        linePaint!!.isAntiAlias = true
        linePaint!!.textAlign = Paint.Align.CENTER

        //设置画笔颜色
        currentPaint!!.color = resources.getColor(R.color.primary)
        notCurrentPaint!!.color = Color.argb(140, 255, 255, 255)
        tipsPaint!!.color = Color.WHITE
        linePaint!!.color = Color.RED

        //设置字体
        currentPaint!!.textSize = lightTextSize
        currentPaint!!.typeface = Typeface.SERIF

        notCurrentPaint!!.textSize = norTextSize
        notCurrentPaint!!.typeface = Typeface.DEFAULT

        tipsPaint!!.textSize = tipsTextSize
        tipsPaint!!.typeface = Typeface.DEFAULT

        linePaint!!.textSize = lightTextSize
        linePaint!!.typeface = Typeface.SERIF
    }

    override fun setPresenter(presenter: PlayMusicContract.Presenter) {
        this.presenter = presenter
    }

    fun setOnLrcSearchClickListener(onLrcSearchClickListener: OnLrcSearchClickListener) {
        this.onLrcSearchClickListener = onLrcSearchClickListener
    }

    fun notifyLrcListsChanged(lrcLists: List<LrcContent>) {
        this.lrcLists = lrcLists
        //设置index=-1
        index = -1

        
        val params1 = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        lrcTextView = LrcTextView(this.context)
        lrcTextView!!.layoutParams = params1

        this.removeAllViews()
        this.addView(lrcTextView)
    }

    fun setSongIndex(index: Int) {
        //歌曲位置发生变化,而且手指不是调整歌词位置的状态
        if (this.index != index && pos == -1) {
            this.scrollTo(0, (index * textHeight).toInt())
        }
        this.index = index
    }

//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//
//        this.viewWidth = w.toFloat()
//        this.viewHeight = h.toFloat()
//
//        notifyLrcListsChanged(lrcLists!!)
//    }

    fun getIndexByLrcTime(currentTime: Int): Int {
        if (lrcLists == null) {
            return 0
        }

        for (i in lrcLists!!.indices) {
            if (currentTime < lrcLists!![i].lrcTime) {
                return i - 1
            }
        }
        return lrcLists!!.size - 1
    }

    fun setLrcState(lrcState: Int) {
        this.lrcState = lrcState
        invalidate()
    }


    internal inner class LrcTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : AppCompatTextView(context, attrs, defStyle) {

        init {
            this.setWillNotDraw(false)
        }

        //绘制歌词
        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            if (canvas == null) return

            var centerY = viewHeight / 2

            when (lrcState) {
                ILrcStateContain.READ_LOC_FAIL -> {
                    tipsPaint!!.isUnderlineText = true
                    canvas.drawText("暂无歌词,点击开始搜索", (viewWidth / 2).toFloat(), centerY.toFloat(), tipsPaint!!)
                }
                ILrcStateContain.QUERY_ONLINE -> {
                    tipsPaint!!.isUnderlineText = false
                    var drawContentStr = "在线匹配歌词"
                    for (i in 0 until count) {
                        drawContentStr += "."
                    }

                    count++
                    if (count >= 6) count = 0

                    canvas.drawText(drawContentStr, (viewWidth / 2).toFloat(), centerY.toFloat(), tipsPaint!!)

                    handler.postDelayed(Runnable { invalidate() }, 500)
                }
                ILrcStateContain.QUERY_ONLINE_OK, ILrcStateContain.READ_LOC_OK -> {
                    //绘制歌词
                    var i = 0
                    while (i < lrcLists!!.size) {
                        if (i == index) {
                            canvas.drawText(lrcLists!![i].lrcStr, (viewWidth / 2).toFloat(), centerY.toFloat(), currentPaint!!)
                        } else if (i == pos) {
                            canvas.drawText(lrcLists!![i].lrcStr, (viewWidth / 2).toFloat(), centerY.toFloat(), linePaint!!)
                        } else {
                            canvas.drawText(lrcLists!![i].lrcStr, (viewWidth / 2).toFloat(), centerY.toFloat(), notCurrentPaint!!)
                        }
                        i++
                        centerY += textHeight.toInt()
                    }
                }
                ILrcStateContain.QUERY_ONLINE_FAIL -> {
                    tipsPaint!!.isUnderlineText = true
                    canvas.drawText("搜索失败，请重试", (viewWidth / 2).toFloat(), centerY.toFloat(), tipsPaint!!)
                }
                ILrcStateContain.QUERY_ONLINE_NULL -> {
                    tipsPaint!!.isUnderlineText = false
                    canvas.drawText("网络无匹配歌词", (viewWidth / 2).toFloat(), centerY.toFloat(), tipsPaint!!)
                }
            }
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)

            viewWidth = measuredWidth
            viewHeight = MeasureSpec.getSize(heightMeasureSpec)
            LogUtils.i(TAG, "viewHeight:" + viewHeight)

            val heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    (viewHeight + textHeight * lrcLists!!.size).toInt(), EXACTLY)
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (canDrawLine) {
            canvas.drawLine(0f, (scrollY + viewHeight / 2).toFloat(), viewWidth.toFloat(), ((scrollY + viewHeight / 2).toFloat()), linePaint!!)
            canvas.drawText(TimeUtil.mill2mmss(lrcLists!![pos].lrcTime.toLong()), 42f, (scrollY + viewHeight / 2 - 2).toFloat(), linePaint!!)
        }
    }


    override fun invalidate() {
        super.invalidate()

        lrcTextView!!.invalidate()
    }

    override fun onScrollChanged() {
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (lrcState) {
            ILrcStateContain.READ_LOC_FAIL, ILrcStateContain.QUERY_ONLINE_FAIL -> return handleTouchLrcFail(event.action)
            ILrcStateContain.READ_LOC_OK, ILrcStateContain.QUERY_ONLINE_OK -> return handleTouchLrcOK(event)
        }

        return false
    }



    internal fun handleTouchLrcOK(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && mIsScrolling) {
            mIsScrolling = false

            if (pos != -1)
                MusicManager.getInstance().seekTo(lrcLists!![pos].lrcTime)
            canDrawLine = false
            pos = -1
            this.invalidate()

            return true
        }
        return mGestureDeterctor.onTouchEvent(event)
    }

    internal fun handleTouchLrcFail(action: Int): Boolean {
        when (action) {
            MotionEvent.ACTION_UP -> if (onLrcSearchClickListener != null) {
                onLrcSearchClickListener!!.onLrcSearchClicked(this)
            }
        }
        return true
    }

    override fun onDown(e: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent) {

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        LogUtils.i(TAG, "onSingleTapUp")
        presenter.performPanelClick()
        return true
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        mIsScrolling = true
        when (e2.action) {
            MotionEvent.ACTION_MOVE -> {
                pos = (this.getScrollY() / textHeight).toInt()

                canDrawLine = true
                this.invalidate()

                Log.i("LrcStateView", "ACTION_DOWN")
            }
        }
        return false
    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    companion object {
        private val TAG = "LrcView"
    }
}
