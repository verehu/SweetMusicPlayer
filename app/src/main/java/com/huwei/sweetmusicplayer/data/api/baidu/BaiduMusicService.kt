package com.huwei.sweetmusicplayer.data.api.baidu

import com.huwei.sweetmusicplayer.data.api.ApiHost.Baidu.Companion.V1_TING
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.ArtistInfo
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.Lrc
import com.huwei.sweetmusicplayer.data.models.baidumusic.resp.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by huwei on 18-1-30.
 */
interface BaiduMusicService {
    companion object {
        const val SEARCH_CATALOGSUG = "baidu.ting.search.catalogSug"
        const val SONG_LRC = "baidu.ting.song.lry "
        const val SONG_PLAY = "baidu.ting.song.play"
        const val GET_SONGINFO = "baidu.ting.song.getInfos"
        const val GET_ARTISTINFO = "baidu.ting.artist.getinfo"    //获取歌手信息
        const val GET_ARTISTSONGLIST = "baidu.ting.artist.getSongList" //获取歌手的歌曲列表
        const val GET_ARTISTALUBMLIST = "baidu.ting.artist.getAlbumList"   //获取歌手的专辑列表;
        const val GET_ALBUMINFO = "baidu.ting.album.getAlbumInfo"
        const val QUERY_MERGE = "baidu.ting.search.merge"
        const val GET_PLAY_MV = "baidu.ting.mv.playMV"

        const val PAGESIZE = 20

        fun getDownloadUrlBySongId(songId: String): String {
            return "http://ting.baidu.com/data/music/links?songIds=" + songId
        }
    }

    @GET(V1_TING + "?method=" + SEARCH_CATALOGSUG)
    fun querySug(@Query("query") query: String): Observable<MusicSearchSugResp>

    @GET(V1_TING + "?method=" + QUERY_MERGE)
    fun queryMerge(@Query("query") query: String,
                   @Query("page_no") pageNo: Int,
                   @Query("page_size") pageSize: Int): Observable<QueryMergeResp>

    @GET(V1_TING + "?method=" + GET_ALBUMINFO)
    fun getAlbumInfo(@Query("album_id") albumId: String): Observable<AlbumDetailResp>

    @GET(V1_TING + "?method=" + SONG_LRC)
    fun queryLrc(@Query("songid") songId: String): Observable<Lrc>

    @GET(V1_TING + "?method=" + SONG_PLAY)
    fun querySong(@Query("songid") songId: String): Observable<SongPlayResp>

    @GET(V1_TING)
    fun getAlbumDetail(@Query("albumId") albumId: String,
                       @Query("type") method: String = "album"): Observable<SongPlayResp>

    @GET(V1_TING + "?method=" + GET_ARTISTINFO)
    fun getArtistInfo(@Query("tinguid") tinguid: String,
                      @Query("artistid") artistid: String): Observable<ArtistInfo>

    @GET(V1_TING + "?method=" + GET_ARTISTSONGLIST)
    fun getArtistSongList(@Query("tinguid") tinguid: String,
                          @Query("artistid") artistid: String,
                          @Query("offset") offset: Int,
                          @Query("limits") limits: Int = PAGESIZE)
            : Observable<ArtistSongListResp>

    @GET(V1_TING + "?method=" + GET_ARTISTALUBMLIST)
    fun getArtistAlbumList(@Query("tinguid") tinguid: String,
                           @Query("artistid") artistid: String,
                           @Query("offset") offset: Int,
                           @Query("limits") limits: Int = PAGESIZE)
            : Observable<ArtistAlbumListResp>

    @GET(V1_TING + "?method=" + GET_PLAY_MV)
    fun getPlayMv(@Query("song_id") songId: String): Observable<PlayMvResp>
}