package com.huwei.sweetmusicplayer.business.playmusic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.business.BaseActivity

/**
 *
 * @author Ezio
 * @date 2017/06/05
 */
class PlayMusicActivity : BaseActivity() {

    companion object {
        fun getStartActIntent(context: Context): Intent {
            val intent: Intent = Intent(context, PlayMusicActivity::class.java)
            return intent
        }
    }

    override fun isNeedStatusView(): Boolean {
        return false
    }

    override fun isNeedWindowBGTransparent(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing)

        PlayMusicPresenter(baseContext, supportFragmentManager.findFragmentById(R.id.play_fragment) as PlayMusicFragment)
    }

    fun closeContent(view: View) {
        finish()
    }
}