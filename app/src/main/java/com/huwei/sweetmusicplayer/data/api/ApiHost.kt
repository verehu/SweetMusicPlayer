package com.huwei.sweetmusicplayer.data.api

/**
 * Created by huwei on 18-1-31.
 */
interface ApiHost {
    interface Baidu {
        companion object {
            const val HOST = "http://tingapi.ting.baidu.com/"
            const val V1_TING = "v1/restserver/ting"
        }
    }
}