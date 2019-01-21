package com.sukitsuki.tsukibb.module

import android.app.Application
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApplicationModule(private val mApplication: Application) {

  @Binds
  @Singleton
  abstract fun providesApplication(app: Application): Application
}