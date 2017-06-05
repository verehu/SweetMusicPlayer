package com.huwei.sweetmusicplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.huwei.sweetmusicplayer.fragments.LocalMusicFragment
import com.huwei.sweetmusicplayer.fragments.LocalMusicFragment.*

/**
 *
 * @author Ezio
 * @date 2017/06/05
 */
class LocalMusicActivity : BottomPlayActivity() {

    companion object {
        val BUNDLE_ARGS = "BUNDLE_ARGS"

        fun getStartActIntent(context: Context, showtype: Int = 0, title: String? = null, primaryId: Long = 0): Intent {
            val intent: Intent = Intent(context, LocalMusicActivity::class.java)
            val bundle: Bundle = Bundle()
            bundle.putString(TITLE_ARG, title)
            bundle.putInt(SHOWTYPE_ARG, showtype)
            bundle.putLong(PRIMARY_ID_ARG, primaryId)
            intent.putExtra(BUNDLE_ARGS, bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localmusic)

        val data: Intent = getIntent()
        with(data) {
            val fragment : LocalMusicFragment = LocalMusicFragment.get()
            fragment.arguments = getBundleExtra(BUNDLE_ARGS)

            val transition : FragmentTransaction = supportFragmentManager.beginTransaction()
            transition.replace(R.id.fragmentContainer, fragment)
            transition.commit()
        }
    }
}