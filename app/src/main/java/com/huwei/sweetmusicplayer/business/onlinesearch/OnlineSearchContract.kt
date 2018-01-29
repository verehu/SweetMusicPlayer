package com.huwei.sweetmusicplayer.business.onlinesearch

import com.huwei.sweetmusicplayer.business.BaseView
import com.huwei.sweetmusicplayer.data.models.baidumusic.po.QueryResult

/**
 * Created by huwei on 18-1-29.
 */
interface OnlineSearchContract {
    interface Presenter {
        fun doQuery(query : String, pageNo : Int, pageSize : Int,
                    onGetQueryData: OnlineSearchActivity.OnGetQueryData? = null)
    }

    interface View : BaseView<Presenter> {
        fun showResultPage(queryMergeResp: QueryResult)
    }
}