package com.huwei.sweetmusicplayer.baidumusic.resp;

import com.huwei.sweetmusicplayer.baidumusic.po.Album2;
import com.huwei.sweetmusicplayer.baidumusic.po.Artist;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;

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
    public List<Song> song;
    public List<Artist> artist;
    public List<Album2> album;
    public String order;
}
