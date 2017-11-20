package com.huwei.sweetmusicplayer.business.baidumusic.resp;

import com.huwei.sweetmusicplayer.business.baidumusic.po.AlbumSug;
import com.huwei.sweetmusicplayer.business.baidumusic.po.ArtistSug;
import com.huwei.sweetmusicplayer.business.baidumusic.po.SongSug;

import java.util.List;

/**
 * 百度音乐API返回的搜索音乐的建议
 * @author Jayce
 * @date 2015/6/11
 */
public class MusicSearchSugResp extends BaseResp {

    /**
     * Json样例
     * {"song":[{"songid":"2103686","songname":"\u4f60\u662f\u6211\u7684\u773c","encrypted_songid":"35062019860855110bd3","has_mv":"1","yyr_artist":"0","artistname":"\u6797\u5ba5\u5609","control":"0000000000"}],"artist":[],"album":[],"order":"song","error_code":22000}
     */
    public List<SongSug> song;
    public List<ArtistSug> artist;
    public List<AlbumSug> album;
    public String order;
}
