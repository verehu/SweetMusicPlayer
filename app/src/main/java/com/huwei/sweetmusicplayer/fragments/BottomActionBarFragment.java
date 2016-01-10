package com.huwei.sweetmusicplayer.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

//import com.huwei.sweetmusicplayer.datamanager.MusicManager;

@EFragment(R.layout.bottom_action_bar)
public class BottomActionBarFragment extends Fragment implements IContain {

    public static final String TAG="BottomActionBarFragment";

    @ViewById
    TextView tv_title, tv_artist;
    @ViewById
    ToggleButton btn_play;
    @ViewById
    Button btn_next;
    @ViewById
    ImageView img_album;
    @ViewById
    ProgressBar pro_music;

    @AfterViews
    void init() {
        initListener();
        initReciever();
    }


    protected void initListener() {
        btn_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MusicManager.getInstance().nextSong();
            }
        });

        btn_play.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked != MusicManager.getInstance().isPlaying()) {
                    //播放意图
                    if (isChecked) {
                        MusicManager.getInstance().play();
                    } else {
                        MusicManager.getInstance().pause();
                    }
                }
            }
        });
    }

    protected void initReciever() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PLAYBAR_UPDATE);
        intentFilter.addAction(CURRENT_UPDATE);
        intentFilter.addAction(PLAY_STATUS_UPDATE);
        getActivity().registerReceiver(receiver, intentFilter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();

            switch (action) {
                case PLAY_STATUS_UPDATE:
                    boolean isPlaying = intent.getBooleanExtra("isPlaying", false);
                    btn_play.setChecked(MusicManager.getInstance().isPlaying());
                    break;
                case PLAYBAR_UPDATE:
                    pro_music.setMax(MusicManager.getInstance().getNowPlayingSong().getDuration());
                    AbstractMusic music = MusicManager.getInstance().getNowPlayingSong();
                    updateBottomBarFromService(music);
                    break;
                case CURRENT_UPDATE:
                    pro_music.setProgress(intent.getIntExtra("currentTime", 0));
                    break;
            }
        }

    };

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    void updateBottomBarFromService(AbstractMusic music) {

        if (music != null) {
            tv_title.setText(music.getTitle());
            tv_artist.setText(music.getArtist());
            btn_play.setChecked(MusicManager.getInstance().isPlaying());


            music.loadArtPic(new AbstractMusic.OnLoadListener() {
                @Override
                public void onSuccessLoad(Bitmap bitmap) {
                    Log.i(TAG, "onSuccessLoad bitmap:" + bitmap);

                    img_album.setImageBitmap(bitmap);
                }
            });
        }

    }
}
