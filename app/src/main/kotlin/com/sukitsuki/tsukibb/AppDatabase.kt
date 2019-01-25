package com.sukitsuki.tsukibb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sukitsuki.tsukibb.dao.SettingDataDao
import com.sukitsuki.tsukibb.entity.SettingData
import com.sukitsuki.tsukibb.utils.SingletonHolder


@Database(entities = [SettingData::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
  abstract fun settingDataDao(): SettingDataDao

  companion object : SingletonHolder<AppDatabase, Context>({
    Room.databaseBuilder(it.applicationContext, AppDatabase::class.java, "TbbDatabase.db").build()
  })

}