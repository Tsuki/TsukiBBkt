package com.sukitsuki.tsukibb.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.service.TbbService
import org.jetbrains.anko.doAsync

class AnimeDetailActivity : AppCompatActivity() {
  private val tag: String = this.javaClass.simpleName
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val intent = this.intent
    val animeList = intent.getParcelableExtra<AnimeList>("animeList")
    Log.d(tag, animeList.toString())
    doAsync {
      val session = TbbService.instance.fetchSeason(animeList.animeId).toFuture().get()
      Log.d(tag, session.toString())
      val pagesp = TbbService.instance.fetchPageSpecials(animeList.seasonId).toFuture().get()
      Log.d(tag, pagesp.toString())
    }
    setContentView(R.layout.activity_anime_detail)
  }
}