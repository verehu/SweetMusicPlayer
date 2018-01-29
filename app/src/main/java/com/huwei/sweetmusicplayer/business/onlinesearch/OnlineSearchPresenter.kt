package com.huwei.sweetmusicplayer.business.onlinesearch

import android.content.Context
import com.google.gson.Gson
import com.huwei.sweetmusicplayer.business.ViewHoldPresenter
import com.huwei.sweetmusicplayer.data.models.baidumusic.resp.QueryMergeResp
import com.huwei.sweetmusicplayer.util.BaiduMusicUtil
import com.huwei.sweetmusicplayer.util.HttpHandler

/**
 * Created by huwei on 18-1-29.
 */
class OnlineSearchPresenter(context: Context, view: OnlineSearchContract.View) :
        ViewHoldPresenter<OnlineSearchContract.View>(context, view), OnlineSearchContract.Presenter {


    override fun doQuery(query: String, pageNo: Int, pageSize: Int,
                         onGetQueryData: OnlineSearchActivity.OnGetQueryData?) {

        BaiduMusicUtil.queryMerge(query, pageNo, pageSize, object : HttpHandler(mContext) {
            override fun onSuccess(response: String) {

                val sug = Gson().fromJson(response, QueryMergeResp::class.java)
                val result = sug.result

                if (onGetQueryData == null) {
                    mView.showResultPage(result)
                } else {
                    onGetQueryData.onGetData(result)
                }
            }
        })
    }
}