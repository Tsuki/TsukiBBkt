package com.sukitsuki.tsukibb.module

import com.sukitsuki.tsukibb.fragment.FavoriteFragment
import com.sukitsuki.tsukibb.fragment.HomeFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
  includes = [ViewModelFactoryModule::class],
  subcomponents = [
    HomeFragment.Component::class
    , FavoriteFragment.Component::class
  ]
)
abstract class FragmentModule {

  @Binds
  @IntoMap
  @ClassKey(HomeFragment::class)
  abstract fun bindHomeFragment(builder: HomeFragment.Component.Builder): AndroidInjector.Factory<*>

  @Binds
  @IntoMap
  @ClassKey(FavoriteFragment::class)
  abstract fun bindFavoriteFragment(builder: FavoriteFragment.Component.Builder): AndroidInjector.Factory<*>

}