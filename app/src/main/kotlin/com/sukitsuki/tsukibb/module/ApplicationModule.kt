package com.sukitsuki.tsukibb.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: Application) {


  @Provides
  @Singleton
  fun providesApplication(app: Application): Application {
    return app
  }
}