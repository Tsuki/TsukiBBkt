package com.sukitsuki.tsukibb.utils

import com.sukitsuki.tsukibb.repository.CookieRepository
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class AppCookieJar(cookieRepository: CookieRepository) : CookieJar {
  override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}