package com.sukitsuki.tsukibb.dao

import androidx.room.*
import com.sukitsuki.tsukibb.entity.Favorite

@Dao
interface FavoriteDao {

  @Query("SELECT * from favorites where anime_id = :animeId limit 1")
  fun findByAnimeId(animeId: Int): Favorite?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertFavorite(favorite: Favorite): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertFavorite(vararg favorite: Favorite): List<Long>

  @Update
  fun updateFavorite(vararg favorite: Favorite)

  @Delete
  fun deleteFavorite(favorite: Favorite)

}