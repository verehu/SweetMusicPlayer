package com.huwei.sweetmusicplayer.helper;

import android.graphics.Bitmap;

import com.huwei.sweetmusicplayer.util.FastBlur;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

/**
 * 单例的模糊Helper
 *
 * @author jerry
 * @date 2015-12-17
 */
@EBean(scope = EBean.Scope.Singleton)
public class BlurHelper {

    /**
     * 异步处理图片
     *
     * @param originBitmap
     * @param radius
     * @param callback
     */
    @Background
    public void blurBitmap(Bitmap originBitmap, int radius, OnGenerateBitmapCallback callback) {
        Bitmap outBitmap = FastBlur.doBlur(originBitmap, radius, false);

        onGenerateBitmap(outBitmap, callback);
        if (originBitmap != null) {
            originBitmap.recycle();
        }
    }

    public interface OnGenerateBitmapCallback {
        void onGenerateBitmap(Bitmap bitmap);
    }

    @UiThread
    void onGenerateBitmap(Bitmap outBitmap, OnGenerateBitmapCallback callback) {
        if (callback != null) {
            callback.onGenerateBitmap(outBitmap);
        }
    }
}
