package com.huwei.sweetmusicplayer;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Audio.Media;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huwei.sweetmusicplayer.dao.DaoSession;
import com.huwei.sweetmusicplayer.dao.MusicInfoDao;
import com.huwei.sweetmusicplayer.models.MusicInfo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_songscan)
public class SongScanActivity extends BaseActivity {

    private static final boolean IS_FAVORITE = false ;
    private List<MusicInfo> musicList = new ArrayList<>() ;
    private Uri contentUri = Media.EXTERNAL_CONTENT_URI;
    private ContentResolver resolver ;
    private ScanMusicThread scanMusicThread ;
    private int songCount = 0 ;
    /** The columns choose to get from your phone */
    private String[] projection = { Media._ID , Media.DISPLAY_NAME , Media.DATA , Media.ALBUM , Media.ARTIST , Media.DURATION , Media.SIZE  , Media.ALBUM_ID};

    /** filter condition */
    String where = "mime_type in ('audio/mpeg','audio/x-ms-wma')  and is_music > 0";

    /** sort order*/
    private String sortOrder = Media.DATA ;

    private DaoSession daoSession ;
    private  MusicInfoDao mifDao ;

    private static final int UPDATE_MESSAGE = 0 ;
    private static final int UPDATE_MUSIC_INFO_DURATION = 200 ;
    private boolean IS_CONTINUE_SCAN = true ;

    @ViewById
    TextView scannow_tv;
    @ViewById
    TextView scancount_tv;
    @ViewById
    Button scanfinish_btn;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case UPDATE_MESSAGE:
                    int insertCount = 0 ;
                    MusicInfo tempMusic = (MusicInfo) msg.obj ;
                    if (SongScanActivity.this != null){
                        songCount ++ ;
                        scancount_tv.setText(String.valueOf(songCount));
                        scannow_tv.setText(tempMusic.getTitle() + "--" + tempMusic.getPath());
                                            //insert the music that not into database into database
                    if(mifDao.load(tempMusic.getSongId()) == null) {
                        daoSession.insert(tempMusic) ;
                            insertCount ++ ;
                        }
                    if(SweetApplication.DEBUG){
                         Log.i("com.cvil.debug" , String.valueOf(insertCount)) ;
                    }
                    }else{
                        break ;
                    }
                    break ;
            }

        }
    } ;

    @ViewById
    Toolbar toolbar;

    @AfterViews
    void init(){
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("歌曲扫描");
        toolbar.setNavigationIcon(R.drawable.mc_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolver = getContentResolver() ;
        daoSession = SweetApplication.getDaoSession() ;
        mifDao = daoSession.getMusicInfoDao() ;
        scanMusicThread = new ScanMusicThread();
        scanMusicThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IS_CONTINUE_SCAN = false ;
        try {
            Thread.sleep(UPDATE_MUSIC_INFO_DURATION);//wait for the thread stop
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scanMusicThread.interrupt();  //interrupt the thread to stop the work of the thread
    }

    /** A thread to scan the music in your phone */
    class ScanMusicThread extends  Thread{
        @Override
        public void run() {
            super.run();
            MusicInfo musicInfo ;
            Cursor cursor = resolver.query(contentUri , projection , where , null , sortOrder) ;
            if(cursor != null){
                while (cursor.moveToNext() && IS_CONTINUE_SCAN){
                        String title = cursor.getString(cursor.getColumnIndex(Media.DISPLAY_NAME));
                        String album = cursor.getString(cursor.getColumnIndex(Media.ALBUM));
                        long albumID = cursor.getLong(cursor.getColumnIndex(Media.ALBUM_ID));
                        long id = cursor.getLong(cursor.getColumnIndex(Media._ID));
                        int duration = cursor.getInt(cursor.getColumnIndex(Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndex(Media.SIZE));
                        String artist = cursor.getString(cursor.getColumnIndex(Media.ARTIST));
                        String path = cursor.getString(cursor.getColumnIndex(Media.DATA)) ;

                        musicInfo = new MusicInfo( id , albumID , title , artist , duration , path , IS_FAVORITE ) ;
                        musicList.add(musicInfo) ;

                    if(mHandler != null && SongScanActivity.this != null){
                        try {
                            Thread.sleep(UPDATE_MUSIC_INFO_DURATION);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = new Message() ;
                        msg.obj = musicInfo ;
                        msg.what = UPDATE_MESSAGE ;
                        mHandler.sendMessage(msg) ;
                    }
                }

            }
        }
    }
}
 
 




