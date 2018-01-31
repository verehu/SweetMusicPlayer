package com.huwei.sweetmusicplayer.business

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.Toast

import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.data.contants.IntentExtra
import com.huwei.sweetmusicplayer.business.interfaces.OnScanListener
import com.huwei.sweetmusicplayer.business.main.MainActivity
import com.huwei.sweetmusicplayer.data.models.MusicInfo
import com.huwei.sweetmusicplayer.util.MusicUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener
import kotlinx.android.synthetic.main.activity_songscan.*

open class SongScanActivity : BaseActivity() {

    private var songCount = 0
    private var mAutoEnterMain: Boolean = false
    private val mMusicUtils = MusicUtils(this)


    internal fun init() {
        toolbar!!.visibility = View.VISIBLE
        toolbar!!.title = "歌曲扫描"
        toolbar!!.setNavigationOnClickListener { onBackPressed() }

        initData()
        initListener()
    }

    internal fun initData() {
        mAutoEnterMain = intent.getBooleanExtra(IntentExtra.EXTRA_AUTO_ENTERMAIN, false)
    }

    internal fun initListener() {
        btnEnterHome!!.setOnClickListener { checkToMain() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songscan)

        init()

        mMusicUtils.setOnScanListener(object : OnScanListener {
            override fun onSuccess() {
                Toast.makeText(mContext, "扫描完毕", Toast.LENGTH_SHORT).show()

                btnEnterHome!!.visibility = View.VISIBLE
            }

            override fun onFail() {
                Toast.makeText(mContext, "扫描失败", Toast.LENGTH_SHORT).show()
            }

            override fun onScan(musicInfo: MusicInfo) {
                songCount++
                tvScanCount!!.text = songCount.toString()
                tvScanNow!!.text = musicInfo.title + "--" + musicInfo.path
            }
        })

        AndPermission.with(this).permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(object : PermissionListener {
                    override fun onSucceed(i: Int, list: List<String>) {
                        mMusicUtils.startScan()
                    }

                    override fun onFailed(i: Int, list: List<String>) {
                        Toast.makeText(mContext, "无存储权限，无法扫描歌曲", Toast.LENGTH_SHORT).show()
                    }
                }).start()
    }

    private fun checkToMain() {
        startActivity(MainActivity.getStartActIntent(mContext))
    }

    companion object {

        fun getStartActIntent(from: Context, autoEnterMain: Boolean = false): Intent {
            val intent = Intent(from, SongScanActivity::class.java)
            intent.putExtra(IntentExtra.EXTRA_AUTO_ENTERMAIN, autoEnterMain)
            return intent
        }
    }
}
 
 




