package com.sukitsuki.tsukibb.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Anime(
  @SerializedName("is_ended")
  val isEnded: Int = 0,
  @SerializedName("keywords")
  val keywords: String = "",
  @SerializedName("author")
  val author: String = "",
  @SerializedName("name_chi")
  val nameChi: String = "",
  @SerializedName("description")
  val description: String = "",
  @SerializedName("id")
  val id: Int = 0,
  @SerializedName("name_jpn")
  val nameJpn: String = "",
  @SerializedName("tags")
  val tags: List<TagsItem>?
) : Parcelable

@Parcelize
data class EpisodesItem(
  @SerializedName("duration")
  val duration: Int = 0,
  @SerializedName("sl")
  val sl: String? = "",
  @SerializedName("id")
  val id: String = "",
  @SerializedName("title")
  val title: String = ""
) : Parcelable

@Parcelize
data class TagsItem(
  @SerializedName("color")
  val color: String = "",
  @SerializedName("name")
  val name: String = "",
  @SerializedName("id")
  val id: Int = 0
) : Parcelable

@Parcelize
data class SeasonsItem(
  @SerializedName("studio")
  val studio: String = "",
  @SerializedName("aired")
  val aired: Int = 0,
  @SerializedName("last_updated")
  val lastUpdated: String = "",
  @SerializedName("season_title")
  val seasonTitle: String = "",
  @SerializedName("is_hidden")
  val isHidden: Int = 0,
  @SerializedName("id")
  val id: Int = 0,
  @SerializedName("sort_index")
  val sortIndex: Int = 0,
  @SerializedName("anime_id")
  val animeId: Int = 0,
  @SerializedName("episodes")
  val episodes: List<EpisodesItem>
) : Parcelable

@Parcelize
data class SeasonList(
  @SerializedName("seasons")
  val seasons: List<SeasonsItem>,
  @SerializedName("anime")
  val anime: Anime
) : Parcelable

@Parcelize
data class Season(
  @SerializedName("success")
  val success: Boolean = false,
  @SerializedName("list")
  val list: SeasonList,
  @SerializedName("status")
  val status: Int = 0
) : Parcelable


