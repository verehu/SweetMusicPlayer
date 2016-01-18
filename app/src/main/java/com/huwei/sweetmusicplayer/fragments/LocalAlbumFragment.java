package com.huwei.sweetmusicplayer.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.contains.IMusicViewTypeContain;
import com.huwei.sweetmusicplayer.fragments.base.BaseFragment;
import com.huwei.sweetmusicplayer.ui.adapters.LocAlbumAdapter;
import com.huwei.sweetmusicplayer.ui.adapters.RecyclerViewAdapterBase;
import com.huwei.sweetmusicplayer.util.FragmentUtil;
import com.huwei.sweetmusicplayer.util.MusicUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_albums)
public class LocalAlbumFragment extends BaseFragment implements IMusicViewTypeContain{
    @ViewById
    RecyclerView rv_album;

    @Bean
    LocAlbumAdapter adapter;

    FragmentManager fragmentManager;

    @AfterViews
    void init(){
        fragmentManager=getActivity().getSupportFragmentManager();

        adapter.setData(MusicUtils.queryAlbumList(getActivity()));
        rv_album.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_album.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewAdapterBase.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(getActivity(),"OnClick:"+position, Toast.LENGTH_LONG).show();
                LocalMusicFragment musicFragment = new LocalMusicFragment_();
                Bundle bundle = new Bundle();
                bundle.putInt(MUSIC_SHOW_TYPE, SHOW_MUSIC_BY_ALBUM);
                bundle.putLong("album_id", adapter.getData().get(position).getAlbumId());
                bundle.putString("album_name", adapter.getData().get(position).getTitle());
                musicFragment.setArguments(bundle);
                FragmentUtil.replace((android.support.v4.app.FragmentActivity) mAct,R.id.main_container,musicFragment);
            }
        });
    }
}
