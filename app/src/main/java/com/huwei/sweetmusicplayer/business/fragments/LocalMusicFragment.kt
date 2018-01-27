package com.huwei.sweetmusicplayer.business.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView

import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.R.layout.fragment_localmusic
import com.huwei.sweetmusicplayer.business.abstracts.AbstractMusic
import com.huwei.sweetmusicplayer.contants.Contants
import com.huwei.sweetmusicplayer.contants.MusicViewTypeContain
import com.huwei.sweetmusicplayer.business.core.MusicManager
import com.huwei.sweetmusicplayer.business.fragments.base.BaseFragment
import com.huwei.sweetmusicplayer.business.models.MusicInfo
import com.huwei.sweetmusicplayer.business.ui.adapters.MusicAdapter
import com.huwei.sweetmusicplayer.contants.IntentExtra
import com.huwei.sweetmusicplayer.util.MusicUtils
import kotlinx.android.synthetic.main.fragment_localmusic.*

/**
 * 装载音乐的fragment容器
 */
class LocalMusicFragment : BaseFragment(), Contants, MusicViewTypeContain {

    private var mMusicAdapter: MusicAdapter? = null

    private var isABC: Boolean = false  //是否显示ABC视图

    internal var inflater: LayoutInflater? = null

    internal var title: String? = null
    internal var showtype: Int = 0
    internal var primaryId: Long? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Contants.PLAYBAR_UPDATE -> mMusicAdapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(fragment_localmusic, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initParams()
        initToolBar()
        showSldingBar()
        initView()
        showMusicList()
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter()
        filter.addAction(Contants.PLAYBAR_UPDATE)
        activity.registerReceiver(receiver, filter)
    }

    override fun onStop() {
        super.onStop()
        activity.unregisterReceiver(receiver)
    }

    internal fun initToolBar() {
        when (showtype) {
            MusicViewTypeContain.SHOW_MUSIC -> toolbar!!.visibility = View.GONE
            MusicViewTypeContain.SHOW_MUSIC_BY_ALBUM, MusicViewTypeContain.SHOW_MUSIC_BY_ARTIST -> {
                toolbar!!.visibility = View.VISIBLE
                toolbar!!.title = title
                toolbar!!.navigationIcon = resources.getDrawable(R.drawable.abc_ic_ab_back_material)
                toolbar!!.setNavigationOnClickListener { activity.onBackPressed() }
            }
        }
    }

    internal fun initParams() {
        title = arguments.getString(IntentExtra.EXTRA_TITLE)
        showtype = arguments.getInt(IntentExtra.EXTAR_SHOWTYPE)
        primaryId = arguments.getLong(IntentExtra.EXTRA_PRIMARY_ID)

        when (showtype) {
            MusicViewTypeContain.SHOW_MUSIC -> isABC = true
            MusicViewTypeContain.SHOW_MUSIC_BY_ALBUM, MusicViewTypeContain.SHOW_MUSIC_BY_ARTIST -> isABC = false
        }
    }

    internal fun showSldingBar() {
        if (isABC) {
            sidebar!!.setTextView(dialog)
            sidebar!!.setOnTouchingLetterChangedListener { s ->
                // TODO Auto-generated method stub
                val position = mMusicAdapter!!.getPositionForSection(s[0].toInt())
                if (position != -1) {
                    lv_song!!.setSelection(position)
                }
            }
        } else {
            sidebar!!.visibility = View.INVISIBLE
        }
    }

    internal fun initView() {
        if (isABC) {
            lv_song!!.setOnScrollListener(object : AbsListView.OnScrollListener {
                override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                    if (view.count != 0) {
                        if (scrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                            dialog!!.visibility = View.VISIBLE
                        } else {
                            lv_song!!.postDelayed({ dialog!!.visibility = View.GONE }, 100)
                        }
                    }
                }

                override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                    val musicInfo = lv_song!!.getItemAtPosition(firstVisibleItem) as MusicInfo?
                    if (dialog != null && musicInfo != null) {
                        dialog!!.text = musicInfo.keyofTitle
                    }
                }
            })
        }
    }

    fun showMusicList() {
        var musicInfoList: List<MusicInfo>? = null
        when (showtype) {
            MusicViewTypeContain.SHOW_MUSIC -> musicInfoList = MusicUtils.queryMusic()
            MusicViewTypeContain.SHOW_MUSIC_BY_ALBUM -> musicInfoList = MusicUtils.queryMusicByAlbumId(primaryId)
            MusicViewTypeContain.SHOW_MUSIC_BY_ARTIST -> musicInfoList = MusicUtils.queryMusicByArtistId(primaryId)
        }
        mMusicAdapter = MusicAdapter(activity, musicInfoList, isABC)
        mMusicAdapter!!.setOnItemClickListener { position ->
            val time = System.currentTimeMillis()
            MusicManager.getInstance().prepareAndPlay(position, mMusicAdapter!!.list as MutableList<AbstractMusic>?)
            Log.i(TAG, "time used:" + (System.currentTimeMillis() - time))

            Log.i(TAG, "clicked music:" + (mMusicAdapter!!.list[position] as AbstractMusic).title)
        }
        lv_song!!.adapter = mMusicAdapter

        val footer = LayoutInflater.from(context).inflate(R.layout.listbottom_music_count, null)
        val tv_music_count = footer.findViewById(R.id.tv_music_count) as TextView
        tv_music_count.text = mMusicAdapter!!.count.toString() + " 首歌曲"
        lv_song!!.addFooterView(footer)
    }

    companion object {
        fun get(): LocalMusicFragment {
            return LocalMusicFragment()
        }
    }
}
