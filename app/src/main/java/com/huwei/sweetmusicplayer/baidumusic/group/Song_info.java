package com.huwei.sweetmusicplayer.baidumusic.group;

import android.os.Parcel;
import android.os.Parcelable;

import com.huwei.sweetmusicplayer.baidumusic.po.Song;

import java.util.List;

/**
 * @author jayce
 * @date 2015/10/20
 */
public class Song_info implements Parcelable{
    public int total;
    public List<Song> song_list;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeTypedList(song_list);
    }

    public Song_info() {
    }

    protected Song_info(Parcel in) {
        this.total = in.readInt();
        this.song_list = in.createTypedArrayList(Song.CREATOR);
    }

    public static final Creator<Song_info> CREATOR = new Creator<Song_info>() {
        public Song_info createFromParcel(Parcel source) {
            return new Song_info(source);
        }

        public Song_info[] newArray(int size) {
            return new Song_info[size];
        }
    };
}
