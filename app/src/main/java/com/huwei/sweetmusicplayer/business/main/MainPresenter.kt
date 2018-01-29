package com.huwei.sweetmusicplayer.business.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.huwei.sweetmusicplayer.IMusicControllerService
import com.huwei.sweetmusicplayer.business.TimeCountDown
import com.huwei.sweetmusicplayer.business.ViewHoldPresenter
import com.huwei.sweetmusicplayer.business.abstracts.AbstractMusic
import com.huwei.sweetmusicplayer.business.core.MusicControllerService
import com.huwei.sweetmusicplayer.business.core.MusicManager
import com.huwei.sweetmusicplayer.util.Environment
import java.util.ArrayList

/**
 * Created by huwei on 18-1-27.
 */
class MainPresenter(context: Context, view: MainContract.View) :
        ViewHoldPresenter<MainContract.View>(context, view), MainContract.Presenter {
    var isServiceBinding = false
    var mTimeCountDown: TimeCountDown? = null

    override fun start() {
        loadMusicPlayAgo()
    }

    override fun bindMusicController() {
        if (!isServiceBinding) {
            val connection = object : ServiceConnection {
                override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                    Log.i(TAG, "onServiceConnected")
                    isServiceBinding = true
                    MusicManager.get().bindService(IMusicControllerService.Stub.asInterface(iBinder))
                }

                override fun onServiceDisconnected(componentName: ComponentName) {
                    isServiceBinding = false
                    MusicManager.get().bindService(null)
                }
            }

            val intent = Intent(mContext, MusicControllerService::class.java)
            mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun unBindMusicController() {
        if (isServiceBinding) {
            val intent = Intent(mContext, MusicControllerService::class.java)
            mContext.stopService(intent)
        }
    }

    override fun loadMusicPlayAgo() {
        val music = Environment.getRecentMusic()
        if (music != null) {
            val list = ArrayList<AbstractMusic>()
            list.add(music)
            MusicManager.get().preparePlayingList(0, list)
        }
    }

    override fun getSleepItem(): Int {
        if (mTimeCountDown == null) {
            mTimeCountDown = TimeCountDown()
        }
        return mTimeCountDown!!.selectItem
    }

    override fun setSleepCountDownByItem(item: Int) {
        if (mTimeCountDown == null) {
            mTimeCountDown = TimeCountDown()
        }
        mTimeCountDown!!.setCountDownItem(item)
    }
}