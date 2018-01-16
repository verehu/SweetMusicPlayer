package com.huwei.sweetmusicplayer.business.ui.itemviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

import com.bumptech.glide.Glide
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.business.models.ArtistInfo
import kotlinx.android.synthetic.main.card_artist.view.*


/**
 * @author jerry
 * @date 2016/01/15
 */
class ArtistItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs), IRecycleViewItem<ArtistInfo> {

    init {
        LayoutInflater.from(context).inflate(R.layout.card_artist, this)
    }

    override fun bind(artistInfo: ArtistInfo) {
        Glide.with(context).load(artistInfo.albumArtPic).into(iv_artist!!)
        tv_title!!.text = artistInfo.artist
        tv_numsongs!!.text = artistInfo.numSongs!!.toString() + "首"
    }
}
