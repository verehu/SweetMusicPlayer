package com.huwei.sweetmusicplayer;

import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.baidumusic.po.AlbumInfo;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.baidumusic.resp.AlbumDetailResp;
import com.huwei.sweetmusicplayer.contains.IntentExtra;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.helper.BlurHelper;
import com.huwei.sweetmusicplayer.ui.adapters.OnlineMusicAdapter;
import com.huwei.sweetmusicplayer.ui.widgets.GradientToolbar;
import com.huwei.sweetmusicplayer.ui.widgets.auto.AutoListView;
import com.huwei.sweetmusicplayer.ui.widgets.auto.IPullRefershBase;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
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
@EActivity(R.layout.activity_album_info)
public class AlbumInfoActivity extends BaseActivity {

    private View mHeaderView;
    private ImageView iv_bg;

    @Bean
    BlurHelper mBlurHelper;

    ImageView iv_album;
    TextView tv_albumname, tv_artist, tv_pub_date;
    @ViewById(R.id.actionbar)
    Toolbar toolbar;
    @ViewById
    GradientToolbar gtoolbar;
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
        initHeaderView();
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

        mMusicAdapter = new OnlineMusicAdapter(mContext, songList);
        lv_albuminfo.setAdapter(mMusicAdapter);
        lv_albuminfo.setOnItemNoneClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicManager.getInstance().preparePlayingList(position, Song.getAbstractMusicList(songList));
                MusicManager.getInstance().play();
            }
        });
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
                    mImageLoader.loadImage(albumDetail.pic_big, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
//                            genBlurBitmap(BitmapUtil.drawable2bitamp(iv_album.getDrawable()));
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            iv_album.setImageBitmap(loadedImage);
                            mBlurHelper.blurBitmap(loadedImage, 80, new BlurHelper.OnGenerateBitmapCallback() {
                                @Override
                                public void onGenerateBitmap(Bitmap bitmap) {
                                    iv_bg.setImageBitmap(bitmap);
                                    gtoolbar.setToolbarBg(bitmap);
                                }
                            });
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

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
