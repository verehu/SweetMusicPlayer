package com.huwei.sweetmusicplayer.baidumusic.po;

import com.huwei.sweetmusicplayer.interfaces.IQueryReuslt;

/**
 * @author jayce
 * @date 2015/10/20
 */
public class Album implements IQueryReuslt{

    /**
     * album_id : 67909
     * author : 周杰伦
     * hot : 22412
     * title : <em>七里香</em>
     * artist_id : 29
     * all_artist_id : 29
     * company : 杰威尔JVR音乐有限公司
     * publishtime : 2004-08-03
     * album_desc : 周杰伦的新专辑在8月3日正式发行，这次引用了诗人席幕蓉名诗《<em>七里香</em>》作为新专辑名称，周杰伦以往每一次的专辑名称都给了歌迷许多想象空间，也给了大家许多惊叹号。这次也许并不令人惊喜。但是周杰伦自有说法：“...
     * pic_small : http://musicdata.baidu.com/data2/pic/115430825/115430825.jpg
     */

    public String album_id;
    public String author;
    public int hot;
    public String title;
    public String artist_id;
    public String all_artist_id;
    public String company;
    public String publishtime;
    public String album_desc;
    public String pic_small;

    @Override
    public String getName() {
        return title;
    }

    @Override
    public QueryType getSearchResultType() {
        return QueryType.Album;
    }
}
