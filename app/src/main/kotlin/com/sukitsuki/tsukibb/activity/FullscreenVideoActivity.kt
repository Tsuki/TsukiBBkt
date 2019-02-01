package com.sukitsuki.tsukibb.activity

import android.app.PictureInPictureParams
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.sukitsuki.tsukibb.R
import dagger.android.AndroidInjector
import dagger.android.DaggerActivity
import org.jetbrains.anko.sdk27.coroutines.onSystemUiVisibilityChange
import javax.inject.Inject


class FullscreenVideoActivity : DaggerActivity(), Player.EventListener {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<FullscreenVideoActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FullscreenVideoActivity>()
  }

  @BindView(R.id.enclosing_layout)
  lateinit var mContentView: View
  @BindView(R.id.playerProgressBar)
  lateinit var mProgressBar: ProgressBar
  @BindView(R.id.fullscreen_player_view)
  lateinit var playerView: PlayerView
  @BindView(R.id.exo_fullscreen_icon)
  lateinit var fullscreenIcon: ImageView

  @Inject
  lateinit var exoPlayer: SimpleExoPlayer

  private val mHideHandler = Handler()
  private val mHideRunnable = Runnable { mHideHandler.postDelayed(mHidePart2Runnable, 300L) }
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
    ButterKnife.bind(this)
    fullscreenIcon.setImageResource(R.drawable.exo_controls_fullscreen_exit)
    onPlayerStateChanged(exoPlayer.playWhenReady, exoPlayer.playbackState)
    exoPlayer.addListener(this@FullscreenVideoActivity)
    playerView.player = exoPlayer
    window.decorView.onSystemUiVisibilityChange { if ((it and View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) delayedHide(1000L) }
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    delayedHide()
  }

  override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
    when (playbackState) {
      Player.STATE_IDLE -> return
      Player.STATE_BUFFERING -> mProgressBar.visibility = View.VISIBLE
      Player.STATE_READY -> mProgressBar.visibility = View.INVISIBLE
      Player.STATE_ENDED -> {
        exoPlayer.playWhenReady = false
        exoPlayer.stop()
        exoPlayer.seekTo(0)
      }
    }
    when (playbackState) {
      Player.STATE_BUFFERING, Player.STATE_READY -> window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
      Player.STATE_IDLE, Player.STATE_ENDED -> window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
  }

  @OnClick(R.id.exo_fullscreen_button)
  internal fun fullscreen() {
    finish()
  }

  private fun delayedHide(uiAnimationDelay: Long = 1000L) {
    mHideHandler.removeCallbacks(mHideRunnable)
    mHideHandler.postDelayed(mHideRunnable, uiAnimationDelay)
  }

  private fun enterPIPMode() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      this.enterPictureInPictureMode(PictureInPictureParams.Builder().build())
    } else {
      @Suppress("DEPRECATION")
      this.enterPictureInPictureMode()
    }
  }

}
