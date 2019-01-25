package com.sukitsuki.tsukibb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "settings", indices = [Index(value = ["key_k"], unique = true)])
data class SettingData(
  @PrimaryKey(autoGenerate = true) val id: Long? = null
  , @ColumnInfo(name = "key_k") val key: String = ""
)