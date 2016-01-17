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
//                Toast.makeText(getActivity(),"OnClick:"+position,Toast.LENGTH_LONG).show();
//				FragmentTransaction transaction=fragmentManager.beginTransaction();
//				LocalMusicFragment musicFragment=new LocalMusicFragment_();
//				Bundle bundle=new Bundle();
//				bundle.putInt(MUSIC_SHOW_TYPE,SHOW_MUSIC_BY_ALBUM);
//				bundle.putLong("album_id",adapter.getData().get(position).getAlbumId());
//				bundle.putString("album_name",adapter.getData().get(position).getTitle());
//				musicFragment.setArguments(bundle);
//				transaction.addToBackStack(null);
//				transaction.replace(R.id.main,musicFragment);
//				transaction.commit();
			}
		});
	}
}
