package com.huwei.sweetmusicplayer.services;

import java.io.IOException;


import com.huwei.sweetmusicplayer.R;
import com.huwei.sweetmusicplayer.MainActivity;
//import com.huwei.sweetmusicplayer.datamanager.MusicManager;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.enums.MusicState;
import com.huwei.sweetmusicplayer.models.MusicInfo;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

@Deprecated
public class IPlayingMusicService extends Service implements OnCompletionListener,IContain {
    private static final String TAG = "LocalMusicService";
    private MediaPlayer mp; // mediaPlayer????
    private int currentTime;
    private int duration;
    private long id;
    private Uri uri;
    private Handler handler;
    NotificationManager notificationManager;

    private final int notiID = NT_PLAYBAR_ID;


    private static final String MUSIC_CURRENT = "com.music.currentTime";
    private static final String MUSIC_DURATION = "com.music.duration";
    private static final String MUSIC_NEXT = "com.music.next";
    private static final String MUSIC_UPDATE = "com.music.update";

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        init();
        if (mp != null) {
            mp.reset();
            mp.release();
        }
        mp = new MediaPlayer();


        mp.setOnCompletionListener(this);
    }


    void showMusicPlayerNotification(String tickerText, int id,
                                     int resId, int photoId, String title, String artist) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        RemoteViews reViews = new RemoteViews(getPackageName(), R.layout.notification_play);
        reViews.setTextViewText(R.id.title, title);
        reViews.setTextViewText(R.id.text, artist);
        reViews.setImageViewResource(R.id.img_album, photoId);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
        reViews.setOnClickPendingIntent(R.id.nt_container, pendingIntent);

        Intent exitIntent = new Intent(PLAYPRO_EXIT);
        PendingIntent exitPendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, exitIntent, 0);
        reViews.setOnClickPendingIntent(R.id.button_exit_notification_play, exitPendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setContent(reViews).setSmallIcon(resId).setTicker("??????SweetMusicPlayer").setOngoing(true);

        notificationManager.notify(id, builder.build());
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        if (mp != null) {
            stop();
            mp.release();
        }

        if (handler != null) {
            handler.removeMessages(1);
            handler = null;
        }

        if (notificationManager != null) {
            notificationManager.cancel(notiID);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub


        MusicInfo music = (MusicInfo) intent.getSerializableExtra("music");
        if (null != music && music.getSongId() != -1) {
            id = music.getSongId();
            uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + id);
            Log.i("playUri", uri.toString());

            try {
                mp.setDataSource(getBaseContext(), uri);

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }


        int op = intent.getIntExtra("op", -1);
        Log.i(TAG, "op:" + op + "");

        if (op != -1) {
            MusicState ms = MusicState.values()[op];

            switch (ms) {
                case PLAYING:
                    //???????
                    showMusicPlayerNotification(getResources().getString(R.string.app_name), notiID,
                            R.drawable.sweet, R.drawable.smiley_24,
                            music.getTitle(), music.getArtist());
                    play();
                    break;
                case PAUSE:
                    pause();
                    break;
                case STOP:
                    stop();
                    break;
                case PROGRESS_CHANGE:
                    int progress = intent.getExtras().getInt("progress");
                    mp.seekTo(progress);
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }


    public void play() {
        setup();

        if (mp != null) {
            mp.start();
        }
    }


    public void pause() {
        //????
        handler.removeMessages(1);

        if (mp != null) {
            mp.pause();
        }
    }


    public void stop() {
        if (handler != null) handler.removeMessages(1);

        if (mp != null) {
            mp.stop();
            mp.reset();
        }
    }


    public void setup() {
        Intent intent = new Intent();
        intent.setAction(MUSIC_DURATION);

        try {
            mp.setScreenOnWhilePlaying(true);

            mp.setOnPreparedListener(new OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    // TODO Auto-generated method stub
//					handler.sendEmptyMessage(1);
                    Log.i(TAG, "sendEmptyMessage");
                }
            });
            handler.sendEmptyMessage(1);
            mp.prepare();
            Log.i(TAG, "mp.prepare()");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        duration = mp.getDuration();
        intent.putExtra("duration", duration);
        sendBroadcast(intent);
    }


    // ?????????
    public void init() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                if (msg.what == 1) {
                    Intent intent = new Intent();
                    intent.setAction(MUSIC_CURRENT);

                    currentTime = mp.getCurrentPosition();
                    Log.i("currentTime", currentTime + "");
                    intent.putExtra("currentTime", currentTime);
                    sendBroadcast(intent);
                }

                handler.sendEmptyMessageDelayed(1, 800);
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub

        mp.reset();

        Intent intent = new Intent();
        intent.setAction(MUSIC_NEXT);
        sendBroadcast(intent);
    }


}
