package com.sukitsuki.tsukibb.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class HpData(
  @SerializedName("calendar")
  val calendar: Calendar,
  @SerializedName("featured")
  val featured: List<FeaturedItem>?
) : Parcelable {

  @Parcelize
  data class FeaturedItem(
    @SerializedName("poster_large")
    val posterLarge: String = "",
    @SerializedName("season_title")
    val seasonTitle: String = "",
    @SerializedName("name_chi")
    val nameChi: String = "",
    @SerializedName("season_id")
    val seasonId: Int = 0,
    @SerializedName("anime_id")
    val animeId: Int = 0
  ) : Parcelable

  @Parcelize
  data class Calendar(
    @SerializedName("days")
    val days: List<DaysItem>?,
    @SerializedName("title")
    val title: String = ""
  ) : Parcelable {


    @Parcelize
    data class DaysItem(
      @SerializedName("animes")
      val animes: List<AnimesItem>?,
      @SerializedName("label")
      val label: String = ""
    ) : Parcelable {

      @Parcelize
      data class AnimesItem(
        @SerializedName("path")
        val path: String = "",
        @SerializedName("name")
        val name: String = ""
      ) : Parcelable
    }
  }
}



