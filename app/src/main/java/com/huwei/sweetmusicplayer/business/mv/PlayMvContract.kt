package com.huwei.sweetmusicplayer.business.mv

import android.view.SurfaceView
import com.huwei.sweetmusicplayer.business.BasePresenter
import com.huwei.sweetmusicplayer.business.BaseView
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.PlayMv

/**
 * Created by huwei on 18-2-1.
 */
interface PlayMvContract {
    interface Presenter : BasePresenter {
        fun loadMvInfo(songId : String)

        fun loadUrlAndPlay(url : String)

        fun togglePlay()

        fun seekTo(position : Long)
    }

    interface View : BaseView<Presenter> {
        fun showMvInfo(info : PlayMv.MvInfo)

        fun getPlayView() : SurfaceView
    }
}