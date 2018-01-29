package com.huwei.sweetmusicplayer.business.interfaces;

import com.huwei.sweetmusicplayer.data.models.AbstractMusic;

import java.util.List;

/**
 * 定义操作播放音乐行为的公共接口*
 *
 * @author jayce
 * @date 2015/01/27
 */
public interface IMusicControl {
    int MSG_PLAY = 0;
    int MSG_PAUSE = 1;
    int MSG_STOP = 2;
    int MSG_SEEK = 3;
    int MSG_PREPARE = 4;
    int MSG_NEXT_SONG = 5;
    int MSG_PRE_SONG = 6;
    int MSG_RANDOM = 7;

    //操作相关方法
    void play();

    void pause();

    void stop();

    void seekTo(int mesc);

    void preparePlayingList(int index, List<AbstractMusic> list);

    void nextSong();

    void preSong();

    void randomSong();

    //状态获取接口
    boolean isPlaying();

    int getNowPlayingIndex();

    AbstractMusic getNowPlayingSong();

    boolean isForeground();

    //界面控制相关
    void updateMusicQueue();
}
