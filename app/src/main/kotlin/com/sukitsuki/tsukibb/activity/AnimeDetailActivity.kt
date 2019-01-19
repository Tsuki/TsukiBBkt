package com.sukitsuki.tsukibb.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.service.TbbService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AnimeDetailActivity : AppCompatActivity() {
  private val tag: String = this.javaClass.simpleName
  private lateinit var progressBar: ProgressBar
  private lateinit var toolbar: Toolbar
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val intent = this.intent
    val animeList = intent.getParcelableExtra<AnimeList>("animeList")
    setContentView(R.layout.activity_anime_detail)
    supportActionBar
//    toolbar = findViewById(R.id.toolbar)
    progressBar = findViewById(R.id.playerProgressBar)
    doAsync {
      val session = TbbService.instance.fetchSeason(animeList.animeId).toFuture().get()
      Log.d(tag, session.toString())
      val pagesp = TbbService.instance.fetchPageSpecials(animeList.seasonId).toFuture().get()
      Log.d(tag, pagesp.toString())
      uiThread {
        progressBar.visibility = View.GONE
      }
    }
  }
}