package com.sukitsuki.tsukibb.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.sukitsuki.tsukibb.utils.SingletonHolder


@Entity(tableName = "settings", indices = [Index(value = ["key_k"], unique = true)])
data class SettingData(
  @PrimaryKey(autoGenerate = true) var id: Long?,
  @ColumnInfo(name = "key_k") var key: String,
  @ColumnInfo(name = "value_v") var value: String
) {
  constructor() : this(null, "", "")
}

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

@Database(entities = [SettingData::class], version = 1)
abstract class TbbDatabase : RoomDatabase() {
  abstract fun settingDataDao(): SettingDataDao

  companion object : SingletonHolder<TbbDatabase, Context>({
    Room.databaseBuilder(it.applicationContext, TbbDatabase::class.java, "TbbDatabase.db").build()
  })

}