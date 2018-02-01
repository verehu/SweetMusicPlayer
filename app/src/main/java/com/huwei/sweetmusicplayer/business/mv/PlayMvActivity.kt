package com.huwei.sweetmusicplayer.business.mv

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.business.BaseActivity
import com.huwei.sweetmusicplayer.data.contants.BusTag
import com.huwei.sweetmusicplayer.data.contants.IntentExtra
import com.huwei.sweetmusicplayer.data.models.baidumusic.VideoPreparedInfo
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.PlayMv
import com.huwei.sweetmusicplayer.frameworks.VideoManager
import com.huwei.sweetmusicplayer.util.TimeUtil
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.activity_playmv.*

/**
 * Created by huwei on 18-2-1.
 */
class PlayMvActivity : BaseActivity(), PlayMvContract.View, View.OnClickListener {
    companion object {
        fun getStartActIntent(context: Context, songId: String): Intent {
            val intent = Intent(context, PlayMvActivity::class.java)
            intent.putExtra(IntentExtra.EXTRA_SONG_ID, songId)
            return intent
        }
    }

    private var mPresenter: PlayMvContract.Presenter? = null
    private var mSongId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playmv)

        mSongId = intent.getStringExtra(IntentExtra.EXTRA_SONG_ID)

        initView()

        PlayMvPresenter(this, this)
        mPresenter!!.start()
        mPresenter!!.loadMvInfo(mSongId)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivBack -> onBackPressed()
            R.id.ivPlayToggle -> {
            }
            R.id.ivFullScreen -> {
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        VideoManager.stop()
    }

    override fun getStatusBarColor(): Int {
        return Color.BLACK
    }

    override fun isActivityNeedBus(): Boolean {
        return true
    }

    private fun initView() {
        videoSurfaceView.setWHRatio(16, 9)

        ivBack.setOnClickListener(this)
        ivPlayToggle.setOnClickListener(this)
        ivFullScreen.setOnClickListener(this)
    }

    override fun setPresenter(presenter: PlayMvContract.Presenter) {
        mPresenter = presenter
    }

    override fun getPlayView(): SurfaceView {
        return videoSurfaceView
    }

    override fun showMvInfo(info: PlayMv.MvInfo) {
        tvSongName.text = info.title
        tvArtist.text = info.artist
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = arrayOf(Tag(BusTag.VideoPlay.PREPARED))
    )
    fun onVideoInfoPrepared(videoPreparedInfo: VideoPreparedInfo) {
        ivPlayToggle.isChecked = videoPreparedInfo.isPlaying
        tvDuration.text = TimeUtil.mill2mmss(videoPreparedInfo.duration)
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = arrayOf(Tag(BusTag.VideoPlay.PROGRESS_CHANGED))
    )
    fun onVideoProgressChanged(progress: Int) {
        seekBarVideo.max = 100
        seekBarVideo.setProgress(progress)
    }
}