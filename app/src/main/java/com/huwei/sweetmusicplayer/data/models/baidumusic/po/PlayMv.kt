package com.huwei.sweetmusicplayer.data.models.baidumusic.po

import com.google.gson.annotations.SerializedName


/**
 * Created by huwei on 18-1-31.
 */

data class PlayMv(
        @SerializedName("video_info") val videoInfo: VideoInfo,
        @SerializedName("files") val files: Files,
        @SerializedName("min_definition") val minDefinition: String, //31
        @SerializedName("max_definition") val maxDefinition: String, //31
        @SerializedName("mv_info") val mvInfo: MvInfo,
        @SerializedName("share_url") val shareUrl: String //http://music.baidu.com/cms/webview/sharevideo?video_id=334828094
) {

    data class VideoInfo(
            @SerializedName("video_id") val videoId: String, //334828095
            @SerializedName("mv_id") val mvId: String, //334828094
            @SerializedName("provider") val provider: String, //12
            @SerializedName("sourcepath") val sourcepath: Any, //null
            @SerializedName("thumbnail") val thumbnail: String, //http://qukufile2.qianqian.com/data2/pic/f02419355d893358a74b3b11aca84d02/334828100/334828100.jpg@s_0,w_160,h_90
            @SerializedName("thumbnail2") val thumbnail2: String, //http://qukufile2.qianqian.com/data2/pic/f02419355d893358a74b3b11aca84d02/334828100/334828100.jpg
            @SerializedName("del_status") val delStatus: String, //0
            @SerializedName("distribution") val distribution: String //0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,0000000000,00000000000000000000
    )

    data class MvInfo(
            @SerializedName("mv_id") val mvId: String, //334828094
            @SerializedName("all_artist_id") val allArtistId: String, //29
            @SerializedName("title") val title: String, //告白气球
            @SerializedName("aliastitle") val aliastitle: String,
            @SerializedName("subtitle") val subtitle: String, //- 2017周杰伦520南京演唱会 饭拍版
            @SerializedName("play_nums") val playNums: String, //10588
            @SerializedName("publishtime") val publishtime: String, //2017-05-24 15:59:53
            @SerializedName("del_status") val delStatus: String, //0
            @SerializedName("artist_list") val artistList: List<Artist>,
            @SerializedName("artist_id") val artistId: String, //29
            @SerializedName("thumbnail") val thumbnail: String, //http://qukufile2.qianqian.com/data2/pic/f02419355d893358a74b3b11aca84d02/334828100/334828100.jpg@s_0,w_160,h_90
            @SerializedName("thumbnail3") val thumbnail3: String, //http://business.cdn.qianqian.com/baidumisic
            @SerializedName("thumbnail2") val thumbnail2: String, //http://qukufile2.qianqian.com/data2/pic/f02419355d893358a74b3b11aca84d02/334828100/334828100.jpg
            @SerializedName("artist") val artist: String, //周杰伦
            @SerializedName("provider") val provider: String //12
    )

    data class Artist(
            @SerializedName("artist_id") val artistId: String, //29
            @SerializedName("ting_uid") val tingUid: String, //7994
            @SerializedName("artist_name") val artistName: String, //周杰伦
            @SerializedName("artist_480_800") val artist480800: String, //http://qukufile2.qianqian.com/data2/pic/105444246/105444246.jpg
            @SerializedName("artist_640_1136") val artist6401136: String, //http://qukufile2.qianqian.com/data2/pic/105444245/105444245.jpg
            @SerializedName("avatar_small") val avatarSmall: String, //http://qukufile2.qianqian.com/data2/pic/046d17bfa056e736d873ec4f891e338f/540336142/540336142.jpg@s_0,w_48
            @SerializedName("avatar_mini") val avatarMini: String, //http://qukufile2.qianqian.com/data2/pic/046d17bfa056e736d873ec4f891e338f/540336142/540336142.jpg@s_0,w_20
            @SerializedName("avatar_s180") val avatarS180: String, //http://qukufile2.qianqian.com/data2/pic/046d17bfa056e736d873ec4f891e338f/540336142/540336142.jpg@s_0,w_180
            @SerializedName("avatar_s300") val avatarS300: String, //http://qukufile2.qianqian.com/data2/pic/046d17bfa056e736d873ec4f891e338f/540336142/540336142.jpg@s_0,w_300
            @SerializedName("avatar_s500") val avatarS500: String, //http://qukufile2.qianqian.com/data2/pic/046d17bfa056e736d873ec4f891e338f/540336142/540336142.jpg@s_0,w_500
            @SerializedName("del_status") val delStatus: String //0
    )

    data class Files(
            @SerializedName("31") val x31: X31
    )

    data class X31(
            @SerializedName("video_file_id") val videoFileId: String, //541638723
            @SerializedName("video_id") val videoId: String, //334828095
            @SerializedName("definition") val definition: String, //31
            @SerializedName("file_link") val fileLink: String, //http://www.yinyuetai.com/mv/video-url/2869417
            @SerializedName("file_format") val fileFormat: String,
            @SerializedName("file_extension") val fileExtension: String, //mp4
            @SerializedName("file_duration") val fileDuration: String, //0
            @SerializedName("file_size") val fileSize: String, //0
            @SerializedName("source_path") val sourcePath: String,
            @SerializedName("aspect_ratio") val aspectRatio: String
    )
}

