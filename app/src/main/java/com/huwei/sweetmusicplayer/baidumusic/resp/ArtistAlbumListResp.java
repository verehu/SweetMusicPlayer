package com.huwei.sweetmusicplayer.baidumusic.resp;

import com.huwei.sweetmusicplayer.baidumusic.po.Album;

import java.util.List;

/**
 * @author jerry
 * @date 2016/01/04
 */
public class ArtistAlbumListResp extends BaseResp {

    public List<Album> albumlist;
    public int albumnums;
    public int havemore;

    public boolean hasmore() {
        return havemore == 1;
    }
}
