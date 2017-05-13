package com.huwei.sweetmusicplayer;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.fragments.MainFragment;
import com.huwei.sweetmusicplayer.fragments.MainFragment_;
import com.huwei.sweetmusicplayer.fragments.PlayingFragment;
import com.huwei.sweetmusicplayer.interfaces.IMusicControl;
import com.huwei.sweetmusicplayer.services.MusicControlerService;
import com.huwei.sweetmusicplayer.ui.widgets.SlidingPanel;
import com.huwei.sweetmusicplayer.util.FragmentUtil;

import java.util.List;

public class MainActivity extends BaseActivity implements IMusicControl, IContain, View.OnClickListener {
    private SlidingPanel mSlidingPanel;
    private PopupWindow pop;

    private IMusicControlerService musicControler;
    private boolean isServiceBinding;

    private FragmentManager manager;

    private PlayingFragment playing_fragment;
    private MainFragment mainFragment;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private View mMenuSongScan, mMenuExit;

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

            //todo
//            if (MusicManager.getInstance().isForeground()) {
//
//                Intent intent = new Intent(PLAYBAR_UPDATE);
//                sendBroadcast(intent);
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceBinding = false;
            musicControler = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicManager.getInstance().bindProxyedObject(this);

        manager=getSupportFragmentManager();

        setContentView(R.layout.activity_main);

        playing_fragment= (PlayingFragment) manager.findFragmentById(R.id.playing_fragment);
//        mainFragment = (MainFragment) manager.findFragmentById(R.id.main);
//        mainFragment = new MainFragment_();
//        FragmentUtil.replace(this,R.id.main_container,mainFragment, false);

        initView();
        initListener();
        initReciever();

        if (!isServiceBinding) {
            Log.i(TAG,"start binding service");
            Intent intent = new Intent(this,MusicControlerService.class);
            bindService(intent, mConnection,BIND_AUTO_CREATE);
        }
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
    public void onBackPressed() {
        if (mSlidingPanel.isExpanded()) {
            if(playing_fragment.isDrawerOpen()){
                playing_fragment.closeDrawer();
            }else {
                mSlidingPanel.close();
            }
        }else if(manager.getBackStackEntryCount()!=0){
            manager.popBackStack();
        }
        else {
            moveTaskToBack(true);

            //finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_song_scan:
                songscan(v);
                break;
            case R.id.fl_exit:
                Process.killProcess(Process.myPid());
                break;
            default:
                break;
        }
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
        } catch (RemoteException e) {
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

    public void stopPlayingService() {
        if (isServiceBinding) {
            unbindService(mConnection);

            Intent intent = new Intent(this, MusicControlerService.class);
            stopService(intent);
        }
    }

    private void initView() {
        mSlidingPanel = (SlidingPanel) findViewById(R.id.sp_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar.setNavigationIcon(R.drawable.actionbar_menu);

        //menu
        mMenuSongScan = findViewById(R.id.fl_song_scan);
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
        mMenuExit.setOnClickListener(this);
    }

    private void initReciever() {
        //ExitPlayProReciever reciever = new ExitPlayProReciever();
        //IntentFilter filter1 = new IntentFilter(SweetMPContains.PLAYPRO_EXIT);
        //registerReceiver(reciever, filter1);
    }

    public void songscan(View v) {

        Intent intent = new Intent();
        intent.setClass(this, SongScanActivity_.class);
        startActivity(intent);
    }

    /**
     * ???content*
     *
     * @param view
     */
    public void closeContent(View view) {
        mSlidingPanel.close();
    }
}
