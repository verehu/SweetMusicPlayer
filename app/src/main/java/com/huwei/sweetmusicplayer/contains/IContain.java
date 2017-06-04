package com.huwei.sweetmusicplayer.contains;

/**
 * 常用常量接口定义
 *
 * @author Jayce
 * @date 2015/6/10
 */
public interface IContain {
    //常见常量定义
    public static final int FILTER_SIZE = 1 * 1024 * 1024;// 1MB
    public static final int FILTER_DURATION = 1 * 60 * 1000;// 1分钟

    /**
     * Define Notification ID*
     */
    public static final int NT_PLAYBAR_ID = 1;

    //Environment中属性名称定义
    public static final String ENV_IS_FILTER_SIZE = "IS_FILTER_SIZE";
    public static final String ENV_IS_FILTER_DURATION = "IS_FLITER_DURATION";
    public static final String ENV_HAS_RUN_COUNT = "ENV_HAS_RUN_COUNT";
    public static final String ENV_RECENT_MUSIC = "ENV_RECENT_MUSIC";

    /**
     * Define ACIOTN*
     */
    public static String PLAYPRO_EXIT = "com.huwei.intent.PLAYPRO_EXIT_ACTION";
    public static final String PLAYBAR_UPDATE = "com.huwei.intent.PLAYBAR_UPDATE";        //关于歌曲信息控件的更新
    public static final String PLAY_STATUS_UPDATE = "com.huwei.intent.PLAY_STATUS_UPDATE";
    public static final String CURRENT_UPDATE = "com.huwei.intent.DURATION_UPDATE";       //当前状态 有关时间控件的更新
    public static final String BUFFER_UPDATE = "com.huwei.intent.BUFFER_UPDATE";          //在线音乐的缓冲更新


    public static final String UPTATE_MUISC_QUEUE = "com.huwei.intent.UPDATE_MUSIC_QUEUE";


    //http请求模块统一标签
    public static final String HTTP = "HTTP";

    public static final String DEFAULT_PIC = "default";
}
