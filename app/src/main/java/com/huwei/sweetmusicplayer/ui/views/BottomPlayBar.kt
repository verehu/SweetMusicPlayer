package com.huwei.sweetmusicplayer.ui.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic
import com.huwei.sweetmusicplayer.contains.IContain
import com.huwei.sweetmusicplayer.datamanager.MusicManager
import kotlinx.android.synthetic.main.bottom_action_bar.view.*

/**
 *
 * @author Ezio
 * @date 2017/06/04
 */
class BottomPlayBar(context: Context?) : LinearLayout(context) {

    val TAG = "BottomActionBarFragment"
    var initRecentMusic : AbstractMusic? = null

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            // TODO Auto-generated method stub
            val action = intent.action

            when (action) {
                IContain.PLAY_STATUS_UPDATE -> {
                    val isPlaying = intent.getBooleanExtra("isPlaying", false)
                    btn_play.isChecked = MusicManager.getInstance().isPlaying
                }
                IContain.PLAYBAR_UPDATE -> {
                    pro_music.max = MusicManager.getInstance().nowPlayingSong.duration!!
                    val music = MusicManager.getInstance().nowPlayingSong
                    updateBottomBar(music)
                }
                IContain.CURRENT_UPDATE -> pro_music.progress = intent.getIntExtra("currentTime", 0)
            }
        }

    }

    init {
        View.inflate(context!!, R.layout.bottom_action_bar, this)

        initListener()
        initRecievers()

        updateBottomBar(initRecentMusic)
    }

    fun initListener() {
        btn_next.setOnClickListener(View.OnClickListener {
            // TODO Auto-generated method stub
            MusicManager.getInstance().nextSong()
        })

        btn_play.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // TODO Auto-generated method stub
            if (isChecked != MusicManager.getInstance().isPlaying) {
                //播放意图
                if (isChecked) {
                    MusicManager.getInstance().play()
                } else {
                    MusicManager.getInstance().pause()
                }
            }
        })
    }

    fun initRecievers() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(IContain.PLAYBAR_UPDATE)
        intentFilter.addAction(IContain.CURRENT_UPDATE)
        intentFilter.addAction(IContain.PLAY_STATUS_UPDATE)
        context.registerReceiver(receiver, intentFilter)
    }

    internal fun updateBottomBar(music: AbstractMusic?) {

        if (music != null) {
            tv_title.text = music.title
            tv_artist.text = music.artist
            btn_play.isChecked = MusicManager.getInstance().isPlaying

            music.loadArtPic { bitmap ->
                Log.i(TAG, "onSuccessLoad bitmap:" + bitmap)

                img_album.setImageBitmap(bitmap)
            }
        }

    }
}