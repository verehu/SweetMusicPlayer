package com.huwei.sweetmusicplayer.baidumusic.resp;

import com.huwei.sweetmusicplayer.baidumusic.po.Song;

import java.util.List;

/**
 * @author jerry
 * @date 2015-12-22
 */
public class ArtistSongListResp extends BaseResp {

    /**
     * songnums : 64
     * havemore : 0
     */
    public List<Song> songlist;
    public String songnums;
    public int havemore;

    public boolean hasmore(){
        return havemore == 1;
    }
}
