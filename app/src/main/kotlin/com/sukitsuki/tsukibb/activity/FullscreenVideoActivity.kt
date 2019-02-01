package com.sukitsuki.tsukibb.activity

import android.Manifest
import android.app.PictureInPictureParams
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.TextureView
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.*
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.utils.showRationale
import com.sukitsuki.tsukibb.utils.takeScreenshot
import dagger.android.AndroidInjector
import dagger.android.DaggerActivity
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onSystemUiVisibilityChange
import org.jetbrains.anko.toast
import permissions.dispatcher.*
import javax.inject.Inject


@RuntimePermissions
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
  lateinit var mPlayerView: PlayerView
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
    mPlayerView.player = exoPlayer
    window.decorView.onSystemUiVisibilityChange { if ((it and View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) delayedHide(1000L) }
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    delayedHide()
  }

  @OnClick(R.id.exo_screenshot)
  internal fun screenshotPermissionCheck() {
    if (mPlayerView.videoSurfaceView !is TextureView) {
      toast("Only support in TextureView")
      return
    }
    screenshotWithPermissionCheck()
  }

  @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
  internal fun screenshot() {
    toast("己保存在 ${takeScreenshot((mPlayerView.videoSurfaceView as TextureView).bitmap)}")
  }

  @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
  fun showRationaleForStorage(request: PermissionRequest) =
    showRationale(request, "挑战需要录音权限，应用将要申请录音权限")

  @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
  fun showDenied() {
    longToast("拒绝录音权限将无法进行挑战")
  }

  @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
  fun onNeverAskAgain() {
    longToast("您已经禁止了录音权限,是否现在去开启")
  }

  override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
    when (playbackState) {
      STATE_IDLE -> return
      STATE_BUFFERING -> mProgressBar.visibility = View.VISIBLE
      STATE_READY -> mProgressBar.visibility = View.INVISIBLE
      STATE_ENDED -> {
        exoPlayer.playWhenReady = false
        exoPlayer.stop()
        exoPlayer.seekTo(0)
      }
    }
    when (playbackState) {
      STATE_BUFFERING, STATE_READY -> window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
      STATE_IDLE, STATE_ENDED -> window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
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
