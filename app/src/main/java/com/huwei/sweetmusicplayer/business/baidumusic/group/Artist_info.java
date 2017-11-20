package com.huwei.sweetmusicplayer.business.baidumusic.group;

import android.os.Parcel;
import android.os.Parcelable;

import com.huwei.sweetmusicplayer.business.baidumusic.po.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jerry
 * @version 4.6
 * @date 2015-11-19
 */
public class Artist_info implements Parcelable {
    public int total;
    public List<Artist> artist_list;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeList(this.artist_list);
    }

    public Artist_info() {
    }

    protected Artist_info(Parcel in) {
        this.total = in.readInt();
        this.artist_list = new ArrayList<Artist>();
        in.readList(this.artist_list, List.class.getClassLoader());
    }

    public static final Creator<Artist_info> CREATOR = new Creator<Artist_info>() {
        public Artist_info createFromParcel(Parcel source) {
            return new Artist_info(source);
        }

        public Artist_info[] newArray(int size) {
            return new Artist_info[size];
        }
    };
}
