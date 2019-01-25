package com.sukitsuki.tsukibb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorites", indices = [Index(value = ["anime_id"], unique = true)])
data class Favorite(
  @PrimaryKey(autoGenerate = true) val id: Long? = null
  , @ColumnInfo(name = "anime_id") val animeId: Int = -1
  , @ColumnInfo(name = "aired") val aired: Int = -1
  , @ColumnInfo(name = "name_chi") val nameChi: String = ""
)