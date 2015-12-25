package com.huwei.sweetmusicplayer;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.baidumusic.po.ArtistInfo;
import com.huwei.sweetmusicplayer.fragments.SongListFragment_;
import com.huwei.sweetmusicplayer.interfaces.IGetBindAutoListView;
import com.huwei.sweetmusicplayer.ui.adapters.PagerAdapter;
import com.huwei.sweetmusicplayer.ui.views.ArtistInfoView;
import com.huwei.sweetmusicplayer.ui.widgets.GradientToolbar;
import com.huwei.sweetmusicplayer.ui.widgets.VerticalScrollView;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;

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
public class ArtistInfoActivity extends BaseActivity {

    public static final String ARTISTID = "artist_id";
    public static final String TINGUID = "tinguid";

    public static final String RES_TITLE[] = new String[]{"歌曲", "专辑"};

    @ViewById
    GradientToolbar gtoolbar;
    @ViewById(R.id.actionbar)
    Toolbar toolbar;
    @ViewById(R.id.view_artist_info)
    ArtistInfoView mHeaderView;
    @ViewById(R.id.view_scroll_container)
    VerticalScrollView mScrollView;
    @ViewById(R.id.viewpager)
    ViewPager mViewPager;
    @ViewById(R.id.tabs)
    TabLayout mTab;

    private String ting_uid;
    private String artist_id;

    private Fragment mSongListFragment;
    private Fragment mAlbumListFragment;
    private PagerAdapter mPagerAdapter;

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
        initViewPager();
        intTab();
        initBinding();
        initListView();
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

    void initViewPager() {
        mSongListFragment = SongListFragment_.builder().artist_id(artist_id).ting_uid(ting_uid).build();
        mAlbumListFragment = SongListFragment_.builder().artist_id(artist_id).ting_uid(ting_uid).build();
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), Arrays.asList(mSongListFragment, mAlbumListFragment)) {
            @Override
            public CharSequence getPageTitle(int position) {
                return RES_TITLE[position];
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
    }

    void intTab() {
        mTab.setupWithViewPager(mViewPager);
        mTab.setTabsFromPagerAdapter(mPagerAdapter);
    }

    void initBinding() {
        gtoolbar.bindScrollView(mScrollView);
        gtoolbar.bindHeaderView(mHeaderView);
//        mScrollView.bindAutoListView(lv_songs_album);
    }

    void initListView() {

    }

    void getArtistInfo() {
        BaiduMusicUtil.getArtistInfo(ting_uid, artist_id, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                ArtistInfo artistInfo = new Gson().fromJson(response, ArtistInfo.class);
                mHeaderView.bind(artistInfo, gtoolbar);
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
        IGetBindAutoListView iGetBindAutoListView = (IGetBindAutoListView) mPagerAdapter.getItem(position);
        mScrollView.bindAutoListView(iGetBindAutoListView.getAutoListView());
    }
}
