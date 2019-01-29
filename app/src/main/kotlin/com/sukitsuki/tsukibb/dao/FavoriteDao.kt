package com.sukitsuki.tsukibb.dao

import androidx.room.Dao
import androidx.room.Query
import com.sukitsuki.tsukibb.entity.Favorite

@Dao
interface FavoriteDao : BaseDao<Favorite> {

  @Query("SELECT * from favorites")
  fun findAll(): List<Favorite>

  @Query("SELECT * from favorites where anime_id = :animeId limit 1")
  fun findByAnimeId(animeId: Int): Favorite?

}