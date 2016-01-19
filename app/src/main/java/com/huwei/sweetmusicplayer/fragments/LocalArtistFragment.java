package com.huwei.sweetmusicplayer.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.contains.IMusicViewTypeContain;
import com.huwei.sweetmusicplayer.fragments.base.BaseFragment;
import com.huwei.sweetmusicplayer.ui.adapters.LocArtistInfoAdapter;
import com.huwei.sweetmusicplayer.ui.adapters.RecyclerViewAdapterBase;
import com.huwei.sweetmusicplayer.util.FragmentUtil;
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

		adapter.setData(MusicUtils.queryArtistList(getActivity()));
        rv_artist.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rv_artist.setAdapter(adapter);
		adapter.setOnItemClickListener(new RecyclerViewAdapterBase.OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				FragmentUtil.replace((android.support.v4.app.FragmentActivity) mAct, R.id.main_container,
						LocalMusicFragment_.builder().showtype(SHOW_MUSIC_BY_ALBUM)
								.primaryId(adapter.getData().get(position).getArtistId())
								.title(adapter.getData().get(position).getArtist())
								.build());
			}
		});
	}
}
