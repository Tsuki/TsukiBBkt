package com.sukitsuki.tsukibb.repository

import com.sukitsuki.tsukibb.AppConst.EbbUserAgent
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.model.HpData
import com.sukitsuki.tsukibb.model.Season
import com.sukitsuki.tsukibb.model.User
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.*


interface TbbRepository {
  @POST("_/logout")
  fun logout(): Observable<String>

  @GET("_/user")
  fun fetchUser(): Maybe<User>

  @GET("_/seasons_watch_history")
  fun fetchSeasonsWatchHistory(e: Any): Observable<String>

  @GET("_/hpdata")
  fun fetchHPData(): Observable<HpData>

  @GET("_/anime_list")
  fun fetchAnimeList(): Observable<List<AnimeList>>

  @Headers("User-Agent: $EbbUserAgent")
  @GET("_/season_list/{season_list}")
  fun fetchSeason(@Path("season_list") season_list: Int): Observable<Season>

  @GET("_/anime_page_sp/{anime_page_sp}")
  fun fetchPageSpecials(@Path("anime_page_sp") anime_page_sp: Int): Observable<String>

  @POST("_/search")
  fun fetchSearchResults(@Body params: SearchBody): Observable<String>

  @POST("_/timeline_anime_list")
  fun fetchTimelineAnimeList(@Body params: TimeLineBody): Observable<String>

  @GET("_/article/{article}")
  fun fetchArticle(@Path("article") article: String): Observable<String>

  @GET("_/watch_history")
  fun fetchWatchHistory(): Observable<String>

  @POST("_/update_watch_history")
  fun updateWatchHistory(@Body e: Any): Observable<String>

  @POST("_/remove_watch_history")
  fun removeWatchHistory(@Body e: Any): Observable<String>

  @POST("_/report_comment")
  fun reportCommentAbuse(@Body e: Any): Observable<String>

  @GET("auth/telegram")
  fun authTelegram(@QueryMap e: Any): Observable<String>

  @GET("auth/google")
  fun authGoogle(@QueryMap e: Any): Observable<String>

  data class TimeLineBody(val year: String, val season: String)

  data class SearchBody(var query: String)
}
