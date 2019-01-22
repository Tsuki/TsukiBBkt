package com.sukitsuki.tsukibb

import android.app.Application
import android.util.Log
import com.sukitsuki.tsukibb.activity.MainActivity
import com.sukitsuki.tsukibb.module.AppModule
import com.sukitsuki.tsukibb.module.ExoPlayerModule
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid
import javax.inject.Inject
import javax.inject.Singleton


class TbbApplication : DaggerApplication() {

  @Singleton
  @dagger.Component(
    modules = [
      AndroidInjectionModule::class
      , MainActivity.Module::class
      , AppModule::class
      , ExoPlayerModule::class
    ]
  )
  interface Component : AndroidInjector<TbbApplication> {

    fun inject(application: Application)
    @dagger.Component.Builder
    abstract class Builder : AndroidInjector.Builder<TbbApplication>() {
      abstract fun plus(appModule: AppModule): Builder
    }
  }

  @Inject
  fun logInjection() {
    Log.d(this::class.java.simpleName, "Injecting ${this::class.java.simpleName}")
  }


  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerTbbApplication_Component.builder().plus(AppModule(this)).create(this)
  }

  override fun onCreate() {
    super.onCreate()
    JodaTimeAndroid.init(this)
  }
}