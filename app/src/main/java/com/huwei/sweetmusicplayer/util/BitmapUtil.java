package com.huwei.sweetmusicplayer.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author jayce
 * @date 2015/11/07
 */
public class BitmapUtil {

    public static Bitmap drawable2bitamp(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    public static Drawable bitmap2drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }
}
