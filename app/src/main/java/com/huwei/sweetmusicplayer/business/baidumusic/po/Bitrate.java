package com.huwei.sweetmusicplayer.business.baidumusic.po;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author jayce
 * @date 2015/08/24
 */
public class Bitrate implements Parcelable, Serializable {
    /**
     * json:
     "bitrate": {
        "file_bitrate": 128,
        "file_link": "http:\/\/file.qianqian.com\/\/data2\/music\/46633720\/46633720.mp3?xcode=e85b6dcdb87ba110539f0e82e788ec87",
        "file_extension": "mp3",
        "original": 0,
        "file_size": 4838611,
        "file_duration": 302,
        "show_link": "http:\/\/pan.baidu.com\/share\/link?shareid=222318411\u0026uk=1565275458",
        "song_file_id": 46633720,
        "replay_gain": "0.000000",
        "free": 1
     },
     */
    private int file_bitrate;
    private String file_link;
    private String file_extension;
    private int original;
    private long file_size;
    private int file_duration;  //s
    private String show_link;
    private long song_file_id;
    private String replay_gain;
    private int free;

    public Bitrate(Parcel parcel) {
        file_bitrate = parcel.readInt();
        file_link = parcel.readString();
        file_extension = parcel.readString();
        original = parcel.readInt();
        file_size = parcel.readLong();
        file_duration = parcel.readInt();
        show_link = parcel.readString();
        song_file_id = parcel.readLong();
        replay_gain = parcel.readString();
        free = parcel.readInt();
    }

    public int getFile_bitrate() {
        return file_bitrate;
    }

    public void setFile_bitrate(int file_bitrate) {
        this.file_bitrate = file_bitrate;
    }

    public String getFile_link() {
        return file_link;
    }

    public void setFile_link(String file_link) {
        this.file_link = file_link;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public int getFile_duration() {
        return file_duration;
    }

    public void setFile_duration(int file_duration) {
        this.file_duration = file_duration;
    }

    public String getShow_link() {
        return show_link;
    }

    public void setShow_link(String show_link) {
        this.show_link = show_link;
    }

    public long getSong_file_id() {
        return song_file_id;
    }

    public void setSong_file_id(long song_file_id) {
        this.song_file_id = song_file_id;
    }

    public String getReplay_gain() {
        return replay_gain;
    }

    public void setReplay_gain(String replay_gain) {
        this.replay_gain = replay_gain;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        file_bitrate = parcel.readInt();
//        file_link = parcel.readString();
//        file_extension = parcel.readString();
//        original = parcel.readInt();
//        file_size = parcel.readLong();
//        duration = parcel.readInt();
//        show_link = parcel.readString();
//        song_file_id = parcel.readLong();
//        replay_gain = parcel.readString();
//        free = parcel.readInt();

        dest.writeInt(file_bitrate);
        dest.writeString(file_link);
        dest.writeString(file_extension);
        dest.writeInt(original);
        dest.writeLong(file_size);
        dest.writeInt(file_duration);
        dest.writeString(show_link);
        dest.writeLong(song_file_id);
        dest.writeString(replay_gain);
        dest.writeInt(free);
    }

    public static final Parcelable.Creator<Bitrate> CREATOR = new Parcelable.Creator<Bitrate>()
    {
        public Bitrate createFromParcel(Parcel in)
        {
            return new Bitrate(in);
        }

        public Bitrate[] newArray(int size)
        {
            return new Bitrate[size];
        }
    };
}
