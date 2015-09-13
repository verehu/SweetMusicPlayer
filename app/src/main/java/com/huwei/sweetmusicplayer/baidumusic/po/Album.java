package com.huwei.sweetmusicplayer.baidumusic.po;

import com.huwei.sweetmusicplayer.interfaces.ISearchReuslt;

/**
 * 百度音乐API中的Album
 * @author Jayce
 * @date 2015/6/11
 */
public class Album implements ISearchReuslt {
    /**
     * Json样列
     * "album":[{"albumid":"183906","albumname":"\u4e03\u91cc\u9999(\u53f0\u6e7e\u6b63\u5f0f\u7248)","artistname":"\u5468\u6770\u4f26","artistpic":"http:\/\/a.hiphotos.baidu.com\/ting\/pic\/item\/3b292df5e0fe9925ff46084536a85edf8db17158.jpg"}]
     */
    private String albumid;
    private String albumname;
    private String artistname;
    private String artistpic;

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
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

    @Override
    public String getName() {
        return albumname;
    }

    @Override
    public SearchResultType getSearchResultType() {
        return SearchResultType.Album;
    }
}
