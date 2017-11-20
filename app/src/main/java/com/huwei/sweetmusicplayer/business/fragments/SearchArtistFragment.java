package com.huwei.sweetmusicplayer.business.fragments;

import android.view.View;
import android.widget.AdapterView;

import com.huwei.sweetmusicplayer.business.ArtistInfoActivity;
import com.huwei.sweetmusicplayer.business.OnlineSearchActivity;
import com.huwei.sweetmusicplayer.business.baidumusic.group.Artist_info;
import com.huwei.sweetmusicplayer.business.baidumusic.po.Artist;
import com.huwei.sweetmusicplayer.business.baidumusic.po.QueryResult;
import com.huwei.sweetmusicplayer.business.ui.adapters.ArtistAdapter;
import com.huwei.sweetmusicplayer.business.ui.widgets.auto.IPullRefershBase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌手搜索结果
 *
 * @author jerry
 * @date 2016/01/04
 */
@EFragment
public class SearchArtistFragment extends AutoListFragment {
    @FragmentArg
    Artist_info artist_info;

    private ArtistAdapter mArtistAdapter;
    private List<Artist> mList = new ArrayList<>();

    @AfterViews
    void init() {
        mPageNo = 2;

        mArtistAdapter = new ArtistAdapter(mAct, mList);
        handleData(artist_info);
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
                startActivity(ArtistInfoActivity.getStartActIntent(mAct, mList.get(i).ting_uid,mList.get(i).artist_id));
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
