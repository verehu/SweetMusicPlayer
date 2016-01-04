package com.huwei.sweetmusicplayer.baidumusic.po;

import android.os.Parcel;
import android.os.Parcelable;

import com.huwei.sweetmusicplayer.interfaces.IQueryReuslt;

/**
 * 百度音乐中的歌手
 * @author jerry
 * @version 4.6
 * @date 2015-11-19
 */
public class Artist implements IQueryReuslt,Parcelable {
    /**
     * artist_id : 73336538
     * author : <em>千古</em>留名
     * ting_uid : 110941978
     * avatar_middle :
     * album_num : 0
     * song_num : 4
     * country : 中国
     * artist_desc :
     * artist_source : yyr
     */

    public String artist_id;
    public String author;
    public String ting_uid;
    public String avatar_middle;
    public int album_num;
    public int song_num;
    public String country;
    public String artist_desc;
    public String artist_source;

    @Override
    public String getName() {
        return author;
    }

    @Override
    public QueryType getSearchResultType() {
        return QueryType.Artist;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.artist_id);
        dest.writeString(this.author);
        dest.writeString(this.ting_uid);
        dest.writeString(this.avatar_middle);
        dest.writeInt(this.album_num);
        dest.writeInt(this.song_num);
        dest.writeString(this.country);
        dest.writeString(this.artist_desc);
        dest.writeString(this.artist_source);
    }

    public Artist() {
    }

    protected Artist(Parcel in) {
        this.artist_id = in.readString();
        this.author = in.readString();
        this.ting_uid = in.readString();
        this.avatar_middle = in.readString();
        this.album_num = in.readInt();
        this.song_num = in.readInt();
        this.country = in.readString();
        this.artist_desc = in.readString();
        this.artist_source = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
