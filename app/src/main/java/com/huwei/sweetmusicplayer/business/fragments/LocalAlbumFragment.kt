package com.huwei.sweetmusicplayer.business.fragments

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.huwei.sweetmusicplayer.business.LocalMusicActivity
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.contants.MusicViewTypeContain
import com.huwei.sweetmusicplayer.business.fragments.base.BaseFragment
import com.huwei.sweetmusicplayer.business.ui.adapters.LocAlbumAdapter
import com.huwei.sweetmusicplayer.util.MusicUtils
import kotlinx.android.synthetic.main.fragment_albums.*

class LocalAlbumFragment : BaseFragment(), MusicViewTypeContain {
    internal var adapter: LocAlbumAdapter? = null

    lateinit internal var fragmentManager: FragmentManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_albums, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentManager = activity.supportFragmentManager

        adapter = LocAlbumAdapter(context)
        adapter!!.setData(MusicUtils.queryAlbumList())
        rv_album!!.layoutManager = LinearLayoutManager(activity)
        rv_album!!.adapter = adapter
        adapter!!.setOnItemClickListener { view, position ->
            startActivity(LocalMusicActivity.getStartActIntent(mAct, MusicViewTypeContain.SHOW_MUSIC_BY_ALBUM,
                    adapter!!.data[position].title, adapter!!.data[position].albumId!!))
        }
    }
}
