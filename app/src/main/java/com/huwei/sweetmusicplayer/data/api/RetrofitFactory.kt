package com.huwei.sweetmusicplayer.data.api

import com.huwei.sweetmusicplayer.BuildConfig
import com.huwei.sweetmusicplayer.data.api.baidu.BaiduMusicService
import com.huwei.sweetmusicplayer.data.api.interceptor.CommonParamsInterceptor

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.*

/**
 * Created by huwei on 18-1-30.
 */
class RetrofitFactory {

    companion object {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(CommonParamsInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG) BASIC else NONE))
                .build()

        fun <T> create(clazz: Class<T>): T {
            return Retrofit.Builder()
                    .baseUrl(getBaseUrl(clazz))
                    // 添加Gson转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    // 添加Retrofit到RxJava的转换器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()
                    .create(clazz)
        }

        fun <T> getBaseUrl(clazz: Class<T>): String {
            when (clazz) {
                BaiduMusicService::class.java -> return ApiHost.Baidu.HOST
            }
            return ""
        }
    }
}