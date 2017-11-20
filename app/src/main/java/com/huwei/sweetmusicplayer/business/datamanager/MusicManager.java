package com.huwei.sweetmusicplayer.business.datamanager;

import com.huwei.sweetmusicplayer.business.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.business.interfaces.IMusicControl;


import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by huwei on 15-1-27.
 */
public class MusicManager implements IMusicControl {
    private static MusicManager instance = new MusicManager();
    IMusicControl t;    //被代理的对象
    private List<AbstractMusic> list;

    ArrayBlockingQueue queue;

    private MusicManager() {
        super();
    }

    public static MusicManager getInstance() {
        return instance;
    }

    @Override
    public void play() {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
                t.play();
//            }
//        }.start();

    }


    @Override
    public void pause() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                t.pause();
            }
        }.start();
    }

    @Override
    public void stop() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                t.stop();
            }
        }.start();
    }

    @Override
    public void seekTo(final int mesc) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                t.seekTo(mesc);
            }
        }.start();
    }

    @Override
    public void preparePlayingList(final int index, final List<AbstractMusic> list) {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
                t.preparePlayingList(index, list);
                if (MusicManager.this.list != list) {
                    updateMusicQueue();
                    MusicManager.this.list = list;
                }
//            }
//        }.start();

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
    public AbstractMusic getNowPlayingSong() {
        return t.getNowPlayingSong();
    }

    @Override
    public boolean isForeground() {
        return t.isForeground();
    }

    @Override
    public void nextSong() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                t.nextSong();
            }
        }.start();
    }

    @Override
    public void preSong() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                t.preSong();
            }
        }.start();
    }

    @Override
    public void randomSong() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                t.randomSong();
            }
        }.start();
    }

    @Override
    public void updateMusicQueue() {
        t.updateMusicQueue();
    }

    //绑定被代理的对象
    public void bindProxyedObject(IMusicControl t) {
        this.t = t;
    }

    public List<AbstractMusic> getPlayingList() {
        return list;
    }


    /**
     * 判断是否为同一个播放列表，都为null判断false，否则比较两者的hashcode
     *
     * @param list2
     * @return
     */
    public static boolean isListEqual(List list2) {
        List list1 = getInstance().getPlayingList();
        if (list1 == null || list2 == null) {
            return false;
        } else {
            return list1.hashCode() == list2.hashCode();
        }
    }

    /**
     * 判断当前列表的歌曲是不是正在播放的歌曲   1,列表是否是正在播放的列表 2,位置是否在正在播放的列表
     *
     * @param list
     * @param pos
     * @return
     */
    public static boolean isIndexNowPLayng(List list, int pos) {
        return isListEqual(list) && getInstance().getNowPlayingIndex() == pos;
    }
}
