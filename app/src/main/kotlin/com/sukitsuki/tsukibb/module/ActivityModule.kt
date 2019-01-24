package com.sukitsuki.tsukibb.module

import com.sukitsuki.tsukibb.activity.AnimeDetailActivity
import com.sukitsuki.tsukibb.activity.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainActivity.Component::class, AnimeDetailActivity.Component::class])
abstract class ActivityModule {

  @dagger.Binds
  @IntoMap
  @ClassKey(MainActivity::class)
  abstract fun bindMainActivity(builder: MainActivity.Component.Builder): AndroidInjector.Factory<*>

  @Binds
  @IntoMap
  @ClassKey(AnimeDetailActivity::class)
  abstract fun bindAnimeDetailActivity(builder: AnimeDetailActivity.Component.Builder): AndroidInjector.Factory<*>

}
