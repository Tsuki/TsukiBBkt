package com.sukitsuki.tsukibb.component

import android.app.Application
import com.sukitsuki.tsukibb.module.ApplicationModule
import com.sukitsuki.tsukibb.module.ExoPlayerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ExoPlayerModule::class, ApplicationModule::class])
interface ApplicationComponent {

  fun inject(app: Application)

//  fun getExoPlayer(): ExoPlayer

}