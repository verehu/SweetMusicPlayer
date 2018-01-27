package com.huwei.sweetmusicplayer.business

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat.setTranslationY
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View

import com.google.gson.Gson
import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.SweetApplication
import com.huwei.sweetmusicplayer.business.baidumusic.po.ArtistInfo
import com.huwei.sweetmusicplayer.business.fragments.AlbumListFragment
import com.huwei.sweetmusicplayer.business.fragments.BaseScrollTabFragment
import com.huwei.sweetmusicplayer.business.fragments.SongListFragment
import com.huwei.sweetmusicplayer.business.interfaces.IAdjustListView
import com.huwei.sweetmusicplayer.business.interfaces.IListViewScroll
import com.huwei.sweetmusicplayer.business.ui.adapters.PagerAdapter
import com.huwei.sweetmusicplayer.frameworks.BundleBuilder
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil
import com.huwei.sweetmusicplayer.util.HttpHandler
import com.huwei.sweetmusicplayer.util.Utils

import java.util.Arrays

import com.huwei.sweetmusicplayer.contants.IntentExtra.EXTRA_ARTIST_ID
import com.huwei.sweetmusicplayer.contants.IntentExtra.EXTRA_TING_UID
import kotlinx.android.synthetic.main.activity_artist_info.*
import kotlinx.android.synthetic.main.layout_gradient_toolbar.*

/**
 * 在线歌手信息
 *
 * @author jerry
 * @date 2015-12-22
 */
class ArtistInfoActivity : BottomPlayActivity(), IListViewScroll {

    private var ting_uid: String? = null
    private var artist_id: String? = null

    private var mSongListFragment: BaseScrollTabFragment? = null
    private var mAlbumListFragment: BaseScrollTabFragment? = null
    private var mPagerAdapter: PagerAdapter? = null

    private var mLimitHeight: Int = 0 //headerflow 移动的最小限制

    val offestY: Int
        get() {
            val paddingHeight = (ll_flow_header!!.translationY + ll_flow_header!!.measuredHeight).toInt()
            Log.i(TAG, "paddingHeight:" + paddingHeight + "ll_flow_header.getTranslationY():" + ll_flow_header!!.translationY)
            return paddingHeight
        }

    override fun isNeedStausView(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_artist_info)

        ting_uid = intent.getStringExtra(TINGUID)
        artist_id = intent.getStringExtra(ARTISTID)

        initToolBar()
        initMeasure()
        initViewPager()
        intTab()
        initGToolBar()
        getArtistInfo()
    }

    internal fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        toolbar!!.setTitle(R.string.activity_artist_info)
        toolbar!!.setNavigationOnClickListener { v -> onBackClicked(v) }
    }

    internal fun initMeasure() {
        ll_flow_header!!.measure(View.MeasureSpec.makeMeasureSpec(SweetApplication.mScreenWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        gtoolbar!!.measure(View.MeasureSpec.makeMeasureSpec(SweetApplication.mScreenWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        //由于这里gtoolbar算出来只是56dp,需要加上状态栏的高度
        mLimitHeight = view_artist_info!!.measuredHeight - (gtoolbar!!.measuredHeight + Utils.getStatusBarHeight())
        Log.i(TAG, "view_artist_info:" + view_artist_info!!.measuredHeight + " gtoolbar.getMeasuredHeight():" + gtoolbar!!.measuredHeight + "   mLimitHeight:" + mLimitHeight)
    }

    internal fun initViewPager() {
        mSongListFragment = SongListFragment()
        mSongListFragment!!.arguments = BundleBuilder().extra(EXTRA_ARTIST_ID, artist_id!!).extra(EXTRA_TING_UID, ting_uid!!).build()
        mAlbumListFragment = AlbumListFragment()
        mAlbumListFragment!!.arguments = BundleBuilder().extra(EXTRA_ARTIST_ID, artist_id!!).extra(EXTRA_TING_UID, ting_uid!!).build()
        mPagerAdapter = object : PagerAdapter(supportFragmentManager, Arrays.asList<Fragment>(mSongListFragment as Fragment?, mAlbumListFragment)) {
            override fun getPageTitle(position: Int): CharSequence {
                return RES_TITLE[position]
            }
        }
        viewpager!!.adapter = mPagerAdapter
        viewpager.addOnPageChangeListener(object  : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                // Something Here
                Log.i(TAG, "viewPager position:" + position)

                val iAdjustListView = mPagerAdapter!!.getItem(position) as IAdjustListView
                iAdjustListView.adjustListView(offestY)
            }
        })

        for (i in 0 until mPagerAdapter!!.count) {
            val fragment = mPagerAdapter!!.getItem(i) as BaseScrollTabFragment
            fragment.setIListViewScroll(this)
            fragment.setFlowHeight(ll_flow_header!!.measuredHeight)
            fragment.setlowLimitHeight(mLimitHeight)
        }
    }

    internal fun intTab() {
        tabLayout!!.setupWithViewPager(viewpager)
    }

    internal fun initGToolBar() {
        gtoolbar!!.setTitle(R.string.activity_artist_info)
        gtoolbar!!.bindHeaderView(view_artist_info)
    }


    internal fun getArtistInfo() {
        BaiduMusicUtil.getArtistInfo(ting_uid, artist_id, object : HttpHandler() {
            override fun onSuccess(response: String) {
                val artistInfo = Gson().fromJson(response, ArtistInfo::class.java)
                view_artist_info!!.bind(artistInfo!!, gtoolbar)
                val mTabSong = tabLayout!!.getTabAt(0)
                if (mTabSong != null) {
                    mTabSong.text = "歌曲(" + artistInfo.songs_total + ")"
                }
                val mTabAlbum = tabLayout!!.getTabAt(1)
                if (mTabAlbum != null) {
                    mTabAlbum.text = "专辑(" + artistInfo.albums_total + ")"
                }

                if (artistInfo != null) {
                    gtoolbar!!.setGradientTitle(artistInfo.name)
                }
            }
        })
    }

    override fun scrollY(scrollY: Int) {
        val translationY = ll_flow_header!!.translationY
        Log.i(TAG, "-scrollY:$scrollY    translationY:$translationY")
        if (translationY != (-scrollY).toFloat()) {
            setTranslationY(ll_flow_header!!, (-scrollY).toFloat())
        }
        gtoolbar!!.adjustHeaderViewAndTitle()
    }

    companion object {

        val ARTISTID = "artist_id"
        val TINGUID = "tinguid"

        val RES_TITLE = arrayOf("歌曲", "专辑")

        fun getStartActIntent(from: Context, ting_uid: String, artist_id: String): Intent {
            val intent = Intent(from, ArtistInfoActivity::class.java)
            intent.putExtra(TINGUID, ting_uid)
            intent.putExtra(ARTISTID, artist_id)
            return intent
        }
    }
}
