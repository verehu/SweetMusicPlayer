package com.huwei.sweetmusicplayer;


import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.huwei.sweetmusicplayer.dao.DaoMaster;
import com.huwei.sweetmusicplayer.dao.DaoSession;
import com.huwei.sweetmusicplayer.dao.MusicInfoDao;
import com.huwei.sweetmusicplayer.models.MusicInfo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@EActivity(R.layout.activity_songscan)
public class SongScanActivity extends BaseActivity {


    private static final boolean DEBUG = true ;
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

    private static final int UPDATE_MESSAGE = 0 ;
    private static final long ALBUM_ID = 1 ;
    private static final int UPDATE_MUSIC_INFO_DURATION = 200 ;

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
                    MusicInfo tempMusic = (MusicInfo) msg.obj ;
                    songCount ++ ;
                    scancount_tv.setText(String.valueOf(songCount));
                    scannow_tv.setText(tempMusic.getTitle() + "--" + tempMusic.getPath());

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
        scanMusicThread = new ScanMusicThread();
        scanMusicThread.start();
    }
    /**A thread to scan the music in your phone */
    class ScanMusicThread extends  Thread{
        @Override
        public void run() {
            super.run();
            Cursor cursor = resolver.query(contentUri , projection , where , null , sortOrder) ;
            if(cursor != null){
                while (cursor.moveToNext()){
                        String title = cursor.getString(cursor.getColumnIndex(Media.DISPLAY_NAME));
                        String album = cursor.getString(cursor.getColumnIndex(Media.ALBUM));
                        long albumID = cursor.getLong(cursor.getColumnIndex(Media.ALBUM_ID));
                        long id = cursor.getLong(cursor.getColumnIndex(Media._ID));
                        int duration = cursor.getInt(cursor.getColumnIndex(Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndex(Media.SIZE));
                        String artist = cursor.getString(cursor.getColumnIndex(Media.ARTIST));
                        String path = cursor.getString(cursor.getColumnIndex(Media.DATA)) ;


                        MusicInfo musicInfo = new MusicInfo( id , albumID , title , artist , duration , path , false ) ;
                        musicList.add(musicInfo) ;
                        try {
                            sleep(UPDATE_MUSIC_INFO_DURATION);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = new Message() ;
                        msg.obj = musicInfo ;
                        msg.what = UPDATE_MESSAGE ;
                        mHandler.sendMessage(msg) ;
                }

                DaoSession daoSession = SweetApplication.getDaoSession() ;
                MusicInfoDao mifDao = daoSession.getMusicInfoDao() ;
                // select the music that not in database then insert into the database
                int insertCount = 0 ;
                for(MusicInfo musicInfo : musicList){
                    if(mifDao.load(musicInfo.getSongId()) != null){
                        daoSession.insert(musicInfo) ;
                        insertCount ++ ;
                    }
                }
                if(DEBUG){
                    Log.i("com.cvil.debug" , String.valueOf(insertCount)) ;
                }
            }


        }
    }
}
 
 




