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


class AnimeListViewModel : ViewModel() {
  var animeList = MutableLiveData<List<AnimeList>>().default(emptyList())

  init {
    init()
  }

  private fun init(): Disposable? {
    return TbbRepository.instance.fetchAnimeList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe {
        animeList.value = it
      }
  }
}
