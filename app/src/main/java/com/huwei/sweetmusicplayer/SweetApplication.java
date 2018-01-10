package com.huwei.sweetmusicplayer;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.RequestQueue;


import com.android.volley.toolbox.Volley;
import com.huwei.sweetmusicplayer.business.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.contains.IContain;
import com.huwei.sweetmusicplayer.dao.DaoMaster;
import com.huwei.sweetmusicplayer.dao.DaoSession;
import com.huwei.sweetmusicplayer.util.Environment;
import com.huwei.sweetmusicplayer.util.WindowTool;

import org.androidannotations.annotations.EApplication;

import static com.huwei.sweetmusicplayer.contains.IContain.NOW_PLAYMUSIC;
import static com.huwei.sweetmusicplayer.contains.IContain.PLAYBAR_UPDATE;

/**
 * Created by huwei on 15-1-20.
 */
@EApplication
public class SweetApplication extends Application {

    private static DaoSession daoSession;
    private static RequestQueue mQueue;

    public static int mScreenWidth;
    public static int mScreenHeight;
    public static SweetApplication CONTEXT;

    /** set the value to decide weather to print debug log , default true in develop*/
    public static final boolean DEBUG = true ;

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
        intentFilter.addAction(IContain.PLAYBAR_UPDATE);
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

    public static RequestQueue getQueue() {
        if(mQueue == null){
            mQueue = Volley.newRequestQueue(CONTEXT);
        }
        return mQueue;
    }

    public static Context get(){
        return CONTEXT;
    }
}
