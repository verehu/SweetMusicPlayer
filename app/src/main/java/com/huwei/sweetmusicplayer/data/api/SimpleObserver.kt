package com.huwei.sweetmusicplayer.data.api

import android.accounts.NetworkErrorException
import android.widget.Toast
import com.huwei.sweetmusicplayer.AppContextHolder
import com.huwei.sweetmusicplayer.util.LogUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Created by huwei on 18-1-31.
 */
abstract class SimpleObserver<T> : Observer<T> {

    companion object {
        val nullRespThrowable = Throwable("respones is null")
    }

    final override fun onError(e: Throwable) {
        onFinish()

        if (e is ConnectException
                || e is TimeoutException
                || e is NetworkErrorException
                || e is UnknownHostException) {
            onFailure(e, true)
        } else {
            onFailure(e, false)
        }
    }

    final override fun onNext(t: T) {
        onFinish()

        if (t != null) {
            onSuccess(t)
        } else {
            onFailure(nullRespThrowable, false)
        }
    }

    final override fun onComplete() {
        LogUtils.i("request complete")
    }

    final override fun onSubscribe(d: Disposable) {
        onStart()
    }

    open fun onStart() {

    }

    open fun onFinish() {

    }

    abstract fun onSuccess(resp: T)

    open fun onFailure(e: Throwable, isNetworkError: Boolean) {
        if (isNetworkError) {
            Toast.makeText(AppContextHolder.getAppContext(), "network error:" + e,
                    Toast.LENGTH_LONG).show()
        }
    }
}