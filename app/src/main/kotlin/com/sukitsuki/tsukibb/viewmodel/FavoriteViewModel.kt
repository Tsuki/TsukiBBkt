package com.sukitsuki.tsukibb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukitsuki.tsukibb.entity.Favorite
import com.sukitsuki.tsukibb.repository.FavoriteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(val repository: FavoriteRepository) : ViewModel() {
  var favorites = MutableLiveData<List<Favorite>>().apply { value = emptyList() }

  init {
    refresh(null)
  }

  private fun refresh(refreshing: ((Boolean) -> Unit)?): Disposable? {
    return repository
      .getAllFavorite()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doFinally { refreshing?.invoke(false);Timber.d("refresh: Finally"); }
      .subscribe { favorites.value = it }
  }
}
