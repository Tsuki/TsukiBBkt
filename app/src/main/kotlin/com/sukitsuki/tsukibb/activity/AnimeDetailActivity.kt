package com.sukitsuki.tsukibb.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.SurfaceView
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_BUFFERING
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory.FLAG_ALLOW_NON_IDR_KEYFRAMES
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory.FLAG_DETECT_ACCESS_UNITS
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.adapter.DescriptionAdapter
import com.sukitsuki.tsukibb.fragment.EpisodesListFragment
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.model.Season
import com.sukitsuki.tsukibb.model.Season.SeasonList.SeasonsItem
import com.sukitsuki.tsukibb.model.Season.SeasonList.SeasonsItem.EpisodesItem
import com.sukitsuki.tsukibb.repository.TbbRepository
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import timber.log.Timber
import javax.inject.Inject


class AnimeDetailActivity : DaggerAppCompatActivity(), Player.EventListener {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<AnimeDetailActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<AnimeDetailActivity>()
  }

  @Inject
  fun logInjection() {
    Timber.d("Injecting ${this::class.java.simpleName}")
  }

  @Inject
  lateinit var exoPlayer: SimpleExoPlayer
  @Inject
  lateinit var playerNotificationManager: PlayerNotificationManager
  @Inject
  lateinit var descriptionAdapter: DescriptionAdapter
  @Inject
  lateinit var mTbbRepository: TbbRepository

  @BindView(R.id.playerProgressBar)
  lateinit var mProgressBar: ProgressBar
  @BindView(R.id.sessionTab)
  lateinit var mTabLayout: TabLayout
  @BindView(R.id.playerView)
  lateinit var mPlayerView: PlayerView
  @BindView(R.id.episodesListView)
  lateinit var mViewPager: ViewPager

  private val mResultCodeForFullscreenVideoActivity: Int = 100
  private var mCurrentEpisodesItem: EpisodesItem = EpisodesItem()
  private var toolbar: ActionBar? = null
  private var fragment = mutableListOf<Fragment>()
  private var getListSuccess = false

  private lateinit var mSeason: Season
  private lateinit var mPageSp: String
  private lateinit var mAnimeList: AnimeList
  private lateinit var mPicasso: Picasso

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val intent = this.intent
    mAnimeList = intent.getParcelableExtra("animeList")
    setContentView(R.layout.activity_anime_detail)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    ButterKnife.bind(this)
    mPicasso = Picasso.get()
    toolbar = supportActionBar
    toolbar?.setDisplayHomeAsUpEnabled(true)
    toolbar?.title = mAnimeList.nameChi
    mPlayerView.player = exoPlayer
    exoPlayer.addListener(this@AnimeDetailActivity)
    setupPlayerView()
    doAsync {
      mSeason = mTbbRepository.fetchSeason(mAnimeList.animeId).toFuture().get()
      mPageSp = mTbbRepository.fetchPageSpecials(mAnimeList.seasonId).toFuture().get()
      getListSuccess = mSeason.status == 200
      uiThread {
        mSeason.list.seasons.forEach { i: SeasonsItem ->
          val episodesListFragment = EpisodesListFragment()
          episodesListFragment.arguments = bundleOf("SeasonsItem" to i)
          fragment.add(episodesListFragment)
        }
        mViewPager.adapter = EpisodesListFragmentAdapter(supportFragmentManager)
        mTabLayout.setupWithViewPager(mViewPager)
      }
    }
  }

  private fun setupPlayerView() {
    exoPlayer.clearVideoSurface()
    exoPlayer.setVideoSurfaceView(mPlayerView.videoSurfaceView as SurfaceView)
    mPlayerView.requestFocus()
  }

  override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
    if (playWhenReady && playbackState == STATE_BUFFERING) {
      mProgressBar.visibility = View.VISIBLE
    } else {
      mProgressBar.visibility = View.INVISIBLE
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      // Use android.R.id rather than packaged id
      android.R.id.home -> {
        this.finish()
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

  fun openEpisodesItem(episodesItem: EpisodesItem, seasonsItem: SeasonsItem) {
    if (getListSuccess) {
      if (mCurrentEpisodesItem == episodesItem) {
        return
      }
      if (exoPlayer.playWhenReady && exoPlayer.playbackState == STATE_READY) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@AnimeDetailActivity)
        builder.setMessage(getString(R.string.alert_sure_to_change))
          .setPositiveButton("Yes") { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) replace(episodesItem, seasonsItem)
          }
          .setNegativeButton("No", null).show()
      } else {
        replace(episodesItem, seasonsItem)
      }
    } else {
      Toast.makeText(applicationContext, R.string.noti_cannot_load_video_source, Toast.LENGTH_LONG).show()
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
    exoPlayer.playWhenReady = true
    descriptionAdapter.contentTitle = mAnimeList.nameChi
    descriptionAdapter.contentText = "${seasonsItem.seasonTitle} - ${episodesItem.title}"
    descriptionAdapter.animeList = mAnimeList
    doAsync {
      descriptionAdapter.largeIcon =
        mPicasso.load("https://seaside.ebb.io/${seasonsItem.animeId}x${seasonsItem.id}.jpg").get()
    }
    playerNotificationManager
    exoPlayer.prepare(hlsMediaSource)
    mCurrentEpisodesItem = episodesItem
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    setupPlayerView()
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