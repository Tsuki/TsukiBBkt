package com.sukitsuki.tsukibb

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceManager
import android.preference.PreferenceManager.KEY_HAS_SET_DEFAULT_VALUES
import com.anggrayudi.materialpreference.util.SaveDir
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.sukitsuki.tsukibb.module.*
import com.sukitsuki.tsukibb.utils.NotificationListener
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


class MainApplication : DaggerApplication() {

  @Singleton
  @dagger.Component(
    modules = [
      AndroidInjectionModule::class
      , AppModule::class
      , ExoPlayerModule::class
      , ActivityModule::class
      , FragmentModule::class
      , RepositoryModule::class
    ]
  )
  interface Component : AndroidInjector<MainApplication> {
    @dagger.Component.Builder
    abstract class Builder : AndroidInjector.Builder<MainApplication>() {
      abstract fun plus(appModule: AppModule): Builder
    }
  }

  @Inject
  fun logInjection() {
    Timber.d("Injecting ${this::class.java.simpleName}")
  }

  @Inject
  lateinit var exoPlayer: SimpleExoPlayer
  @Inject
  lateinit var playerNotificationManager: PlayerNotificationManager

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerMainApplication_Component.builder().plus(AppModule(this)).create(this)
  }

  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    JodaTimeAndroid.init(this)
    playerNotificationManager.setPlayer(exoPlayer)
    playerNotificationManager.setNotificationListener(NotificationListener())
    PreferenceManager.setDefaultValues(this, R.xml.preferences, true)
    /*
       DO NOT USE THIS METHOD to set your preferences' default value. It is inefficient!!!
       PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
       USE THE FOLLOWING TECHNIQUE INSTEAD
        */
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    if (!preferences.getBoolean(KEY_HAS_SET_DEFAULT_VALUES, false)) {
      preferences.edit().putBoolean(KEY_HAS_SET_DEFAULT_VALUES, true).apply()
      setDefaultPreferenceValues(this)
    }
  }

  companion object {

    fun setDefaultPreferenceValues(context: Context) {
      val preferences = PreferenceManager.getDefaultSharedPreferences(context)
      preferences.edit()
        .putBoolean("auto_update", true)
        .putBoolean("wifi_only", true)
        .putString("update_interval", "Weekly")
        .putString("backupLocation", SaveDir.DOWNLOADS)
        .putInt("themeColor", Color.parseColor("#37474F"))
        .apply()
    }
  }
}