package com.huwei.sweetmusicplayer;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.baidumusic.po.ArtistInfo;
import com.huwei.sweetmusicplayer.fragments.AlbumListFragment_;
import com.huwei.sweetmusicplayer.fragments.BaseScrollTabFragment;
import com.huwei.sweetmusicplayer.fragments.SongListFragment_;
import com.huwei.sweetmusicplayer.interfaces.IListViewScroll;
import com.huwei.sweetmusicplayer.ui.adapters.PagerAdapter;
import com.huwei.sweetmusicplayer.ui.views.ArtistInfoView;
import com.huwei.sweetmusicplayer.ui.widgets.GradientToolbar;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;
import com.nineoldandroids.view.ViewHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PageSelected;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

/**
 * 在线歌手信息
 *
 * @author jerry
 * @date 2015-12-22
 */
@EActivity(R.layout.activity_artist_info)
public class ArtistInfoActivity extends BaseActivity implements IListViewScroll {

    public static final String ARTISTID = "artist_id";
    public static final String TINGUID = "tinguid";

    public static final String RES_TITLE[] = new String[]{"歌曲", "专辑"};

    @ViewById
    GradientToolbar gtoolbar;
    @ViewById(R.id.actionbar)
    Toolbar toolbar;
    @ViewById(R.id.view_artist_info)
    ArtistInfoView mArtistInfoView;
    @ViewById(R.id.viewpager)
    public ViewPager mViewPager;
    @ViewById(R.id.tabs)
    TabLayout mTab;
    @ViewById(R.id.ll_flow_header)
    View mHeaderFlow;

    private String ting_uid;
    private String artist_id;

    private BaseScrollTabFragment mSongListFragment;
    private BaseScrollTabFragment mAlbumListFragment;
    private PagerAdapter mPagerAdapter;

    private int mLimitHeight; //headerflow 移动的最小限制

    public static Intent getStartActIntent(Context from, String ting_uid, String artist_id) {
        Intent intent = new Intent(from, ArtistInfoActivity_.class);
        intent.putExtra(TINGUID, ting_uid);
        intent.putExtra(ARTISTID, artist_id);
        return intent;
    }

    @AfterViews
    void init() {
        ting_uid = getIntent().getStringExtra(TINGUID);
        artist_id = getIntent().getStringExtra(ARTISTID);

        initToolBar();
        initMeasure();
        initViewPager();
        intTab();
        initBinding();
        getArtistInfo();
    }

    void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setTitle(R.string.activity_artist_info);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked(v);
            }
        });
    }

    void initMeasure() {
        mHeaderFlow.measure(View.MeasureSpec.makeMeasureSpec(SweetApplication.mScreenWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mLimitHeight = mArtistInfoView.getMeasuredHeight();
    }

    void initViewPager() {
        mSongListFragment = SongListFragment_.builder().artist_id(artist_id).ting_uid(ting_uid).build();
        mAlbumListFragment = AlbumListFragment_.builder().artist_id(artist_id).ting_uid(ting_uid).build();
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), Arrays.asList((Fragment) mSongListFragment, mAlbumListFragment)) {
            @Override
            public CharSequence getPageTitle(int position) {
                return RES_TITLE[position];
            }
        };
        mViewPager.setAdapter(mPagerAdapter);

        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            BaseScrollTabFragment fragment = (BaseScrollTabFragment) mPagerAdapter.getItem(i);
            fragment.setIListViewScroll(this);
            fragment.setFlowHeight(mHeaderFlow.getMeasuredHeight());
            fragment.setlowLimitHeight(mLimitHeight);
        }
    }

    void intTab() {
        mTab.setTabsFromPagerAdapter(mPagerAdapter);
    }

    void initBinding() {
        gtoolbar.bindHeaderView(mArtistInfoView);
//        mScrollView.bindAutoListView(lv_songs_album);
    }


    void getArtistInfo() {
        BaiduMusicUtil.getArtistInfo(ting_uid, artist_id, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                ArtistInfo artistInfo = new Gson().fromJson(response, ArtistInfo.class);
                mArtistInfoView.bind(artistInfo, gtoolbar);
                TabLayout.Tab mTabSong = mTab.getTabAt(0);
                if (mTabSong != null) {
                    mTabSong.setText("歌曲(" + artistInfo.songs_total + ")");
                }
                TabLayout.Tab mTabAlbum = mTab.getTabAt(1);
                if (mTabAlbum != null) {
                    mTabAlbum.setText("专辑(" + artistInfo.albums_total + ")");
                }
            }
        });
    }

    @PageSelected(R.id.viewpager)
    void onPageSelected(int position) {
        // Something Here
        Log.i(TAG, "viewPager position:" + position);
    }

    @Override
    public void scrollY(int scrollY) {
        Log.i(TAG, "locY:" + scrollY);

        int topY = mHeaderFlow.getTop();
        ViewHelper.setTranslationY(mHeaderFlow, -scrollY - topY);
    }

    public int getOffestY() {
        int paddingHeight = (int) (mHeaderFlow.getTop() + mHeaderFlow.getMeasuredHeight());
        Log.i(TAG, "paddingHeight:" + paddingHeight + "getTop:" + mHeaderFlow.getTop());
        return paddingHeight;
    }
}
