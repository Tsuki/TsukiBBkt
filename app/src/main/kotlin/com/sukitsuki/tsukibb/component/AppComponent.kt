package com.sukitsuki.tsukibb.component

import android.app.Application
import com.sukitsuki.tsukibb.module.AppModule
import com.sukitsuki.tsukibb.module.ExoPlayerModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ExoPlayerModule::class])
interface AppComponent {

  fun inject(app: Application)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(app: Application): Builder

    fun appModule(app: AppModule): Builder
    fun exoPlayerModule(app: ExoPlayerModule): Builder

    fun build(): AppComponent
  }

}