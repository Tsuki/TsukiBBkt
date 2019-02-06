package com.sukitsuki.tsukibb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "histories", indices = [Index(value = ["anime_id", "season_id", "episode_title"], unique = true)])
data class History(
  @PrimaryKey(autoGenerate = true) val id: Long? = null
  , @ColumnInfo(name = "anime_id") val animeId: Int = -1
  , @ColumnInfo(name = "season_id") val seasonId: Int = -1
  , @ColumnInfo(name = "episode_title") val episodeTitle: String = ""
  , @ColumnInfo(name = "aired") val aired: Int = -1
  , @ColumnInfo(name = "name_chi") val nameChi: String = ""
  , @ColumnInfo(name = "season_title") val seasonTitle: String = ""
  , @ColumnInfo(name = "position") val position: Long = -1
  , @ColumnInfo(name = "create_at") val createAt: Date = Date(java.util.Date().time)
)