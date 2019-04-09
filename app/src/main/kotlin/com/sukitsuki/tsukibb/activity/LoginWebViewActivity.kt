package com.sukitsuki.tsukibb.activity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import butterknife.BindView
import butterknife.ButterKnife
import com.google.gson.GsonBuilder
import com.sukitsuki.tsukibb.AppConst
import com.sukitsuki.tsukibb.AppDatabase
import com.sukitsuki.tsukibb.BuildConfig
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.entity.Cookie
import com.sukitsuki.tsukibb.model.TelegramAuth
import com.sukitsuki.tsukibb.repository.TbbRepository
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
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
  private val gson by lazy { GsonBuilder().serializeNulls().create() }

  private lateinit var cookieManager: CookieManager
  private var method: Int = -1

  @Inject
  lateinit var mTbbRepository: TbbRepository
  @Inject
  lateinit var appDatabase: AppDatabase
  @Inject
  lateinit var sharedPreferences: SharedPreferences
  @Inject
  lateinit var okHttpClient: OkHttpClient

  @BindView(R.id.webview)
  lateinit var webView: WebView

  var toolbar: ActionBar? = null

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    method = intent.getIntExtra("login", -2)
    toolbar = supportActionBar
    Timber.d("login method $method")
    setContentView(R.layout.activity_web_view)
    ButterKnife.bind(this)
    cookieManager = CookieManager.getInstance()
//    cookieManager.removeAllCookies(null)
//    cookieManager.removeSessionCookies(null)
    // TODO Load cookie from SQLite
    cookieManager.flush()
    webView.settings.javaScriptEnabled = true
    webView.settings.userAgentString = AppConst.FakeUserAgent

    WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    webView.webViewClient = LoginWebViewClient()
    //  webView.webChromeClient = LoginWebViewClient()
    when (method) {
      0 -> webView.loadUrl(googleUrl)
      1 -> webView.loadUrl(telegramUrl)
      else -> finish()
    }

  }

  inner class LoginWebViewClient : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
      Timber.d("url:$url")
      toolbar?.title = url
      when (method) {
        0 -> processGoogleLogin(url)
        1 -> processTelegramLogin(url)
        else -> finish()
      }
      super.onPageStarted(view, url, favicon)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
      Timber.d("shouldOverrideUrlLoading url: $url")
      return false
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
      Timber.d("shouldOverrideUrlLoading request: ${request?.url}")
      return false
    }

    private fun processGoogleLogin(url: String?) {
      if (url == "https://ebb.io/#") {
        val cookieString = CookieManager.getInstance().getCookie(url)
        Timber.d("cookieString $cookieString")
        val cookies = HttpUrl.parse(url)?.let {
          cookieString.split(";").mapNotNull { cs -> okhttp3.Cookie.parse(it, cs) }
        } ?: emptyList()
        doAsync {
          appDatabase.cookieDao().insert(*cookies.map(::Cookie).toTypedArray())
          sharedPreferences.edit().putBoolean("is_login", true).apply()
          uiThread {
            Toast.makeText(this@LoginWebViewActivity, "Login success", Toast.LENGTH_LONG).show()
            setResult(RESULT_OK, null)
            finish()
          }
        }
      }
    }

    private fun processTelegramLogin(url: String?) {
      if (url == "https://oauth.telegram.org/close") {
        val cookieString = CookieManager.getInstance().getCookie(url)
        Timber.d("cookieString:$cookieString")

        val cookies = HttpUrl.parse(url)?.let {
          cookieString.split(";").mapNotNull { cs -> okhttp3.Cookie.parse(it, cs) }
        } ?: emptyList()

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, "origin=https://ebb.io")
        val request = Request.Builder()
          .url(telegramAuthUrl).post(body)
          .addHeader("origin", "https://oauth.telegram.org")
          .addHeader("accept", "*/*")
          .addHeader("x-requested-with", "XMLHttpRequest")
          .build()
        doAsync {
          appDatabase.cookieDao().insert(*cookies.map(::Cookie).toTypedArray())
          val response = okHttpClient.newCall(request).execute()
          val telegramAuth = response.body()!!.let { gson.fromJson(it.string(), TelegramAuth::class.java) }
          Timber.d("telegramAuth.user ${telegramAuth.user}")
          val authTelegram = mTbbRepository.authTelegram(telegramAuth.user.toMap())
          authTelegram.first("").blockingGet()
          uiThread {
            toast("Login success")
            setResult(RESULT_OK, null)
            sharedPreferences.edit().putBoolean("is_login", true).apply()
            finish()
          }
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    webView.destroy()
  }
}