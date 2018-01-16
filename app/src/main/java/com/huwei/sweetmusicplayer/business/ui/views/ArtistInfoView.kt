package com.huwei.sweetmusicplayer.business.ui.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.business.baidumusic.po.ArtistInfo
import com.huwei.sweetmusicplayer.frameworks.image.BlurBitmapTransformation
import com.huwei.sweetmusicplayer.frameworks.image.GlideApp
import com.huwei.sweetmusicplayer.business.ui.widgets.GradientToolbar
import com.huwei.sweetmusicplayer.util.DisplayUtil
import kotlinx.android.synthetic.main.listheader_artistinfo.view.*

/**
 * @author jerry
 * @date 2015-12-22
 */
class ArtistInfoView @JvmOverloads constructor(internal var mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(mContext, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.listheader_artistinfo, this)
        val layoutParams = AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mContext, HEIGHT_DP.toFloat()))
        setLayoutParams(layoutParams)
    }

    fun bind(artistInfo: ArtistInfo, gtoolbar: GradientToolbar) {
        tv_artist!!.text = artistInfo.name
        tv_country!!.text = artistInfo.country + "歌手"
        GlideApp.with(mContext).asBitmap().load(artistInfo.avatar_s500).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Bitmap, model: Any, target: Target<Bitmap>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                iv_bg!!.setImageBitmap(resource)
                return false
            }
        }).transform(BlurBitmapTransformation(100)).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                gtoolbar.setToolbarBg(resource)
            }
        })

        gtoolbar.setGradientTitle(artistInfo.name)

    }

    companion object {

        val HEIGHT_DP = 196
    }
}
