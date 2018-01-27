package com.huwei.sweetmusicplayer.business.fragments

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup

import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.business.OnlineSearchActivity
import com.huwei.sweetmusicplayer.contants.MusicViewTypeContain
import com.huwei.sweetmusicplayer.business.fragments.base.BaseFragment
import com.huwei.sweetmusicplayer.business.ui.adapters.PagerStateAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_gradient_toolbar.*


/**
 * @author Jayce
 * @date 2015/6/17
 */
class MainFragment : BaseFragment(), MusicViewTypeContain {

    private var mView: View? = null

    lateinit internal var mPagerAdapter: PagerStateAdapter

    private var sleeptime: Long = 0
    private val SLEEP = 0

    val isSleepCountDown: Boolean
        get() = sleeptime > 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //第二次可以直接返回mView
        if (mView != null) {
            val parent = mView!!.parent as ViewGroup
            parent?.removeView(mView)
            return mView
        }
        Log.i(TAG, "onCreateView")

        return LayoutInflater.from(mContext).inflate(R.layout.fragment_main, null)
    }

    override fun onResume() {
        super.onResume()
        if (isSleepCountDown) {
            setSleepBarVisiable(true)
        } else {
            setSleepBarVisiable(false)
        }

        mPagerAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //防止第二次加载
        if (mView == null) {
            mView = getView()
            initToolBar()
            initPager()
        }
    }

    internal fun initToolBar() {
        toolbar!!.title = "SweetMusicPlayer"
        toolbar!!.inflateMenu(R.menu.activity_main_menu)
        initMenu(toolbar!!.menu)
        toolbar!!.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_search -> {
                }
                else -> {
                }
            }
            true
        }
    }


    private fun initMenu(menu: Menu) {
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        val searchManager = mAct.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val componentName = ComponentName(mAct, OnlineSearchActivity::class.java)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(true)

        searchView.setOnSearchClickListener { }
    }

    private fun initPager() {
        mPagerAdapter = object : PagerStateAdapter(
                activity.supportFragmentManager) {

            override fun getPageTitle(position: Int): CharSequence {
                return resources.getStringArray(R.array.tab_titles)[position]
            }
        }

        // add tabs_recent
        //        mPagerAdapter.addFragment(new RecentlyAddedFragment());
        // add tab_songs
        val bundle = Bundle()
        bundle.putInt(MusicViewTypeContain.MUSIC_SHOW_TYPE, MusicViewTypeContain.SHOW_MUSIC)

        val tabs_str = resources.getStringArray(R.array.tab_titles)

        val musicFragment = LocalMusicFragment()
        musicFragment.arguments = bundle
        mPagerAdapter.addFragment(musicFragment)
        // add tab_artists
        mPagerAdapter.addFragment(LocalArtistFragment())
        // add tab_albums
        mPagerAdapter.addFragment(LocalAlbumFragment())
        //add tab_online
        //        mPagerAdapter.addFragment(new OnlineFragment_());
        // add tab_playlists
        //        mPagerAdapter.addFragment(new PlaylistsFragment());
        // add tab_genres
        //        mPagerAdapter.addFragment(new GenresFragment());

        viewPager!!.adapter = mPagerAdapter

        tabs!!.setupWithViewPager(viewPager)

        tabs!!.setTabsFromPagerAdapter(mPagerAdapter)
    }


    fun setSleepBarVisiable(flag: Boolean) {
        val visiblity = if (flag) View.VISIBLE else View.GONE
        if (ll_sleepbar!!.visibility != visiblity) {
            ll_sleepbar!!.visibility = visiblity
        }
    }
}
