package com.sukitsuki.tsukibb.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.sukitsuki.tsukibb.R
import dagger.android.AndroidInjector
import dagger.android.DaggerActivity
import javax.inject.Inject


class FullscreenVideoActivity : DaggerActivity() {
  private val uiAnimationDelay = 300L

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<FullscreenVideoActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FullscreenVideoActivity>()
  }

  @BindView(R.id.enclosing_layout)
  lateinit var mContentView: View

  @BindView(R.id.fullscreen_player_view)
  lateinit var playerView: PlayerView
  //  from playerView
//  @BindView(R.id.exo_controller)
  lateinit var controlView: View
  //  from controlView
  @BindView(R.id.exo_fullscreen_icon)
  lateinit var fullscreenIcon: ImageView

  @Inject
  lateinit var exoPlayer: SimpleExoPlayer

  private val mHideHandler = Handler()
  private val mHideRunnable = Runnable { hide() }
  private val mHidePart2Runnable = Runnable {
    mContentView.systemUiVisibility = (
      View.SYSTEM_UI_FLAG_LOW_PROFILE
        or View.SYSTEM_UI_FLAG_FULLSCREEN
//        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
      )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fullscreen_video)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    ButterKnife.bind(this)
    fullscreenIcon.setImageResource(R.drawable.exo_controls_fullscreen_exit)
    playerView.player = exoPlayer
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    delayedHide()
  }

  @OnClick(R.id.exo_fullscreen_button)
  internal fun fullscreen() {
    finish()
  }

  private fun hide() {
    mHideHandler.postDelayed(mHidePart2Runnable, 100L)
  }

  private fun delayedHide() {
    mHideHandler.removeCallbacks(mHideRunnable)
    mHideHandler.postDelayed(mHideRunnable, uiAnimationDelay)
  }
}
