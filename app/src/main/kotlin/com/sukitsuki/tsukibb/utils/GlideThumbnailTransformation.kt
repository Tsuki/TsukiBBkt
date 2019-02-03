package com.sukitsuki.tsukibb.utils

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.ByteBuffer
import java.security.MessageDigest
import kotlin.math.floor
import kotlin.math.roundToInt


class GlideThumbnailTransformation(private val position: Long, private val max: Long) : BitmapTransformation() {

  var epsiso: Int = 0

  init {
    epsiso = floor(position * 99 / max.toDouble()).toInt()
  }

  override fun updateDiskCacheKey(messageDigest: MessageDigest) {
    val data = ByteBuffer.allocate(8).putInt(epsiso).array()
    messageDigest.update(data)
  }

  override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
    return Bitmap.createBitmap(
      toTransform,
      floor(toTransform.width * epsiso / 100.toDouble()).roundToInt(),
      0,
      toTransform.width / 100,
      toTransform.height
    )
  }

  override fun equals(other: Any?): Boolean =
    other is GlideThumbnailTransformation && epsiso == other.epsiso


  override fun hashCode(): Int = ("$position/$max").hashCode()

}