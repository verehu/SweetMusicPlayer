package com.huwei.sweetmusicplayer.business.ui.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.RESTART
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.thinkcool.circletextimageview.CircleTextImageView

/**
 * Created by huwei on 18-1-17.
 */

class RotateCircleImageView @JvmOverloads constructor(internal var context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CircleTextImageView(context, attrs, defStyleAttr) {

    val ratoteAnimator: ObjectAnimator

    init {
        ratoteAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
        ratoteAnimator.setDuration(2000)
        ratoteAnimator.repeatMode = RESTART
        ratoteAnimator.repeatCount = INFINITE

        setBackgroundColor(Color.BLACK)

        start()
    }

    fun start() {
        ratoteAnimator.start()
    }

    fun pause() {
        ratoteAnimator.pause()
    }

    fun stop() {
        ratoteAnimator.pause()
    }

    private fun reset() {

    }
}
