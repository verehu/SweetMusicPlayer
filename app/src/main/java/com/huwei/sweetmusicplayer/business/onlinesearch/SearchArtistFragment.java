package com.huwei.sweetmusicplayer.business.onlinesearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.huwei.sweetmusicplayer.business.ArtistInfoActivity;
import com.huwei.sweetmusicplayer.business.AutoListFragment;
import com.huwei.sweetmusicplayer.data.models.baidumusic.group.Artist_info;
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.Artist;
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.QueryResult;
import com.huwei.sweetmusicplayer.ui.adapters.ArtistAdapter;
import com.huwei.sweetmusicplayer.ui.widgets.auto.IPullRefershBase;
import com.huwei.sweetmusicplayer.data.contants.IntentExtra;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌手搜索结果
 *
 * @author jerry
 * @date 2016/01/04
 */

public class SearchArtistFragment extends AutoListFragment {

    private ArtistAdapter mArtistAdapter;
    private List<Artist> mList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPageNo = 2;

        mArtistAdapter = new ArtistAdapter(mAct, mList);
        handleData((Artist_info) getArguments().getParcelable(IntentExtra.EXTRA_ARTISTINFO));
        mAutoListView.setAdapter(mArtistAdapter);

        mAutoListView.setRefreshEnable(false);
        mAutoListView.setOnLoadListener(new IPullRefershBase.OnLoadListener() {
            @Override
            public void onLoad() {
                ((OnlineSearchActivity) mAct).doQuery(mPageNo, new OnlineSearchActivity.OnGetQueryData() {
                    @Override
                    public void onGetData(QueryResult queryMergeResp) {
                        mPageNo++;

                        if (queryMergeResp != null) {
                            handleData(queryMergeResp.artist_info);
                        }
                    }
                });
            }
        });
        mAutoListView.setOnItemNoneClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ArtistInfoActivity.Companion.getStartActIntent(mAct, mList.get(i).ting_uid, mList.get(i).artist_id));
            }
        });
    }

    void handleData(Artist_info temp_song_info) {
        if (temp_song_info != null && temp_song_info.artist_list != null) {
            mList.addAll(temp_song_info.artist_list);
            mArtistAdapter.notifyDataSetChanged();

            mAutoListView.onLoadComplete(hasMore(temp_song_info.total));
        }
    }

    public boolean hasMore(int total) {
        return total > mList.size();
    }
}
