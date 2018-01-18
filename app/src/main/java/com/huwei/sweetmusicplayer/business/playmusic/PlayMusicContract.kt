package com.huwei.sweetmusicplayer.business.playmusic

import com.huwei.sweetmusicplayer.business.BasePresenter
import com.huwei.sweetmusicplayer.business.BaseView

/**
 *
 * @author Ezio
 * @date 2018/01/18
 */
interface PlayMusicContract {

    interface View : BaseView<Presenter> {
        fun togglePanel()
    }

    interface Presenter : BasePresenter {
        fun performPanelClick()
    }
}