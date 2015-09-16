package com.huwei.sweetmusicplayer;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.baidumusic.po.AlbumDetail;
import com.huwei.sweetmusicplayer.baidumusic.resp.AlbumDetailResp;
import com.huwei.sweetmusicplayer.contains.IntentExtra;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 专辑详情页面
 *
 * @author jerry
 * @date 2015-09-13
 */
@EActivity(R.layout.activity_album_detail)
public class AlbumDetailActivity extends BaseActivity {
    @ViewById
    ImageView iv_album;
    @ViewById
    TextView tv_albumname, tv_artist, tv_pub_date;
    @ViewById
    Toolbar toolbar;

    String albumId;

    private ImageLoader mImageLoader;

    @AfterViews
    void init(){
        mImageLoader = SweetApplication.getImageLoader();
        albumId = getIntent().getStringExtra(IntentExtra.EXTRA_ALBUM_ID);

        initToolBar();
        getAlubmInfo();
    }

    void initToolBar(){
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

    private void getAlubmInfo(){
        BaiduMusicUtil.getAlbumDetail(albumId,new HttpHandler(this) {
            @Override
            public void onSuccess(String response) {
                AlbumDetailResp resp = new Gson().fromJson(response,AlbumDetailResp.class);
                AlbumDetail album = resp.data;
                if(album!=null){
                    mImageLoader.displayImage(album.getAlbumPicSmall(),iv_album);
                    tv_albumname.setText(album.getAlbumName());
                    tv_artist.setText(album.getArtistName());
                }
            }
        });
    }
}
