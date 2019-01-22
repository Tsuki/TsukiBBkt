package com.sukitsuki.tsukibb.component

import com.sukitsuki.tsukibb.TbbApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
//    AppModule::class,
//    ExoPlayerModule::class,
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class
  ]
)
interface AppComponent : AndroidInjector<TbbApplication> {
  abstract class Builder : AndroidInjector.Builder<TbbApplication>()
}