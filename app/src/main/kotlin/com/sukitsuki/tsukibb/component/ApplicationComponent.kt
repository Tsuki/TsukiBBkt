package com.sukitsuki.tsukibb.component

import com.sukitsuki.tsukibb.module.ExoPlayerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ExoPlayerModule::class])
interface ApplicationComponent {

//  fun getExoPlayer(): ExoPlayer

}