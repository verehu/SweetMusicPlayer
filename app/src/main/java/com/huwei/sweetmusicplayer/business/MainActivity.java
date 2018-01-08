package com.huwei.sweetmusicplayer.business;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.huwei.sweetmusicplayer.IMusicControlerService;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.business.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.business.core.MusicManager;
import com.huwei.sweetmusicplayer.business.interfaces.IMusicControl;
import com.huwei.sweetmusicplayer.business.core.MusicControlerService;
import com.huwei.sweetmusicplayer.util.Environment;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BottomPlayActivity implements IMusicControl, IContain, View.OnClickListener {
    private IMusicControlerService musicControler;
    private boolean isServiceBinding;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private View mMenuSongScan, mMenuExit, mMenuCountDown;
    private TimeCountDown mTimeCountDown;

    public static Intent getStartActIntent(Context from){
        Intent intent = new Intent(from,MainActivity.class);
        return intent;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG,"onServiceConnected");
            isServiceBinding = true;
            musicControler = IMusicControlerService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceBinding = false;
            musicControler = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MusicManager.getInstance().bindProxyedObject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTimeCountDown  = new TimeCountDown();

        initView();
        initListener();

        if (!isServiceBinding) {
            Log.i(TAG,"start binding service");
            Intent intent = new Intent(this,MusicControlerService.class);
            bindService(intent, mConnection,BIND_AUTO_CREATE);
        }

        //加载默认歌曲
        loadDefaultMusic();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!isServiceBinding) {
            Intent intent = new Intent(this, MusicControlerService.class);
            startService(intent);

            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected boolean isNeedStausView() {
        return false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    @Override
    public void onClick(final View v) {
        mDrawerLayout.closeDrawers();
        mDrawerLayout.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (v.getId()) {
                    case R.id.fl_song_scan:
                        songscan(v);
                        break;
                    case R.id.fl_count_down:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle(R.string.sleep_countdown_dialog_title);
                        builder.setSingleChoiceItems(R.array.sleep_times, mTimeCountDown.getSelectItem(),new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTimeCountDown.setCountDownItem(which);

                                dialog.dismiss();
                            }
                        });

                        builder.show();

                        break;
                    case R.id.fl_exit:
                        Process.killProcess(Process.myPid());
                        break;
                    default:
                        break;
                }
            }
        }, 260);
    }

    @Override
    public void play() {
        try {
            musicControler.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            musicControler.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            musicControler.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekTo(int progress) {
        try {
            musicControler.seekTo(progress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void preparePlayingList(int index, List list) {
        try {
            musicControler.preparePlayingList(index, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPlaying() {
        if (musicControler == null) return false;

        try {
            return musicControler.isPlaying();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public AbstractMusic getNowPlayingSong() {
        try {
            return musicControler.getNowPlayingSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isForeground() {
        if (musicControler == null) return false;

        try {
            return musicControler.isForeground();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getNowPlayingIndex() {
        if (musicControler == null) return -1;

        try {
            return musicControler.getPlayingSongIndex();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void nextSong() {
        try {
            musicControler.nextSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void preSong() {
        try {
            musicControler.preSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void randomSong() {
        try {
            musicControler.randomSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMusicQueue() {
        Intent intent = new Intent(UPTATE_MUISC_QUEUE);
        sendBroadcast(intent);
    }

    private void loadDefaultMusic(){
        AbstractMusic music = Environment.getRecentMusic();
        if (music != null) {
            List<AbstractMusic> list = new ArrayList<>();
            list.add(music);
            MusicManager.getInstance().preparePlayingList(0, list);
        }
    }

    public void stopPlayingService() {
        if (isServiceBinding) {
            unbindService(mConnection);

            Intent intent = new Intent(this, MusicControlerService.class);
            stopService(intent);
        }
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar.setNavigationIcon(R.drawable.actionbar_menu);

        //menu
        mMenuSongScan = findViewById(R.id.fl_song_scan);
        mMenuCountDown = findViewById(R.id.fl_count_down);
        mMenuExit = findViewById(R.id.fl_exit);
    }

    private void initListener(){
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        //menu
        mMenuSongScan.setOnClickListener(this);
        mMenuCountDown.setOnClickListener(this);
        mMenuExit.setOnClickListener(this);
    }



    public void songscan(View v) {
        Intent intent = new Intent();
        intent.setClass(this, SongScanActivity_.class);
        startActivity(intent);
    }
}
