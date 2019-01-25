package com.sukitsuki.tsukibb.repository

import com.sukitsuki.tsukibb.dao.CookieDao
import okhttp3.CookieJar
import okhttp3.HttpUrl
import timber.log.Timber
import javax.inject.Inject

class CookieRepository @Inject constructor(cookieDao: CookieDao) : CookieJar {
  private var cookies: Map<HttpUrl, List<okhttp3.Cookie>> =
    cookieDao.getAll().map { it.toOkHttpCookie(it) }.groupBy { HttpUrl.parse(it.domain())!! }

  override fun saveFromResponse(url: HttpUrl, cookies: List<okhttp3.Cookie>) {
    Timber.d("saveFromResponse")
  }

  override fun loadForRequest(url: HttpUrl): List<okhttp3.Cookie> {
    Timber.d("loadForRequest")
    return cookies[url] ?: emptyList()
  }

}