package com.huwei.sweetmusicplayer.frameworks

import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import java.io.Serializable
import java.util.ArrayList

import java.util.HashMap

/**
 * @author Ezio
 * @date 2018/01/16
 */

class BundleBuilder {
    var mExtras: Bundle = Bundle()

    fun extra(name: String, value: Boolean): BundleBuilder {
        mExtras.putBoolean(name, value)
        return this
    }

    fun extra(name: String, value: Byte): BundleBuilder {
        mExtras.putByte(name, value)
        return this
    }

    fun extra(name: String, value: Char): BundleBuilder {
        mExtras.putChar(name, value)
        return this
    }

    fun extra(name: String, value: Short): BundleBuilder {
        mExtras.putShort(name, value)
        return this
    }

    fun extra(name: String, value: Int): BundleBuilder {
        mExtras.putInt(name, value)
        return this
    }

    fun extra(name: String, value: Long): BundleBuilder {
        mExtras.putLong(name, value)
        return this
    }

    fun extra(name: String, value: Float): BundleBuilder {
        mExtras.putFloat(name, value)
        return this
    }

    fun extra(name: String, value: Double): BundleBuilder {
        mExtras.putDouble(name, value)
        return this
    }

    fun extra(name: String, value: String): BundleBuilder {
        mExtras.putString(name, value)
        return this
    }

    fun extra(name: String, value: CharSequence): BundleBuilder {
        mExtras.putCharSequence(name, value)
        return this
    }

    fun extra(name: String, value: Parcelable): BundleBuilder {
        mExtras.putParcelable(name, value)
        return this
    }

    fun extra(name: String, value: Array<Parcelable>): BundleBuilder {
        mExtras.putParcelableArray(name, value)
        return this
    }

//    fun extra(name: String, value: ArrayList<out Parcelable>): BundleBuilder {
//        if (mExtras == null) {
//            mExtras = Bundle()
//        }
//        mExtras.putParcelableArrayList(name, value)
//        return this
//    }
//
//    fun extra(name: String, value: ArrayList<Int>): BundleBuilder {
//        if (mExtras == null) {
//            mExtras = Bundle()
//        }
//        mExtras.putIntegerArrayList(name, value)
//        return this
//    }
//
//    fun extra(name: String, value: ArrayList<String>): BundleBuilder {
//        if (mExtras == null) {
//            mExtras = Bundle()
//        }
//        mExtras.putStringArrayList(name, value)
//        return this
//    }
//
//    fun extra(name: String, value: ArrayList<CharSequence>): BundleBuilder {
//        if (mExtras == null) {
//            mExtras = Bundle()
//        }
//        mExtras.putCharSequenceArrayList(name, value)
//        return this
//    }

    fun extra(name: String, value: Serializable?): BundleBuilder {
        mExtras.putSerializable(name, value)
        return this
    }

    fun extra(name: String, value: BooleanArray): BundleBuilder {
        mExtras.putBooleanArray(name, value)
        return this
    }

    fun extra(name: String, value: ByteArray): BundleBuilder {
        mExtras.putByteArray(name, value)
        return this
    }

    fun extra(name: String, value: ShortArray): BundleBuilder {
        mExtras.putShortArray(name, value)
        return this
    }

    fun extra(name: String, value: CharArray): BundleBuilder {
        mExtras.putCharArray(name, value)
        return this
    }

    fun extra(name: String, value: IntArray): BundleBuilder {
        mExtras.putIntArray(name, value)
        return this
    }

    fun extra(name: String, value: LongArray): BundleBuilder {
        mExtras.putLongArray(name, value)
        return this
    }

    fun extra(name: String, value: FloatArray): BundleBuilder {
        mExtras.putFloatArray(name, value)
        return this
    }

    fun extra(name: String, value: DoubleArray): BundleBuilder {
        mExtras.putDoubleArray(name, value)
        return this
    }

    fun extra(name: String, value: Array<String>): BundleBuilder {
        mExtras.putStringArray(name, value)
        return this
    }

    fun extra(name: String, value: Array<CharSequence>): BundleBuilder {
        mExtras.putCharSequenceArray(name, value)
        return this
    }

    fun extra(name: String, value: Bundle): BundleBuilder {
        mExtras.putBundle(name, value)
        return this
    }

    @Deprecated(" ")
    fun extra(name: String, value: IBinder): BundleBuilder {
        mExtras.putBinder(name, value)
        return this
    }

    fun extras(src: Intent): BundleBuilder {
        if (src.extras != null) {
            mExtras.putAll(src.extras)
        }
        return this
    }

    fun extras(extras: Bundle): BundleBuilder {
        mExtras.putAll(extras)
        return this
    }

    fun build(): Bundle {
        return mExtras;
    }
}
