package com.sukitsuki.tsukibb.module

import com.sukitsuki.tsukibb.activity.AnimeDetailActivity
import com.sukitsuki.tsukibb.activity.FullscreenVideoActivity
import com.sukitsuki.tsukibb.activity.LoginWebViewActivity
import com.sukitsuki.tsukibb.activity.MainActivity
import com.sukitsuki.tsukibb.preference.PreferenceActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
  subcomponents = [
    MainActivity.Component::class
    , AnimeDetailActivity.Component::class
    , FullscreenVideoActivity.Component::class
    , LoginWebViewActivity.Component::class
    , PreferenceActivity.Component::class
  ]
)
abstract class ActivityModule {

  @Binds
  @IntoMap
  @ClassKey(MainActivity::class)
  abstract fun bindMainActivity(builder: MainActivity.Component.Builder): AndroidInjector.Factory<*>

  @Binds
  @IntoMap
  @ClassKey(AnimeDetailActivity::class)
  abstract fun bindAnimeDetailActivity(builder: AnimeDetailActivity.Component.Builder): AndroidInjector.Factory<*>

  @Binds
  @IntoMap
  @ClassKey(FullscreenVideoActivity::class)
  abstract fun bindFullscreenVideoActivity(builder: FullscreenVideoActivity.Component.Builder): AndroidInjector.Factory<*>

  @Binds
  @IntoMap
  @ClassKey(LoginWebViewActivity::class)
  abstract fun bindLoginWebViewActivity(builder: LoginWebViewActivity.Component.Builder): AndroidInjector.Factory<*>

  @Binds
  @IntoMap
  @ClassKey(PreferenceActivity::class)
  abstract fun bindPreferenceActivity(builder: PreferenceActivity.Component.Builder): AndroidInjector.Factory<*>

}
