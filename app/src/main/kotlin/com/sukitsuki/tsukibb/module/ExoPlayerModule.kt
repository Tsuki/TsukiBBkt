package com.sukitsuki.tsukibb.module

import android.app.Application
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExoPlayerModule {

  @Provides
  @Singleton
  fun provideExoPlayer(application: Application): ExoPlayer {
    return ExoPlayerFactory.newSimpleInstance(application.applicationContext, DefaultTrackSelector())
  }
}