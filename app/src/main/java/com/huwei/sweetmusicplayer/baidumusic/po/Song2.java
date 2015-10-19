package com.huwei.sweetmusicplayer.baidumusic.po;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.huwei.sweetmusicplayer.SweetApplication;
import com.huwei.sweetmusicplayer.abstracts.AbstractMusic;
import com.huwei.sweetmusicplayer.interfaces.IQueryReuslt;
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 百度音乐API返回的Song
 *
 * @author Jayce
 * @date 2015/6/11
 */
public class Song2 extends AbstractMusic implements IQueryReuslt {

    public static final String TAG="Song";

    private String songid;
    private String songname;
    private String encrypted_songid;
    private String has_mv;
    private String yyr_artist;
    private String artistname;
    private String control;

    public Bitrate bitrate;
    public SongInfo songInfo;

    public static final Parcelable.Creator<Song2> CREATOR = new Parcelable.Creator<Song2>() {

        @Override
        public Song2 createFromParcel(Parcel source) {
            return new Song2(source);
        }

        @Override
        public Song2[] newArray(int size) {
            return new Song2[size];
        }

    };

    public Song2() {

    }

    public Song2(Parcel parcel) {
        songid = parcel.readString();
        songname = parcel.readString();
        encrypted_songid = parcel.readString();
        has_mv = parcel.readString();
        yyr_artist = parcel.readString();
        artistname = parcel.readString();
        control = parcel.readString();
        bitrate = parcel.readParcelable(Bitrate.class.getClassLoader());
        songInfo = parcel.readParcelable(SongInfo.class.getClassLoader());
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getEncrypted_songid() {
        return encrypted_songid;
    }

    public void setEncrypted_songid(String encrypted_songid) {
        this.encrypted_songid = encrypted_songid;
    }

    public String getHas_mv() {
        return has_mv;
    }

    public void setHas_mv(String has_mv) {
        this.has_mv = has_mv;
    }

    public String getYyr_artist() {
        return yyr_artist;
    }

    public void setYyr_artist(String yyr_artist) {
        this.yyr_artist = yyr_artist;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    @Override
    public String getName() {
        return songname;
    }

    @Override
    public QueryType getSearchResultType() {
        return QueryType.Song;
    }

    @Override
    public Uri getDataSoure() {
        String url = bitrate!=null?bitrate.getFile_link(): BaiduMusicUtil.getDownloadUrlBySongId(songid);
        return Uri.parse(url);
    }

    @Override
    public Integer getDuration() {
        return bitrate!=null?bitrate.getFile_duration()*1000:0;
    }

    @Override
    public MusicType getType() {
        return MusicType.Online;
    }

    @Override
    public String getTitle() {
        return songname;
    }

    @Override
    public String getArtist() {
        return artistname;
    }

    //返回""加载默认的图片
    public String getArtPic() {
        return Uri.parse(songInfo!=null?songInfo.getPic_small():"").toString();
    }

    @Override
    public void loadArtPic(final OnLoadListener loadListener) {
        ImageLoader imageLoader = SweetApplication.getImageLoader();
        imageLoader.loadImage(getArtPic(),new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                Log.i(TAG,"onLoadingComplete   --->uri:"+imageUri);

                if(loadListener!=null){
                    loadListener.onSuccessLoad(loadedImage);
                }
            }
        });
    }

    public boolean hasGetDetailInfo(){
        return bitrate!=null||songInfo!=null;
    }


    @Override
    public Song2 createFromParcel(Parcel source) {
        return new Song2(source);
    }

    @Override
    public Song2[] newArray(int size) {
        return new Song2[size];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songid);
        dest.writeString(songname);
        dest.writeString(encrypted_songid);
        dest.writeString(has_mv);
        dest.writeString(yyr_artist);
        dest.writeString(artistname);
        dest.writeString(control);
        dest.writeParcelable(bitrate,flags);
        dest.writeParcelable(songInfo,flags);
    }
}
