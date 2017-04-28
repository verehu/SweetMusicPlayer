package com.huwei.sweetmusicplayer;


import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.baidumusic.po.Album;
import com.huwei.sweetmusicplayer.baidumusic.po.Artist;
import com.huwei.sweetmusicplayer.baidumusic.po.QueryResult;
import com.huwei.sweetmusicplayer.baidumusic.resp.QueryMergeResp;
import com.huwei.sweetmusicplayer.fragments.SearchAlbumFragment_;
import com.huwei.sweetmusicplayer.fragments.SearchArtistFragment_;
import com.huwei.sweetmusicplayer.fragments.SearchSongFragment_;
import com.huwei.sweetmusicplayer.ui.adapters.PagerAdapter;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

/**
 * 在线音乐搜索的结果页面
 *
 * @author Jayce
 * @date 2015/8/17
 */
@EActivity(R.layout.activity_online_search)
public class OnlineSearchActivity extends BaseActivity {
    public static final String TAG = "OnlineSearchActivity";

    private int total = 0;

    private int pageNo = 1;
    private int pageSize = 50;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById
    View ll_link_view;
    @ViewById
    ImageView iv_img;
    @ViewById
    TextView tv_primary, tv_second;
    @ViewById(R.id.viewpager)
    ViewPager mViewpager;
    @ViewById(R.id.tabs)
    TabLayout mTabs;

    private String mQuery;
    private ImageLoader mImageLoader = SweetApplication.getImageLoader();

    public static final String TAB_TITLES[] = {"歌曲", "歌手", "专辑"};

    @AfterViews
    void init() {
        initView();
        initListener();

        handleIntent(getIntent());
    }

    void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked(v);
            }
        });

    }

    void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            setTitle(mQuery);

            doQuery();
            Toast.makeText(mContext, mQuery, Toast.LENGTH_SHORT).show();
        }
    }

    void doQuery() {
        doQuery(pageNo, new OnGetQueryData() {
            @Override
            public void onGetData(QueryResult queryMergeResp) {
                //初始化ViewPager

                if (queryMergeResp != null) {
                    handleLinkView(queryMergeResp);

                    Fragment mSongFragment = SearchSongFragment_.builder().song_info(queryMergeResp.song_info).build();
                    Fragment mArtistFragment = SearchArtistFragment_.builder().artist_info(queryMergeResp.artist_info).build();
                    Fragment mAlbumFrament = SearchAlbumFragment_.builder().album_info(queryMergeResp.album_info).build();

                    mViewpager.setAdapter(new PagerAdapter(getSupportFragmentManager(), Arrays.asList(mSongFragment, mArtistFragment, mAlbumFrament)) {
                        @Override
                        public CharSequence getPageTitle(int position) {
                            return TAB_TITLES[position];
                        }
                    });

                    mTabs.setupWithViewPager(mViewpager);

                }
            }
        });
    }

    public void doQuery(int pageNo, final OnGetQueryData onGetQueryData) {
        //todo暂时只搜索 1-50个  后续加入下拉刷新列表
        BaiduMusicUtil.queryMerge(mQuery, pageNo, pageSize, new HttpHandler(mContext) {
            @Override
            public void onSuccess(String response) {

                final QueryMergeResp sug = new Gson().fromJson(response, QueryMergeResp.class);
                QueryResult result = sug.result;

                if (onGetQueryData != null && result != null) {
                    onGetQueryData.onGetData(result);
                }
            }
        });
    }

    void handleLinkView(QueryResult queryMergeResp) {
        if (queryMergeResp == null) {
            return;
        }
        switch (queryMergeResp.rqt_type) {
            case 2:
                ll_link_view.setVisibility(View.VISIBLE);
                if (queryMergeResp.artist_info != null && queryMergeResp.artist_info.artist_list != null && queryMergeResp.artist_info.artist_list.size() > 0) {
                    Artist artist = queryMergeResp.artist_info.artist_list.get(0);
                    if (artist != null) {
                        mImageLoader.displayImage(artist.avatar_middle, iv_img);
                        tv_primary.setText(artist.author);
                        tv_second.setText(String.format(mContext.getResources().getString(R.string.artist_second_text), artist.song_num, artist.album_num));
                    }
                }
                break;
            case 3:
                ll_link_view.setVisibility(View.VISIBLE);
                if (queryMergeResp.album_info != null && queryMergeResp.album_info.album_list != null && queryMergeResp.album_info.album_list.size() > 0) {
                    Album album = queryMergeResp.album_info.album_list.get(0);
                    if (album != null) {
                        mImageLoader.displayImage(album.pic_small, iv_img);
                        tv_primary.setText(album.title);
                        tv_second.setText(album.author);
                    }
                }
                break;
            default:
                ll_link_view.setVisibility(View.GONE);
        }
    }


    public interface OnGetQueryData {
        void onGetData(QueryResult queryMergeResp);
    }
}
