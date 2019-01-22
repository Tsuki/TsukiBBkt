package com.sukitsuki.tsukibb.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [])
class AppModule(var app: Application) {

  @Singleton
  @Provides
  fun providesApplication(): Application {
    return app
  }


}