package com.huwei.sweetmusicplayer.util;

import com.huwei.sweetmusicplayer.contants.Url;

/**
 * 使用百度音乐API的工具类
 *
 * @author Jayce
 * @date 2015/6/11
 */
public class BaiduMusicUtil implements Url {
    public static final int PAGESIZE = 20;

    public static final String SEARCH_CATALOGSUG = "baidu.ting.search.catalogSug";
    public static final String SONG_LRC = "baidu.ting.song.lry ";
    public static final String SONG_PLAY = "baidu.ting.song.play";
    public static final String GET_SONGINFO = "baidu.ting.song.getInfos";
    public static final String GET_ARTISTINFO = "baidu.ting.artist.getinfo";    //获取歌手信息
    public static final String GET_ARTISTSONGLIST = "baidu.ting.artist.getSongList"; //获取歌手的歌曲列表
    public static final String GET_ARTISTALUBMLIST = "baidu.ting.artist.getAlbumList";   //获取歌手的专辑列表;
    public static final String GET_ALBUMINFO = "baidu.ting.album.getAlbumInfo";
    public static final String QUERY_MERGE = "baidu.ting.search.merge";


    private static void addCommonarams(HttpParams params ) {
        params.add("from", "android");
        params.add("version", "5.6.5.0");
    }

    /**
     * 根据歌名 艺术家 专家搜索歌曲的建议
     *
     * @param keyword
     * @param httpHandler
     */
    public static void querySug(String keyword, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();

        addCommonarams(params);

        params.add("format", "json");
        params.add("method", SEARCH_CATALOGSUG);
        params.add("from", "android");
        params.add("query", keyword);
        HttpUtil.get(BAIDU_MUSIC, params, httpHandler);
    }

    public static void queryMerge(String keyword, int pageno, int pageSize, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();

        addCommonarams(params);

        params.add("format", "json");
        params.add("method", QUERY_MERGE);
        params.add("query", keyword);
        params.add("page_no", pageno);
        params.add("page_size", pageSize);
        params.add("type", -1);
        params.add("data_source", 0);
        params.add("use_cluster", 1);
        HttpUtil.get(BAIDU_MUSIC, params, httpHandler);
    }

    public static void getSongInfo(String songId, HttpHandler httpHandler) {

    }

    /**
     * 获取专辑详情
     *
     * @param albumId
     * @param httpHandler
     */

    public static void getAlbumInfo(String albumId, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();

        addCommonarams(params);

        params.add("format", "json");
        params.add("method", GET_ALBUMINFO);
        params.add("album_id", albumId);
        HttpUtil.get(BAIDU_MUSIC, params, httpHandler);
    }


    /**
     * 根据songid查询歌词 songid为百度的id 非数据库中的songid
     *
     * @param songid
     */
    public static void queryLrc(String songid, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();

        addCommonarams(params);

        params.add("format", "json");
        params.add("method", SONG_LRC);
        params.add("songid", songid);
        HttpUtil.get(BAIDU_MUSIC, params, httpHandler);
    }

    /**
     * 请求歌曲信息
     *QUERY_MERGE
     * @param songid
     * @return
     */
    public static void querySong(String songid, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();

        addCommonarams(params);

        params.add("format", "json");
        params.add("method", SONG_PLAY);
        params.add("songid", songid);
        HttpUtil.get(BAIDU_MUSIC, params, httpHandler);
    }

    /**
     * 获取Album详细信息
     *
     * @param albumId
     * @param httpHandler
     */
    @Deprecated
    public static void getAlbumDetail(String albumId, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();

        addCommonarams(params);

        params.add("albumId", albumId);
        params.add("type", "album");
        HttpUtil.get(BAIDU_MUSIC_ALBUM, params, httpHandler, true);
    }

    /**
     * 获取歌手信息
     *
     * @param tinguid
     * @param artistid
     * @param httpHandler
     */
    public static void getArtistInfo(String tinguid, String artistid, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();

        addCommonarams(params);

        params.add("tinguid", tinguid);
        params.add("artistid", artistid);
        params.add("method", GET_ARTISTINFO);
        HttpUtil.get(BAIDU_MUSIC, params, httpHandler, true);
    }

    /**
     * 获取歌手页面的  歌曲列表
     * @param ting_uid
     * @param artist_id
     * @param pageNo
     * @param httpHandler
     */
    public static void getArtistSongList(String ting_uid, String artist_id, int pageNo, HttpHandler httpHandler) {
        HttpParams params = new HttpParams();

        addCommonarams(params);

        params.add("tinguid", ting_uid);
        params.add("artistid", artist_id);
        params.add("offset", pageNo * PAGESIZE);
        params.add("limits",PAGESIZE);
        params.add("method", GET_ARTISTSONGLIST);
        HttpUtil.get(BAIDU_MUSIC, params, httpHandler, true);
    }

    public static void getAritistAlbumList(String ting_uid, String artist_id, int pageNo, HttpHandler httpHandler){
        HttpParams params = new HttpParams();

        addCommonarams(params);

        params.add("tinguid", ting_uid);
        params.add("artistid", artist_id);
        params.add("offset", pageNo * PAGESIZE);
        params.add("limits",PAGESIZE);
        params.add("method", GET_ARTISTALUBMLIST);
        HttpUtil.get(BAIDU_MUSIC, params, httpHandler, true);
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
