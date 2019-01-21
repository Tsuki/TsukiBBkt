package com.sukitsuki.tsukibb.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.tabs.TabLayout
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.fragment.EpisodesListFragment
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.model.EpisodesItem
import com.sukitsuki.tsukibb.model.Season
import com.sukitsuki.tsukibb.model.SeasonsItem
import com.sukitsuki.tsukibb.service.TbbService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class AnimeDetailActivity : AppCompatActivity() {
  private val tag: String = this.javaClass.simpleName
  private var toolbar: ActionBar? = null
  private lateinit var progressBar: ProgressBar
  private lateinit var tabLayout: TabLayout
  private lateinit var playerView: PlayerView
  private lateinit var season: Season
  private lateinit var pageSp: String
  private lateinit var viewPager: ViewPager
  private lateinit var exoPlayer: SimpleExoPlayer
  private var fragment = mutableListOf<Fragment>()
  private var getListSuccess = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val intent = this.intent
    val animeList = intent.getParcelableExtra<AnimeList>("animeList")
    setContentView(R.layout.activity_anime_detail)

    toolbar = supportActionBar
    toolbar?.setDisplayHomeAsUpEnabled(true)
    toolbar?.title = animeList.nameChi

    progressBar = findViewById(R.id.playerProgressBar)
    tabLayout = findViewById(R.id.sessionTab)
    viewPager = findViewById(R.id.episodesListView)
    playerView = findViewById(R.id.playerView)


    exoPlayer = ExoPlayerFactory.newSimpleInstance(applicationContext, DefaultTrackSelector())
    doAsync {
      season = TbbService.instance.fetchSeason(animeList.animeId).toFuture().get()
      pageSp = TbbService.instance.fetchPageSpecials(animeList.seasonId).toFuture().get()
      getListSuccess = season.status == 200
      uiThread {
        season.list.seasons.forEach { i: SeasonsItem ->
          val episodesListFragment = EpisodesListFragment()
          episodesListFragment.arguments = bundleOf("SeasonsItem" to i)
          fragment.add(episodesListFragment)
        }
        viewPager.adapter = EpisodesListFragmentAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
      }
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
        Log.d(tag, "itemId" + item?.itemId?.let { resources.getResourceName(it).split("\\/") })
      }
    }
    return super.onOptionsItemSelected(item)
  }

  fun openEpisodesItem(episodesItem: EpisodesItem) {
    if (getListSuccess) {
      val dataFactory = DefaultDataSourceFactory(applicationContext, Util.getUserAgent(applicationContext, "ebb"))
      val httpDataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(applicationContext, "ebb"), null)
      httpDataSourceFactory.defaultRequestProperties.set("Referer", "https://ebb.io/anime/228x797")
      val hlsMediaSource = HlsMediaSource.Factory(dataFactory).createMediaSource(Uri.parse(episodesItem.sl))
      exoPlayer.prepare(hlsMediaSource)
    }
//    Util.getUserAgent(applicationContext, "TsukiBB")
//    HlsMediaSource.Factory()
//    exoPlayer.prepare(HlsMediaSource(uri, dataSourceFactory, handler, eventListener))
//    playerView.player.
    Log.d(tag, "" + episodesItem)
  }

  inner class EpisodesListFragmentAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getPageTitle(position: Int): CharSequence? {
      return season.list.seasons[position].seasonTitle
    }

    override fun getItem(position: Int): Fragment {
      return fragment[position]
    }

    override fun getCount(): Int {
      return season.list.seasons.size
    }
  }
}