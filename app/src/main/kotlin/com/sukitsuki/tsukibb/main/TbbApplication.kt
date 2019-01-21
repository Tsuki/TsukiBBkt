package com.sukitsuki.tsukibb.main

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class TbbApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    JodaTimeAndroid.init(this)
  }
}