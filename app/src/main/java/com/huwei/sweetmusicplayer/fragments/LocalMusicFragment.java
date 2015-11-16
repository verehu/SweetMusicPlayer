package com.huwei.sweetmusicplayer.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.contains.IMusicViewTypeContain;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.fragments.base.BaseFragment;
import com.huwei.sweetmusicplayer.models.MusicInfo;
import com.huwei.sweetmusicplayer.ui.adapters.MusicAdapter;
import com.huwei.sweetmusicplayer.ui.listeners.OnTouchingLetterChangedListener;
import com.huwei.sweetmusicplayer.ui.widgets.SideBar;
import com.huwei.sweetmusicplayer.util.MusicUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * 装载音乐的fragment容器
 */
@EFragment(R.layout.fragment_localmusic)
public class LocalMusicFragment extends BaseFragment implements IContain, IMusicViewTypeContain{
    public static final String TAG="LocalMusicFragment";

    private MusicAdapter mMusicAdapter;

    @ViewById
    ListView lv_song;
    @ViewById
    SideBar sidebar;
    @ViewById
    TextView dialog;
    @ViewById
    Toolbar toolbar;

    private boolean isABC;  //是否显示ABC视图
    private int showtype=-1;

    @SystemService
    LayoutInflater inflater;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case PLAYBAR_UPDATE:
                    mMusicAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @AfterViews
    void init(){
        initParams();
        initToolBar();
        showSldingBar();
        showMusicList();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PLAYBAR_UPDATE);
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
    }

    void initToolBar(){
        switch (showtype){
            case SHOW_MUSIC:
                toolbar.setVisibility(View.GONE);
                break;
            case SHOW_MUSIC_BY_ALBUM:
                toolbar.setVisibility(View.VISIBLE);
                toolbar.setTitle(getArguments().getString("album_name"));
                toolbar.setNavigationIcon(R.drawable.mc_back);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                });
                break;
        }
    }

    void initParams(){
        showtype = getArguments().getInt(MUSIC_SHOW_TYPE);
        switch (showtype){
            case SHOW_MUSIC:
                isABC=true;
                break;
            case SHOW_MUSIC_BY_ALBUM:
                isABC=false;
                break;
        }
    }

    void showSldingBar(){
        if(isABC){
            sidebar.setTextView(dialog);
            sidebar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

                @Override
                public void onTouchingLetrerChanged(String s) {
                    // TODO Auto-generated method stub
                    int position = mMusicAdapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        lv_song.setSelection(position);
                    }
                }
            });
        }else {
            sidebar.setVisibility(View.INVISIBLE);
        }
    }

    public void showMusicList() {
        List<MusicInfo> musicInfoList=null;
        switch (showtype) {
            case SHOW_MUSIC:
                musicInfoList = MusicUtils.queryMusic(getActivity());
                break;
            case SHOW_MUSIC_BY_ALBUM:
                //todo
                musicInfoList = MusicUtils.queryMusicByAlbumId(getArguments().getLong("album_id"));
                break;
        }
        mMusicAdapter = new MusicAdapter(getActivity(),musicInfoList,isABC);
        mMusicAdapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                long time = System.currentTimeMillis();
                MusicManager.getInstance().preparePlayingList(position, mMusicAdapter.getList());
                Log.i(TAG, "time used:" + (System.currentTimeMillis() - time));

                Log.i(TAG,"clicked music:"+((AbstractMusic)mMusicAdapter.getList().get(position)).getTitle());

//                MusicManager.getInstance().play();
            }
        });
        lv_song.setAdapter(mMusicAdapter);

        View footer=inflater.inflate(R.layout.listbottom_music_count,null);
        TextView tv_music_count= (TextView) footer.findViewById(R.id.tv_music_count);
        tv_music_count.setText(mMusicAdapter.getCount()+" 首歌曲");
        lv_song.addFooterView(footer);
    }
}
