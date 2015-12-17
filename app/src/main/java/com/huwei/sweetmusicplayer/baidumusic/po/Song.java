package com.huwei.sweetmusicplayer.baidumusic.po;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.util.Log;
import android.view.View;

import com.huwei.sweetmusicplayer.SweetApplication;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.interfaces.IQueryReuslt;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jayce
 * @date 2015/10/20
 */
public class Song extends AbstractMusic implements IQueryReuslt {
    public static final String TAG="Song";

    /**
     * content :
     * copy_type : 1
     * toneid : 0
     * info :
     * all_rate : 320,128,flac,256,192,64,24
     * resource_type : 2
     * relate_status : 0
     * has_mv_mobile : 1
     * song_id : 18401298
     * title : 七里香
     * ting_uid : 7994
     * author : 周杰伦
     * album_id : 18394691
     * album_title : 第五届百事音乐风云榜
     * is_first_publish : 0
     * havehigh : 2
     * charge : 0
     * has_mv : 0
     * learn : 0
     * song_source : web
     * piao_id : 0
     * korean_bb_song : 0
     * resource_type_ext : 0
     * artist_id : 29
     * all_artist_id : 29
     * lrclink : http://musicdata.baidu.com/data2/lrc/65094964/%E4%B8%83%E9%87%8C%E9%A6%99.lrc
     * data_source : 0
     * cluster_id : 93119037
     */

    public String content;
    public String copy_type;
    public String toneid;
    public String info;
    public String all_rate;
    public int resource_type;
    public int relate_status;
    public int has_mv_mobile;
    public String song_id;
    public String title;
    public String ting_uid;
    public String author;
    public String album_id;
    public String album_title;
    public int is_first_publish;
    public int havehigh;
    public int charge;
    public int has_mv;
    public int learn;
    public String song_source;
    public String piao_id;
    public String korean_bb_song;
    public String resource_type_ext;
    public String artist_id;
    public String all_artist_id;
    public String lrclink;
    public int data_source;
    public int cluster_id;

    public Bitrate bitrate;
    public SongInfo songinfo;

    public Song() {
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public QueryType getSearchResultType() {
        return QueryType.Song;
    }

    @Override
    public Uri getDataSoure() {
        String url = bitrate!=null?bitrate.getFile_link(): BaiduMusicUtil.getDownloadUrlBySongId(song_id);
        return Uri.parse(url);
    }

    @Override
    public Integer getDuration() {
        return bitrate != null ? bitrate.getFile_duration() * 1000 : 0;
    }

    @Override
    public MusicType getType() {
        return MusicType.Online;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getArtist() {
        return author;
    }

    @Override
    public void loadArtPic(PicSizeType picSizeType, OnLoadListener loadListener) {
        String uri="";
        switch (picSizeType){
            case SMALL:
                uri = Uri.parse(songinfo !=null? songinfo.getPic_small():"").toString();
                break;
            case BIG:
                uri = Uri.parse(songinfo !=null? songinfo.getPic_big():"").toString();
                break;
            case PREIUM:
                uri = Uri.parse(songinfo !=null? songinfo.getPic_premium():"").toString();
                break;
            case HUGE:
                uri = Uri.parse(songinfo !=null? songinfo.getPic_huge():"").toString();
                break;
        }
        loadArtPic(uri,loadListener);
    }

    /**
     * 默认加载samll
     * @param loadListener
     */
    @Override
    public void loadArtPic(final OnLoadListener loadListener) {
        loadArtPic(PicSizeType.SMALL,loadListener);
    }

    private void loadArtPic(String artUri,final OnLoadListener loadListener){
        Log.i(TAG, "loadArtPic   --->uri:" + artUri);
        ImageLoader imageLoader = SweetApplication.getImageLoader();
        imageLoader.loadImage(artUri,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);


                if(loadListener!=null && StringUtils.isNotEmpty(imageUri)){
                    Log.i(TAG, "onSuccessLoad   --->uri:" + imageUri);
                    loadListener.onSuccessLoad(loadedImage);
                }
            }
        });
    }



    public boolean hasGetDetailInfo(){
        return bitrate!=null|| songinfo !=null;
    }


    public Song createFromParcel(Parcel source) {
        return new Song(source);
    }

    public Song[] newArray(int size) {
        return new Song[size];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.copy_type);
        dest.writeString(this.toneid);
        dest.writeString(this.info);
        dest.writeString(this.all_rate);
        dest.writeInt(this.resource_type);
        dest.writeInt(this.relate_status);
        dest.writeInt(this.has_mv_mobile);
        dest.writeString(this.song_id);
        dest.writeString(this.title);
        dest.writeString(this.ting_uid);
        dest.writeString(this.author);
        dest.writeString(this.album_id);
        dest.writeString(this.album_title);
        dest.writeInt(this.is_first_publish);
        dest.writeInt(this.havehigh);
        dest.writeInt(this.charge);
        dest.writeInt(this.has_mv);
        dest.writeInt(this.learn);
        dest.writeString(this.song_source);
        dest.writeString(this.piao_id);
        dest.writeString(this.korean_bb_song);
        dest.writeString(this.resource_type_ext);
        dest.writeString(this.artist_id);
        dest.writeString(this.all_artist_id);
        dest.writeString(this.lrclink);
        dest.writeInt(this.data_source);
        dest.writeInt(this.cluster_id);
        dest.writeParcelable(this.bitrate, 0);
        dest.writeParcelable(this.songinfo, 0);
    }

    protected Song(Parcel in) {
        this.content = in.readString();
        this.copy_type = in.readString();
        this.toneid = in.readString();
        this.info = in.readString();
        this.all_rate = in.readString();
        this.resource_type = in.readInt();
        this.relate_status = in.readInt();
        this.has_mv_mobile = in.readInt();
        this.song_id = in.readString();
        this.title = in.readString();
        this.ting_uid = in.readString();
        this.author = in.readString();
        this.album_id = in.readString();
        this.album_title = in.readString();
        this.is_first_publish = in.readInt();
        this.havehigh = in.readInt();
        this.charge = in.readInt();
        this.has_mv = in.readInt();
        this.learn = in.readInt();
        this.song_source = in.readString();
        this.piao_id = in.readString();
        this.korean_bb_song = in.readString();
        this.resource_type_ext = in.readString();
        this.artist_id = in.readString();
        this.all_artist_id = in.readString();
        this.lrclink = in.readString();
        this.data_source = in.readInt();
        this.cluster_id = in.readInt();
        this.bitrate = in.readParcelable(Bitrate.class.getClassLoader());
        this.songinfo = in.readParcelable(SongInfo.class.getClassLoader());
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public static List<AbstractMusic> getAbstractMusicList(List<Song> songList){
        List<AbstractMusic> list = new ArrayList<>();
        for(Song song:songList){
            list.add(song);
        }
        return list;
    }
}
