package com.sukitsuki.tsukibb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sukitsuki.tsukibb.model.AnimeList
import org.joda.time.Instant
import java.sql.Date

@Entity(tableName = "favorites", indices = [Index(value = ["anime_id"], unique = true)])
data class Favorite(
  @PrimaryKey(autoGenerate = true) val id: Long? = null
  , @ColumnInfo(name = "anime_id") val animeId: Int = -1
  , @ColumnInfo(name = "season_title") val seasonTitle: String = ""
  , @ColumnInfo(name = "season_id") val seasonId: Int = -1
  , @ColumnInfo(name = "aired") val aired: Int = -1
  , @ColumnInfo(name = "name_chi") val nameChi: String = ""
  , @ColumnInfo(name = "last_updated") var lastUpdated: Date = Date(0)
  , @ColumnInfo(name = "episode_title") var episodeTitle: String = ""
  , @ColumnInfo(name = "create_at") val createAt: Date = Date(java.util.Date().time)
) {
  constructor(anime: AnimeList) : this(
    null,
    anime.animeId,
    anime.seasonTitle,
    anime.seasonId,
    anime.aired,
    anime.nameChi,
    Date(Instant.parse(anime.lastUpdated).millis),
    anime.episodeTitle
  )

}