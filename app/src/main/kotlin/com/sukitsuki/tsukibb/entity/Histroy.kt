package com.sukitsuki.tsukibb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "histroys", indices = [Index(value = ["anime_id", "seasonId", "episodeTitle"], unique = true)])
data class Histroy(
  @PrimaryKey(autoGenerate = true) val id: Long? = null
  , @ColumnInfo(name = "anime_id") val animeId: Int
  , @ColumnInfo(name = "seasonId") val seasonId: Int
  , @ColumnInfo(name = "aired") val aired: Int
  , @ColumnInfo(name = "episodeTitle") val episodeTitle: String
  , @ColumnInfo(name = "nameChi") val nameChi: String
  , @ColumnInfo(name = "seasonTitle") val seasonTitle: String
  , @ColumnInfo(name = "position") val position: Long
)