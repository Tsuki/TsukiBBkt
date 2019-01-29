package com.sukitsuki.tsukibb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(val repository: FavoriteRepository) : ViewModel() {
  var animeList = MutableLiveData<List<AnimeList>>().apply { value = emptyList() }
}
