package com.sukitsuki.tsukibb.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.sukitsuki.tsukibb.R
import okhttp3.Cookie
import okhttp3.HttpUrl


class LoginWebViewActivity : AppCompatActivity() {
  private val url = "https://ebb.io/account/login"
  private lateinit var webView: WebView

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val cookieManager = CookieManager.getInstance()
    cookieManager.flush()
    cookieManager.removeAllCookies(null)
    cookieManager.removeSessionCookies(null)
    setContentView(R.layout.activity_web_view)
    webView = findViewById(R.id.webview)
    webView.settings.javaScriptEnabled = true
    webView.webViewClient = WebViewClient()
    webView.webChromeClient = WebChromeClient()
    webView.loadUrl(url)
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