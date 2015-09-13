package com.huwei.sweetmusicplayer.services;


import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.*;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.huwei.sweetmusicplayer.IMusicControlerService;
import com.huwei.sweetmusicplayer.MainActivity;
import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.SweetApplication;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;

import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.baidumusic.resp.SongPlayResp;
import com.huwei.sweetmusicplayer.recievers.BringToFrontReceiver;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

/**
 * 后台控制播放音乐的service
 */
public class MusicControlerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, IContain {
    private String TAG = "MusicControlerService";
    private int musicIndex = -1;
    private long lastSongID = -1;
    private List<AbstractMusic> musicList;

    private MediaPlayer mp;

    RemoteViews reViews;

    private boolean isForeground;

    public static final int MSG_CURRENT = 0;
    public static final int MSG_BUFFER_UPDATE = 1;

    public static final int MSG_NOTICATION_UPDATE = 2;

    public static final int MSG_PLAY = 101;

    public static final String PLAYPRO_EXIT = "com.huwei.intent.PLAYPRO_EXIT_ACTION";
    public static final String NEXTSONG = "com.intent.action.NEXTSONG";
    public static final String PRESONG = "com.intent.action.PRESONG";


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
                case MSG_BUFFER_UPDATE:

                    intent = new Intent(BUFFER_UPDATE);
                    int bufferTime = msg.arg1;
                    Log.i("bufferTime", bufferTime + "");
                    intent.putExtra("bufferTime", bufferTime);
                    sendBroadcast(intent);
                    break;
                case MSG_NOTICATION_UPDATE:
                    reViews.setImageViewBitmap(R.id.img_album, (Bitmap) msg.obj);
                    break;
                case MSG_PLAY:
                    AbstractMusic music = (AbstractMusic) msg.obj;
                    play(music);
                    break;
            }
        }
    };

    private BroadcastReceiver controlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
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
//            if (musicIndex != -1) {
//                AbstractMusic music = musicList.get(musicIndex);
//                startPlay(music);
//            }
//
//            handler.sendEmptyMessage(MSG_CURRENT);
            //准备播放源，准备后播放
            Log.i(TAG, "play()");
            if (!mp.isPlaying()) {
                mp.start();
                Intent intent = new Intent(PLAYBAR_UPDATE);
                intent.putExtra("isNewPlayMusic", false);

                AbstractMusic music = mBinder.getNowPlayingSong();
                intent.putExtra("newMusic",music);
                sendBroadcast(intent);
            }
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

//            if (!isPlaying()) {
//                play();
//            }
        }

        @Override
        public void preparePlayingList(int index, List list) throws RemoteException {
            musicIndex = index;
            musicList = list;

            Log.d(TAG, "musicList:" + list + " musicIndex:" + index+"now title:"+((AbstractMusic)list.get(index)).getTitle());

            if (musicList == null || musicList.size() == 0) {
                Toast.makeText(getBaseContext(), "播放列表为空", Toast.LENGTH_LONG).show();
                return;
            }

            AbstractMusic song = musicList.get(musicIndex);
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
        public AbstractMusic getNowPlayingSong() throws RemoteException {
            return musicList.get(musicIndex);
        }

        @Override
        public boolean isForeground() throws RemoteException {
            return isForeground;
        }

        @Override
        public void nextSong() throws RemoteException {
//            if(musicList!=null) {
            musicIndex = (musicIndex + 1) % musicList.size();
            prepareSong(musicList.get(musicIndex));
//                play();
//            }
        }

        @Override
        public void preSong() throws RemoteException {
            musicIndex = (musicIndex - 1) % musicList.size();
            prepareSong(musicList.get(musicIndex));
//            play();
        }

        @Override
        public void randomSong() throws RemoteException {
            musicIndex = new Random().nextInt(musicList.size());
            prepareSong(musicList.get(musicIndex));
//            play();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        if (mp != null) {
            mp.release();
            mp.reset();
        }

        mp = getMediaPlayer(getBaseContext());
        mp.setOnCompletionListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                handler.sendEmptyMessage(MSG_CURRENT);

                try {
                    mBinder.play();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        IntentFilter filter = new IntentFilter();
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
        mp.release();
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
     *
     * @param isNewPlayMusic
     */
    private void updatePlayBar(boolean isNewPlayMusic) {
        Intent intent = new Intent(PLAYBAR_UPDATE);
        intent.putExtra("isNewPlayMusic", isNewPlayMusic);

        AbstractMusic music = null;
        try {
            music = mBinder.getNowPlayingSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        intent.putExtra("newMusic",music);
        sendBroadcast(intent);
    }

    /**
     * 准备音乐并播放
     *
     * @param music
     */
    private void prepareSong(AbstractMusic music) {
        //        showMusicPlayerNotification(getResources().getString(R.string.app_name),NT_PLAYBAR_ID,
//                R.drawable.sweet, music.getAlbumId(),
//                music.getTitle(), music.getArtist());
        showMusicPlayerNotification(getResources().getString(R.string.app_name), NT_PLAYBAR_ID,
                R.drawable.sweet, "",
                music.getTitle(), music.getArtist(), music);

//        updatePlayBar(music.getSongId() != lastSongID);
        updatePlayBar(true);
//        lastSongID = music.getSongId();

        //如果是网络歌曲,而且未从网络获取详细信息，则需要获取歌曲的详细信息
        if(music.getType() == AbstractMusic.MusicType.Online) {
            final Song song = (Song) music;
            if(!song.hasGetDetailInfo()) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();

                        //同步请求到歌曲信息
                            SongPlayResp resp = BaiduMusicUtil.querySong(song.getSongid());
                            if(resp!=null) {
                                song.bitrate = resp.bitrate;
                                song.songInfo = resp.songinfo;

                                Log.i(TAG,"song hasGetDetailInfo:"+song);

                                updatePlayBar(false);

                                Message msg = Message.obtain();
                                msg.what = MSG_PLAY;
                                msg.obj = song;
                                handler.sendMessage(msg);
                            }
                    }
                }.start();
            }else{
                play(music);
            }
        }else{
            play(music);
        }
    }

    private void play(AbstractMusic music){
        if(mp!=null) {
            mp.reset();
        }

        try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Log.i(TAG, "datasoure:" + music.getDataSoure());
            mp.setDataSource(getBaseContext(), music.getDataSoure());

            mp.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void startPlay(AbstractMusic music) {
////        showMusicPlayerNotification(getResources().getString(R.string.app_name),NT_PLAYBAR_ID,
////                R.drawable.sweet, music.getAlbumId(),
////                music.getTitle(), music.getArtist());
//        showMusicPlayerNotification(getResources().getString(R.string.app_name), NT_PLAYBAR_ID,
//                R.drawable.sweet, 0,
//                music.getTitle(), music.getArtist());
//        mp.start();
//
////        updatePlayBar(music.getSongId() != lastSongID);
//        updatePlayBar(true);
////        lastSongID = music.getSongId();
//    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        AbstractMusic music = null;
        try {
            music = mBinder.getNowPlayingSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Message msg = Message.obtain();
        msg.what = MSG_BUFFER_UPDATE;
        msg.arg1 = percent * music.getDuration() / 100;

        handler.sendMessage(msg);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        try {
            Log.i(TAG, "onCompletion");
            mBinder.nextSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void showMusicPlayerNotification(String tickerText, int id,
                                     int resId, String picUri, String title, String artist, AbstractMusic music) {
        if (reViews == null) {
            reViews = new RemoteViews(getPackageName(), R.layout.notification_play);
        }
        reViews.setTextViewText(R.id.title, title);
        reViews.setTextViewText(R.id.text, artist);

        reViews.setImageViewResource(R.id.img_album, R.drawable.img_album_background);
        ImageLoader imageLoader = SweetApplication.getImageLoader();
        imageLoader.loadImage(music.getArtPic(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);

                reViews.setImageViewBitmap(R.id.img_album, loadedImage);
            }
        });

        Log.i(TAG, "picUri:" + picUri);


        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
        reViews.setOnClickPendingIntent(R.id.nt_container, pendingIntent);

        Intent exitIntent = new Intent(PLAYPRO_EXIT);
        PendingIntent exitPendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, exitIntent, 0);
        reViews.setOnClickPendingIntent(R.id.button_exit_notification_play, exitPendingIntent);

        Intent nextInent = new Intent(NEXTSONG);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, nextInent, 0);
        reViews.setOnClickPendingIntent(R.id.button_next_notification_play, nextPendingIntent);

        Intent preInent = new Intent(PRESONG);
        PendingIntent prePendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, preInent, 0);
        reViews.setOnClickPendingIntent(R.id.button_previous_notification_play, prePendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setContent(reViews).setSmallIcon(resId).setTicker(title).setOngoing(true);

        startForeground(id, builder.build());
        isForeground = true;
    }

    static MediaPlayer getMediaPlayer(Context context) {

        MediaPlayer mediaplayer = new MediaPlayer();

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return mediaplayer;
        }

        try {
            Class<?> cMediaTimeProvider = Class.forName("android.media.MediaTimeProvider");
            Class<?> cSubtitleController = Class.forName("android.media.SubtitleController");
            Class<?> iSubtitleControllerAnchor = Class.forName("android.media.SubtitleController$Anchor");
            Class<?> iSubtitleControllerListener = Class.forName("android.media.SubtitleController$Listener");

            Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});

            Object subtitleInstance = constructor.newInstance(context, null, null);

            Field f = cSubtitleController.getDeclaredField("mHandler");

            f.setAccessible(true);
            try {
                f.set(subtitleInstance, new Handler());
            } catch (IllegalAccessException e) {
                return mediaplayer;
            } finally {
                f.setAccessible(false);
            }

            Method setsubtitleanchor = mediaplayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);

            setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null);
            //Log.e("", "subtitle is setted :p");
        } catch (Exception e) {
        }

        return mediaplayer;
    }


}
