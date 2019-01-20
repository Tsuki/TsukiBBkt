package com.sukitsuki.tsukibb.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.model.Season
import com.sukitsuki.tsukibb.service.TbbService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class AnimeDetailActivity : AppCompatActivity() {
  private val tag: String = this.javaClass.simpleName
  private var toolbar: ActionBar? = null
  private lateinit var progressBar: ProgressBar
  private lateinit var tabLayout: TabLayout
  private lateinit var season: Season
  private lateinit var pagesp: String
  private lateinit var viewPager: ViewPager
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val intent = this.intent
    val animeList = intent.getParcelableExtra<AnimeList>("animeList")
    setContentView(R.layout.activity_anime_detail)
    toolbar = supportActionBar
    tabLayout = findViewById(R.id.sessionTab)
    progressBar = findViewById(R.id.playerProgressBar)
    viewPager = findViewById(R.id.episodesListView)
    doAsync {
      season = TbbService.instance.fetchSeason(animeList.animeId).toFuture().get()
      Log.d(tag, season.toString())
      pagesp = TbbService.instance.fetchPageSpecials(animeList.seasonId).toFuture().get()
      Log.d(tag, pagesp)
      uiThread {
        tabLayout.removeAllTabs()
        season.list.seasons?.forEach { i -> tabLayout.addTab(tabLayout.newTab().setText(i.seasonTitle)) }
//        viewPager.adapter = EpisodesListPageViewAdapter()
        progressBar.visibility = View.GONE
      }
    }
  }

  inner class EpisodesListPageViewAdapter : PagerAdapter() {
    override fun isViewFromObject(view: View, o: Any): Boolean {
      return o == view
    }

    override fun getCount(): Int {
      return season.list.seasons?.size ?: 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
      container.removeView(o as View)
    }
  }
}