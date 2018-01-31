package com.huwei.sweetmusicplayer;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;

import com.huwei.sweetmusicplayer.data.models.AbstractMusic;
import com.huwei.sweetmusicplayer.data.contants.Contants;
import com.huwei.sweetmusicplayer.data.dao.DaoMaster;
import com.huwei.sweetmusicplayer.data.dao.DaoSession;
import com.huwei.sweetmusicplayer.util.Environment;
import com.huwei.sweetmusicplayer.util.WindowTool;

import static com.huwei.sweetmusicplayer.data.contants.Contants.NOW_PLAYMUSIC;
import static com.huwei.sweetmusicplayer.data.contants.Contants.PLAYBAR_UPDATE;

/**
 * Created by huwei on 15-1-20.
 */
public class SweetApplication extends Application {

    private static DaoSession daoSession;

    public static int mScreenWidth;
    public static int mScreenHeight;
    public static SweetApplication CONTEXT;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();

            switch (action) {
                case PLAYBAR_UPDATE:
                    AbstractMusic music = intent.getParcelableExtra(NOW_PLAYMUSIC);
                    if (music != null) {
                        Environment.saveRecentMusic(music);
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;

        mScreenWidth = WindowTool.getWidth(this);
        mScreenHeight = WindowTool.getHeight(this);

        AppContextHolder.setContext(this);
        initRecievers();
    }

    void initRecievers() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Contants.PLAYBAR_UPDATE);
        registerReceiver(receiver, intentFilter);
    }

    void unregisterRecievers(){
        unregisterReceiver(receiver);
    }

    public static DaoSession getDaoSession(){
        if(daoSession==null){
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(CONTEXT, "notes-db", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static Context get(){
        return CONTEXT;
    }
}
