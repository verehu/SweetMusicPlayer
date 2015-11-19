package com.huwei.sweetmusicplayer.baidumusic.po;

import com.huwei.sweetmusicplayer.interfaces.IQueryReuslt;

/**
 * 百度音乐中的歌手
 * @author jerry
 * @version 4.6
 * @date 2015-11-19
 */
public class Artist implements IQueryReuslt {
    /**
     * artist_id : 73336538
     * author : <em>千古</em>留名
     * ting_uid : 110941978
     * avatar_middle :
     * album_num : 0
     * song_num : 4
     * country : 中国
     * artist_desc :
     * artist_source : yyr
     */

    public String artist_id;
    public String author;
    public String ting_uid;
    public String avatar_middle;
    public int album_num;
    public int song_num;
    public String country;
    public String artist_desc;
    public String artist_source;

    @Override
    public String getName() {
        return author;
    }

    @Override
    public QueryType getSearchResultType() {
        return QueryType.Artist;
    }
}
