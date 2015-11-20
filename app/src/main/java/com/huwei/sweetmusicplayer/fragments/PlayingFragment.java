package com.huwei.sweetmusicplayer.fragments;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.baidumusic.po.Lrc;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.baidumusic.po.SongSug;
import com.huwei.sweetmusicplayer.baidumusic.resp.MusicSearchSugResp;
import com.huwei.sweetmusicplayer.comparator.LrcComparator;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.contains.ILrcStateContain;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.models.LrcContent;
import com.huwei.sweetmusicplayer.ui.adapters.QueueAdapter;
import com.huwei.sweetmusicplayer.ui.listeners.OnLrcSearchClickListener;
import com.huwei.sweetmusicplayer.ui.widgets.LrcView;
import com.huwei.sweetmusicplayer.ui.widgets.SlidingPanel;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.huwei.sweetmusicplayer.util.HttpHandler;
import com.huwei.sweetmusicplayer.util.LrcUtil;
import com.huwei.sweetmusicplayer.util.TimeUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.Collections;
import java.util.List;

/**
 *
 *  播放界面
 */
@EFragment
public class PlayingFragment extends Fragment implements IContain, OnLrcSearchClickListener, ILrcStateContain {
    private static final String TAG = "PlayingFragment";

    private Button playpage_return_btn;
    private TextView playpage_title_tv;
    private TextView playpage_artist_tv;
    private TextView playpage_playtime_tv;
    private TextView playpage_duration_tv;
    private SeekBar playpage_progressbar;
    private ImageView playpage_next_btn;
    private ImageView playpage_previous_btn;
    private ToggleButton playpage_play_btn;
    private LrcView playpage_lrcview;

    @ViewById
    DrawerLayout dl_music_queue;
    @ViewById
    Button btn_show_music_queue;
    @ViewById
    ListView lv_music_queue;

    private IntentFilter intentFilter;

    private int mScreenWidth;

    private boolean mProgressBarLock;

    @SystemService
    LayoutInflater inflater;
    @Bean
    QueueAdapter queueAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        intentFilter = new IntentFilter();
        intentFilter.addAction(PLAYBAR_UPDATE);
        intentFilter.addAction(CURRENT_UPDATE);
        intentFilter.addAction(UPTATE_MUISC_QUEUE);
        intentFilter.addAction(BUFFER_UPDATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View root = inflater.inflate(R.layout.fragment_playing, null);

        playpage_return_btn = (Button) root.findViewById(R.id.playpage_return);
        playpage_title_tv = (TextView) root.findViewById(R.id.playpage_title_tv);
        playpage_artist_tv = (TextView) root.findViewById(R.id.playpage_artist);
        playpage_playtime_tv = (TextView) root.findViewById(R.id.playpage_playtime_tv);
        playpage_duration_tv = (TextView) root.findViewById(R.id.playpage_duration_tv);
        playpage_progressbar = (SeekBar) root.findViewById(R.id.playpage_progressbar);
        playpage_next_btn = (ImageView) root.findViewById(R.id.playpage_next);
        playpage_previous_btn = (ImageView) root.findViewById(R.id.playpage_previous);
        playpage_play_btn = (ToggleButton) root.findViewById(R.id.playpage_play);
        playpage_lrcview = (LrcView) root.findViewById(R.id.playpage_lrcview);

        initListener();

        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;

        return root;
    }

    @AfterViews
    public void init() {
        updateMusicQueue();
        lv_music_queue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicManager.getInstance().preparePlayingList(position, MusicManager.getInstance().getPlayingList());
                MusicManager.getInstance().play();
                queueAdapter.notifyDataSetChanged();
            }
        });
    }

    @Click(R.id.btn_show_music_queue)
    void btn_show_music_queueWasClicked() {
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            openDrawer();
        }
    }

    public void openDrawer() {
        dl_music_queue.openDrawer(Gravity.END);
    }

    public void closeDrawer() {
        dl_music_queue.closeDrawers();
    }

    public boolean isDrawerOpen() {
        return dl_music_queue.isDrawerOpen(Gravity.END);
    }


    public void UpdateTopData() {
        AbstractMusic song = MusicManager.getInstance().getNowPlayingSong();
        playpage_title_tv.setText(song.getTitle());
        playpage_artist_tv.setText(song.getArtist());

        playpage_duration_tv.setText(song.getDurationStr());
        playpage_progressbar.setMax(song.getDuration());
    }


    public void updateMusicQueue() {
        List<AbstractMusic> nowPlayings = MusicManager.getInstance().getPlayingList();
        if (nowPlayings != null) {
            queueAdapter.setList(nowPlayings);
            lv_music_queue.setAdapter(queueAdapter);
        }
    }

    public void initListener() {


        playpage_progressbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            int pro;


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                mProgressBarLock = false;
                MusicManager.getInstance().seekTo(pro);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                mProgressBarLock = true;
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                pro = progress;
                playpage_playtime_tv.setText(TimeUtil.mill2mmss(progress));

            }
        });

        playpage_next_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MusicManager.getInstance().nextSong();
            }
        });

        playpage_previous_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MusicManager.getInstance().preSong();
            }
        });


        playpage_play_btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked != MusicManager.getInstance().isPlaying()) {

                    if (isChecked) {
                        MusicManager.getInstance().play();
                    } else {
                        MusicManager.getInstance().pause();
                    }
                }
            }
        });

        playpage_lrcview.setOnLrcSearchClickListener(this);

    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();

            switch (action) {
                case PLAYBAR_UPDATE:
                    playpage_play_btn.setChecked(MusicManager.getInstance().isPlaying());

                    boolean isNewPlayMusic = intent.getBooleanExtra("isNewPlayMusic", false);
                    if (isNewPlayMusic) {
                        UpdateTopData();
                        loadLrcView();
                    }
                    break;
                case CURRENT_UPDATE:
                    int currentTime = intent.getIntExtra("currentTime", 0);
                    playpage_playtime_tv.setText(TimeUtil.mill2mmss(currentTime));
                    if (!mProgressBarLock) playpage_progressbar.setProgress(currentTime);

                    updateLrcView(currentTime);
                    break;
                case BUFFER_UPDATE:
                    int bufferTime = intent.getIntExtra("bufferTime", 0);
                    if (!mProgressBarLock) playpage_progressbar.setSecondaryProgress(bufferTime);

                case UPTATE_MUISC_QUEUE:
                    updateMusicQueue();
                    break;
            }
        }

    };


    void loadLrcView() {
        AbstractMusic song = MusicManager.getInstance().getNowPlayingSong();
        List<LrcContent> lrcLists = null;
        lrcLists = LrcUtil.loadLrc(song);
        playpage_lrcview.setLrcLists(lrcLists);
        playpage_lrcview.setLrcState(lrcLists.size() == 0 ? READ_LOC_FAIL : READ_LOC_OK);
    }

    void updateLrcView(int currentTime) {
//
//        if (SlidingPanel.mTracking) return;

        int tempIndex = playpage_lrcview.getIndexByLrcTime(currentTime);
        if (tempIndex != playpage_lrcview.getIndex()) {
            playpage_lrcview.setIndex(tempIndex);
            playpage_lrcview.invalidate();
        }
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.i(TAG, "onResume");
        getActivity().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i(TAG, "onDestory()");
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onLrcSearchClicked(View view) {
        showLrcSearchDialog();
    }

    public void showLrcSearchDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.lrc_dialog);
        View content = inflater.inflate(R.layout.dialog_lrc, null);
        content.setMinimumWidth(mScreenWidth - 40);
        dialog.setContentView(content);
        dialog.show();

        final Button okBtn = (Button) content.findViewById(R.id.ok_btn);
        final Button cancleBtn = (Button) content.findViewById(R.id.cancel_btn);
        final EditText artistEt = (EditText) content.findViewById(R.id.artist_tv);
        final EditText musicEt = (EditText) content.findViewById(R.id.music_tv);

        final AbstractMusic musicInfo = MusicManager.getInstance().getNowPlayingSong();
        artistEt.setText(musicInfo.getArtist());
        musicEt.setText(musicInfo.getTitle());
        OnClickListener btnListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v == okBtn) {
                    dialog.dismiss();

                    //搜索歌曲
                    BaiduMusicUtil.querySug(musicEt.getText().toString().split("\\(")[0] + " " + artistEt.getText().toString(), new HttpHandler(getActivity()) {
                        @Override
                        public void onStart() {
                            super.onStart();
                            playpage_lrcview.setLrcState(QUERY_ONLINE);
                        }

                        @Override
                        public void onSuccess(String response) {
                            Log.i(TAG, "SUG JSON:" + response);

                            MusicSearchSugResp sug = new Gson().fromJson(response, MusicSearchSugResp.class);

                            if (!sug.isValid()) {
                                playpage_lrcview.setLrcState(QUERY_ONLINE_NULL);
                                return;
                            }

                            List<SongSug> songList = sug.song;
                            findLrc(songList, 0);
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            super.onErrorResponse(error);
                            playpage_lrcview.setLrcState(QUERY_ONLINE_FAIL);
                        }
                    });
                } else if (v == cancleBtn) {
                    dialog.dismiss();
                }
            }
        };
        okBtn.setOnClickListener(btnListener);
        cancleBtn.setOnClickListener(btnListener);
    }



    private void findLrc(final List<SongSug> songList,final int index){
        if (songList.size() == 0) {
            playpage_lrcview.setLrcState(QUERY_ONLINE_NULL);
           return;
        }
        final SongSug song = songList.get(index);
        String songid = song.songid;
        BaiduMusicUtil.queryLrc(songid, new HttpHandler(getActivity()) {
            @Override
            public void onSuccess(String response) {
                Lrc lrc = new Gson().fromJson(response, Lrc.class);

                if (!lrc.isValid()) {
                    playpage_lrcview.setLrcState(QUERY_ONLINE_NULL);
                    return;
                }

                List<LrcContent> lrcLists = LrcUtil.parseLrcStr(lrc.getLrcContent());
                // 按时间排序
                Collections.sort(lrcLists, new LrcComparator());
                playpage_lrcview.setLrcLists(lrcLists);
                playpage_lrcview.setLrcState(lrcLists.size() == 0 ? QUERY_ONLINE_NULL : QUERY_ONLINE_OK);

                if (lrcLists.size() != 0) {
                    LrcUtil.writeLrcToLoc(song.getTitle(), song.getArtist(), lrc.getLrcContent());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);

                if(index+1<songList.size()) {
                    findLrc(songList, index + 1);
                }else{
                    playpage_lrcview.setLrcState(QUERY_ONLINE_FAIL);
                }
            }
        });
    }
}
