package com.huwei.sweetmusicplayer.business

/**
 *
 * @author Ezio
 * @date 2018/01/18
 */
interface BaseView<T> {
    fun setPresenter(presenter: T)
}