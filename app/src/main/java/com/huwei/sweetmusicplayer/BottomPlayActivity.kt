package com.huwei.sweetmusicplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic
import com.huwei.sweetmusicplayer.contains.IContain
import com.huwei.sweetmusicplayer.contains.IContain.NOW_PLAYMUSIC

import com.huwei.sweetmusicplayer.ui.views.BottomPlayBar
import com.huwei.sweetmusicplayer.util.Environment

/**
 *
 * @author Ezio
 * @date 2017/06/04
 */
abstract class BottomPlayActivity : BaseActivity() {
    var bottomPlayBar: BottomPlayBar? = null
    var barContainerView: FrameLayout? = null
    var isBarAdd: Boolean = false
    var isReceiverRegistered = false

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            // TODO Auto-generated method stub
            val action = intent.action

            when (action) {
                IContain.PLAYBAR_UPDATE -> {
                    if (!isBarAdd) {
                        addBottomPlayBar(intent.getParcelableExtra(NOW_PLAYMUSIC))
                    }
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val music: AbstractMusic? = Environment.getRecentMusic()
        addBottomPlayBar(music)

        if (!isReceiverRegistered && !isBarAdd) initRecievers()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isReceiverRegistered) unregisterReceiver(receiver)
        bottomPlayBar?.unRegisterRecievers()
    }

    fun initRecievers() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(IContain.PLAYBAR_UPDATE)
        registerReceiver(receiver, intentFilter)

        isReceiverRegistered = true
    }

    fun addBottomPlayBar(music: AbstractMusic?) {
        if (!isBarAdd && music != null) {
            bottomPlayBar = BottomPlayBar(this)
            bottomPlayBar!!.updateBottomBar(music)

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

            isBarAdd = true
        }
    }

    /**
     * 防止退出activity时闪烁
     */
    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}