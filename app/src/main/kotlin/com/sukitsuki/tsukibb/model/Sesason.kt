package com.sukitsuki.tsukibb.model

import com.google.gson.annotations.SerializedName

data class Sesason(
  @SerializedName("list")
  val list: List,
  @SerializedName("status")
  val status: Int
) {
  data class List(
    @SerializedName("anime")
    val anime: Anime,
    @SerializedName("seasons")
    val seasons: List
  ) {
    data class Season(
      @SerializedName("aired")
      val aired: Int,
      @SerializedName("anime_id")
      val animeId: Int,
      @SerializedName("episodes")
      val episodes: List,
      @SerializedName("id")
      val id: Int,
      @SerializedName("is_hidden")
      val isHidden: Int,
      @SerializedName("last_updated")
      val lastUpdated: String,
      @SerializedName("season_title")
      val seasonTitle: String,
      @SerializedName("sort_index")
      val sortIndex: Int,
      @SerializedName("studio")
      val studio: String
    ) {
      data class Episode(
        @SerializedName("duration")
        val duration: Int,
        @SerializedName("id")
        val id: String,
        @SerializedName("sl")
        val sl: Any,
        @SerializedName("title")
        val title: String
      )
    }

    data class Anime(
      @SerializedName("author")
      val author: String,
      @SerializedName("description")
      val description: String,
      @SerializedName("id")
      val id: Int,
      @SerializedName("is_ended")
      val isEnded: Int,
      @SerializedName("keywords")
      val keywords: String,
      @SerializedName("name_chi")
      val nameChi: String,
      @SerializedName("name_jpn")
      val nameJpn: String,
      @SerializedName("tags")
      val tags: List
    ) {
      data class Tag(
        @SerializedName("color")
        val color: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
      )
    }
  }
}