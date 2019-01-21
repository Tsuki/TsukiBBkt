package com.sukitsuki.tsukibb

import android.app.Application
import com.sukitsuki.tsukibb.component.ApplicationComponent
import com.sukitsuki.tsukibb.model.TbbDatabase
import net.danlew.android.joda.JodaTimeAndroid

class TbbApplication : Application() {
  private lateinit var applicationComponent: ApplicationComponent
  override fun onCreate() {
    super.onCreate()
    JodaTimeAndroid.init(this)

//    applicationComponent = DaggerApplicationComponent.builder().build()
    TbbDatabase.getInstance(this)
  }

  fun getApplicationComponent(): ApplicationComponent {
    return applicationComponent
  }
}