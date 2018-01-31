package com.huwei.sweetmusicplayer.data.models.baidumusic.po;

import com.huwei.sweetmusicplayer.business.interfaces.IQueryReuslt;

/**
 * 百度音乐API中的Artist
 * @author Jayce
 * @date 2015/6/11
 */
public class ArtistSug implements IQueryReuslt {
    /**
     * Json 样例
     *  {"artistid":"1454","artistname":"\u6797\u5ba5\u5609","artistpic":"http:\/\/musicdata.baidu.com\/data2\/pic\/116050828\/116050828.jpg","yyr_artist":"0"}
     */
    private String artistid;
    private String artistname;
    private String artistpic;
    private String yyr_artist;

    public String getArtistid() {
        return artistid;
    }

    public void setArtistid(String artistid) {
        this.artistid = artistid;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getArtistpic() {
        return artistpic;
    }

    public void setArtistpic(String artistpic) {
        this.artistpic = artistpic;
    }

    public String getYyr_artist() {
        return yyr_artist;
    }

    public void setYyr_artist(String yyr_artist) {
        this.yyr_artist = yyr_artist;
    }

    @Override
    public String getName() {
        return artistname;
    }

    @Override
    public QueryType getSearchResultType() {
        return QueryType.Artist;
    }
}
