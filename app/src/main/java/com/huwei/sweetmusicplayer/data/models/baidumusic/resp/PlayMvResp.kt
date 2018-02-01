package com.huwei.sweetmusicplayer.data.models.baidumusic.resp

import com.google.gson.annotations.SerializedName
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.PlayMv

/**
 * Created by huwei on 18-1-31.
 */
data class PlayMvResp(
        @SerializedName("result") val result: PlayMv
) : BaseResp()
