package com.huwei.sweetmusicplayer.baidumusic.po;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 专辑信息
 *
 * @author jayce
 * @date 2015/10/10
 */
public class AlbumInfo implements Parcelable {

    /**
     * albumInfo : {"album_id":"67909","author":"周杰伦","title":"七里香","publishcompany":"杰威尔JVR音乐有限公司","prodcompany":"","country":"港台","language":"国语","songs_total":"9","info":"周杰伦的新专辑在8月3日正式发行，这次引用了诗人席幕蓉名诗《七里香》作为新专辑名称，周杰伦以往每一次的专辑名称都给了歌迷许多想象空间，也给了大家许多惊叹号。这次也许并不令人惊喜。但是周杰伦自有说法：\u201c之所以要把新专辑定名为《七里香》，是因为对这一次专辑的音乐充满自信，希望大家能把注意力焦点放在音乐上，将话题回归到音乐上。 这张《七里香》仍是周杰伦与最佳拍档方文山合作的作品。在炎热的夏天听《七里香》，有一种如沐清风的凉爽","styles":"流行","style_id":"3","publishtime":"2004-08-03","artist_ting_uid":"7994","all_artist_ting_uid":null,"gender":"0","area":"1","pic_small":"http://musicdata.baidu.com/data2/pic/115430825/115430825.jpg","pic_big":"http://musicdata.baidu.com/data2/pic/115430815/115430815.jpg","hot":"","favorites_num":null,"recommend_num":null,"artist_id":"29","all_artist_id":"29","pic_radio":"http://musicdata.baidu.com/data2/pic/115430799/115430799.jpg","pic_s500":"http://musicdata.baidu.com/data2/pic/115430794/115430794.jpg","pic_s1000":"http://musicdata.baidu.com/data2/pic/115430787/115430787.jpg"}
     */


    /**
     * album_id : 67909
     * author : 周杰伦
     * title : 七里香
     * publishcompany : 杰威尔JVR音乐有限公司
     * prodcompany :
     * country : 港台
     * language : 国语
     * songs_total : 9
     * info : 周杰伦的新专辑在8月3日正式发行，这次引用了诗人席幕蓉名诗《七里香》作为新专辑名称，周杰伦以往每一次的专辑名称都给了歌迷许多想象空间，也给了大家许多惊叹号。这次也许并不令人惊喜。但是周杰伦自有说法：“之所以要把新专辑定名为《七里香》，是因为对这一次专辑的音乐充满自信，希望大家能把注意力焦点放在音乐上，将话题回归到音乐上。 这张《七里香》仍是周杰伦与最佳拍档方文山合作的作品。在炎热的夏天听《七里香》，有一种如沐清风的凉爽
     * styles : 流行
     * style_id : 3
     * publishtime : 2004-08-03
     * artist_ting_uid : 7994
     * all_artist_ting_uid : null
     * gender : 0
     * area : 1
     * pic_small : http://musicdata.baidu.com/data2/pic/115430825/115430825.jpg
     * pic_big : http://musicdata.baidu.com/data2/pic/115430815/115430815.jpg
     * hot :
     * favorites_num : null
     * recommend_num : null
     * artist_id : 29
     * all_artist_id : 29
     * pic_radio : http://musicdata.baidu.com/data2/pic/115430799/115430799.jpg
     * pic_s500 : http://musicdata.baidu.com/data2/pic/115430794/115430794.jpg
     * pic_s1000 : http://musicdata.baidu.com/data2/pic/115430787/115430787.jpg
     */

    private String album_id;
    private String author;
    private String title;
    private String publishcompany;
    private String prodcompany;
    private String country;
    private String language;
    private String songs_total;
    private String info;
    private String styles;
    private String style_id;
    private String publishtime;
    private String artist_ting_uid;
    private String all_artist_ting_uid;
    private String gender;
    private String area;
    private String pic_small;
    private String pic_big;
    private String hot;
    private int favorites_num;
    private int recommend_num;
    private String artist_id;
    private String all_artist_id;
    private String pic_radio;
    private String pic_s500;
    private String pic_s1000;


    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishcompany(String publishcompany) {
        this.publishcompany = publishcompany;
    }

    public void setProdcompany(String prodcompany) {
        this.prodcompany = prodcompany;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSongs_total(String songs_total) {
        this.songs_total = songs_total;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    public void setStyle_id(String style_id) {
        this.style_id = style_id;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public void setArtist_ting_uid(String artist_ting_uid) {
        this.artist_ting_uid = artist_ting_uid;
    }

    public void setAll_artist_ting_uid(String all_artist_ting_uid) {
        this.all_artist_ting_uid = all_artist_ting_uid;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setPic_small(String pic_small) {
        this.pic_small = pic_small;
    }

    public void setPic_big(String pic_big) {
        this.pic_big = pic_big;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public void setFavorites_num(int favorites_num) {
        this.favorites_num = favorites_num;
    }

    public void setRecommend_num(int recommend_num) {
        this.recommend_num = recommend_num;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public void setAll_artist_id(String all_artist_id) {
        this.all_artist_id = all_artist_id;
    }

    public void setPic_radio(String pic_radio) {
        this.pic_radio = pic_radio;
    }

    public void setPic_s500(String pic_s500) {
        this.pic_s500 = pic_s500;
    }

    public void setPic_s1000(String pic_s1000) {
        this.pic_s1000 = pic_s1000;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishcompany() {
        return publishcompany;
    }

    public String getProdcompany() {
        return prodcompany;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public String getSongs_total() {
        return songs_total;
    }

    public String getInfo() {
        return info;
    }

    public String getStyles() {
        return styles;
    }

    public String getStyle_id() {
        return style_id;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public String getArtist_ting_uid() {
        return artist_ting_uid;
    }

    public String getAll_artist_ting_uid() {
        return all_artist_ting_uid;
    }

    public String getGender() {
        return gender;
    }

    public String getArea() {
        return area;
    }

    public String getPic_small() {
        return pic_small;
    }

    public String getPic_big() {
        return pic_big;
    }

    public String getHot() {
        return hot;
    }

    public int getFavorites_num() {
        return favorites_num;
    }

    public int getRecommend_num() {
        return recommend_num;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public String getAll_artist_id() {
        return all_artist_id;
    }

    public String getPic_radio() {
        return pic_radio;
    }

    public String getPic_s500() {
        return pic_s500;
    }

    public String getPic_s1000() {
        return pic_s1000;
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
        dest.writeString(this.songs_total);
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
        dest.writeString(this.hot);
        dest.writeInt(this.favorites_num);
        dest.writeInt(this.recommend_num);
        dest.writeString(this.artist_id);
        dest.writeString(this.all_artist_id);
        dest.writeString(this.pic_radio);
        dest.writeString(this.pic_s500);
        dest.writeString(this.pic_s1000);
    }

    public AlbumInfo() {
    }

    protected AlbumInfo(Parcel in) {
        this.album_id = in.readString();
        this.author = in.readString();
        this.title = in.readString();
        this.publishcompany = in.readString();
        this.prodcompany = in.readString();
        this.country = in.readString();
        this.language = in.readString();
        this.songs_total = in.readString();
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
        this.hot = in.readString();
        this.favorites_num = in.readInt();
        this.recommend_num = in.readInt();
        this.artist_id = in.readString();
        this.all_artist_id = in.readString();
        this.pic_radio = in.readString();
        this.pic_s500 = in.readString();
        this.pic_s1000 = in.readString();
    }

    public static final Parcelable.Creator<AlbumInfo> CREATOR = new Parcelable.Creator<AlbumInfo>() {
        public AlbumInfo createFromParcel(Parcel source) {
            return new AlbumInfo(source);
        }

        public AlbumInfo[] newArray(int size) {
            return new AlbumInfo[size];
        }
    };
}
