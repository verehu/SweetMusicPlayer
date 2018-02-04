package com.huwei.sweetmusicplayer.business.mv

import android.content.Context
import android.text.TextUtils
import com.huwei.sweetmusicplayer.business.ViewHoldPresenter
import com.huwei.sweetmusicplayer.data.api.RetrofitFactory
import com.huwei.sweetmusicplayer.data.api.SimpleObserver
import com.huwei.sweetmusicplayer.data.api.baidu.BaiduMusicService
import com.huwei.sweetmusicplayer.data.models.baidumusic.resp.PlayMvResp
import com.huwei.sweetmusicplayer.frameworks.VideoManager
import com.huwei.sweetmusicplayer.util.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by huwei on 18-2-1.
 */
class PlayMvPresenter(context: Context?, view: PlayMvContract.View?)
    : ViewHoldPresenter<PlayMvContract.View>(context, view), PlayMvContract.Presenter {

    override fun start() {

    }

    override fun loadMvInfo(songId: String) {
        RetrofitFactory.create(BaiduMusicService::class.java)
                .getPlayMv(songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<PlayMvResp>() {
                    override fun onSuccess(resp: PlayMvResp) {
                        val x31 = resp.result.files.x31
                        val mv = resp.result.mvInfo
                        if (!TextUtils.isEmpty(x31.fileLink)) {
                            LogUtils.i("fileLink:" + x31.fileLink + "   fileDuration:" + x31.fileDuration)

                            VideoManager.load(x31.fileLink, mView.getPlayView())
                        }

                        mView.showMvInfo(mv)
                    }
                })
    }

    override fun loadUrlAndPlay(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun togglePlay() {
        if (VideoManager.isPlaying()) {
            VideoManager.pause()
        } else {
            VideoManager.start()
        }
    }

    override fun seekTo(position : Long) {
        VideoManager.seekTo(position)
    }
}