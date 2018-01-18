package com.huwei.sweetmusicplayer.business.playmusic

import android.content.Context
import com.huwei.sweetmusicplayer.business.ContextPresenter

/**
 *
 * @author Ezio
 * @date 2018/01/18
 */
class PlayMusicPresenter(context: Context, view: PlayMusicContract.View) : ContextPresenter<PlayMusicContract.View>(context, view), PlayMusicContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun start() {

    }

    override fun performPanelClick() {
        view.togglePanel()
    }
}