package com.huwei.sweetmusicplayer.data.models.baidumusic.resp;

import com.huwei.sweetmusicplayer.data.models.baidumusic.po.AlbumInfo;
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.Song;

import java.util.List;

/**
 * @author jerry
 * @date 2015-09-13
 */
public class AlbumDetailResp extends BaseResp {
    public AlbumInfo albumInfo;
    public List<Song> songlist;
}
