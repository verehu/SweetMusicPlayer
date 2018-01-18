package com.huwei.sweetmusicplayer.business

import android.content.Context
import com.huwei.sweetmusicplayer.business.playmusic.PlayMusicContract

/**
 *
 * @author Ezio
 * @date 2018/01/18
 */
open class ContextPresenter<T : BaseView<out BasePresenter>>(protected val context: Context,protected val view: T)