package com.huwei.sweetmusicplayer.baidumusic.group;

import android.os.Parcel;
import android.os.Parcelable;

import com.huwei.sweetmusicplayer.baidumusic.po.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jayce
 * @date 2015/10/20
 */
public class Album_info implements Parcelable {
    public int total;
    public List<Album> album_list;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeList(this.album_list);
    }

    public Album_info() {
    }

    protected Album_info(Parcel in) {
        this.total = in.readInt();
        this.album_list = new ArrayList<Album>();
        in.readList(this.album_list, List.class.getClassLoader());
    }

    public static final Creator<Album_info> CREATOR = new Creator<Album_info>() {
        public Album_info createFromParcel(Parcel source) {
            return new Album_info(source);
        }

        public Album_info[] newArray(int size) {
            return new Album_info[size];
        }
    };
}
