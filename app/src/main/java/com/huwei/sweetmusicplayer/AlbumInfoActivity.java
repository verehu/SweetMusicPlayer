package com.huwei.sweetmusicplayer;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

    private View mHeaderView;

    ImageView iv_album;
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

    void initHeaderView(){
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.listheader_albumdetail,null);
        iv_album = (ImageView) mHeaderView.findViewById(R.id.iv_album);
        tv_albumname = (TextView) mHeaderView.findViewById(R.id.tv_albumname);
        tv_artist = (TextView) mHeaderView.findViewById(R.id.tv_artist);
        tv_pub_date = (TextView) mHeaderView.findViewById(R.id.tv_pub_date);
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
                    mImageLoader.displayImage(albumDetail.pic_big, iv_album ,new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
//                            handleAlbumBackgroundColor(BitmapUtil.drawableToBitamp(iv_album.getDrawable()));
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            handleAlbumBackgroundColor(loadedImage);
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {

                        }
                    });
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

    private void handleAlbumBackgroundColor(Bitmap bitmap){
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            /**
             * 提取完之后的回调方法
             */
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if(vibrant!=null) {
                    mHeaderView.setBackgroundColor(vibrant.getPopulation());
                }
            }
        });

    }
}
