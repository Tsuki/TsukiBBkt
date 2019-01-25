package com.sukitsuki.tsukibb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sukitsuki.tsukibb.entity.SettingData

@Dao
interface SettingDataDao {

  @Query("SELECT * from settings")
  fun getAll(): List<SettingData>

  @Query("SELECT * from settings where key_k = :key LIMIT 1")
  fun findByKey(key: String): SettingData

  @Query("SELECT * from settings where key_k = :key LIMIT 1")
  fun findLiveByKey(key: String): LiveData<SettingData>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertSetting(settingData: SettingData)

  @Update
  fun updateSetting(vararg users: SettingData)

  @Query("DELETE from settings")
  fun deleteAll()
}