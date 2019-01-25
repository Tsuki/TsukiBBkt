package com.sukitsuki.tsukibb.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.sukitsuki.tsukibb.BuildConfig
import com.sukitsuki.tsukibb.R
import okhttp3.*
import timber.log.Timber


class LoginWebViewActivity : AppCompatActivity() {
  private val url = "https://ebb.io/account/login"
  private val telegramUrl = "https://oauth.telegram.org/auth?bot_id=639045451&origin=https%3A%2F%2Febb.io"
  private lateinit var webView: WebView
  private lateinit var cookies: List<Cookie>

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val cookieManager = CookieManager.getInstance()
    cookieManager.flush()
//    cookieManager.removeAllCookies(null)
//    cookieManager.removeSessionCookies(null)
    setContentView(R.layout.activity_web_view)
    webView = findViewById(R.id.webview)
    webView.settings.javaScriptEnabled = true
//    webView.settings.javaScriptCanOpenWindowsAutomatically = true
//    webView.settings.useWideViewPort = true
//    webView.settings.setSupportMultipleWindows(true)

    webView.settings.userAgentString =
      "Mozilla/5.0 (Linux; Android 9; ONEPLUS A5000) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.99 Mobile Safari/537.36"
    WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    webView.webViewClient = LoginWebViewClient()
//    webView.webChromeClient = LoginWebViewClient()
    webView.loadUrl(url)
  }

  inner class LoginWebViewClient : WebViewClient() {
    private fun parseCookies(url: HttpUrl, cookieStrings: String?): List<Cookie> {
      if (cookieStrings == null) {
        return emptyList()
      }

      var cookies: MutableList<Cookie>? = null
      val pieces = cookieStrings.split(";")
      for (piece in pieces) {
        val cookie = Cookie.parse(url, piece) ?: continue
        if (cookies == null) {
          cookies = ArrayList()
        }
        cookies.add(cookie)
      }

      return cookies ?: emptyList()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
      Timber.d("url:$url")
      if (url == "https://oauth.telegram.org/close") {
        val cookieString = CookieManager.getInstance().getCookie(url)
        cookies = HttpUrl.parse(url)?.let { parseCookies(it, cookieString) }!!
        cookies = cookies.filter { (it.name() == "stel_ssid") or (it.name() == "stel_token") }
        cookies.let { Timber.d(it.joinToString("\n")) }
        setResult(RESULT_OK, null)
        finish()

        val client = OkHttpClient()

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, "origin=https%3A%2F%2Febb.io")
        val request = Request.Builder()
          .url("https://oauth.telegram.org/auth/get?bot_id=639045451&lang=en")
          .post(body)
          .addHeader("cookie", cookieString)
          .addHeader("origin", "https://oauth.telegram.org")
          .addHeader("content-type", "application/x-www-form-urlencoded")
          .addHeader("accept", "*/*")
          .addHeader("x-requested-with", "XMLHttpRequest")
          .build()
        val response = client.newCall(request).execute()

        "https://ebb.io/auth/telegram?id=**&first_name=**&username=**&auth_date=unix&hash=**"
      }
      super.onPageFinished(view, url)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    webView.destroy()
    val cookieManager = CookieManager.getInstance()
    val cookiesString = cookieManager.getCookie(url)
    if (cookiesString != null && !cookiesString.isEmpty()) {
      val eUrl = HttpUrl.parse(url)
      for (header in cookiesString.split(";")) if (eUrl != null) {
        val eCookie = Cookie.parse(eUrl, header)
        if (eCookie != null) {
//          store.addCookie(longLive(eCookie))
        }
      }
    }
  }
}