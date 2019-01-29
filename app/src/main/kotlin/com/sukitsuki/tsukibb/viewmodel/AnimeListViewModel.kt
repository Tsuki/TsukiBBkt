package com.sukitsuki.tsukibb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.repository.TbbRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class AnimeListViewModel @Inject constructor(val repository: TbbRepository) : ViewModel() {
  var animeList = MutableLiveData<List<AnimeList>>().apply { value = emptyList() }
  private lateinit var disposable: Disposable

  init {
    refresh(null)
  }

  fun refresh(refreshing: ((Boolean) -> Unit)?) {
    disposable = repository
      .fetchAnimeList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doFinally { refreshing?.invoke(false);Timber.d("refresh: Finally"); }
      .subscribe { animeList.value = it }
  }


  override fun onCleared() {
    super.onCleared()
    if (::disposable.isInitialized) disposable.dispose()
  }
}
