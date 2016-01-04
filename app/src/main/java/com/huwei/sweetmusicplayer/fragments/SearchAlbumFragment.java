package com.huwei.sweetmusicplayer.fragments;

import android.view.View;
import android.widget.AdapterView;

import com.huwei.sweetmusicplayer.AlbumInfoActivity;
import com.huwei.sweetmusicplayer.OnlineSearchActivity;
import com.huwei.sweetmusicplayer.baidumusic.group.Album_info;
import com.huwei.sweetmusicplayer.baidumusic.po.Album;
import com.huwei.sweetmusicplayer.baidumusic.po.QueryResult;
import com.huwei.sweetmusicplayer.ui.adapters.AlbumAdapter;
import com.huwei.sweetmusicplayer.ui.widgets.auto.IPullRefershBase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jerry
 * @date 2016/01/04
 */
@EFragment
public class SearchAlbumFragment extends AutoListFragment {
    @FragmentArg
    Album_info album_info;

    private AlbumAdapter mAlbumAdapter;
    private List<Album> mList = new ArrayList<>();

    @AfterViews
    void init() {
        mPageNo = 2;

        mAlbumAdapter = new AlbumAdapter(mAct, mList);
        handleData(album_info);
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
                startActivity(AlbumInfoActivity.getStartActInent(mAct, mList.get(i).album_id));
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
