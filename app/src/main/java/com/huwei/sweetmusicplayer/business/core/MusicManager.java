package com.huwei.sweetmusicplayer.business.core;

import com.huwei.sweetmusicplayer.IMusicControllerService;
import com.huwei.sweetmusicplayer.data.models.AbstractMusic;
import com.huwei.sweetmusicplayer.business.interfaces.IMusicControl;


import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by huwei on 15-1-27.
 */
public class MusicManager implements IMusicControl {
    private static MusicManager instance = new MusicManager();
    private IMusicControllerService service;    //被代理的对象
    private List<AbstractMusic> list;
    private ExecutorService mPlayThreadPool;

    private MusicManager() {
        super();

        mPlayThreadPool = ThreadFactroy.get().getMusicOperateExecutor();
    }

    public static MusicManager get() {
        return instance;
    }

    public void bindService(IMusicControllerService service) {
        this.service = service;
    }

    @Override
    public void play() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    service.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void pause() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    service.pause();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void stop() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    service.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void seekTo(final int mesc) {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    service.seekTo(mesc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            }
        });
    }

    public void preparePlayingListInner(final int index, final List<AbstractMusic> list) {
        try {
            service.preparePlayingList(index, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (MusicManager.this.list != list) {
            updateMusicQueue();
            MusicManager.this.list = list;
        }
    }

    @Override
    public boolean isPlaying() {
        try {
            return service.isPlaying();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getNowPlayingIndex() {
        try {
            return service.getPlayingSongIndex();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public AbstractMusic getNowPlayingSong() {
        try {
            return service.getNowPlayingSong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isForeground() {
        try {
            return service.isForeground();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void nextSong() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    service.nextSong();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void preSong() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    service.preSong();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void randomSong() {
        mPlayThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    service.randomSong();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void updateMusicQueue() {
        //service.updateMusicQueue();
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
        List list1 = get().getPlayingList();
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
    public static boolean isIndexNowPlaying(List list, int pos) {
        return isListEqual(list) && get().getNowPlayingIndex() == pos;
    }
}
