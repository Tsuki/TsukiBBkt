package com.sukitsuki.tsukibb.module

import android.app.Application
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
    return ExoPlayerFactory.newSimpleInstance(app.applicationContext, DefaultTrackSelector())
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