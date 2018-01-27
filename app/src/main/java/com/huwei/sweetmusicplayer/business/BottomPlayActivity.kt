package com.huwei.sweetmusicplayer.business

import android.view.ViewGroup
import com.huwei.sweetmusicplayer.contants.Contants
import com.huwei.sweetmusicplayer.contants.Contants.NOW_PLAYMUSIC

/**
 *
 * @author Ezio
 * @date 2017/06/04
 */
abstract class BottomPlayActivity : BaseActivity() {
    var bottomPlayBar: com.huwei.sweetmusicplayer.business.ui.views.BottomPlayBar? = null
    var barContainerView: android.widget.FrameLayout? = null
    var isBarAdd: Boolean = false
    var isReceiverRegistered = false

    private val receiver = object : android.content.BroadcastReceiver() {

        override fun onReceive(context: android.content.Context, intent: android.content.Intent) {
            // TODO Auto-generated method stub
            val action = intent.action

            when (action) {
                Contants.PLAYBAR_UPDATE -> {
                    if (!isBarAdd) {
                        addBottomPlayBar(intent.getParcelableExtra(NOW_PLAYMUSIC))
                    }
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: android.os.Bundle?) {
        super.onPostCreate(savedInstanceState)

        val music: com.huwei.sweetmusicplayer.business.abstracts.AbstractMusic? = com.huwei.sweetmusicplayer.util.Environment.getRecentMusic()
        addBottomPlayBar(music)

        if (!isReceiverRegistered && !isBarAdd) initRecievers()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isReceiverRegistered) unregisterReceiver(receiver)
        bottomPlayBar?.unRegisterRecievers()
    }

    fun initRecievers() {
        val intentFilter = android.content.IntentFilter()
        intentFilter.addAction(Contants.PLAYBAR_UPDATE)
        registerReceiver(receiver, intentFilter)

        isReceiverRegistered = true
    }

    fun addBottomPlayBar(music: com.huwei.sweetmusicplayer.business.abstracts.AbstractMusic?) {
        if (!isBarAdd && music != null) {
            bottomPlayBar = com.huwei.sweetmusicplayer.business.ui.views.BottomPlayBar(this)
            bottomPlayBar!!.updateBottomBar(music)

            val container: android.widget.FrameLayout? = findViewById(com.huwei.sweetmusicplayer.R.id.bottomPlayContainer) as android.widget.FrameLayout?

            if (container == null) {
                val mDecorView = window.decorView as android.view.ViewGroup
                barContainerView = (mDecorView.getChildAt(0) as android.view.ViewGroup).getChildAt(1) as android.widget.FrameLayout
            } else {
                barContainerView = container
            }

            //add to container
            val layoutParams = android.widget.FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.gravity = android.view.Gravity.BOTTOM
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