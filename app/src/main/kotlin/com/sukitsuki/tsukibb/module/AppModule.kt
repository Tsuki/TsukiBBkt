package com.sukitsuki.tsukibb.module

import android.app.Application
import androidx.room.Room
import com.sukitsuki.tsukibb.AppDatabase
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
  fun providesAppDatabase(application: Application): AppDatabase =
    Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "TbbDatabase.db").build()

}