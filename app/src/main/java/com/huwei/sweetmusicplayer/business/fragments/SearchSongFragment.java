package com.huwei.sweetmusicplayer.business.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.huwei.sweetmusicplayer.business.OnlineSearchActivity;
import com.huwei.sweetmusicplayer.business.baidumusic.group.Song_info;
import com.huwei.sweetmusicplayer.business.baidumusic.po.QueryResult;
import com.huwei.sweetmusicplayer.business.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.business.core.MusicManager;
import com.huwei.sweetmusicplayer.business.ui.adapters.SongAdapter;
import com.huwei.sweetmusicplayer.business.ui.widgets.auto.IPullRefershBase;
import com.huwei.sweetmusicplayer.contains.IntentExtra;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌曲搜索结果
 * @author jerry
 * @date 2016/01/04
 */
public class SearchSongFragment extends AutoListFragment {
    Song_info song_info;

    private SongAdapter mSongAdapter;
    private List<Song> mList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPageNo = 2;

        mSongAdapter = new SongAdapter(mAct, mList);
        handleData((Song_info) getArguments().getParcelable(IntentExtra.EXTRA_SONGINFO));
        mAutoListView.setAdapter(mSongAdapter);

        mAutoListView.setRefreshEnable(false);
        mAutoListView.setOnLoadListener(new IPullRefershBase.OnLoadListener() {
            @Override
            public void onLoad() {
                ((OnlineSearchActivity) mAct).doQuery(mPageNo, new OnlineSearchActivity.OnGetQueryData() {
                    @Override
                    public void onGetData(QueryResult queryMergeResp) {
                        mPageNo++;
                        if(queryMergeResp!=null) {
                            handleData(queryMergeResp.song_info);
                        }
                    }
                });
            }
        });
        mAutoListView.setOnItemNoneClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MusicManager.getInstance().prepareAndPlay(i,Song.getAbstractMusicList(mList));
            }
        });
    }

    void handleData(Song_info temp_song_info){
        if (temp_song_info != null && temp_song_info.song_list != null) {
            mList.addAll(temp_song_info.song_list);
            mSongAdapter.notifyDataSetChanged();

            mAutoListView.onLoadComplete(hasMore(temp_song_info.total));
        }
    }

    public boolean hasMore(int total) {
        return total > mList.size();
    }
}
