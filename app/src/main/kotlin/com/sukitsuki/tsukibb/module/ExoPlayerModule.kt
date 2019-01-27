package com.sukitsuki.tsukibb.module

import android.app.Application
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultLoadControl.*
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.adapter.DescriptionAdapter
import com.sukitsuki.tsukibb.model.Season.SeasonList.SeasonsItem.EpisodesItem
import dagger.Provides
import javax.inject.Singleton

@dagger.Module(includes = [])
class ExoPlayerModule {

  @Provides
  @Singleton
  fun providesExoPlayer(app: Application): SimpleExoPlayer {
    val loadControl = DefaultLoadControl.Builder()
      .setBackBuffer(30 * 1000, false)
      .setBufferDurationsMs(
        DEFAULT_MIN_BUFFER_MS,
        5 * 60 * 1000,
        DEFAULT_BUFFER_FOR_PLAYBACK_MS,
        DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS
      )
    return ExoPlayerFactory.newSimpleInstance(
      app.applicationContext,
      DefaultRenderersFactory(app.applicationContext),
      DefaultTrackSelector(),
      loadControl.createDefaultLoadControl()
    )
  }

  @Provides
  @Singleton
  fun providesDescriptionAdapter(app: Application): DescriptionAdapter {
    return DescriptionAdapter(app)
  }

  @Provides
  @Singleton
  fun providesCurrentEpisodesItem(): EpisodesItem {
    return EpisodesItem()
  }

  @Provides
  @Singleton
  fun providesPlayerNotificationManager(app: Application, adapter: DescriptionAdapter): PlayerNotificationManager {
    return PlayerNotificationManager
      .createWithNotificationChannel(
        app.applicationContext, "CHANNEL_ID", R.string.exo_download_notification_channel_name, 1000, adapter
      )
  }
}