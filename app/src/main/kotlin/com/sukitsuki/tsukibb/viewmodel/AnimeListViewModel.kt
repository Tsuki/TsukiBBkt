package com.sukitsuki.tsukibb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.repository.TbbRepository
import com.sukitsuki.tsukibb.utils.default
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AnimeListViewModel(repository: TbbRepository) : ViewModel() {
  var animeList = MutableLiveData<List<AnimeList>>().default(emptyList())
  private var disposable: Disposable

  init {
    disposable = repository
      .fetchAnimeList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { animeList.value = it }
  }

  override fun onCleared() {
    super.onCleared()
    disposable.dispose()
  }
}
