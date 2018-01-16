package com.huwei.sweetmusicplayer.business.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.business.AlbumInfoActivity;
import com.huwei.sweetmusicplayer.business.baidumusic.po.Album;
import com.huwei.sweetmusicplayer.business.baidumusic.resp.ArtistAlbumListResp;
import com.huwei.sweetmusicplayer.business.ui.adapters.AlbumAdapter;
import com.huwei.sweetmusicplayer.business.ui.widgets.auto.IPullRefershBase;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import static com.huwei.sweetmusicplayer.contains.IntentExtra.EXTRA_ARTIST_ID;
import static com.huwei.sweetmusicplayer.contains.IntentExtra.EXTRA_TING_UID;

/**
 * @author jerry
 * @date 2015/12/24
 */
public class AlbumListFragment extends BaseScrollTabFragment {

    private AlbumAdapter mAlbumAdapter;
    private List<Album> mAlbumList = new ArrayList<>();

    String ting_uid;
    String artist_id;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ting_uid = getArguments().getString(EXTRA_TING_UID);
        artist_id = getArguments().getString(EXTRA_ARTIST_ID);

        mAlbumAdapter = new AlbumAdapter(mAct, mAlbumList);
        mAutoListView.setAdapter(mAlbumAdapter);

        mAutoListView.setRefreshEnable(false);
        mAutoListView.setOnLoadListener(new IPullRefershBase.OnLoadListener() {
            @Override
            public void onLoad() {
                BaiduMusicUtil.getAritistAlbumList(ting_uid, artist_id, mPageNo, new HttpHandler() {
                    @Override
                    public void onSuccess(String response) {
                        ArtistAlbumListResp resp = new Gson().fromJson(response, ArtistAlbumListResp.class);
                        if (resp != null && resp.albumlist != null) {
                            mPageNo++;
                            mAlbumList.addAll(resp.albumlist);
                            mAlbumAdapter.notifyDataSetChanged();

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
                Album album = mAlbumList.get(position);
                if (album != null) {
                    startActivity(AlbumInfoActivity.Companion.getStartActInent(mAct, album.album_id));
                }
            }
        });
    }
}
