package com.sukitsuki.tsukibb.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sukitsuki.tsukibb.utils.ViewModelKey
import com.sukitsuki.tsukibb.viewmodel.AnimeListViewModel
import com.sukitsuki.tsukibb.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.multibindings.IntoMap

abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(AnimeListViewModel::class)
  abstract fun bindCodeDetailViewModel(viewModel: AnimeListViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}