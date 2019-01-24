package com.sukitsuki.tsukibb.module

import androidx.lifecycle.ViewModel
import com.sukitsuki.tsukibb.repository.TbbRepository
import com.sukitsuki.tsukibb.utils.ViewModelKey
import com.sukitsuki.tsukibb.viewmodel.AnimeListViewModel
import dagger.Provides
import dagger.multibindings.IntoMap

@dagger.Module(includes = [])
class ViewModelModule {


  //TODO change to binds
  @Provides
  @IntoMap
  @ViewModelKey(AnimeListViewModel::class)
  fun provideAnimeListViewModel(repository: TbbRepository): ViewModel = AnimeListViewModel(repository)

}