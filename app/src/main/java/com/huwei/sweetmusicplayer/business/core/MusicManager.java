package com.huwei.sweetmusicplayer.business.core;

import com.huwei.sweetmusicplayer.business.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.business.interfaces.IMusicControl;


import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by huwei on 15-1-27.
 */
public class MusicManager implements IMusicControl {
    private static MusicManager instance = new MusicManager();
    IMusicControl t;    //被代理的对象
    private List<AbstractMusic> list;
    private ExecutorService mPlayThreadPool;

    private MusicManager() {
        super();

        mPlayThreadPool = ThreadFactroy.get().getMusicOperateExecutor();
    }

    public static MusicManager getInstance() {
        return instance;
    }

    @Override
    public void play() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                t.play();
            }
        });
    }


    @Override
    public void pause() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                t.pause();
            }
        });
    }

    @Override
    public void stop() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                t.stop();
            }
        });
    }

    @Override
    public void seekTo(final int mesc) {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                t.seekTo(mesc);
            }
        });
    }

    @Override
    public void preparePlayingList(final int index, final List<AbstractMusic> list) {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                preparePlayingListInner(index, list);
            }
        });
    }

    public void prepareAndPlay(final int index, final List<AbstractMusic> list) {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                preparePlayingListInner(index, list);
                //t.play();
            }
        });
    }

    public void preparePlayingListInner(final int index, final List<AbstractMusic> list) {
        t.preparePlayingList(index, list);
        if (MusicManager.this.list != list) {
            updateMusicQueue();
            MusicManager.this.list = list;
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
    public AbstractMusic getNowPlayingSong() {
        return t.getNowPlayingSong();
    }

    @Override
    public boolean isForeground() {
        return t.isForeground();
    }

    @Override
    public void nextSong() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                t.nextSong();
            }
        });
    }

    @Override
    public void preSong() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                t.preSong();
            }
        });
    }

    @Override
    public void randomSong() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                t.randomSong();
            }
        });
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
