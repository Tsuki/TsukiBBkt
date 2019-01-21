package com.sukitsuki.tsukibb.main

import android.app.Application
import com.sukitsuki.tsukibb.model.TbbDatabase
import net.danlew.android.joda.JodaTimeAndroid

class TbbApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    JodaTimeAndroid.init(this)
    TbbDatabase.getInstance(this)
  }
}