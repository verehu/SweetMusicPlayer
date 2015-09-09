// IPlayingMusicService.aidl
package com.huwei.sweetmusicplayer;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;

// Declare any non-default types here with import statements

interface IMusicControlerService {

    
    int getPid();
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
            
    void play();

    void pause();

    void stop();

    void seekTo(int mesc);

    void preparePlayingList(int musicIndex,in List  list);
    
    boolean isPlaying();
    
    int getPlayingSongIndex();
    
    AbstractMusic getNowPlayingSong();
    
    boolean isForeground();
    
    void nextSong();
    
    void preSong();
    
    void randomSong();
}
