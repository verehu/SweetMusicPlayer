package com.huwei.sweetmusicplayer.ui.widgets

import android.content.Context
import android.graphics.Color
import android.view.SurfaceHolder.Callback
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * Created by huwei on 18-2-1.
 */
class VideoSurfaceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), Callback {

    var wRatio: Int = 1
    var hRatio: Int = 1

    init {
        //holder.addCallback(this)
    }

    fun setWHRatio(w: Int, h: Int) {
        wRatio = w
        hRatio = h
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, width * hRatio / wRatio)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val canvas = holder.lockCanvas()
        canvas.drawColor(Color.BLACK)
        holder.unlockCanvasAndPost(canvas)
    }
}