package com.huwei.sweetmusicplayer.baidumusic.po;

import java.util.List;

/**
 * @author jerry
 * @date 2015-09-13
 */
public class AlbumDetail {
    /**
     *  albumId: "67909"
        albumName: "七里香"
        albumPicSmall: "http://musicdata.baidu.com/data2/pic/115430825/115430825.jpg"
        artistId: "29"
        artistName: "周杰伦"
        songIdList: [23744596, 271627, 274085, 260390, 3451498, 3451496, 272420, 3451500, 290466]
     */
    private String albumId;
    private String albumName;
    private String albumPicSmall;
    private String artistId;
    private String artistName;
    private List<String> songIdList;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumPicSmall() {
        return albumPicSmall;
    }

    public void setAlbumPicSmall(String albumPicSmall) {
        this.albumPicSmall = albumPicSmall;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<String> getSongIdList() {
        return songIdList;
    }

    public void setSongIdList(List<String> songIdList) {
        this.songIdList = songIdList;
    }
}
