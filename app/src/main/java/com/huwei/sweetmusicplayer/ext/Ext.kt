package com.huwei.sweetmusicplayer.ext

import android.content.Context
import android.widget.Toast

/**
 * Created by huwei on 17-6-1.
 */

fun Context.toast(msg : String, duration : Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration)
}