package com.sukitsuki.tsukibb.module

import androidx.lifecycle.ViewModelProvider
import com.sukitsuki.tsukibb.utils.ViewModelKey
import com.sukitsuki.tsukibb.viewmodel.AnimeListViewModel
import com.sukitsuki.tsukibb.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.multibindings.IntoMap

@dagger.Module(includes = [])
abstract class ViewModelModule {

//  @Binds
//  @IntoMap
//  @ViewModelKey(AnimeListViewModel::class)
//  abstract fun bindAnimeListViewModel(viewModel: AnimeListViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(AnimeListViewModel::class)
  abstract fun bindAnimeListViewModel(viewModel: AnimeListViewModel): AnimeListViewModel

  @Binds
  abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

}