package com.huwei.sweetmusicplayer.business

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat.setTranslationY
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View

import com.huwei.sweetmusicplayer.R
import com.huwei.sweetmusicplayer.SweetApplication
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.ArtistInfo
import com.huwei.sweetmusicplayer.business.onlinesearch.AlbumListFragment
import com.huwei.sweetmusicplayer.business.onlinesearch.SongListFragment
import com.huwei.sweetmusicplayer.business.interfaces.IAdjustListView
import com.huwei.sweetmusicplayer.business.interfaces.IListViewScroll
import com.huwei.sweetmusicplayer.data.api.RetrofitFactory
import com.huwei.sweetmusicplayer.data.api.SimpleObserver
import com.huwei.sweetmusicplayer.data.api.baidu.BaiduMusicService
import com.huwei.sweetmusicplayer.ui.adapters.PagerAdapter
import com.huwei.sweetmusicplayer.frameworks.BundleBuilder
import com.huwei.sweetmusicplayer.util.Utils

import java.util.Arrays

import com.huwei.sweetmusicplayer.data.contants.IntentExtra.EXTRA_ARTIST_ID
import com.huwei.sweetmusicplayer.data.contants.IntentExtra.EXTRA_TING_UID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_artist_info.*
import kotlinx.android.synthetic.main.layout_gradient_toolbar.*

/**
 * 在线歌手信息
 *
 * @author jerry
 * @date 2015-12-22
 */
class ArtistInfoActivity : BottomPlayActivity(), IListViewScroll {

    private var ting_uid: String = ""
    private var artist_id: String = ""

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

    override fun isNeedStatusView(): Boolean {
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
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
        RetrofitFactory.create(BaiduMusicService::class.java)
                .getArtistInfo(ting_uid, artist_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<ArtistInfo>() {
                    override fun onSuccess(resp: ArtistInfo) {
                        view_artist_info!!.bind(resp!!, gtoolbar)
                        val mTabSong = tabLayout!!.getTabAt(0)
                        if (mTabSong != null) {
                            mTabSong.text = "歌曲(" + resp.songs_total + ")"
                        }
                        val mTabAlbum = tabLayout!!.getTabAt(1)
                        if (mTabAlbum != null) {
                            mTabAlbum.text = "专辑(" + resp.albums_total + ")"
                        }

                        if (resp != null) {
                            gtoolbar!!.setGradientTitle(resp.name)
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
