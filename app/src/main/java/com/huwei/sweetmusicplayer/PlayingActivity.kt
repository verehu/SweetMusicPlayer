package com.huwei.sweetmusicplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

/**
 *
 * @author Ezio
 * @date 2017/06/05
 */
class PlayingActivity : BaseActivity() {

    companion object {
        fun getStartActIntent(context: Context): Intent {
            val intent: Intent = Intent(context, PlayingActivity::class.java)
            return intent
        }
    }

    override fun isNeedStausView(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing)
    }

    fun closeContent(view: View) {
        finish()
    }
}