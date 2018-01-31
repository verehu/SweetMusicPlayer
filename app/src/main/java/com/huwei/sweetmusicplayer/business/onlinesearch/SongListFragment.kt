package com.huwei.sweetmusicplayer.business.onlinesearch

import android.os.Bundle
import android.view.View

import com.huwei.sweetmusicplayer.business.BaseScrollTabFragment
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.Song
import com.huwei.sweetmusicplayer.data.models.baidumusic.resp.ArtistSongListResp
import com.huwei.sweetmusicplayer.business.core.MusicManager
import com.huwei.sweetmusicplayer.data.api.RetrofitFactory
import com.huwei.sweetmusicplayer.data.api.SimpleObserver
import com.huwei.sweetmusicplayer.data.api.baidu.BaiduMusicService
import com.huwei.sweetmusicplayer.ui.adapters.SongAdapter


import java.util.ArrayList

import com.huwei.sweetmusicplayer.data.contants.IntentExtra.EXTRA_ARTIST_ID
import com.huwei.sweetmusicplayer.data.contants.IntentExtra.EXTRA_TING_UID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 在线歌曲列表
 *
 * @author jerry
 * @date 2015/12/24
 */
class SongListFragment : BaseScrollTabFragment() {

    private var mMusicAdapter: SongAdapter? = null
    private val mSongList = ArrayList<Song>()

    internal var ting_uid: String = ""
    internal var artist_id: String = ""

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ting_uid = arguments.getString(EXTRA_TING_UID)
        artist_id = arguments.getString(EXTRA_ARTIST_ID)

        mMusicAdapter = SongAdapter(mAct, mSongList)
        mAutoListView.adapter = mMusicAdapter

        mAutoListView.setRefreshEnable(false)
        mAutoListView.setOnLoadListener {

            RetrofitFactory.create(BaiduMusicService::class.java)
                    .getArtistSongList(ting_uid, artist_id, mPageNo * BaiduMusicService.PAGESIZE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SimpleObserver<ArtistSongListResp>() {
                        override fun onSuccess(resp: ArtistSongListResp) {
                            if (resp.songlist != null) {
                                mPageNo++
                                mSongList.addAll(resp.songlist)
                                mMusicAdapter!!.notifyDataSetChanged()

                                mAutoListView.onLoadComplete(resp.hasmore())
                            }
                        }
                    })
        }
        mAutoListView.onLoad()
        mAutoListView.setOnItemNoneClickListener { parent, view, position, id -> MusicManager.get().prepareAndPlay(position, Song.getAbstractMusicList(mSongList)) }
    }


}
