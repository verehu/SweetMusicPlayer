package com.huwei.sweetmusicplayer.business.onlinesearch

import android.os.Bundle
import android.view.View
import android.widget.AdapterView

import com.google.gson.Gson
import com.huwei.sweetmusicplayer.business.AlbumInfoActivity
import com.huwei.sweetmusicplayer.business.BaseScrollTabFragment
import com.huwei.sweetmusicplayer.data.api.RetrofitFactory
import com.huwei.sweetmusicplayer.data.api.SimpleObserver
import com.huwei.sweetmusicplayer.data.api.baidu.BaiduMusicService
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.Album
import com.huwei.sweetmusicplayer.data.models.baidumusic.resp.ArtistAlbumListResp
import com.huwei.sweetmusicplayer.ui.adapters.AlbumAdapter

import java.util.ArrayList

import com.huwei.sweetmusicplayer.data.contants.IntentExtra.EXTRA_ARTIST_ID
import com.huwei.sweetmusicplayer.data.contants.IntentExtra.EXTRA_TING_UID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author jerry
 * @date 2015/12/24
 */
class AlbumListFragment : BaseScrollTabFragment() {

    private var mAlbumAdapter: AlbumAdapter? = null
    private val mAlbumList = ArrayList<Album>()

    var ting_uid: String = ""
    var artist_id: String = ""

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ting_uid = arguments.getString(EXTRA_TING_UID)
        artist_id = arguments.getString(EXTRA_ARTIST_ID)

        mAlbumAdapter = AlbumAdapter(mAct, mAlbumList)
        mAutoListView.adapter = mAlbumAdapter

        mAutoListView.setRefreshEnable(false)
        mAutoListView.setOnLoadListener {

            RetrofitFactory.create(BaiduMusicService::class.java)
                    .getArtistAlbumList(ting_uid, artist_id, mPageNo * BaiduMusicService.PAGESIZE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SimpleObserver<ArtistAlbumListResp>() {
                        override fun onSuccess(resp: ArtistAlbumListResp) {
                            if (resp.albumlist != null) {
                                mPageNo++
                                mAlbumList.addAll(resp.albumlist)
                                mAlbumAdapter!!.notifyDataSetChanged()

                                mAutoListView.onLoadComplete(resp.hasmore())
                            }
                        }
                    })
        }
        mAutoListView.onLoad()
        mAutoListView.setOnItemNoneClickListener { parent, view, position, id ->
            val album = mAlbumList[position]
            if (album != null) {
                startActivity(AlbumInfoActivity.getStartActInent(mAct, album.album_id))
            }
        }
    }
}
