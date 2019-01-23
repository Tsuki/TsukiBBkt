package com.sukitsuki.tsukibb.module

import androidx.lifecycle.ViewModelProvider
import com.sukitsuki.tsukibb.viewmodel.DaggerViewModelFactory
import dagger.Binds

@dagger.Module(includes = [])
abstract class ViewModelFactoryModule {

  @Binds
  abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

}