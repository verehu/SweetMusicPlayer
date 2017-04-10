package com.huwei.sweetmusicplayer.baidumusic.resp;

import android.os.Parcel;
import android.os.Parcelable;

import com.huwei.sweetmusicplayer.baidumusic.po.AlbumInfo;
import com.huwei.sweetmusicplayer.baidumusic.po.SongInfo;

import java.util.List;

/**
 * @author jayce
 * @date 2015/10/10
 */
public class GetAlbumInfoResp implements Parcelable {
    private AlbumInfo albumInfo;
    private List<SongInfo> songlist;

    public AlbumInfo getAlbumInfo() {
        return albumInfo;
    }

    public void setAlbumInfo(AlbumInfo albumInfo) {
        this.albumInfo = albumInfo;
    }

    public List<SongInfo> getSonglist() {
        return songlist;
    }

    public void setSonglist(List<SongInfo> songlist) {
        this.songlist = songlist;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.albumInfo, 0);
        dest.writeTypedList(songlist);
    }

    public GetAlbumInfoResp() {
    }

    protected GetAlbumInfoResp(Parcel in) {
        this.albumInfo = in.readParcelable(AlbumInfo.class.getClassLoader());
        this.songlist = in.createTypedArrayList(SongInfo.CREATOR);
    }

    public static final Parcelable.Creator<GetAlbumInfoResp> CREATOR = new Parcelable.Creator<GetAlbumInfoResp>() {
        public GetAlbumInfoResp createFromParcel(Parcel source) {
            return new GetAlbumInfoResp(source);
        }

        public GetAlbumInfoResp[] newArray(int size) {
            return new GetAlbumInfoResp[size];
        }
    };
}
