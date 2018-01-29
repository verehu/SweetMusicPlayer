package com.huwei.sweetmusicplayer.data.models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;

import com.huwei.sweetmusicplayer.util.TimeUtil;

import java.io.Serializable;

/**
 * 定义播放实体的抽象类，子类包含MusicInfo（本地音乐）,Song（在线音乐  来源于百度）
 *
 * @author Jayce
 * @date 2015/8/21
 */
public abstract class AbstractMusic implements Serializable, Parcelable, Parcelable.Creator<AbstractMusic> {

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

    /**
     * 获取艺术家图片
     * @return   uri
     */
    public abstract String getArtPic();

    public String getArtPicHuge(){
        return getArtPic();
    }

    public String getArtPremium() {
        return getArtPic();
    }

    /**
     * 专辑图片高斯模糊值
     * @return
     */
    public abstract int blurValueOfPlaying();

    /**
     * 获取时间字符串
     *
     * @return
     */
    public String getDurationStr() {
        return TimeUtil.mill2mmss(getDuration());
    }

    public boolean isOnlineMusic(){
        return getType() == MusicType.Online;
    }


    public enum MusicType {
        Local, Online
    }

    public interface OnLoadListener {
        void onSuccessLoad(Bitmap bitmap);
    }

    /**
     * PIC 尺寸枚举
     */
    public enum PicSizeType {
        SMALL, BIG, PREIUM, HUGE
    }
}
