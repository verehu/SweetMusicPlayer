package com.huwei.sweetmusicplayer.business.fragments;

import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.business.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.business.baidumusic.resp.ArtistSongListResp;
import com.huwei.sweetmusicplayer.business.core.MusicManager;
import com.huwei.sweetmusicplayer.business.ui.adapters.SongAdapter;
import com.huwei.sweetmusicplayer.business.ui.widgets.auto.IPullRefershBase;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线歌曲列表
 *
 * @author jerry
 * @date 2015/12/24
 */
@EFragment
public class SongListFragment extends BaseScrollTabFragment  {
    
    private SongAdapter mMusicAdapter;
    private List<Song> mSongList = new ArrayList<>();

    @FragmentArg
    String ting_uid;
    @FragmentArg
    String artist_id;
    
    @AfterViews
    void init() {
        mMusicAdapter = new SongAdapter(mAct, mSongList);
        mAutoListView.setAdapter(mMusicAdapter);

        mAutoListView.setRefreshEnable(false);
        mAutoListView.setOnLoadListener(new IPullRefershBase.OnLoadListener() {
            @Override
            public void onLoad() {
                BaiduMusicUtil.getArtistSongList(ting_uid, artist_id, mPageNo, new HttpHandler() {
                    @Override
                    public void onSuccess(String response) {
                        ArtistSongListResp resp = new Gson().fromJson(response, ArtistSongListResp.class);
                        if (resp != null && resp.songlist!=null) {
                            mPageNo++;
                            mSongList.addAll(resp.songlist);
                            mMusicAdapter.notifyDataSetChanged();

                            mAutoListView.onLoadComplete(resp.hasmore());
                        }
                    }
                });
            }
        });
        mAutoListView.onLoad();
        mAutoListView.setOnItemNoneClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicManager.getInstance().prepareAndPlay(position, Song.getAbstractMusicList(mSongList));
            }
        });
    }


}
