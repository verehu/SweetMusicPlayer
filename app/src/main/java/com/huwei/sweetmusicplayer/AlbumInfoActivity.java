package com.huwei.sweetmusicplayer;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.baidumusic.po.AlbumDetail;
import com.huwei.sweetmusicplayer.baidumusic.po.AlbumInfo;
import com.huwei.sweetmusicplayer.baidumusic.resp.AlbumDetailResp;
import com.huwei.sweetmusicplayer.baidumusic.resp.GetAlbumInfoResp;
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
public class AlbumInfoActivity extends BaseActivity {
    @ViewById
    ImageView iv_album;
    @ViewById
    TextView tv_albumname, tv_artist, tv_pub_date;
    @ViewById(R.id.actionbar)
    Toolbar toolbar;
    @ViewById
    ListView lv_albuminfo;

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
        BaiduMusicUtil.getAlbumInfo(albumId, new HttpHandler(this) {
            @Override
            public void onSuccess(String response) {
                GetAlbumInfoResp resp = new Gson().fromJson(response, GetAlbumInfoResp.class);
                AlbumInfo album = resp.getAlbumInfo();
                if (album != null) {
                    mImageLoader.displayImage(album.getPic_big(), iv_album);
                    tv_albumname.setText(album.getTitle());
                    tv_artist.setText(album.getAuthor());
                    tv_pub_date.setText(album.getPublishtime());
                }
            }
        });
    }
}
