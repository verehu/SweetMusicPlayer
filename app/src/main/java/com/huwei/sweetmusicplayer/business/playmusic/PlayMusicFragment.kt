package com.huwei.sweetmusicplayer.business.playmusic

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

import com.android.volley.VolleyError
import com.google.gson.Gson
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.Lrc
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.Song
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.SongSug
import com.huwei.sweetmusicplayer.data.models.baidumusic.resp.MusicSearchSugResp
import com.huwei.sweetmusicplayer.business.comparator.LrcComparator
import com.huwei.sweetmusicplayer.business.BaseFragment
import com.huwei.sweetmusicplayer.data.contants.Contants
import com.huwei.sweetmusicplayer.data.contants.LrcStateContants
import com.huwei.sweetmusicplayer.business.core.MusicManager
import com.huwei.sweetmusicplayer.frameworks.image.BlurBitmapTransformation
import com.huwei.sweetmusicplayer.frameworks.image.GlideApp
import com.huwei.sweetmusicplayer.data.models.LrcContent
import com.huwei.sweetmusicplayer.ui.adapters.QueueAdapter
import com.huwei.sweetmusicplayer.ui.listeners.OnLrcSearchClickListener
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil
import com.huwei.sweetmusicplayer.util.HttpHandler
import com.huwei.sweetmusicplayer.util.LrcUtil
import com.huwei.sweetmusicplayer.util.TimeUtil
import kotlinx.android.synthetic.main.fragment_playing.*

import java.util.Collections

/**
 * 播放界面
 */
class PlayMusicFragment : BaseFragment(), Contants, OnLrcSearchClickListener, LrcStateContants, OnClickListener, PlayMusicContract.View {

    private var mRootView: View? = null

    private var intentFilter: IntentFilter? = null

    private var mScreenWidth: Int = 0

    private var mProgressBarLock: Boolean = false

    internal var queueAdapter: QueueAdapter? = null

    private var mIsFirst = true

    val isDrawerOpen: Boolean
        get() = dl_music_queue!!.isDrawerOpen(Gravity.END)

    private lateinit var presenter: PlayMusicContract.Presenter

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            // TODO Auto-generated method stub
            val action = intent.action

            when (action) {
                Contants.PLAY_STATUS_UPDATE -> {
                    val isPlaying = intent.getBooleanExtra("isPlaying", false)
                    playpage_play!!.isChecked = isPlaying
                    if (isPlaying) rotateView.resume() else rotateView.pause()
                }
                Contants.PLAYBAR_UPDATE -> {
                    val isNewPlayMusic = intent.getBooleanExtra("isNewPlayMusic", false)
                    if (isNewPlayMusic) {
                        loadLrcView()
                        initMusicView()
                    }
                    UpdateSongInfoView()
                }
                Contants.CURRENT_UPDATE -> {
                    val currentTime = intent.getIntExtra("currentTime", 0)
                    playpage_playtime_tv!!.text = TimeUtil.mill2mmss(currentTime.toLong())
                    if (!mProgressBarLock) playpage_progressbar!!.progress = currentTime

                    updateLrcView(currentTime)
                }
                Contants.BUFFER_UPDATE -> {
                    val bufferTime = intent.getIntExtra("bufferTime", 0)
                    if (!mProgressBarLock) playpage_progressbar!!.secondaryProgress = bufferTime
                    updateMusicQueue()
                }

                Contants.UPTATE_MUISC_QUEUE -> updateMusicQueue()
            }
        }

    }

    override fun setPresenter(presenter: PlayMusicContract.Presenter) {
        this.presenter = presenter

        playpage_lrcview.setPresenter(presenter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState)

        intentFilter = IntentFilter()
        intentFilter!!.addAction(Contants.PLAYBAR_UPDATE)
        intentFilter!!.addAction(Contants.CURRENT_UPDATE)
        intentFilter!!.addAction(Contants.UPTATE_MUISC_QUEUE)
        intentFilter!!.addAction(Contants.BUFFER_UPDATE)
        intentFilter!!.addAction(Contants.PLAY_STATUS_UPDATE)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // TODO Auto-generated method stub
        mRootView = LayoutInflater.from(context).inflate(R.layout.fragment_playing, null)
        return mRootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

        val metric = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metric)
        mScreenWidth = metric.widthPixels

        initMusicView()
        UpdateSongInfoView()
        loadLrcView()

        queueAdapter = QueueAdapter(context)

        updateMusicQueue()
        lv_music_queue!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            MusicManager.get().prepareAndPlay(position, MusicManager.get().playingList)
            queueAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_show_music_queue -> btn_show_music_queueWasClicked()
            R.id.centerFrameLayout -> togglePanel()
        }
    }

    override fun togglePanel() {
        if (rotateView.visibility == VISIBLE) {
            rotateView.visibility = GONE
            playpage_lrcview.visibility = VISIBLE
        } else {
            rotateView.visibility = VISIBLE
            playpage_lrcview.visibility = GONE
        }
    }

    internal fun btn_show_music_queueWasClicked() {
        if (isDrawerOpen) {
            closeDrawer()
        } else {
            openDrawer()
        }
    }

    fun openDrawer() {
        dl_music_queue!!.openDrawer(Gravity.END)
    }

    fun closeDrawer() {
        dl_music_queue!!.closeDrawers()
    }


    fun UpdateSongInfoView() {
        val song = MusicManager.get().nowPlayingSong
        if (song != null) {
            playpage_title_tv!!.text = song.title
            playpage_artist_tv!!.text = song.artist

            playpage_duration_tv!!.text = song.durationStr
            playpage_progressbar!!.max = song.duration!!
        }
    }

    fun updateMusicQueue() {
        val nowPlayings = MusicManager.get().playingList
        if (nowPlayings != null) {
            queueAdapter!!.list = nowPlayings
            lv_music_queue!!.adapter = queueAdapter
        }
    }

    fun initListener() {
        playpage_progressbar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            internal var pro: Int = 0

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
                mProgressBarLock = false
                MusicManager.get().seekTo(pro)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
                mProgressBarLock = true
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                           fromUser: Boolean) {
                // TODO Auto-generated method stub
                pro = progress
                playpage_playtime_tv!!.text = TimeUtil.mill2mmss(progress.toLong())

            }
        })

        playpage_next!!.setOnClickListener {
            // TODO Auto-generated method stub
            MusicManager.get().nextSong()
        }

        playpage_previous!!.setOnClickListener {
            // TODO Auto-generated method stub
            MusicManager.get().preSong()
        }


        playpage_play!!.setOnCheckedChangeListener { buttonView, isChecked ->
            // TODO Auto-generated method stub
            if (isChecked != MusicManager.get().isPlaying) {

                if (isChecked) {
                    MusicManager.get().play()
                } else {
                    MusicManager.get().pause()
                }
            }
        }

        playpage_lrcview!!.setOnLrcSearchClickListener(this)
        btn_show_music_queue.setOnClickListener(this)
        centerFrameLayout.setOnClickListener(this)
    }

    internal fun initMusicView() {
        val song = MusicManager.get().nowPlayingSong

        GlideApp.with(context).load(song.artPicHuge).into(rotateView)
        //加载模糊背景图
        GlideApp.with(context).load(song.artPic).transform(BlurBitmapTransformation(song.blurValueOfPlaying())).into(iv_playing_bg)

        val isPlaying = MusicManager.get().isPlaying
        playpage_play!!.isChecked = isPlaying

        if (isPlaying) {
            rotateView.start()
        } else {
            rotateView.pause()
        }
    }

    internal fun loadLrcView() {
        val song = MusicManager.get().nowPlayingSong
        var lrcLists: List<LrcContent>? = null
        if (song is Song) {
            loadLrcBySongId(song)
        }
        lrcLists = LrcUtil.loadLrc(song)
        playpage_lrcview!!.notifyLrcListsChanged(lrcLists)
        playpage_lrcview!!.setLrcState(if (lrcLists!!.size == 0) LrcStateContants.READ_LOC_FAIL else LrcStateContants.READ_LOC_OK)
    }

    internal fun updateLrcView(currentTime: Int) {
        val tempIndex = playpage_lrcview!!.getIndexByLrcTime(currentTime)
        if (tempIndex != playpage_lrcview!!.index) {
            playpage_lrcview!!.setSongIndex(tempIndex)
            playpage_lrcview!!.invalidate()
        }
    }

    override fun onStart() {
        // TODO Auto-generated method stub
        super.onStart()
        Log.i(TAG, "onStart")
        activity.registerReceiver(receiver, intentFilter)

        if (mIsFirst) {
            mIsFirst = false

        }
    }

    override fun onStop() {
        // TODO Auto-generated method stub
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    override fun onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy()
        Log.i(TAG, "onDestory()")
        activity.unregisterReceiver(receiver)
    }

    override fun onLrcSearchClicked(view: View) {
        showLrcSearchDialog()
    }

    fun showLrcSearchDialog() {
        val dialog = Dialog(activity, R.style.lrc_dialog)
        val content = LayoutInflater.from(context).inflate(R.layout.dialog_lrc, null)
        content.minimumWidth = mScreenWidth - 40
        dialog.setContentView(content)
        dialog.show()

        val okBtn = content.findViewById(R.id.ok_btn) as Button
        val cancleBtn = content.findViewById(R.id.cancel_btn) as Button
        val artistEt = content.findViewById(R.id.artist_tv) as EditText
        val musicEt = content.findViewById(R.id.music_tv) as EditText

        val musicInfo = MusicManager.get().nowPlayingSong
        artistEt.setText(musicInfo.artist)
        musicEt.setText(musicInfo.title)
        val btnListener = OnClickListener { v ->
            if (v === okBtn) {
                dialog.dismiss()

                //搜索歌曲
                BaiduMusicUtil.querySug(musicEt.text.toString().split("\\(".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] + " " + artistEt.text.toString(), object : HttpHandler(activity) {
                    override fun onStart() {
                        super.onStart()
                        playpage_lrcview!!.setLrcState(LrcStateContants.QUERY_ONLINE)
                    }

                    override fun onSuccess(response: String) {
                        Log.i(HttpHandler.TAG, "SUG JSON:" + response)

                        val sug = Gson().fromJson(response, MusicSearchSugResp::class.java)

                        if (!sug.isValid) {
                            playpage_lrcview!!.setLrcState(LrcStateContants.QUERY_ONLINE_NULL)
                            return
                        }

                        val songList = sug.song
                        findLrc(songList, 0)
                    }

                    override fun onErrorResponse(error: VolleyError) {
                        super.onErrorResponse(error)
                        playpage_lrcview?.setLrcState(LrcStateContants.QUERY_ONLINE_FAIL)
                    }
                })
            } else if (v === cancleBtn) {
                dialog.dismiss()
            }
        }
        okBtn.setOnClickListener(btnListener)
        cancleBtn.setOnClickListener(btnListener)
    }


    private fun findLrc(songList: List<SongSug>?, index: Int) {
        if (songList == null || songList.size == 0) {
            playpage_lrcview!!.setLrcState(LrcStateContants.QUERY_ONLINE_NULL)
            return
        }
        val song = songList[index]
        val songid = song.songid
        BaiduMusicUtil.queryLrc(songid, object : HttpHandler(activity) {
            override fun onSuccess(response: String) {
                val lrc = Gson().fromJson(response, Lrc::class.java)

                if (!lrc.isValid) {
                    playpage_lrcview!!.setLrcState(LrcStateContants.QUERY_ONLINE_NULL)
                    return
                }

                val lrcLists = LrcUtil.parseLrcStr(lrc.lrcContent)
                // 按时间排序
                Collections.sort(lrcLists, LrcComparator())
                playpage_lrcview!!.notifyLrcListsChanged(lrcLists)
                playpage_lrcview!!.setLrcState(if (lrcLists.size == 0) LrcStateContants.QUERY_ONLINE_NULL else LrcStateContants.QUERY_ONLINE_OK)

                if (lrcLists.size != 0) {
                    LrcUtil.writeLrcToLoc(song.title, song.artist, lrc.lrcContent)
                }
            }

            override fun onErrorResponse(error: VolleyError) {
                super.onErrorResponse(error)

                if (index + 1 < songList.size) {
                    findLrc(songList, index + 1)
                } else {
                    playpage_lrcview!!.setLrcState(LrcStateContants.QUERY_ONLINE_FAIL)
                }
            }
        })
    }

    //加载网络歌曲歌词
    private fun loadLrcBySongId(song: Song?) {
        if (song != null) {
            BaiduMusicUtil.queryLrc(song.song_id, object : HttpHandler(activity) {
                override fun onSuccess(response: String) {
                    val lrc = Gson().fromJson(response, Lrc::class.java)

                    if (!lrc.isValid) {
                        playpage_lrcview!!.setLrcState(LrcStateContants.QUERY_ONLINE_NULL)
                        return
                    }

                    val lrcLists = LrcUtil.parseLrcStr(lrc.lrcContent)
                    // 按时间排序
                    Collections.sort(lrcLists, LrcComparator())
                    playpage_lrcview!!.notifyLrcListsChanged(lrcLists)
                    playpage_lrcview!!.setLrcState(if (lrcLists.size == 0) LrcStateContants.QUERY_ONLINE_NULL else LrcStateContants.QUERY_ONLINE_OK)

                    if (lrcLists.size != 0) {
                        LrcUtil.writeLrcToLoc(song.getTitle(), song.artist, lrc.lrcContent)
                    }
                }

                override fun onErrorResponse(error: VolleyError) {
                    super.onErrorResponse(error)
                    playpage_lrcview!!.setLrcState(LrcStateContants.QUERY_ONLINE_FAIL)
                }
            })
        }
    }
}
