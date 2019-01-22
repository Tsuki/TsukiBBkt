package com.sukitsuki.tsukibb.module

import android.app.Application
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ExoPlayerModule {

  @Singleton
  @Provides
  fun provideExoPlayer(app: Application): SimpleExoPlayer {
    return ExoPlayerFactory.newSimpleInstance(app.applicationContext, DefaultTrackSelector())
  }
}