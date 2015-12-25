package com.huwei.sweetmusicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.ArtistInfoActivity;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.baidumusic.resp.ArtistSongListResp;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.fragments.base.BaseFragment;
import com.huwei.sweetmusicplayer.interfaces.IGetBindAutoListView;
import com.huwei.sweetmusicplayer.ui.adapters.OnlineMusicAdapter;
import com.huwei.sweetmusicplayer.ui.widgets.auto.AutoListView;
import com.huwei.sweetmusicplayer.ui.widgets.auto.CompatScrollViewAutoListView;
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
public class SongListFragment extends BaseFragment implements IGetBindAutoListView {

    private CompatScrollViewAutoListView lv_songs;
    private OnlineMusicAdapter mMusicAdapter;
    private List<Song> mSongList = new ArrayList<>();
    private int mPageNo;

    @FragmentArg
    String ting_uid;
    @FragmentArg
    String artist_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lv_songs = new CompatScrollViewAutoListView(mAct);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lv_songs.setLayoutParams(params);
        return lv_songs;
    }


    @AfterViews
    void init() {
        mMusicAdapter = new OnlineMusicAdapter(mAct, mSongList);
        lv_songs.setAdapter(mMusicAdapter);

        lv_songs.setRefreshEnable(false);
        lv_songs.setOnLoadListener(new IPullRefershBase.OnLoadListener() {
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

                            lv_songs.onLoadComplete(resp.hasmore());
                        }
                    }
                });
            }
        });
        lv_songs.onLoad();
        lv_songs.setOnItemNoneClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicManager.getInstance().preparePlayingList(position, Song.getAbstractMusicList(mSongList));
                MusicManager.getInstance().play();
            }
        });
    }

    @Override
    public AutoListView getAutoListView() {
        return lv_songs;
    }
}
