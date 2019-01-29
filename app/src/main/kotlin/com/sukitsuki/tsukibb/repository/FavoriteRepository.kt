package com.sukitsuki.tsukibb.repository

import com.sukitsuki.tsukibb.dao.FavoriteDao
import com.sukitsuki.tsukibb.entity.Favorite
import com.sukitsuki.tsukibb.model.AnimeList

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

  fun getAllFavorite(): List<Favorite> {
    return favoriteDao.findAll()
  }

  fun getFavorite(animeList: AnimeList): Favorite? {
    return favoriteDao.findByAnimeId(animeList.animeId)
  }

  fun deleteFavorite(favorite: Favorite) {
    return favoriteDao.delete(favorite)
  }

  fun insertFavorite(animeList: AnimeList) {
    favoriteDao.insert(Favorite(animeList))
    return
  }
}