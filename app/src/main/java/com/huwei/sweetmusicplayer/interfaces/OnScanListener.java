package com.huwei.sweetmusicplayer.interfaces;

import com.huwei.sweetmusicplayer.models.MusicInfo;

/**
 * @author jerry
 * @date 2016-01-19
 */
public interface OnScanListener {
    //扫描后
    void afterScan(MusicInfo musicInfo);
}
