package com.sukitsuki.tsukibb.utils

import okhttp3.Interceptor
import okhttp3.ResponseBody
import rufus.lzstring4java.LZString

object Interceptors {
  val decode = Interceptor { chain ->
    val original = chain.request()
    if (!original.url().toString().startsWith("https://ebb.io/_")) {
      return@Interceptor chain.proceed(original)
    }
    val response = chain.proceed(original)
    val body = response.body()
    val newBody = ResponseBody.create(body?.contentType(), LZString.decompressFromUTF16(body?.string()))
    return@Interceptor response.newBuilder().body(newBody).build()
  }
}