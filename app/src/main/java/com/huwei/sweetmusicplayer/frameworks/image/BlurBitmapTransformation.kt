package com.huwei.sweetmusicplayer.frameworks.image

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.huwei.sweetmusicplayer.util.FastBlur
import java.security.MessageDigest

/**
 * Created by huwei on 17-11-21.
 */
class BlurBitmapTransformation : BitmapTransformation{
    var mRadius : Int

    constructor(radius: Int) {
        mRadius = radius
    }
    override fun updateDiskCacheKey(messageDigest: MessageDigest?) {

    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return FastBlur.doBlur(toTransform, mRadius, false)
    }

}