package com.sukitsuki.tsukibb.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import butterknife.BindView
import butterknife.ButterKnife
import com.sukitsuki.tsukibb.AppConst
import com.sukitsuki.tsukibb.AppDatabase
import com.sukitsuki.tsukibb.BuildConfig
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.entity.Cookie
import com.sukitsuki.tsukibb.repository.TbbRepository
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import okhttp3.*
import org.jetbrains.anko.doAsync
import timber.log.Timber
import javax.inject.Inject


class LoginWebViewActivity : DaggerAppCompatActivity() {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<LoginWebViewActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<LoginWebViewActivity>()
  }


  private val googleUrl = "https://ebb.io/auth/google"
  private val telegramUrl = "https://oauth.telegram.org/auth?bot_id=639045451&origin=https%3A%2F%2Febb.io"
  private val telegramAuthUrl = "https://oauth.telegram.org/auth/get?bot_id=639045451&lang=en"

  private lateinit var cookieManager: CookieManager
  private var method: Int = -1

  @Inject
  lateinit var mTbbRepository: TbbRepository
  @Inject
  lateinit var appDatabase: AppDatabase
  @Inject
  lateinit var okHttpClient: OkHttpClient

  @BindView(R.id.webview)
  lateinit var webView: WebView

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    method = intent.getIntExtra("login", -2)
    Timber.d("login method $method")
    setContentView(R.layout.activity_web_view)
    ButterKnife.bind(this)
    cookieManager = CookieManager.getInstance()
//    cookieManager.removeAllCookies(null)
//    cookieManager.removeSessionCookies(null)
    cookieManager.flush()
    webView.settings.javaScriptEnabled = true
    webView.settings.userAgentString = AppConst.FakeUserAgent
//    webView.settings.javaScriptCanOpenWindowsAutomatically = true
//    webView.settings.useWideViewPort = true
//    webView.settings.setSupportMultipleWindows(true)

    WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    webView.webViewClient = LoginWebViewClient()
//    webView.webChromeClient = LoginWebViewClient()
    when (method) {
      0 -> webView.loadUrl(googleUrl)
      1 -> webView.loadUrl(telegramUrl)
      else -> finish()
    }

  }

  inner class LoginWebViewClient : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
      Timber.d("url:$url")
      if (url == "https://oauth.telegram.org/close") {
        val cookieString = CookieManager.getInstance().getCookie(url)
        Timber.d("cookieString:$cookieString")

        val cookies = HttpUrl.parse(url)?.let {
          cookieString.split(";").mapNotNull { cs -> okhttp3.Cookie.parse(it, cs) }
        } ?: emptyList()

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, "origin=https://Febb.io")
        val request = Request.Builder()
          .url(telegramAuthUrl)
          .addHeader("origin", "https://oauth.telegram.org")
          .addHeader("content-type", "application/x-www-form-urlencoded")
          .addHeader("accept", "*/*")
          .addHeader("x-requested-with", "XMLHttpRequest")
          .build()
        doAsync {
          appDatabase.cookieDao().insertCookie(*cookies.map(::Cookie).toTypedArray())
          val response = okHttpClient.newCall(request).execute()
          response.body()
        }

        setResult(RESULT_OK, null)
        finish()
//        cookies = HttpUrl.parse(url)?.let { okhttp3.Cookie.parse(it, cookieString) }!!
//        cookies = cookies.filter { (it.domain() == "telegram") }
//        cookies.let { Timber.d(it.joinToString("\n")) }

//        val client = OkHttpClient()
//
//
//          .url("https://oauth.telegram.org/auth/get?bot_id=639045451&lang=en")
//          .post(body)
//          .addHeader("cookie", cookieString)
//          .build()
//        val response = client.newCall(request).execute()

//        "https://ebb.io/auth/telegram?id=**&first_name=**&username=**&auth_date=unix&hash=**"
      }
      super.onPageFinished(view, url)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    webView.destroy()
  }
}