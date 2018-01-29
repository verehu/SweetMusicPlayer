package com.huwei.sweetmusicplayer.business.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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



import java.util.ArrayList;
import java.util.List;

import static com.huwei.sweetmusicplayer.contants.IntentExtra.EXTRA_ARTIST_ID;
import static com.huwei.sweetmusicplayer.contants.IntentExtra.EXTRA_TING_UID;

/**
 * 在线歌曲列表
 *
 * @author jerry
 * @date 2015/12/24
 */
public class SongListFragment extends BaseScrollTabFragment  {
    
    private SongAdapter mMusicAdapter;
    private List<Song> mSongList = new ArrayList<>();

    String ting_uid;
    String artist_id;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ting_uid = getArguments().getString(EXTRA_TING_UID);
        artist_id = getArguments().getString(EXTRA_ARTIST_ID);

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
                MusicManager.get().prepareAndPlay(position, Song.getAbstractMusicList(mSongList));
            }
        });
    }


}
