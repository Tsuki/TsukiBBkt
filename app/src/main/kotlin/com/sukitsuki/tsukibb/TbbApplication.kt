package com.sukitsuki.tsukibb

import android.app.Activity
import android.app.Application
import com.sukitsuki.tsukibb.component.AppComponent
import com.sukitsuki.tsukibb.component.DaggerAppComponent
import com.sukitsuki.tsukibb.module.AppModule
import com.sukitsuki.tsukibb.module.ExoPlayerModule
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

  private lateinit var applicationComponent: AppComponent
  override fun onCreate() {
    super.onCreate()
    JodaTimeAndroid.init(this)
    applicationComponent = DaggerAppComponent.builder()
      .application(this)
      .appModule(AppModule(this))
      .exoPlayerModule(ExoPlayerModule())
      .build()
    applicationComponent.inject(this)
//    applicationComponent = DaggerApplicationComponent.builder().build()
//    applicationComponent.inject(this)
  }

  fun getApplicationComponent(): AppComponent {
    return applicationComponent
  }
}