package com.huwei.sweetmusicplayer.baidumusic.po;

import com.huwei.sweetmusicplayer.interfaces.IQueryReuslt;

/**
 * 专辑简略信息
 * @author jayce
 * @date 2015/10/20
 */
public class Album implements IQueryReuslt{


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
    public Object style_id;
    public String publishtime;
    public String artist_ting_uid;
    public Object all_artist_ting_uid;
    public Object gender;
    public Object area;
    public String pic_small;
    public String pic_big;
    public int hot;
    public Object favorites_num;
    public Object recommend_num;
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
}
