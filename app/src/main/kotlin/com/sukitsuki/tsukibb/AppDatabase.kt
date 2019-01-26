package com.sukitsuki.tsukibb

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sukitsuki.tsukibb.dao.CookieDao
import com.sukitsuki.tsukibb.dao.FavoriteDao
import com.sukitsuki.tsukibb.dao.HistoryDao
import com.sukitsuki.tsukibb.dao.SettingDataDao
import com.sukitsuki.tsukibb.entity.Cookie
import com.sukitsuki.tsukibb.entity.Favorite
import com.sukitsuki.tsukibb.entity.History
import com.sukitsuki.tsukibb.entity.SettingData


@Database(
  entities = [
    SettingData::class
    , Cookie::class
    , Favorite::class
    , History::class
  ], version = 3, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun settingDataDao(): SettingDataDao
  abstract fun cookieDao(): CookieDao
  abstract fun favoriteDao(): FavoriteDao
  abstract fun historyDao(): HistoryDao

  companion object {
    val AppDatabase = fun(it: Application): AppDatabase {
      return Room.databaseBuilder(it.applicationContext, AppDatabase::class.java, "TbbDatabase.db")
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
        // .fallbackToDestructiveMigration()
        .build()
    }
  }

}