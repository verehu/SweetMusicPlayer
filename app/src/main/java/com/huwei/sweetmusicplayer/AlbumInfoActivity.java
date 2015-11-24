package com.huwei.sweetmusicplayer;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.baidumusic.po.AlbumInfo;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.baidumusic.resp.AlbumDetailResp;
import com.huwei.sweetmusicplayer.contains.IntentExtra;
import com.huwei.sweetmusicplayer.ui.adapters.OnlineMusicAdapter;
import com.huwei.sweetmusicplayer.ui.widgets.auto.AutoListView;
import com.huwei.sweetmusicplayer.ui.widgets.auto.IPullRefershBase;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线 专辑详情页面
 *
 * @author jerry
 * @date 2015-09-13
 */
@EActivity(R.layout.activity_album_detail)
public class AlbumInfoActivity extends BaseActivity {
    @ViewById
    ImageView iv_album;
    @ViewById
    TextView tv_albumname, tv_artist, tv_pub_date;
    @ViewById(R.id.actionbar)
    Toolbar toolbar;
    @ViewById
    AutoListView lv_albuminfo;

    String albumId;

    private OnlineMusicAdapter mMusicAdapter;
    private List<Song> songList = new ArrayList<>();

    private ImageLoader mImageLoader;

    @AfterViews
    void init() {
        mImageLoader = SweetApplication.getImageLoader();
        albumId = getIntent().getStringExtra(IntentExtra.EXTRA_ALBUM_ID);

        initToolBar();
        initView();
    }

    void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setTitle(R.string.ativity_album_detail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked(v);
            }
        });
    }

    void initView() {
        lv_albuminfo.setRefreshEnable(false);
        lv_albuminfo.setOnLoadListener(new IPullRefershBase.OnLoadListener() {
            @Override
            public void onLoad() {
                getAlbumInfo();
            }
        });
        lv_albuminfo.onLoad();

        mMusicAdapter = new OnlineMusicAdapter(mContext, songList);
        lv_albuminfo.setAdapter(mMusicAdapter);
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
                    mImageLoader.displayImage(albumDetail.pic_big, iv_album);
                    tv_albumname.setText(albumDetail.title);
                    tv_artist.setText("歌手：" + albumDetail.author);
                    tv_pub_date.setText("发行时间：" + albumDetail.publishtime);
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
