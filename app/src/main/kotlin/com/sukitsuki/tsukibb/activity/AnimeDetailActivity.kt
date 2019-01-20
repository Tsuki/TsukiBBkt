package com.sukitsuki.tsukibb.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
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
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val intent = this.intent
    val animeList = intent.getParcelableExtra<AnimeList>("animeList")
    setContentView(R.layout.activity_anime_detail)
    toolbar = supportActionBar
    tabLayout = findViewById(R.id.sessionTab)
    progressBar = findViewById(R.id.playerProgressBar)
    doAsync {
      season = TbbService.instance.fetchSeason(animeList.animeId).toFuture().get()
//      Log.d(tag, season.list.season.toString())
      Log.d(tag, season.toString())
      pagesp = TbbService.instance.fetchPageSpecials(animeList.seasonId).toFuture().get()
      Log.d(tag, pagesp)
      uiThread {
        tabLayout.removeAllTabs()
        season.list.seasons?.forEach { i -> tabLayout.addTab(tabLayout.newTab().setText(i.seasonTitle)) }
        progressBar.visibility = View.GONE
      }
    }
  }
}