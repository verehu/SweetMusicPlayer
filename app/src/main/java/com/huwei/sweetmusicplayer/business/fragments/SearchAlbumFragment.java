package com.huwei.sweetmusicplayer.business.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.huwei.sweetmusicplayer.business.AlbumInfoActivity;
import com.huwei.sweetmusicplayer.business.OnlineSearchActivity;
import com.huwei.sweetmusicplayer.data.models.baidumusic.group.Album_info;
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.Album;
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.QueryResult;
import com.huwei.sweetmusicplayer.ui.adapters.AlbumAdapter;
import com.huwei.sweetmusicplayer.ui.widgets.auto.IPullRefershBase;
import com.huwei.sweetmusicplayer.data.contants.IntentExtra;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jerry
 * @date 2016/01/04
 */

public class SearchAlbumFragment extends AutoListFragment {
    Album_info album_info;

    private AlbumAdapter mAlbumAdapter;
    private List<Album> mList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPageNo = 2;

        mAlbumAdapter = new AlbumAdapter(mAct, mList);
        handleData((Album_info) getArguments().getParcelable(IntentExtra.EXTRA_ALBUMINFO));
        mAutoListView.setAdapter(mAlbumAdapter);

        mAutoListView.setRefreshEnable(false);
        mAutoListView.setOnLoadListener(new IPullRefershBase.OnLoadListener() {
            @Override
            public void onLoad() {
                ((OnlineSearchActivity) mAct).doQuery(mPageNo, new OnlineSearchActivity.OnGetQueryData() {
                    @Override
                    public void onGetData(QueryResult queryMergeResp) {
                        mPageNo++;

                        if (queryMergeResp != null) {
                            handleData(queryMergeResp.album_info);
                        }
                    }
                });
            }
        });
        mAutoListView.setOnItemNoneClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(AlbumInfoActivity.Companion.getStartActInent(mAct, mList.get(i).album_id));
            }
        });
    }

    void handleData(Album_info temp_song_info) {
        if (temp_song_info != null && temp_song_info.album_list != null) {
            mList.addAll(temp_song_info.album_list);
            mAlbumAdapter.notifyDataSetChanged();

            mAutoListView.onLoadComplete(hasMore(temp_song_info.total));
        }
    }


    public boolean hasMore(int total) {
        return total > mList.size();
    }
}
