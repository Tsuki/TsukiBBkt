package com.sukitsuki.tsukibb.repository

import com.sukitsuki.tsukibb.dao.CookieDao
import com.sukitsuki.tsukibb.entity.Cookie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import okhttp3.CookieJar
import okhttp3.HttpUrl
import timber.log.Timber
import javax.inject.Inject


class CookieRepository @Inject constructor(
  private val cookieDao: CookieDao, private val compositeDisposable: CompositeDisposable) : CookieJar {

  private var cookies: Map<String, List<okhttp3.Cookie>> =
    cookieDao.getAll().blockingFirst().map(Cookie::toOkHttpCookie).groupBy(okhttp3.Cookie::domain)

  init {
    updateFromSQLite()
  }

  private fun updateFromSQLite(): Disposable? {
    return cookieDao.getAll()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnError { Timber.w(it) }
      .doOnNext { Timber.d("updateFromSQLite") }
      .map { it.map(Cookie::toOkHttpCookie) }
      .map { it.groupBy { cookie -> cookie.domain() } }
      .onErrorReturn { emptyMap() }
      .subscribe { cookies = it }.addTo(compositeDisposable)
  }

  private fun saveCookieToSQLite(cookies: List<okhttp3.Cookie>) {
    cookieDao.insertCookie(*(cookies.map { Cookie(it) }.toTypedArray()))
  }

  override fun saveFromResponse(url: HttpUrl, cookies: List<okhttp3.Cookie>) {
    saveCookieToSQLite(cookies)
  }

  override fun loadForRequest(url: HttpUrl): List<okhttp3.Cookie> {
    return cookies[url.host()] ?: emptyList()
  }

}