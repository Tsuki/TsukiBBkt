package com.sukitsuki.tsukibb.repository

import com.sukitsuki.tsukibb.dao.FavoriteDao
import com.sukitsuki.tsukibb.entity.Favorite
import com.sukitsuki.tsukibb.model.AnimeList

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

  fun getFavorite(animeList: AnimeList): Favorite? {
    return favoriteDao.findByAnimeId(animeList.animeId)
  }

  fun deleteFavorite(favorite: Favorite) {
    return favoriteDao.deleteFavorite(favorite)
  }

  fun insertFavorite(animeList: AnimeList) {
    favoriteDao.insertFavorite(Favorite(animeList))
    return
  }
}