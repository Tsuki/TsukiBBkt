package com.sukitsuki.tsukibb.service

import com.google.gson.annotations.SerializedName

data class RespHpData(
  @SerializedName("calendar")
  val calendar: Calendar,
  @SerializedName("featured")
  val featured: List<Featured>
) {
  data class Featured(
    @SerializedName("anime_id")
    val animeId: Int,
    @SerializedName("name_chi")
    val nameChi: String,
    @SerializedName("poster_large")
    val posterLarge: String,
    @SerializedName("season_id")
    val seasonId: Int,
    @SerializedName("season_title")
    val seasonTitle: String
  )

  data class Calendar(
    @SerializedName("days")
    val days: List<Day>,
    @SerializedName("title")
    val title: String
  ) {
    data class Day(
      @SerializedName("animes")
      val animes: List<Anime>,
      @SerializedName("label")
      val label: String
    ) {
      data class Anime(
        @SerializedName("name")
        val name: String,
        @SerializedName("path")
        val path: String
      )
    }
  }
}