package com.sukitsuki.tsukibb

import android.app.Application
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid
import javax.inject.Singleton


class TbbApplication : DaggerApplication() {

  @Singleton
  @dagger.Component(
    modules = [
      AndroidInjectionModule::class
    ]
  )
  interface Component : AndroidInjector<TbbApplication> {

    fun inject(application: Application)
    @dagger.Component.Builder
    abstract class Builder : AndroidInjector.Builder<TbbApplication>()
  }


  override fun applicationInjector(): AndroidInjector<TbbApplication> {
    return DaggerTbbApplication_Component.builder().create(this)
  }

  override fun onCreate() {
    super.onCreate()
    JodaTimeAndroid.init(this)
  }
}