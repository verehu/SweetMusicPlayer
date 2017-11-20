package com.huwei.sweetmusicplayer.business.baidumusic.po;

import android.os.Parcel;
import android.os.Parcelable;

import com.huwei.sweetmusicplayer.business.interfaces.IQueryReuslt;

/**
 * 专辑简略信息
 * @author jayce
 * @date 2015/10/20
 */
public class Album implements IQueryReuslt, Parcelable {


    /**
     * album_id : 17776914
     * author : 周杰伦
     * title : Partners 拍档
     * publishcompany : 阿尔发
     * prodcompany :
     * country : 港台
     * language : 国语
     * songs_total : 2
     * info :
     * styles : 流行
     * style_id : null
     * publishtime : 2002-04-01
     * artist_ting_uid : 7994
     * all_artist_ting_uid : null
     * gender : null
     * area : null
     * pic_small : http://musicdata.baidu.com/data2/pic/7d0318e550649dc3fe66f98e01f65d59/253166632/253166632.jpg
     * pic_big : http://musicdata.baidu.com/data2/pic/93db1a8b75a90673bf55677ae1dd4ee4/253166466/253166466.jpg
     * hot : 80020
     * favorites_num : null
     * recommend_num : null
     * artist_id : 29
     * all_artist_id : 29
     * pic_radio : http://musicdata.baidu.com/data2/pic/84cee906843e4c2b8ea16b5d3b656de4/253166963/253166963.jpg
     * pic_s180 : http://musicdata.baidu.com/data2/pic/94c9752a88d9b79c349ef390d5b0f129/253167311/253167311.jpg
     */

    public String album_id;
    public String author;
    public String title;
    public String publishcompany;
    public String prodcompany;
    public String country;
    public String language;
    public int songs_total;
    public String info;
    public String styles;
    public String style_id;
    public String publishtime;
    public String artist_ting_uid;
    public String all_artist_ting_uid;
    public String gender;
    public String area;
    public String pic_small;
    public String pic_big;
    public int hot;
    public int favorites_num;
    public int recommend_num;
    public String artist_id;
    public String all_artist_id;
    public String pic_radio;
    public String pic_s180;

    @Override
    public String getName() {
        return title;
    }

    @Override
    public QueryType getSearchResultType() {
        return QueryType.Album;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.album_id);
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeString(this.publishcompany);
        dest.writeString(this.prodcompany);
        dest.writeString(this.country);
        dest.writeString(this.language);
        dest.writeInt(this.songs_total);
        dest.writeString(this.info);
        dest.writeString(this.styles);
        dest.writeString(this.style_id);
        dest.writeString(this.publishtime);
        dest.writeString(this.artist_ting_uid);
        dest.writeString(this.all_artist_ting_uid);
        dest.writeString(this.gender);
        dest.writeString(this.area);
        dest.writeString(this.pic_small);
        dest.writeString(this.pic_big);
        dest.writeInt(this.hot);
        dest.writeInt(this.favorites_num);
        dest.writeInt(this.recommend_num);
        dest.writeString(this.artist_id);
        dest.writeString(this.all_artist_id);
        dest.writeString(this.pic_radio);
        dest.writeString(this.pic_s180);
    }

    public Album() {
    }

    protected Album(Parcel in) {
        this.album_id = in.readString();
        this.author = in.readString();
        this.title = in.readString();
        this.publishcompany = in.readString();
        this.prodcompany = in.readString();
        this.country = in.readString();
        this.language = in.readString();
        this.songs_total = in.readInt();
        this.info = in.readString();
        this.styles = in.readString();
        this.style_id = in.readString();
        this.publishtime = in.readString();
        this.artist_ting_uid = in.readString();
        this.all_artist_ting_uid = in.readString();
        this.gender = in.readString();
        this.area = in.readString();
        this.pic_small = in.readString();
        this.pic_big = in.readString();
        this.hot = in.readInt();
        this.favorites_num = in.readInt();
        this.recommend_num = in.readInt();
        this.artist_id = in.readString();
        this.all_artist_id = in.readString();
        this.pic_radio = in.readString();
        this.pic_s180 = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
