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
  , @ColumnInfo(name = "aired") val aired: Int = -1
  , @ColumnInfo(name = "name_chi") val nameChi: String = ""
  , @ColumnInfo(name = "last_updated") val lastUpdated: Date = Date(0)
  , @ColumnInfo(name = "episode_title") val episodeTitle: String = ""
) {
  constructor(anime: AnimeList) : this(
    null,
    anime.animeId,
    anime.aired,
    anime.nameChi,
    Date(Instant.parse(anime.lastUpdated).millis),
    anime.episodeTitle
  )

}