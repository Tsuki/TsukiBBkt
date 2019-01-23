package com.sukitsuki.tsukibb.activity

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory.FLAG_ALLOW_NON_IDR_KEYFRAMES
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory.FLAG_DETECT_ACCESS_UNITS
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerControlView
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
import com.sukitsuki.tsukibb.model.EpisodesItem
import com.sukitsuki.tsukibb.model.Season
import com.sukitsuki.tsukibb.model.SeasonsItem
import com.sukitsuki.tsukibb.repository.TbbRepository
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject


class AnimeDetailActivity : DaggerAppCompatActivity(), PlayerControlView.VisibilityListener {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<AnimeDetailActivity> {

    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<AnimeDetailActivity>()
  }

  @dagger.Module(subcomponents = [Component::class])
  abstract class Module {
    @dagger.Binds
    @IntoMap
    @ClassKey(AnimeDetailActivity::class)
    abstract fun bind(builder: Component.Builder): AndroidInjector.Factory<*>
  }

  @Inject
  fun logInjection() {
    Log.d(this::class.java.simpleName, "Injecting ${this::class.java.simpleName}")
  }

  @Inject
  lateinit var exoPlayer: SimpleExoPlayer
  @Inject
  lateinit var playerNotificationManager: PlayerNotificationManager
  @Inject
  lateinit var descriptionAdapter: DescriptionAdapter

  private val tag: String = this.javaClass.simpleName
  private var toolbar: ActionBar? = null
  private lateinit var mProgressBar: ProgressBar
  private lateinit var mTabLayout: TabLayout
  private lateinit var mPlayerView: PlayerView
  private lateinit var mSeason: Season
  private lateinit var mPageSp: String
  private lateinit var mViewPager: ViewPager
  private lateinit var mAnimeList: AnimeList
  private lateinit var mPicasso: Picasso
  private lateinit var mTbbRepository: TbbRepository
  private var fragment = mutableListOf<Fragment>()
  private var getListSuccess = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val intent = this.intent
    mAnimeList = intent.getParcelableExtra("animeList")
    setContentView(R.layout.activity_anime_detail)
    mPicasso = Picasso.get()
    toolbar = supportActionBar
    toolbar?.setDisplayHomeAsUpEnabled(true)
    toolbar?.title = mAnimeList.nameChi

    mProgressBar = findViewById(R.id.playerProgressBar)
    mTabLayout = findViewById(R.id.sessionTab)
    mViewPager = findViewById(R.id.episodesListView)

    mPlayerView = findViewById(R.id.playerView)
    mPlayerView.setControllerVisibilityListener(this)
    mPlayerView.requestFocus()

    Log.d(this.javaClass.simpleName, "" + exoPlayer)
    mPlayerView.player = exoPlayer

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


  override fun onVisibilityChange(visibility: Int) {
    Log.d(tag, "visibility: $visibility")
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      // Use android.R.id rather than packaged id
      android.R.id.home -> {
        this.finish()
        return true
      }
      else -> {
        Log.d(tag, "itemId" + item?.itemId?.let { resources.getResourceName(it).split("\\/") })
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private var mCurrentEpisodesItem: EpisodesItem = EpisodesItem()
  fun openEpisodesItem(episodesItem: EpisodesItem, seasonsItem: SeasonsItem) {
    if (getListSuccess) {
      if (mCurrentEpisodesItem == episodesItem) {
        return
      }
      if (exoPlayer.playWhenReady) {
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
      DefaultHlsExtractorFactory(FLAG_DETECT_ACCESS_UNITS and FLAG_ALLOW_NON_IDR_KEYFRAMES)
    val hlsMediaSource = HlsMediaSource.Factory(httpDataSourceFactory)
      .setExtractorFactory(defaultHlsExtractorFactory)
      .setAllowChunklessPreparation(true)
      .createMediaSource(Uri.parse(episodesItem.sl))
    exoPlayer.playWhenReady = true
    descriptionAdapter.contentTitle = mAnimeList.nameChi
    descriptionAdapter.contentText = "${seasonsItem.seasonTitle} - ${episodesItem.title}"
    doAsync {
      descriptionAdapter.largeIcon =
        mPicasso.load("https://seaside.ebb.io/${seasonsItem.animeId}x${seasonsItem.id}.jpg").get()
    }
    playerNotificationManager
    exoPlayer.prepare(hlsMediaSource)
    mCurrentEpisodesItem = episodesItem
  }


  inner class EpisodesListFragmentAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
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