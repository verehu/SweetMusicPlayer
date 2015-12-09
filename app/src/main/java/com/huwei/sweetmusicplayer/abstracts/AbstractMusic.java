package com.huwei.sweetmusicplayer.abstracts;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;

import com.huwei.sweetmusicplayer.util.TimeUtil;

/**
 * 定义播放实体的抽象类，子类包含MusicInfo（本地音乐）,Song（在线音乐  来源于百度）
 *
 * @author Jayce
 * @date 2015/8/21
 */
public abstract class AbstractMusic implements Parcelable, Parcelable.Creator<AbstractMusic> {

    public static Creator<AbstractMusic> CREATOR;

    public AbstractMusic() {
        //给CREATOR赋值
        CREATOR = this;
    }

    public abstract Uri getDataSoure();

    public abstract Integer getDuration();

    public abstract MusicType getType();

    /**
     * 获取歌曲名
     */
    public abstract String getTitle();

    public abstract String getArtist();

//    /**
//     * 获取艺术家图片
//     * @return   uri
//     */
//    public abstract String getArtPic();

    /**
     * 加载艺术家图片  上面的方法废弃
     * @param loadListener
     */
    public abstract void loadArtPic(OnLoadListener loadListener);

    /**
     * 获取时间字符串
     * @return
     */
    public String getDurationStr(){
        return TimeUtil.mill2mmss(getDuration());
    }


    public enum MusicType {
        Local, Online
    }

    public interface OnLoadListener{
        void onSuccessLoad(Bitmap bitmap);
    }

}
