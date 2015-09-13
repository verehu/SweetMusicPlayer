package com.huwei.sweetmusicplayer.baidumusic.resp;

import com.huwei.sweetmusicplayer.baidumusic.po.Bitrate;
import com.huwei.sweetmusicplayer.baidumusic.po.SongInfo;

/**
 * 在线音乐的信息返回实体
 * @author jayce
 * @date 2015/08/24
 */
public class SongPlayResp extends BaseResp {
    public Bitrate bitrate;
    public SongInfo songinfo;
}
