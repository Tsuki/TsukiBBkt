package com.sukitsuki.tsukibb.module

import androidx.lifecycle.ViewModelProvider
import com.sukitsuki.tsukibb.utils.ViewModelFactory
import dagger.Binds
import javax.inject.Singleton

@dagger.Module(includes = [ViewModelModule::class])
abstract class ViewModelFactoryModule {

  @Binds
  @Singleton
  abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}