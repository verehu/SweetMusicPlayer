package com.huwei.sweetmusicplayer.business.onlinesearch

import android.content.Context

import com.huwei.sweetmusicplayer.business.ViewHoldPresenter
import com.huwei.sweetmusicplayer.data.api.RetrofitFactory
import com.huwei.sweetmusicplayer.data.api.SimpleObserver
import com.huwei.sweetmusicplayer.data.api.baidu.BaiduMusicService
import com.huwei.sweetmusicplayer.data.models.baidumusic.resp.QueryMergeResp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by huwei on 18-1-29.
 */
class OnlineSearchPresenter(context: Context, view: OnlineSearchContract.View) :
        ViewHoldPresenter<OnlineSearchContract.View>(context, view), OnlineSearchContract.Presenter {

    override fun doQuery(query: String, pageNo: Int, pageSize: Int,
                         onGetQueryData: OnlineSearchActivity.OnGetQueryData?) {
        RetrofitFactory.create(BaiduMusicService::class.java).
                queryMerge(query, pageNo, pageSize).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(object : SimpleObserver<QueryMergeResp>() {
                    override fun onSuccess(resp: QueryMergeResp) {
                        if (onGetQueryData == null) {
                            mView.showResultPage(resp.result)
                        } else {
                            onGetQueryData.onGetData(resp.result)
                        }
                    }
                })
    }
}