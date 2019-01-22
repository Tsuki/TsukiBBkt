package com.sukitsuki.tsukibb

import com.sukitsuki.tsukibb.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import net.danlew.android.joda.JodaTimeAndroid


class TbbApplication : DaggerApplication() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent.builder().build()
  }

  override fun onCreate() {
    super.onCreate()
    JodaTimeAndroid.init(this)
  }
}