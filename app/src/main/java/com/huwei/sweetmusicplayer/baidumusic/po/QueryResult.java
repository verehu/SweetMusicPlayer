package com.huwei.sweetmusicplayer.baidumusic.po;



import com.huwei.sweetmusicplayer.baidumusic.group.Album_info;
import com.huwei.sweetmusicplayer.baidumusic.group.Artist_info;
import com.huwei.sweetmusicplayer.baidumusic.group.Song_info;

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
    public int rqt_type;
    public Song_info song_info;
    public Album_info album_info;
    public Artist_info artist_info;
}
