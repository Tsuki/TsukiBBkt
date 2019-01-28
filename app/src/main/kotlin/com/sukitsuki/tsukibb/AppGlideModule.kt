package com.sukitsuki.tsukibb

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.DiskCache
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class AppGlideModule : AppGlideModule() {
  override fun applyOptions(context: Context, builder: GlideBuilder) {
    super.applyOptions(context, builder)
    builder.setDiskCache(InternalCacheDiskCacheFactory(
      context,
      "cache_image",
      DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE.toLong()
    ))
  }
}