package com.huwei.sweetmusicplayer.business.main.localmusic

import com.huwei.sweetmusicplayer.business.BasePresenter
import com.huwei.sweetmusicplayer.business.BaseView
import com.huwei.sweetmusicplayer.data.models.MusicInfo

/**
 * Created by huwei on 18-1-29.
 */
interface LocalMusicContract {
    interface Presenter : BasePresenter {
        fun loadMusicByShowType(showType : Int, primaryId : Long)
    }

    interface View : BaseView<Presenter> {
        fun showMusicList(musicList: List<MusicInfo>)
    }
}