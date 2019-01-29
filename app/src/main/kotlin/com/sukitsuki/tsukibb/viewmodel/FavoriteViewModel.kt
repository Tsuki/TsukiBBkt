package com.sukitsuki.tsukibb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.repository.FavoriteRepository
import com.sukitsuki.tsukibb.utils.default
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(repository: FavoriteRepository) : ViewModel() {

  var animeList = MutableLiveData<List<AnimeList>>().default(emptyList())
}
