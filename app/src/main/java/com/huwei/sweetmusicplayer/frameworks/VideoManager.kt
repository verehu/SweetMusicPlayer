package com.huwei.sweetmusicplayer.frameworks

import android.view.SurfaceView
import com.huwei.sweetmusicplayer.data.contants.BusTag
import com.huwei.sweetmusicplayer.data.models.baidumusic.VideoPreparedInfo
import com.huwei.sweetmusicplayer.util.LogUtils
import com.hwangjr.rxbus.RxBus
import hu.akarnokd.rxjava2.operators.FlowableTransformers
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import tv.danmaku.ijk.media.player.IMediaPlayer
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import java.util.concurrent.TimeUnit
import io.reactivex.processors.PublishProcessor

/**
 * Created by huwei on 18-1-31.
 */
object VideoManager : IMediaPlayer.OnPreparedListener, IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnSeekCompleteListener, IMediaPlayer.OnBufferingUpdateListener,
        IMediaPlayer.OnErrorListener {
    private var mediaPlayer: IMediaPlayer? = null
    private val mPlayPublish = PublishProcessor.create<Boolean>()

    init {
        createPlayer()

        Flowable.interval(500, TimeUnit.MILLISECONDS)
                .compose(FlowableTransformers.valve(mPlayPublish, false))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    LogUtils.i("subscribe:" + Thread.currentThread() +
                            "    pos:" + mediaPlayer!!.currentPosition +
                            "   duration:" + mediaPlayer!!.duration)

                    RxBus.get().post(BusTag.VideoPlay.PROGRESS_CHANGED,
                            100 * mediaPlayer!!.currentPosition / mediaPlayer!!.duration)
                }
    }

    fun load(dateSource: String, surfaceView: SurfaceView) {
        //每次都要重新创建IMediaPlayer
        createPlayer()

        try {
            mediaPlayer!!.setDataSource(dateSource)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //给mediaPlayer设置视图
        mediaPlayer!!.setDisplay(surfaceView.getHolder());

        mediaPlayer!!.prepareAsync()
    }

    fun createPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.setDisplay(null)
            mediaPlayer?.release()
        }
        val ijkMediaPlayer = IjkMediaPlayer()
        ijkMediaPlayer.setLogEnabled(false)

        //开启硬解码        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1)
        mediaPlayer = ijkMediaPlayer

        mediaPlayer!!.setOnPreparedListener(this)
        mediaPlayer!!.setOnInfoListener(this)
        mediaPlayer!!.setOnSeekCompleteListener(this)
        mediaPlayer!!.setOnBufferingUpdateListener(this)
        mediaPlayer!!.setOnErrorListener(this)
        mediaPlayer!!.setOnTimedTextListener { iMediaPlayer, ijkTimedText ->
            LogUtils.i("Video: time：" + ijkTimedText.text + "   timeNum:" + iMediaPlayer.currentPosition
                    + "   duration:" + iMediaPlayer.duration)
        }
    }

    override fun onPrepared(mediaPlayer: IMediaPlayer) {
        RxBus.get().post(BusTag.VideoPlay.PREPARED, VideoPreparedInfo(mediaPlayer.duration))
        start()
    }

    override fun onInfo(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
        return true
    }

    override fun onSeekComplete(p0: IMediaPlayer?) {
    }

    override fun onBufferingUpdate(p0: IMediaPlayer?, p1: Int) {
    }

    override fun onError(p0: IMediaPlayer?, p1: Int, p2: Int): Boolean {
        return true
    }

    fun start() {
        mediaPlayer?.start()
        notifyPlayStatusChange()
    }

    fun release() {
        if (mediaPlayer != null) {
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    fun pause() {
        if (mediaPlayer != null) {
            mediaPlayer!!.pause()
            notifyPlayStatusChange()
        }
    }

    fun stop() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            notifyPlayStatusChange()
        }
    }

    fun reset() {
        if (mediaPlayer != null) {
            mediaPlayer!!.reset()
        }
    }

    fun getDuration(): Long {
        if (mediaPlayer != null) {
            return mediaPlayer!!.getDuration()
        } else {
            return 0
        }
    }

    fun getCurrentPosition(): Long {
        return mediaPlayer!!.getCurrentPosition()
    }

    fun seekTo(l: Long) {
        mediaPlayer!!.seekTo(l)
    }

    private fun notifyPlayStatusChange() {
        val isPlaying = mediaPlayer!!.isPlaying
        mPlayPublish.onNext(isPlaying)

        RxBus.get().post(BusTag.VideoPlay.PREPARED, isPlaying)
    }
}