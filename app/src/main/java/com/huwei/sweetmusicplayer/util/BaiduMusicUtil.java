package com.huwei.sweetmusicplayer.util;

import com.google.gson.Gson;
import com.huwei.sweetmusicplayer.contains.IUrl;
import com.huwei.sweetmusicplayer.baidumusic.resp.SongPlayResp;

/**
 * 使用百度音乐API的工具类
 *
 * @author Jayce
 * @date 2015/6/11
 */
public class BaiduMusicUtil implements IUrl {
    public static final String SEARCH_CATALOGSUG = "baidu.ting.search.catalogSug";
    public static final String SONG_LRC = "baidu.ting.song.lry ";
    public static final String SONG_PLAY = "baidu.ting.song.play";
    public static final String GET_ALBUMINFO = "baidu.ting.album.getAlbumInfo";
    public static final String QUERY_MERGE = "baidu.ting.search.merge";

    /**
     * 根据歌名 艺术家 专家搜索歌曲的建议
     *
     * @param keyword
     * @param httpHandler
     */
    public static void querySug(String keyword, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();
        params.add("format", "json");
        params.add("method", SEARCH_CATALOGSUG);
        params.add("query", keyword);
        HttpUtil.get(BADDU_MUSIC, params, httpHandler);
    }

    public static void queryMerge(String keyword, int pageno, int pageSize, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();
        params.add("format", "json");
        params.add("method", QUERY_MERGE);
        params.add("query", keyword);
        params.add("page_no", pageno);
        params.add("page_size", pageSize);
        params.add("type", -1);
        params.add("data_source", 0);
        params.add("use_cluster", 1);
        HttpUtil.get(BADDU_MUSIC, params, httpHandler);
    }

    /**
     * 获取专辑详情
     *
     * @param albumId
     * @param httpHandler
     */
    public static void getAlbumInfo(String albumId, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();
        params.add("format", "json");
        params.add("method", GET_ALBUMINFO);
        params.add("album_id", albumId);
        HttpUtil.get(BADDU_MUSIC, params, httpHandler);
    }


    /**
     * 根据songid查询歌词 songid为百度的id 非数据库中的songid
     *
     * @param songid
     */
    public static void queryLrc(String songid, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();
        params.add("format", "json");
        params.add("method", SONG_LRC);
        params.add("songid", songid);
        HttpUtil.get(BADDU_MUSIC, params, httpHandler);
    }

    /**
     * 同步请求歌曲信息
     *
     * @param songid
     * @return
     */
    public static SongPlayResp querySong(String songid) {
        HttpParams params = new HttpParams();
        params.add("format", "json");
        params.add("method", SONG_PLAY);
        params.add("songid", songid);
        String result = HttpUtil.getSync(BADDU_MUSIC, params);
        return new Gson().fromJson(result, SongPlayResp.class);
    }

    /**
     * 获取Album详细信息
     *
     * @param albumId
     * @param httpHandler
     */
    public static void getAlbumDetail(String albumId, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();
        params.add("albumId", albumId);
        params.add("type", "album");
        HttpUtil.get(BAIDU_MUSIC_ALBUM, params, httpHandler, true);
    }

    /**
     * 通过歌曲Id获取歌曲下载地址  同在线播放的地址
     *
     * @param songId
     * @return
     */
    public static String getDownloadUrlBySongId(String songId) {
        return "http://ting.baidu.com/data/music/links?songIds=" + songId;
    }
}
