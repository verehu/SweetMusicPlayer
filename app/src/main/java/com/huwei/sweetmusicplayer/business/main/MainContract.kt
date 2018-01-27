package com.huwei.sweetmusicplayer.business.main

import com.huwei.sweetmusicplayer.business.BasePresenter
import com.huwei.sweetmusicplayer.business.BaseView

/**
 * Created by huwei on 18-1-27.
 */
interface MainContract {
    interface Presenter : BasePresenter {
        fun bindMusicController()

        fun unBindMusicController()

        fun loadMusicPlayAgo() //加载上一个退出时播放的音乐

        fun getSleepItem() : Int

        fun setSleepCountDownByItem(item: Int)
    }

    interface View : BaseView<Presenter>
}