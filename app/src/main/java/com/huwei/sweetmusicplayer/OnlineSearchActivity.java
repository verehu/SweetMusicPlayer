package com.huwei.sweetmusicplayer;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.interfaces.ISearchReuslt;
import com.huwei.sweetmusicplayer.baidumusic.po.Album;
import com.huwei.sweetmusicplayer.baidumusic.po.Artist;
import com.huwei.sweetmusicplayer.baidumusic.resp.MusicSearchSugResp;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.ui.adapters.SearchResultAdapter;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线音乐搜索的结果页面
 *
 * @author Jayce
 * @date 2015/8/17
 */
@EActivity(R.layout.activity_online_search)
public class OnlineSearchActivity extends BaseActivity {
    public static final String TAG = "OnlineSearchActivity";

    @ViewById
    ListView lv_online_search;
    @ViewById
    Toolbar toolbar;

    SearchResultAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            adapter.notifyDataSetInvalidated();
        }
    };

    @AfterViews
    void init() {
        initView();
        initListener();

        handleIntent(getIntent());
    }

    void initView() {
        adapter = new SearchResultAdapter(mContext);
        lv_online_search.setAdapter(adapter);

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
        lv_online_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ISearchReuslt reuslt = (ISearchReuslt) parent.getItemAtPosition(position);
                switch (reuslt.getSearchResultType()) {
                    case Song:
                        List<AbstractMusic> list = new ArrayList<>();
                        Log.i(TAG, "song:" + ((Song) reuslt).songInfo);
                        list.add((Song) reuslt);
                        //点击当前歌曲，把当前歌曲加入播放队列
                        MusicManager.getInstance().preparePlayingList(0, list);
//                        MusicManager.getInstance().play();
//                        adapter.notifyDataSetInvalidated();

                        finish();
                        break;
                }
            }
        });
    }

    void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            setTitle(query);
            doQuery(query);

            Toast.makeText(mContext, query, Toast.LENGTH_SHORT).show();
        }
    }

    void doQuery(String query) {
        BaiduMusicUtil.query(query, new HttpHandler(mContext) {
            @Override
            public void onSuccess(String response) {
                adapter.getData().clear();

                final MusicSearchSugResp sug = new Gson().fromJson(response, MusicSearchSugResp.class);
                for (Album album : sug.getAlbum()) {
                    adapter.add(album);
                }

                for (Artist artist : sug.getArtist()) {
                    adapter.add(artist);
                }

                for (Song song : sug.getSong()) {
                    adapter.add(song);
                }

                adapter.notifyDataSetInvalidated();

//                //子线程网络请求
//                new Thread(){
//                    @Override
//                    public void run() {
//
//                        for (Song song:sug.getSong()){
//                            //同步请求到歌曲信息
//                            SongPlayResp resp = BaiduMusicUtil.querySong(song.getSongid());
//                            if(resp!=null) {
//                                song.bitrate = resp.bitrate;
//                                song.songInfo = resp.songinfo;
//
//                                Log.i(TAG,"song add:"+song);
//                                adapter.add(song);
//                            }
//                        }
//
//                        handler.sendEmptyMessage(0);
//                    }
//                }.start();
            }
        });
    }

}
