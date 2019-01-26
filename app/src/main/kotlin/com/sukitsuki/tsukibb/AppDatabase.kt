package com.sukitsuki.tsukibb

import android.content.Context
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
import com.sukitsuki.tsukibb.utils.SingletonHolder


@Database(
  entities = [
    SettingData::class
    , Cookie::class
    , Favorite::class
    , History::class
  ], version = 1, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun settingDataDao(): SettingDataDao
  abstract fun cookieDao(): CookieDao
  abstract fun favoriteDao(): FavoriteDao
  abstract fun historyDao(): HistoryDao

  companion object : SingletonHolder<AppDatabase, Context>({
    Room.databaseBuilder(it.applicationContext, AppDatabase::class.java, "TbbDatabase.db")
      .allowMainThreadQueries().build()
  })

}