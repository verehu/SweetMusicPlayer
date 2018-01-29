package com.huwei.sweetmusicplayer.data.models.baidumusic.po;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 专辑详细信息
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

    public String album_id;
    public String author;
    public String title;
    public String publishcompany;
    public String prodcompany;
    public String country;
    public String language;
    public String songs_total;
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
    public String hot;
    public int favorites_num;
    public int recommend_num;
    public String artist_id;
    public String all_artist_id;
    public String pic_radio;
    public String pic_s500;
    public String pic_s1000;

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
