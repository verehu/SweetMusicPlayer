package com.huwei.sweetmusicplayer.business.interfaces;



import com.huwei.sweetmusicplayer.business.abstracts.AbstractMusic;

import java.util.List;

/**
 * 定义操作播放音乐行为的公共接口*
 * @author jayce
 * @date 2015/01/27
 */
public interface IMusicControl {
    public static final int MSG_PLAY = 0;
    public static final int MSG_PAUSE = 1;
    public static final int MSG_STOP = 2;
    public static final int MSG_SEEK = 3;
    public static final int MSG_PREPARE = 4;
    public static final int MSG_NEXT_SONG = 5;
    public static final int MSG_PRE_SONG = 6;
    public static final int MSG_RANDOM = 7;

    //操作相关方法
    public void play();
    
    public void pause();
    
    public void stop();

    public void seekTo(int mesc);
    
    public void preparePlayingList(int index,List<AbstractMusic> list);

    public void nextSong();

    public void preSong();

    public void randomSong();

    //状态获取接口
    public boolean isPlaying();
    
    public int getNowPlayingIndex();

    public AbstractMusic getNowPlayingSong();
    
    public boolean isForeground();


    //界面控制相关
    public void updateMusicQueue();
}
