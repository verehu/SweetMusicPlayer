package com.huwei.sweetmusicplayer.business.ui.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.RESTART
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import com.huwei.sweetmusicplayer.R
import com.thinkcool.circletextimageview.CircleTextImageView

/**
 * Created by huwei on 18-1-17.
 */

class RotateCircleImageView @JvmOverloads constructor(internal var context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CircleTextImageView(context, attrs, defStyleAttr) {

    val ratoteAnimator: ObjectAnimator

    init {
        ratoteAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
        ratoteAnimator.setInterpolator(LinearInterpolator())
        ratoteAnimator.setDuration(20000)
        ratoteAnimator.repeatMode = RESTART
        ratoteAnimator.repeatCount = INFINITE

        setBorderColorResource(R.color.black)
        setBorderWidth(5)

        setImageResource(R.drawable.turntable)
    }

    fun start() {
        ratoteAnimator.setupStartValues()
        ratoteAnimator.start()
    }

    fun pause() {
        ratoteAnimator.pause()
    }

    fun resume() {
        ratoteAnimator.resume()
    }
}
