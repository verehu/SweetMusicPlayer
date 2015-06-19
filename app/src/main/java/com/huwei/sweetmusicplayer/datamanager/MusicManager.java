package com.huwei.sweetmusicplayer.datamanager;

import android.content.Intent;

import com.huwei.sweetmusicplayer.interfaces.IMusicControl;
import com.huwei.sweetmusicplayer.models.MusicInfo;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by huwei on 15-1-27.
 */
public class MusicManager implements IMusicControl {
    private static MusicManager instance = new MusicManager();
    IMusicControl t;    //被代理的对象
    private List list;

    ArrayBlockingQueue queue;

    private MusicManager() {
        super();
    }

    public static MusicManager getInstance() {
        return instance;
    }

    @Override
    public void play() {
        t.play();
    }



    @Override
    public void pause() {
        t.pause();
    }

    @Override
    public void stop() {
        t.stop();
    }

    @Override
    public void seekTo(int mesc) {
        t.seekTo(mesc);
    }

    @Override
    public void preparePlayingList(int index,List list) {
        t.preparePlayingList(index,list);
        if(this.list!=list){
            updateMusicQueue();
            this.list=list;
        }

    }


    @Override
    public boolean isPlaying() {
        return t.isPlaying();
    }

    @Override
    public int getNowPlayingIndex() {
        return t.getNowPlayingIndex();
    }
    
    @Override
    public MusicInfo getNowPlayingSong() {
        return t.getNowPlayingSong();
    }

    @Override
    public boolean isForeground() {
        return t.isForeground();
    }

    @Override
    public void nextSong() {
        t.nextSong();
    }

    @Override
    public void preSong() {
        t.preSong();
    }

    @Override
    public void randomSong() {
        t.randomSong();
    }

    @Override
    public void updateMusicQueue() {
        t.updateMusicQueue();
    }

    //绑定被代理的对象
    public void bindProxyedObject(IMusicControl t) {
        this.t = t;
    }

    public List getPlayingList(){
        return list;
    }
}
