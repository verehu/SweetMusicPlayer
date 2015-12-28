package com.huwei.sweetmusicplayer.fragments;

import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.baidumusic.resp.ArtistSongListResp;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.ui.adapters.OnlineMusicAdapter;
import com.huwei.sweetmusicplayer.ui.widgets.auto.IPullRefershBase;
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
    
    private OnlineMusicAdapter mMusicAdapter;
    private List<Song> mSongList = new ArrayList<>();

    @FragmentArg
    String ting_uid;
    @FragmentArg
    String artist_id;
    
    @AfterViews
    void init() {
        mMusicAdapter = new OnlineMusicAdapter(mAct, mSongList);
        mAutoListView.setAdapter(mMusicAdapter);

        mAutoListView.setRefreshEnable(false);
        mAutoListView.setOnLoadListener(new IPullRefershBase.OnLoadListener() {
            @Override
            public void onLoad() {
                BaiduMusicUtil.getArtistSongList(ting_uid, artist_id, mPageNo, new HttpHandler() {
                    @Override
                    public void onSuccess(String response) {
                        ArtistSongListResp resp = new Gson().fromJson(response, ArtistSongListResp.class);
                        if (resp != null) {
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
                MusicManager.getInstance().preparePlayingList(position, Song.getAbstractMusicList(mSongList));
                MusicManager.getInstance().play();
            }
        });
    }


}
