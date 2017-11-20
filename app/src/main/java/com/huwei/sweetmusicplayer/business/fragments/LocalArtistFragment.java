package com.huwei.sweetmusicplayer.business.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.huwei.sweetmusicplayer.business.LocalMusicActivity;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.contains.IMusicViewTypeContain;
import com.huwei.sweetmusicplayer.business.fragments.base.BaseFragment;
import com.huwei.sweetmusicplayer.business.ui.adapters.LocArtistInfoAdapter;
import com.huwei.sweetmusicplayer.business.ui.adapters.RecyclerViewAdapterBase;
import com.huwei.sweetmusicplayer.util.MusicUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 本地音乐的Artist列表页面
 */
@EFragment(R.layout.fragment_artists)
public class LocalArtistFragment extends BaseFragment implements IMusicViewTypeContain{

	@ViewById
	RecyclerView rv_artist;

	@Bean
    LocArtistInfoAdapter adapter;

	FragmentManager fragmentManager;

	@AfterViews
	void init(){
		fragmentManager=getActivity().getSupportFragmentManager();

		adapter.setData(MusicUtils.queryArtistList());
        rv_artist.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rv_artist.setAdapter(adapter);
		adapter.setOnItemClickListener(new RecyclerViewAdapterBase.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				startActivity(LocalMusicActivity.Companion.getStartActIntent(mAct, SHOW_MUSIC_BY_ARTIST,
						adapter.getData().get(position).getArtist(), adapter.getData().get(position).getArtistId()));
			}
		});
	}
}
