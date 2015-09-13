package com.huwei.sweetmusicplayer.baidumusic.resp;

import com.huwei.sweetmusicplayer.baidumusic.po.Album;
import com.huwei.sweetmusicplayer.baidumusic.po.Artist;
import com.huwei.sweetmusicplayer.baidumusic.po.Song;
import com.huwei.sweetmusicplayer.baidumusic.resp.BaseResp;

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
    private List<Song> song;
    private List<Artist> artist;
    private List<Album> album;
    private String order;


    public List<Song> getSong() {
        return song;
    }

    public void setSong(List<Song> song) {
        this.song = song;
    }

    public List<Artist> getArtist() {
        return artist;
    }

    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
