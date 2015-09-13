package com.huwei.sweetmusicplayer.baidumusic.po;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author jayce
 * @date 2015/08/24
 */
public class SongInfo implements Parcelable {
    /**
     * json:
     "songinfo": {
     "artist_id": "50804",
     "all_artist_id": "50804",
     "album_no": "0",
     "pic_big": "",
     "pic_small": "",
     "relate_status": "1",
     "resource_type": "2",
     "copy_type": "1",
     "lrclink": "http:\/\/musicdata.baidu.com\/data2\/lrc\/246652140\/%E4%B8%83%E9%87%8C%E9%A6%99%E5%A5%B3%E7%94%9F%E7%89%88.lrc",
     "pic_radio": "",
     "toneid": "0",
     "all_rate": "64",
     "play_type": "",
        "has_mv_mobile": 0,
        "pic_premium": "",
        "pic_huge": "",
        "resource_type_ext": "0",
        "bitrate_fee": "{\u00220\u0022:\u00220|0\u0022,\u00221\u0022:\u00220|0\u0022}",
        "song_id": "65278823",
        "title": "\u4e03\u91cc\u9999\u5973\u751f\u7248",
        "ting_uid": "88010744",
        "author": "\u4f5a\u540d",
        "album_id": "0",
        "album_title": "",
        "is_first_publish": 0,
        "havehigh": 0,
        "charge": 0,
        "has_mv": 0,
        "learn": 0,
        "song_source": "web",
        "piao_id": "0",
        "korean_bb_song": "0",
        "special_type": 0
     }
     */

    private String artist_id;
    private String all_artist_id;
    private String album_no;
    private String pic_big;
    private String pic_small;
    private String relate_status;
    private String resource_type;
    private String copy_type;
    private String lrclink;
    private String pic_radio;
    private String toneid;
    private String all_rate;
    private String play_type;
    private int has_mv_mobile;
    private String pic_premium;
    private String pic_huge;
    private String resource_type_ext;
    private String bitrate_fee;
    private String song_id;
    private String title;
    private String ting_uid;
    private String author;
    private String album_id;
    private String album_title;
    private int is_first_publish;
    private int havehigh;
    private int charge;
    private int has_mv;
    private int learn;
    private String song_source;
    private String piao_id;
    private String korean_bb_song;
    private int special_type;

    public SongInfo(Parcel parcel) {
        artist_id = parcel.readString();
        all_artist_id = parcel.readString();
        album_no = parcel.readString();
        pic_big = parcel.readString();
        pic_small = parcel.readString();
        relate_status = parcel.readString();
        resource_type = parcel.readString();
        copy_type = parcel.readString();
        lrclink = parcel.readString();
        pic_radio = parcel.readString();
        toneid = parcel.readString();
        all_rate = parcel.readString();
        play_type = parcel.readString();
        has_mv_mobile = parcel.readInt();
        pic_premium = parcel.readString();
        pic_huge = parcel.readString();
        resource_type_ext = parcel.readString();
        bitrate_fee = parcel.readString();
        song_id = parcel.readString();
        title = parcel.readString();
        ting_uid = parcel.readString();
        author = parcel.readString();
        album_id = parcel.readString();
        album_title = parcel.readString();
        is_first_publish = parcel.readInt();
        havehigh = parcel.readInt();
        charge = parcel.readInt();
        has_mv = parcel.readInt();
        learn = parcel.readInt();
        song_source = parcel.readString();
        piao_id = parcel.readString();
        korean_bb_song = parcel.readString();
        special_type = parcel.readInt();
    }

    public String getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getAll_artist_id() {
        return all_artist_id;
    }

    public void setAll_artist_id(String all_artist_id) {
        this.all_artist_id = all_artist_id;
    }

    public String getAlbum_no() {
        return album_no;
    }

    public void setAlbum_no(String album_no) {
        this.album_no = album_no;
    }

    public String getPic_big() {
        return pic_big;
    }

    public void setPic_big(String pic_big) {
        this.pic_big = pic_big;
    }

    public String getPic_small() {
        return pic_small;
    }

    public void setPic_small(String pic_small) {
        this.pic_small = pic_small;
    }

    public String getRelate_status() {
        return relate_status;
    }

    public void setRelate_status(String relate_status) {
        this.relate_status = relate_status;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getCopy_type() {
        return copy_type;
    }

    public void setCopy_type(String copy_type) {
        this.copy_type = copy_type;
    }

    public String getLrclink() {
        return lrclink;
    }

    public void setLrclink(String lrclink) {
        this.lrclink = lrclink;
    }

    public String getPic_radio() {
        return pic_radio;
    }

    public void setPic_radio(String pic_radio) {
        this.pic_radio = pic_radio;
    }

    public String getToneid() {
        return toneid;
    }

    public void setToneid(String toneid) {
        this.toneid = toneid;
    }

    public String getAll_rate() {
        return all_rate;
    }

    public void setAll_rate(String all_rate) {
        this.all_rate = all_rate;
    }

    public String getPlay_type() {
        return play_type;
    }

    public void setPlay_type(String play_type) {
        this.play_type = play_type;
    }

    public int getHas_mv_mobile() {
        return has_mv_mobile;
    }

    public void setHas_mv_mobile(int has_mv_mobile) {
        this.has_mv_mobile = has_mv_mobile;
    }

    public String getPic_premium() {
        return pic_premium;
    }

    public void setPic_premium(String pic_premium) {
        this.pic_premium = pic_premium;
    }

    public String getPic_huge() {
        return pic_huge;
    }

    public void setPic_huge(String pic_huge) {
        this.pic_huge = pic_huge;
    }

    public String getResource_type_ext() {
        return resource_type_ext;
    }

    public void setResource_type_ext(String resource_type_ext) {
        this.resource_type_ext = resource_type_ext;
    }

    public String getBitrate_fee() {
        return bitrate_fee;
    }

    public void setBitrate_fee(String bitrate_fee) {
        this.bitrate_fee = bitrate_fee;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTing_uid() {
        return ting_uid;
    }

    public void setTing_uid(String ting_uid) {
        this.ting_uid = ting_uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public int getIs_first_publish() {
        return is_first_publish;
    }

    public void setIs_first_publish(int is_first_publish) {
        this.is_first_publish = is_first_publish;
    }

    public int getHavehigh() {
        return havehigh;
    }

    public void setHavehigh(int havehigh) {
        this.havehigh = havehigh;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getHas_mv() {
        return has_mv;
    }

    public void setHas_mv(int has_mv) {
        this.has_mv = has_mv;
    }

    public int getLearn() {
        return learn;
    }

    public void setLearn(int learn) {
        this.learn = learn;
    }

    public String getSong_source() {
        return song_source;
    }

    public void setSong_source(String song_source) {
        this.song_source = song_source;
    }

    public String getPiao_id() {
        return piao_id;
    }

    public void setPiao_id(String piao_id) {
        this.piao_id = piao_id;
    }

    public String getKorean_bb_song() {
        return korean_bb_song;
    }

    public void setKorean_bb_song(String korean_bb_song) {
        this.korean_bb_song = korean_bb_song;
    }

    public int getSpecial_type() {
        return special_type;
    }

    public void setSpecial_type(int special_type) {
        this.special_type = special_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artist_id);
        dest.writeString(all_artist_id);
        dest.writeString(album_no);
        dest.writeString(pic_big);
        dest.writeString(pic_small);
        dest.writeString(relate_status);
        dest.writeString(resource_type);
        dest.writeString(copy_type);
        dest.writeString(lrclink);
        dest.writeString(pic_radio);
        dest.writeString(toneid);
        dest.writeString(all_rate);
        dest.writeString(play_type);
        dest.writeInt(has_mv_mobile);
        dest.writeString(pic_premium);
        dest.writeString(pic_huge);
        dest.writeString(resource_type_ext);
        dest.writeString(bitrate_fee);
        dest.writeString(song_id);
        dest.writeString(title);
        dest.writeString(ting_uid);
        dest.writeString(author);
        dest.writeString(album_id);
        dest.writeString(album_title);
        dest.writeInt(is_first_publish);
        dest.writeInt(havehigh);
        dest.writeInt(charge);
        dest.writeInt(has_mv);
        dest.writeInt(learn);
        dest.writeString(song_source);
        dest.writeString(piao_id);
        dest.writeString(korean_bb_song);
        dest.writeInt(special_type);
    }

    public static final Parcelable.Creator<SongInfo> CREATOR = new Parcelable.Creator<SongInfo>()
    {
        public SongInfo createFromParcel(Parcel in)
        {
            return new SongInfo(in);
        }

        public SongInfo[] newArray(int size)
        {
            return new SongInfo[size];
        }
    };
}
