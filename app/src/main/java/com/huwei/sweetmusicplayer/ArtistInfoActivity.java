package com.huwei.sweetmusicplayer;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.baidumusic.po.ArtistInfo;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.baidumusic.resp.ArtistSongListResp;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.ui.adapters.OnlineMusicAdapter;
import com.huwei.sweetmusicplayer.ui.views.ArtistInfoView;
import com.huwei.sweetmusicplayer.ui.widgets.GradientToolbar;
import com.huwei.sweetmusicplayer.ui.widgets.VerticalScrollView;
import com.huwei.sweetmusicplayer.ui.widgets.auto.CompatScrollViewAutoListView;
import com.huwei.sweetmusicplayer.ui.widgets.auto.IPullRefershBase;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

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


    @ViewById
    GradientToolbar gtoolbar;
    @ViewById(R.id.actionbar)
    Toolbar toolbar;
    @ViewById(R.id.view_artist_info)
    ArtistInfoView mHeaderView;
    @ViewById(R.id.view_scroll_container)
    VerticalScrollView mScrollView;



    private String ting_uid;
    private String artist_id;

    private int mSongPageNo;

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
        initListViewHeader();
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

    void initListViewHeader() {
        gtoolbar.bindScrollView(mScrollView);
        gtoolbar.bindHeaderView(mHeaderView);
        mScrollView.bindAutoListView(lv_songs_album);
    }

    void initListView() {

    }

    void getArtistInfo() {
        BaiduMusicUtil.getArtistInfo(ting_uid, artist_id, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                ArtistInfo artistInfo = new Gson().fromJson(response, ArtistInfo.class);
                mHeaderView.bind(artistInfo, gtoolbar);
            }
        });
    }
}
