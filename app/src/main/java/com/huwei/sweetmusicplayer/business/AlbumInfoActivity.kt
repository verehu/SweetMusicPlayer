package com.huwei.sweetmusicplayer.business

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.business.baidumusic.po.Song
import com.huwei.sweetmusicplayer.business.baidumusic.resp.AlbumDetailResp
import com.huwei.sweetmusicplayer.contants.IntentExtra
import com.huwei.sweetmusicplayer.business.core.MusicManager
import com.huwei.sweetmusicplayer.frameworks.image.BlurBitmapTransformation

import com.huwei.sweetmusicplayer.business.ui.adapters.SongAdapter
import com.huwei.sweetmusicplayer.business.ui.widgets.auto.IPullRefershBase
import com.huwei.sweetmusicplayer.frameworks.image.GlideApp

import com.huwei.sweetmusicplayer.util.BaiduMusicUtil
import com.huwei.sweetmusicplayer.util.HttpHandler
import kotlinx.android.synthetic.main.activity_album_info.*
import kotlinx.android.synthetic.main.layout_gradient_toolbar.*

import java.util.ArrayList

/**
 * 在线 专辑详情页面
 *
 * @author jerry
 * @date 2015-09-13
 */
open class AlbumInfoActivity : BottomPlayActivity() {

    private var mHeaderView: View? = null
    private var iv_bg: ImageView? = null

    lateinit internal var iv_album: ImageView
    lateinit internal var tv_albumname: TextView
    lateinit internal var tv_artist: TextView
    lateinit internal var tv_pub_date: TextView

    lateinit internal var albumId: String

    private var mMusicAdapter: SongAdapter? = null
    private val songList = ArrayList<Song>()

    override fun isNeedStausView(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_album_info)
        init()
    }

    internal fun init() {
        albumId = intent.getStringExtra(IntentExtra.EXTRA_ALBUM_ID)

        initToolBar()
        initHeaderView()
        initView()
    }

    internal fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        gtoolbar.setTitle(R.string.ativity_album_detail)
        toolbar.setNavigationOnClickListener(View.OnClickListener { v -> onBackClicked(v) })
    }

    internal fun initHeaderView() {
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.listheader_albumdetail, null)
        iv_bg = mHeaderView!!.findViewById(R.id.iv_bg) as ImageView
        iv_album = mHeaderView!!.findViewById(R.id.iv_album) as ImageView
        tv_albumname = mHeaderView!!.findViewById(R.id.tv_albumname) as TextView
        tv_artist = mHeaderView!!.findViewById(R.id.tv_artist) as TextView
        tv_pub_date = mHeaderView!!.findViewById(R.id.tv_pub_date) as TextView


        gtoolbar.bindListView(lv_albuminfo)
        gtoolbar.bindHeaderView(mHeaderView)
    }

    internal fun initView() {
        lv_albuminfo.addHeaderView(mHeaderView)
        lv_albuminfo.setRefreshEnable(false)
        lv_albuminfo.setOnLoadListener(IPullRefershBase.OnLoadListener { getAlbumInfo() })
        lv_albuminfo.onLoad()
        lv_albuminfo.setOnScrollListener(gtoolbar)

        mMusicAdapter = SongAdapter(mContext, songList)
        lv_albuminfo.setAdapter(mMusicAdapter)
        lv_albuminfo.setOnItemNoneClickListener(AdapterView.OnItemClickListener { parent, view, position, id -> MusicManager.getInstance().prepareAndPlay(position, Song.getAbstractMusicList(songList)) })
    }

    /**
     * 获取专辑详情 包含歌曲的详细信息
     */
    private fun getAlbumInfo() {
        BaiduMusicUtil.getAlbumInfo(albumId, object : HttpHandler() {
            override fun onSuccess(response: String) {
                val resp = Gson().fromJson(response, AlbumDetailResp::class.java)
                val albumDetail = resp.albumInfo
                if (albumDetail != null) {
                    GlideApp.with(mContext).asBitmap().load(albumDetail.pic_big).transform(object : BlurBitmapTransformation(80) {
                        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
                            iv_album.setImageBitmap(toTransform)
                            return super.transform(pool, toTransform, outWidth, outHeight)
                        }
                    }).into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                            iv_bg!!.setImageBitmap(resource)
                            gtoolbar.setToolbarBg(resource)
                        }
                    })
                    tv_albumname.text = albumDetail.title
                    tv_artist.text = "歌手：" + albumDetail.author
                    tv_pub_date.text = "发行时间：" + albumDetail.publishtime

                    gtoolbar.setGradientTitle(albumDetail.title)
                }

                val data = resp.songlist
                if (data != null) {
                    songList.clear()

                    songList.addAll(data)
                    mMusicAdapter!!.notifyDataSetInvalidated()

                    lv_albuminfo.onLoadComplete(false)
                }
            }
        })
    }

    companion object {

        fun getStartActInent(from: Context, albumId: String): Intent {
            val intent = Intent(from, AlbumInfoActivity::class.java)
            intent.putExtra(IntentExtra.EXTRA_ALBUM_ID, albumId)
            return intent
        }
    }
}
