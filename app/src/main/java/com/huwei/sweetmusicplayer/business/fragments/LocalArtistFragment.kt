package com.huwei.sweetmusicplayer.business.fragments

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.huwei.sweetmusicplayer.business.LocalMusicActivity
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.contains.IMusicViewTypeContain
import com.huwei.sweetmusicplayer.business.fragments.base.BaseFragment
import com.huwei.sweetmusicplayer.business.ui.adapters.LocArtistInfoAdapter
import com.huwei.sweetmusicplayer.util.MusicUtils
import kotlinx.android.synthetic.main.fragment_artists.*

/**
 * 本地音乐的Artist列表页面
 */
class LocalArtistFragment : BaseFragment(), IMusicViewTypeContain {
    internal var adapter: LocArtistInfoAdapter? = null

    lateinit internal var fragmentManager: FragmentManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_artists, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        fragmentManager = activity.supportFragmentManager

        adapter = LocArtistInfoAdapter(context)
        adapter!!.setData(MusicUtils.queryArtistList())
        rv_artist!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_artist!!.adapter = adapter
        adapter!!.setOnItemClickListener { view, position ->
            startActivity(LocalMusicActivity.getStartActIntent(mAct, IMusicViewTypeContain.SHOW_MUSIC_BY_ARTIST,
                    adapter!!.data[position].artist, adapter!!.data[position].artistId!!))
        }
    }
}
