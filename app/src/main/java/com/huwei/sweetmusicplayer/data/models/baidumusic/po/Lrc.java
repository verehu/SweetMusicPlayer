package com.huwei.sweetmusicplayer.data.models.baidumusic.po;

import com.huwei.sweetmusicplayer.data.models.baidumusic.resp.BaseResp;

/**
 * @author Jayce
 * @date 2015/6/12
 */
public class Lrc extends BaseResp {


    private String title;
    private String lrcContent;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLrcContent() {
        return lrcContent;
    }

    public void setLrcContent(String lrcContent) {
        this.lrcContent = lrcContent;
    }


}
