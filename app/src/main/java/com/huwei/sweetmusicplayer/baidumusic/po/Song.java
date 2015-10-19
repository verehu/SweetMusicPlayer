package com.huwei.sweetmusicplayer.baidumusic.po;

import com.huwei.sweetmusicplayer.interfaces.IQueryReuslt;

/**
 * @author jayce
 * @date 2015/10/20
 */
public class Song implements IQueryReuslt {

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

    @Override
    public String getName() {
        return title;
    }

    @Override
    public QueryType getSearchResultType() {
        return QueryType.Song;
    }
}
