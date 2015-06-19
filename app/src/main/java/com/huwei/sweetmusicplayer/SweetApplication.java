package com.huwei.sweetmusicplayer;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.huwei.sweetmusicplayer.dao.DaoMaster;
import com.huwei.sweetmusicplayer.dao.DaoSession;

import org.androidannotations.annotations.EApplication;


/**
 * Created by huwei on 15-1-20.
 */
@EApplication
public class SweetApplication extends Application {
    private static DaoSession daoSession;
    private static RequestQueue mQueue;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static DaoSession getDaoSession(){
        if(daoSession==null){
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-db", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static RequestQueue getQueue() {
        if(mQueue == null){
            mQueue = Volley.newRequestQueue(context);
        }
        return mQueue;
    }
}
