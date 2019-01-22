package com.sukitsuki.tsukibb

import android.app.Activity
import android.app.Application
import com.sukitsuki.tsukibb.component.ApplicationComponent
import com.sukitsuki.tsukibb.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import net.danlew.android.joda.JodaTimeAndroid
import javax.inject.Inject


class TbbApplication : Application(), HasActivityInjector {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  override fun activityInjector(): AndroidInjector<Activity> {
    return dispatchingAndroidInjector
  }

  private lateinit var applicationComponent: ApplicationComponent
  override fun onCreate() {
    super.onCreate()
    JodaTimeAndroid.init(this)
    applicationComponent = DaggerApplicationComponent.builder().build()
    applicationComponent.inject(this)
  }

  fun getApplicationComponent(): ApplicationComponent {
    return applicationComponent
  }
}