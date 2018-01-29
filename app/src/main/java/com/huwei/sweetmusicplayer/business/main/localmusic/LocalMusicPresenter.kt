package com.huwei.sweetmusicplayer.business.main.localmusic

import android.content.Context
import com.huwei.sweetmusicplayer.business.ViewHoldPresenter
import com.huwei.sweetmusicplayer.data.contants.MusicViewTypeContain
import com.huwei.sweetmusicplayer.data.models.MusicInfo
import com.huwei.sweetmusicplayer.util.MusicUtils

/**
 * Created by huwei on 18-1-29.
 */
class LocalMusicPresenter(context: Context, view: LocalMusicContract.View) :
        ViewHoldPresenter<LocalMusicContract.View>(context, view), LocalMusicContract.Presenter {
    override fun start() {

    }

    override fun loadMusicByShowType(showType: Int, primaryId: Long) {
        var musicInfoList: List<MusicInfo>? = null
        when (showType) {
            MusicViewTypeContain.SHOW_MUSIC ->
                musicInfoList = MusicUtils.queryMusic()
            MusicViewTypeContain.SHOW_MUSIC_BY_ALBUM ->
                musicInfoList = MusicUtils.queryMusicByAlbumId(primaryId)
            MusicViewTypeContain.SHOW_MUSIC_BY_ARTIST ->
                musicInfoList = MusicUtils.queryMusicByArtistId(primaryId)
        }

        mView.showMusicList(musicInfoList!!)
    }
}