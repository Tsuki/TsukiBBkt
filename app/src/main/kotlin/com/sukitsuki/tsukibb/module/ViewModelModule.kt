package com.sukitsuki.tsukibb.module

import androidx.lifecycle.ViewModel
import com.sukitsuki.tsukibb.utils.ViewModelKey
import com.sukitsuki.tsukibb.viewmodel.AnimeListViewModel
import dagger.Binds
import dagger.multibindings.IntoMap

@dagger.Module(includes = [])
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(AnimeListViewModel::class)
  abstract fun bindAnimeListViewModel(viewModel: AnimeListViewModel): ViewModel

}