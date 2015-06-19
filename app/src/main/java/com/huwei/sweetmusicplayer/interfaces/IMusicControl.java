package com.huwei.sweetmusicplayer.interfaces;

import com.huwei.sweetmusicplayer.models.MusicInfo;

import java.util.List;

/**
 * 定义操作播放音乐行为的公共接口*
 * Created by huwei on 15-1-27.
 */
public interface IMusicControl {
    public void play();
    
    public void pause();
    
    public void stop();

    public void seekTo(int mesc);
    
    public void preparePlayingList(int index,List list);

    public boolean isPlaying();
    
    public int getNowPlayingIndex();

    public MusicInfo getNowPlayingSong();
    
    public boolean isForeground();

    public void nextSong();

    public void preSong();

    public void randomSong();

    //界面控制相关
    public void updateMusicQueue();
}
