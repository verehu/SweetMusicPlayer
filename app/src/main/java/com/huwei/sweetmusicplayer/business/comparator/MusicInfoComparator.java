package com.huwei.sweetmusicplayer.business.comparator;

import com.huwei.sweetmusicplayer.data.models.MusicInfo;

import java.util.Comparator;

/**
 * @author Jayce
 * @date 2015/6/13
 */
public class MusicInfoComparator implements Comparator<MusicInfo> {
    @Override
    public int compare(MusicInfo lhs, MusicInfo rhs) {
        return lhs.getKeyofTitle().charAt(0)-rhs.getKeyofTitle().charAt(0);
    }
}
