package com.sukitsuki.tsukibb.model

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName
import com.sukitsuki.tsukibb.repository.TbbRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HpData(

  @SerializedName("calendar")
  val calendar: Calendar,

  @SerializedName("featured")
  val featured: List<Featured>
) : Parcelable {

  @Parcelize
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
  ) : Parcelable

  @Parcelize
  data class Calendar(

    @SerializedName("days")
    val days: ArrayList<Day>,

    @SerializedName("title")
    val title: String
  ) : Parcelable {

    @Parcelize
    data class Day(

      @SerializedName("animes")
      val animes: List<Anime>,

      @SerializedName("label")
      val label: String
    ) : Parcelable {

      @Parcelize
      data class Anime(

        @SerializedName("name")
        val name: String,

        @SerializedName("path")
        val path: String
      ) : Parcelable
    }
  }
}

class HpDataViewModel : ViewModel() {
  var hpData = MutableLiveData<HpData>()

  init {
    init()
  }

  private fun init(): Disposable? {
    return TbbRepository.instance.fetchHPData()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe {
        hpData.value = it
      }
  }
}