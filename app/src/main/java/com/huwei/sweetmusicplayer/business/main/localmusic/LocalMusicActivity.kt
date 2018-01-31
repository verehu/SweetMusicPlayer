package com.huwei.sweetmusicplayer.business.main.localmusic

import com.huwei.sweetmusicplayer.business.BottomPlayActivity
import com.huwei.sweetmusicplayer.data.contants.IntentExtra.*

/**
 *
 * @author Ezio
 * @date 2017/06/05
 */
class LocalMusicActivity : BottomPlayActivity() {

    companion object {
        val BUNDLE_ARGS = "BUNDLE_ARGS"

        fun getStartActIntent(context: android.content.Context, showtype: Int = 0, title: String? = null, primaryId: Long = 0): android.content.Intent {
            val intent: android.content.Intent = android.content.Intent(context, LocalMusicActivity::class.java)
            val bundle: android.os.Bundle = android.os.Bundle()
            bundle.putString(EXTRA_TITLE, title)
            bundle.putInt(EXTAR_SHOWTYPE, showtype)
            bundle.putLong(EXTRA_PRIMARY_ID, primaryId)
            intent.putExtra(BUNDLE_ARGS, bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.huwei.sweetmusicplayer.R.layout.activity_localmusic)

        val data: android.content.Intent = getIntent()
        with(data) {
            val fragment : LocalMusicFragment = LocalMusicFragment.get()
            fragment.arguments = getBundleExtra(BUNDLE_ARGS)

            val transition : android.support.v4.app.FragmentTransaction = supportFragmentManager.beginTransaction()
            transition.replace(com.huwei.sweetmusicplayer.R.id.fragmentContainer, fragment)
            transition.commit()
        }
    }
}