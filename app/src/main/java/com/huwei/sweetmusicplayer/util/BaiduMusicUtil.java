package com.huwei.sweetmusicplayer.util;

import com.huwei.sweetmusicplayer.contains.IUrl;

/**
 * 使用百度音乐API的工具类
 * @author Jayce
 * @date 2015/6/11
 */
public class BaiduMusicUtil implements IUrl{
    public static final String SEARCH_CATALOGSUG="baidu.ting.search.catalogSug";
    public static final String SONG_LRC="baidu.ting.song.lry ";



    /**
     * 根据歌名 艺术家 专家搜索歌曲
     * @param keyword
     * @param httpHandler
     */
    public static void query(String keyword,HttpHandler httpHandler){
        HttpParams params=new HttpParams();
        params.add("format","json");
        params.add("method",SEARCH_CATALOGSUG);
        params.add("query",keyword);
        HttpUtil.get(BADDU_MUSIC,params,httpHandler);
    }


    /**
     * 根据songid查询歌词 songid为百度的id 非数据库中的songid
     * @param songid
     */
    public static void queryLrc(String songid,HttpHandler httpHandler){
        HttpParams params=new HttpParams();
        params.add("format","json");
        params.add("method",SONG_LRC);
        params.add("songid",songid);
        HttpUtil.get(BADDU_MUSIC,params,httpHandler);
    }
}
