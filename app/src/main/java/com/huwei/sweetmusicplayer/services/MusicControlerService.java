package com.huwei.sweetmusicplayer.services;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.*;
import android.os.Process;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.huwei.sweetmusicplayer.IMusicControlerService;
import com.huwei.sweetmusicplayer.MainActivity;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.models.MusicInfo;
import com.huwei.sweetmusicplayer.recievers.BringToFrontReceiver;
import com.huwei.sweetmusicplayer.util.MusicUtils;

import java.util.List;
import java.util.Random;

public class MusicControlerService extends Service implements MediaPlayer.OnCompletionListener,IContain{
    private String TAG = "MusicControlerService";
    private int musicIndex = -1;
    private long lastSongID = -1;
    private List<MusicInfo> musicList;


    private MediaPlayer mp;

    private boolean isForeground;

    public static final int MSG_CURRENT = 0;

    public static final String PLAYPRO_EXIT="com.huwei.intent.PLAYPRO_EXIT_ACTION";
    public static final String NEXTSONG="com.intent.action.NEXTSONG";
    public static final String PRESONG="com.intent.action.PRESONG";
 

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CURRENT:
                    Intent intent = new Intent(CURRENT_UPDATE);
                    int currentTime = mp.getCurrentPosition();
                    Log.i("currentTime", currentTime + "");
                    intent.putExtra("currentTime", currentTime);
                    sendBroadcast(intent);

                    handler.sendEmptyMessageDelayed(MSG_CURRENT, 500);
                    break;
            }
        }
    };
    
    private BroadcastReceiver controlReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case PLAYPRO_EXIT:
                    stopSelf();
                    stopForeground(true);
                    
                    Process.killProcess(Process.myPid());
                    break;
                case NEXTSONG:
                    try {
                        mBinder.nextSong();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    
                    break;
                case PRESONG:
                    try {
                        mBinder.preSong();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private IMusicControlerService.Stub mBinder = new IMusicControlerService.Stub() {
        @Override
        public int getPid() throws RemoteException {
            return Process.myPid();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void play() throws RemoteException {
            if (musicIndex != -1) {
                MusicInfo music=musicList.get(musicIndex);
                startPlay(music);
            }

            handler.sendEmptyMessage(MSG_CURRENT);
        }

        @Override
        public void pause() throws RemoteException {
            mp.pause();
            updatePlayBar(false);
            handler.removeMessages(MSG_CURRENT);
        }

        @Override
        public void stop() throws RemoteException {
            stopForeground(true);
        }

        @Override
        public void seekTo(int mesc) throws RemoteException {
            mp.seekTo(mesc);

            if(!isPlaying()){
                play();
            }
        }

        @Override
        public void preparePlayingList(int index, List list) throws RemoteException {
            musicIndex = index;
            musicList = list;

            if (musicList == null || musicList.size() == 0) {
                Toast.makeText(getBaseContext(), "播放列表为空", Toast.LENGTH_LONG).show();
                return;
            }

            MusicInfo song = musicList.get(musicIndex);
            prepareSong(song);
        }

        @Override
        public boolean isPlaying() {
            return mp != null && mp.isPlaying();
        }

        @Override
        public int getPlayingSongIndex() throws RemoteException {
            return musicIndex;
        }

        @Override
        public MusicInfo getNowPlayingSong() throws RemoteException {
            return musicList.get(musicIndex);
        }

        @Override
        public boolean isForeground() throws RemoteException {
            return isForeground;
        }

        @Override
        public void nextSong() throws RemoteException {
            musicIndex = (musicIndex + 1) % musicList.size();
            prepareSong(musicList.get(musicIndex));
            play();
        }

        @Override
        public void preSong() throws RemoteException {
            musicIndex = (musicIndex - 1) % musicList.size();
            prepareSong(musicList.get(musicIndex));
            play();
        }

        @Override
        public void randomSong() throws RemoteException {
            musicIndex = new Random().nextInt(musicList.size());
            prepareSong(musicList.get(musicIndex));
            play();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        if (mp != null) {
            mp.release();
            mp.reset();
        }

        mp = new MediaPlayer();
        mp.setOnCompletionListener(this);

        IntentFilter filter=new IntentFilter();
        filter.addAction(PLAYPRO_EXIT);
        filter.addAction(PRESONG);
        filter.addAction(NEXTSONG);
        filter.addAction(BringToFrontReceiver.ACTION_BRING_TO_FRONT);
        registerReceiver(controlReceiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        isForeground = false;

        super.onDestroy();
    }

    /**
     * 和上一次操作的歌曲不同，代表新播放的歌曲
     * @param isNewPlayMusic
     */
    private void updatePlayBar(boolean isNewPlayMusic) {
        Intent intent = new Intent(PLAYBAR_UPDATE);
        intent.putExtra("isNewPlayMusic",isNewPlayMusic);
        sendBroadcast(intent);
    }


    private void prepareSong(MusicInfo song) {

        mp.reset();

        try {
            mp.setDataSource(getBaseContext(), Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + song.getSongId()));
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPlay(MusicInfo music) {
        showMusicPlayerNotification(getResources().getString(R.string.app_name),NT_PLAYBAR_ID,
                R.drawable.sweet, music.getAlbumId(),
                music.getTitle(), music.getArtist());
        mp.start();
        updatePlayBar(music.getSongId() != lastSongID);
        lastSongID = music.getSongId();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        try {
            mBinder.nextSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void showMusicPlayerNotification(String tickerText, int id,
                                     int resId, long album_id, String title, String artist) {


        RemoteViews reViews = new RemoteViews(getPackageName(), R.layout.notification_play);
        reViews.setTextViewText(R.id.title, title);
        reViews.setTextViewText(R.id.text, artist);
        reViews.setImageViewBitmap(R.id.img_album, MusicUtils.getCachedArtwork(getApplicationContext(), album_id,R.drawable.img_album_background));


        Intent intent=new Intent(getBaseContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
        reViews.setOnClickPendingIntent(R.id.nt_container, pendingIntent);


        Intent exitIntent = new Intent(PLAYPRO_EXIT);
        PendingIntent exitPendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, exitIntent, 0);
        reViews.setOnClickPendingIntent(R.id.button_exit_notification_play, exitPendingIntent);
        
        Intent nextInent=new Intent(NEXTSONG);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, nextInent, 0);
        reViews.setOnClickPendingIntent(R.id.button_next_notification_play, nextPendingIntent);

        Intent preInent=new Intent(PRESONG);
        PendingIntent prePendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, preInent, 0);
        reViews.setOnClickPendingIntent(R.id.button_previous_notification_play, prePendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setContent(reViews).setSmallIcon(resId).setTicker(title).setOngoing(true);


        startForeground(id, builder.build());
        isForeground = true;
    }

    String getTopActivity(Activity context)

    {

        ActivityManager manager = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE) ;

        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1) ;


        if(runningTaskInfos != null)

            return (runningTaskInfos.get(0).topActivity).toString() ;

        else

            return null ;

    }
}
