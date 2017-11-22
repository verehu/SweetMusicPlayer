package com.huwei.sweetmusicplayer.business;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.business.baidumusic.po.AlbumInfo;
import com.huwei.sweetmusicplayer.business.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.business.baidumusic.resp.AlbumDetailResp;
import com.huwei.sweetmusicplayer.contains.IntentExtra;
import com.huwei.sweetmusicplayer.business.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.frameworks.image.BlurBitmapTransformation;
import com.huwei.sweetmusicplayer.frameworks.image.GlideApp;
import com.huwei.sweetmusicplayer.business.ui.adapters.SongAdapter;
import com.huwei.sweetmusicplayer.business.ui.widgets.GradientToolbar;
import com.huwei.sweetmusicplayer.business.ui.widgets.auto.AutoListView;
import com.huwei.sweetmusicplayer.business.ui.widgets.auto.IPullRefershBase;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线 专辑详情页面
 *
 * @author jerry
 * @date 2015-09-13
 */
@EActivity(R.layout.activity_album_info)
public class AlbumInfoActivity extends BaseActivity {

    private View mHeaderView;
    private ImageView iv_bg;

    ImageView iv_album;
    TextView tv_albumname, tv_artist, tv_pub_date;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById
    GradientToolbar gtoolbar;
    @ViewById
    AutoListView lv_albuminfo;

    String albumId;

    private SongAdapter mMusicAdapter;
    private List<Song> songList = new ArrayList<>();

    @Override
    protected boolean isNeedStausView() {
        return false;
    }

    @AfterViews
    void init() {
        albumId = getIntent().getStringExtra(IntentExtra.EXTRA_ALBUM_ID);

        initToolBar();
        initHeaderView();
        initView();
    }

    void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        gtoolbar.setTitle(R.string.ativity_album_detail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked(v);
            }
        });
    }

    void initHeaderView() {
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.listheader_albumdetail, null);
        iv_bg = (ImageView) mHeaderView.findViewById(R.id.iv_bg);
        iv_album = (ImageView) mHeaderView.findViewById(R.id.iv_album);
        tv_albumname = (TextView) mHeaderView.findViewById(R.id.tv_albumname);
        tv_artist = (TextView) mHeaderView.findViewById(R.id.tv_artist);
        tv_pub_date = (TextView) mHeaderView.findViewById(R.id.tv_pub_date);


        gtoolbar.bindListView(lv_albuminfo);
        gtoolbar.bindHeaderView(mHeaderView);
    }

    void initView() {
        lv_albuminfo.addHeaderView(mHeaderView);
        lv_albuminfo.setRefreshEnable(false);
        lv_albuminfo.setOnLoadListener(new IPullRefershBase.OnLoadListener() {

            @Override
            public void onLoad() {
                getAlbumInfo();
            }
        });
        lv_albuminfo.onLoad();
        lv_albuminfo.setOnScrollListener(gtoolbar);

        mMusicAdapter = new SongAdapter(mContext, songList);
        lv_albuminfo.setAdapter(mMusicAdapter);
        lv_albuminfo.setOnItemNoneClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicManager.getInstance().preparePlayingList(position, Song.getAbstractMusicList(songList));
                MusicManager.getInstance().play();
            }
        });
    }

    public static Intent getStartActInent(Context from, String albumId) {
        Intent intent = new Intent(from, AlbumInfoActivity_.class);
        intent.putExtra(IntentExtra.EXTRA_ALBUM_ID, albumId);
        return intent;
    }

    /**
     * 获取专辑详情 包含歌曲的详细信息
     */
    private void getAlbumInfo() {
        BaiduMusicUtil.getAlbumInfo(albumId, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                AlbumDetailResp resp = new Gson().fromJson(response, AlbumDetailResp.class);
                AlbumInfo albumDetail = resp.albumInfo;
                if (albumDetail != null) {
                    GlideApp.with(mContext).asBitmap().load(albumDetail.pic_big).transform(new BlurBitmapTransformation(80) {
                        @NotNull
                        @Override
                        protected Bitmap transform(@NotNull BitmapPool pool, @NotNull Bitmap toTransform, int outWidth, int outHeight) {
                            iv_album.setImageBitmap(toTransform);
                            return super.transform(pool, toTransform, outWidth, outHeight);
                        }
                    }).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            iv_bg.setImageBitmap(resource);
                            gtoolbar.setToolbarBg(resource);
                        }
                    });
                    tv_albumname.setText(albumDetail.title);
                    tv_artist.setText("歌手：" + albumDetail.author);
                    tv_pub_date.setText("发行时间：" + albumDetail.publishtime);

                    gtoolbar.setGradientTitle(albumDetail.title);
                }

                List<Song> data = resp.songlist;
                if (data != null) {
                    songList.clear();

                    songList.addAll(data);
                    mMusicAdapter.notifyDataSetInvalidated();

                    lv_albuminfo.onLoadComplete(false);
                }
            }
        });
    }
}
