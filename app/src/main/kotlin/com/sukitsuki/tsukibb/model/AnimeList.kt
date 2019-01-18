package com.sukitsuki.tsukibb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimeList(

  @SerializedName("aired")
  val aired: Int,

  @SerializedName("anime_id")
  val animeId: Int,

  @SerializedName("episode_title")
  val episodeTitle: String,

  @SerializedName("is_ended")
  val isEnded: Int,

  @SerializedName("is_hidden")
  val isHidden: Int,

  @SerializedName("last_updated")
  val lastUpdated: String,

  @SerializedName("name_chi")
  val nameChi: String,

  @SerializedName("name_jpn")
  val nameJpn: String,

  @SerializedName("season_id")
  val seasonId: Int,

  @SerializedName("season_title")
  val seasonTitle: String
) : Parcelable