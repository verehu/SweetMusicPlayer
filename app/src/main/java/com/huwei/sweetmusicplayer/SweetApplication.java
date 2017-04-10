package com.huwei.sweetmusicplayer;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.RequestQueue;


import com.android.volley.toolbox.Volley;
import com.huwei.sweetmusicplayer.dao.DaoMaster;
import com.huwei.sweetmusicplayer.dao.DaoSession;
import com.huwei.sweetmusicplayer.util.WindowTool;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.androidannotations.annotations.EApplication;



/**
 * Created by huwei on 15-1-20.
 */
@EApplication
public class SweetApplication extends Application {

    private static DaoSession daoSession;
    private static RequestQueue mQueue;
    public static Context context;
    private static ImageLoader mImageLoader;

    public static int mScreenWidth;
    public static int mScreenHeight;

    /** set the value to decide weather to print debug log , default true in develop*/
    public static final boolean DEBUG = true ;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

        mScreenWidth = WindowTool.getWidth(this);
        mScreenHeight = WindowTool.getHeight(this);
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

    public static ImageLoader getImageLoader(){
        if(mImageLoader==null){
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
            .build();
            mImageLoader =ImageLoader.getInstance();
            mImageLoader.init(config);
        }
        return mImageLoader;
    }
}
