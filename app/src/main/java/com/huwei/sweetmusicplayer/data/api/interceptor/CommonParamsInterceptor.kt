package com.huwei.sweetmusicplayer.data.api.interceptor


import com.huwei.sweetmusicplayer.data.api.ApiHost
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by huwei on 18-1-30.
 */
class CommonParamsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val oldRequest = chain!!.request()

        // 添加新的参数
        val authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())

        // 添加参数操作
        when (oldRequest.url().host()) {
            getHost(ApiHost.Baidu.HOST) -> {
                authorizedUrlBuilder.addQueryParameter("from", "android")
                authorizedUrlBuilder.addQueryParameter("version", "5.6.5.0")
            }
        }

        // 新的请求
        val newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .addHeader("Content-Type", "text/html; charset=utf-8")
                .addHeader("User-Agent",
                        "Mozilla/5.0 (Linux; Android 4.4.4; HTC D820u Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.89 Mobile Safari/537.36")
                .build()

        return chain.proceed(newRequest)
    }

    fun getHost(url: String): String {
        return url.substring(url.indexOf("//") + 2, url.lastIndexOf("/"))
    }
}