package com.huwei.sweetmusicplayer.ui.itemviews

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout

import com.bumptech.glide.Glide
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.data.models.AlbumInfo
import kotlinx.android.synthetic.main.card_ablum.view.*

/**
 * @author Jayce
 * @date 2015/6/14
 */
class AlbumItemView(context: Context?) : RelativeLayout(context), IRecycleViewItem<AlbumInfo> {

    init {
        LayoutInflater.from(context).inflate(R.layout.card_ablum, this)
    }

    override fun bind(albumInfo: AlbumInfo) {
        Glide.with(context).load(albumInfo.albumArtPic).into(img_album!!)
        tv_title!!.text = albumInfo.title
        tv_numsongs!!.text = albumInfo.numSongs!!.toString() + "首"
    }
}
