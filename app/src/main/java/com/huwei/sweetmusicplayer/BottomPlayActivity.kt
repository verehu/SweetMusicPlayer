package com.huwei.sweetmusicplayer

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic
import com.huwei.sweetmusicplayer.contains.IContain.ENV_RECENT_MUSIC
import com.huwei.sweetmusicplayer.ui.views.BottomPlayBar
import com.huwei.sweetmusicplayer.util.Environment
import com.huwei.sweetmusicplayer.util.SpUtils

/**
 *
 * @author Ezio
 * @date 2017/06/04
 */
abstract class BottomPlayActivity : BaseActivity() {
    var bottomPlayBar : BottomPlayBar? = null
    var barContainerView : FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val music : AbstractMusic? = Environment.getRecentMusic()

        if (music != null) {
            bottomPlayBar = BottomPlayBar(this)
            bottomPlayBar!!.initRecentMusic = music

            val container: FrameLayout? = findViewById(R.id.bottomPlayContainer) as FrameLayout?

            if (container == null) {
                val mDecorView = window.decorView as ViewGroup
                barContainerView = (mDecorView.getChildAt(0) as ViewGroup).getChildAt(1) as FrameLayout
            } else {
                barContainerView = container
            }

            //add to container
            val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.gravity = Gravity.BOTTOM
            barContainerView!!.addView(bottomPlayBar, layoutParams)
        }
    }
}