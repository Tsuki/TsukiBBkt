package com.sukitsuki.tsukibb.module

import android.app.Application
import com.sukitsuki.tsukibb.AppDatabase
import com.sukitsuki.tsukibb.AppDatabase.Companion.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [])
class AppModule(var app: Application) {

  @Singleton
  @Provides
  fun providesApplication(): Application = app

  @Singleton
  @Provides
  fun providesAppDatabase(application: Application): AppDatabase = AppDatabase(application)

}