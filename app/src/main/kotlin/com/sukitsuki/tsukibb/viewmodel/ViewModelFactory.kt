package com.sukitsuki.tsukibb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sukitsuki.tsukibb.repository.TbbRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: TbbRepository) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return if (modelClass.isAssignableFrom(AnimeListViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      AnimeListViewModel(this.repository) as T
    } else {
      throw IllegalArgumentException("ViewModel Not Found")
    }
  }

}