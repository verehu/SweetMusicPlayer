package com.huwei.sweetmusicplayer.data.models.baidumusic.po;



import com.huwei.sweetmusicplayer.data.models.baidumusic.group.Album_info;
import com.huwei.sweetmusicplayer.data.models.baidumusic.group.Artist_info;
import com.huwei.sweetmusicplayer.data.models.baidumusic.group.Song_info;

/**
 * @author jayce
 * @date 2015/10/20
 */
public class QueryResult {

    /**
     * query : 七里香
     * syn_words :
     * rqt_type : 1
     */

    public String query;
    public String syn_words;
    public int rqt_type;    //专辑3 歌手2 歌曲1
    public Song_info song_info;
    public Album_info album_info;
    public Artist_info artist_info;
}
