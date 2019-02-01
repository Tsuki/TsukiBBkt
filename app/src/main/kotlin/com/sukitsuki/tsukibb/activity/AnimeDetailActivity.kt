package com.sukitsuki.tsukibb.activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.BindColor
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.*
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory.FLAG_ALLOW_NON_IDR_KEYFRAMES
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory.FLAG_DETECT_ACCESS_UNITS
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.tabs.TabLayout
import com.sukitsuki.tsukibb.GlideApp
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.adapter.DescriptionAdapter
import com.sukitsuki.tsukibb.entity.Favorite
import com.sukitsuki.tsukibb.fragment.EpisodesListFragment
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.model.Season
import com.sukitsuki.tsukibb.model.Season.SeasonList.SeasonsItem
import com.sukitsuki.tsukibb.model.Season.SeasonList.SeasonsItem.EpisodesItem
import com.sukitsuki.tsukibb.repository.FavoriteRepository
import com.sukitsuki.tsukibb.repository.TbbRepository
import com.sukitsuki.tsukibb.utils.showRationale
import com.sukitsuki.tsukibb.utils.takeScreenshot
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import jp.wasabeef.blurry.Blurry
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import permissions.dispatcher.*
import timber.log.Timber
import javax.inject.Inject

@RuntimePermissions
class AnimeDetailActivity : DaggerAppCompatActivity(), Player.EventListener {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<AnimeDetailActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<AnimeDetailActivity>()
  }

  @Inject
  lateinit var exoPlayer: SimpleExoPlayer
  @Inject
  lateinit var descriptionAdapter: DescriptionAdapter
  @Inject
  lateinit var mTbbRepository: TbbRepository
  @Inject
  lateinit var mFavoriteRepository: FavoriteRepository

  @BindView(R.id.playerProgressBar)
  lateinit var mProgressBar: ProgressBar
  @BindView(R.id.sessionTab)
  lateinit var mTabLayout: TabLayout
  @BindView(R.id.playerView)
  lateinit var mPlayerView: PlayerView
  @BindView(R.id.episodesListView)
  lateinit var mViewPager: ViewPager
  @BindView(R.id.toolbar)
  lateinit var mToolbar: Toolbar
  @BindView(R.id.bookmarkButton)
  lateinit var mBookmarkButton: ImageButton
  @BindView(R.id.exo_artwork)
  lateinit var mArtwork: ImageView
  @BindView(R.id.exo_artwork_mask)
  lateinit var mArtworkMask: ImageView

  private val mResultCodeForFullscreenVideoActivity: Int = 100
  private val compositeDisposable: CompositeDisposable = CompositeDisposable()
  private var mCurrentEpisodesItem: EpisodesItem = EpisodesItem()
  private var toolbar: ActionBar? = null
  private var mFavorite: Favorite? = null
  private var fragment = mutableListOf<Fragment>()
  private var getListSuccess = false

  private lateinit var mSeason: Season
  private lateinit var mPageSp: String
  private lateinit var mAnimeList: AnimeList
  private val mImageLoader by lazy { GlideApp.with(this) }
  private val mBlurry by lazy { Blurry.with(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_anime_detail)
    ButterKnife.bind(this)
    setSupportActionBar(mToolbar)
    toolbar = supportActionBar
    toolbar?.setDisplayHomeAsUpEnabled(true)
    mPlayerView.player = exoPlayer
    exoPlayer.addListener(this@AnimeDetailActivity)
  }

  override fun onResume() {
    super.onResume()
    setupPlayerView()
    initAnimeList()
  }

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.clear()
  }

  private fun initAnimeList() {
    mAnimeList = intent.getParcelableExtra("animeList")
    toolbar?.title = mAnimeList.nameChi
    mImageLoader.load("https://seaside.ebb.io/${mAnimeList.animeId}x${mAnimeList.seasonId}.jpg").into(mArtworkMask)
    mImageLoader.load("https://seaside.ebb.io/${mAnimeList.animeId}x${mAnimeList.seasonId}.jpg").into(mArtwork)
    doAsync {
      mSeason = mTbbRepository.fetchSeason(mAnimeList.animeId).toFuture().get()
      getListSuccess = mSeason.status == 200
      mFavorite = mFavoriteRepository.getFavorite(mAnimeList)
      mPageSp = mTbbRepository.fetchPageSpecials(mAnimeList.seasonId).toFuture().get()
      uiThread {
        mSeason.list.seasons.forEach { i: SeasonsItem ->
          val episodesListFragment = EpisodesListFragment()
          episodesListFragment.arguments = bundleOf("SeasonsItem" to i)
          fragment.add(episodesListFragment)
        }
        mViewPager.adapter = EpisodesListFragmentAdapter(supportFragmentManager)
        mTabLayout.setupWithViewPager(mViewPager)
        updateBookmark()
        if (exoPlayer.playbackState == STATE_IDLE) {
          mBlurry.radius(50).sampling(5).capture(mArtworkMask).into(mArtworkMask)
          mArtwork.visibility = View.VISIBLE
          // mArtworkMask.visibility = View.VISIBLE
        }
      }
    }
  }

  private fun setupPlayerView() {
    exoPlayer.clearVideoSurface()
    when (mPlayerView.videoSurfaceView) {
      is SurfaceView -> exoPlayer.setVideoSurfaceView(mPlayerView.videoSurfaceView as SurfaceView)
      is TextureView -> exoPlayer.setVideoTextureView(mPlayerView.videoSurfaceView as TextureView)
    }
    mPlayerView.requestFocus()
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

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      // Use android.R.id rather than packaged id
      android.R.id.home -> {
        onBackPressed()
        return true
      }
      else -> {
        Timber.d("itemId${item?.itemId?.let { resources.getResourceName(it).split("\\/") }}")
      }
    }
    return super.onOptionsItemSelected(item)
  }

  @OnClick(R.id.exo_fullscreen_button)
  internal fun fullscreen() {
    val intent = Intent(applicationContext, FullscreenVideoActivity::class.java)
    startActivityForResult(intent, mResultCodeForFullscreenVideoActivity)
  }

  @BindColor(R.color.colorAccent)
  @JvmField
  var bookmarkActive: Int = 0

  @BindColor(R.color.primaryTextColor)
  @JvmField
  var bookmarkInactive: Int = 0

  @OnClick(R.id.bookmarkButton)
  internal fun bookmark() {
    Timber.d("bookmark: $mAnimeList")
    doAsync {
      mFavorite?.let { mFavoriteRepository.deleteFavorite(mFavorite!!) }
        ?: run { mFavoriteRepository.insertFavorite(mAnimeList) }
      mFavorite = mFavoriteRepository.getFavorite(mAnimeList)
      uiThread { updateBookmark() }
    }
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

  private fun updateBookmark() {
    mFavorite?.let { mBookmarkButton.setColorFilter(bookmarkActive) }
      ?: run { mBookmarkButton.setColorFilter(bookmarkInactive) }
  }

  fun openEpisodesItem(episodesItem: EpisodesItem, seasonsItem: SeasonsItem) {
    if (getListSuccess) {
      if (mCurrentEpisodesItem == episodesItem) {
        return
      }
      if (exoPlayer.playWhenReady && exoPlayer.playbackState == STATE_READY) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@AnimeDetailActivity)
        builder.setMessage(R.string.alert_sure_to_change)
          .setPositiveButton("Yes") { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) replace(episodesItem, seasonsItem)
          }
          .setNegativeButton("No", null).show()
      } else {
        replace(episodesItem, seasonsItem)
      }
    } else {
      longToast(R.string.noti_cannot_load_video_source)
    }
  }

  private fun replace(episodesItem: EpisodesItem, seasonsItem: SeasonsItem) {
    val httpDataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(applicationContext, "ebb"), null)
    httpDataSourceFactory.defaultRequestProperties.set("Referer", "https://ebb.io/anime/")
    val defaultHlsExtractorFactory =
      DefaultHlsExtractorFactory(FLAG_DETECT_ACCESS_UNITS or FLAG_ALLOW_NON_IDR_KEYFRAMES)
    val hlsMediaSource = HlsMediaSource.Factory(httpDataSourceFactory)
      .setExtractorFactory(defaultHlsExtractorFactory)
      .setAllowChunklessPreparation(true)
      .createMediaSource(Uri.parse(episodesItem.sl))
    descriptionAdapter.contentTitle = mAnimeList.nameChi
    descriptionAdapter.contentText = "${seasonsItem.seasonTitle} - ${episodesItem.title}"
    descriptionAdapter.animeList = mAnimeList
    doAsync {
      val request = mImageLoader.asBitmap()
        .load("https://seaside.ebb.io/${seasonsItem.animeId}x${seasonsItem.id}.jpg").submit()
      descriptionAdapter.largeIcon = request.get()
    }
    exoPlayer.prepare(hlsMediaSource)
    exoPlayer.playWhenReady = true
    mCurrentEpisodesItem = episodesItem
  }

  inner class EpisodesListFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getPageTitle(position: Int): CharSequence? {
      return mSeason.list.seasons[position].seasonTitle
    }

    override fun getItem(position: Int): Fragment {
      return fragment[position]
    }

    override fun getCount(): Int {
      return mSeason.list.seasons.size
    }
  }
}