package com.sukitsuki.tsukibb.repository

import com.sukitsuki.tsukibb.AppEnum.EbbUserAgent
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.model.HpData
import com.sukitsuki.tsukibb.model.Season
import com.sukitsuki.tsukibb.model.User
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.*


interface TbbRepository {
  @POST("logout")
  fun logout(): Observable<String>

  @GET("user")
  fun fetchUser(): Maybe<User>

  @GET("seasons_watch_history")
  fun fetchSeasonsWatchHistory(e: Any): Observable<String>

  @GET("hpdata")
  fun fetchHPData(): Observable<HpData>

  @GET("anime_list")
  fun fetchAnimeList(): Observable<List<AnimeList>>

  @Headers("User-Agent: $EbbUserAgent")
  @GET("season_list/{season_list}")
  fun fetchSeason(@Path("season_list") season_list: Int): Observable<Season>

  @GET("anime_page_sp/{anime_page_sp}")
  fun fetchPageSpecials(@Path("anime_page_sp") anime_page_sp: Int): Observable<String>

  @POST("search")
  fun fetchSearchResults(@Body params: SearchBody): Observable<String>

  @POST("timeline_anime_list")
  fun fetchTimelineAnimeList(@Body params: TimeLineBody): Observable<String>

  @GET("article/{article}")
  fun fetchArticle(@Path("article") article: String): Observable<String>

  @GET("watch_history")
  fun fetchWatchHistory(): Observable<String>

  @POST("update_watch_history")
  fun updateWatchHistory(@Body e: Any): Observable<String>

  @POST("remove_watch_history")
  fun removeWatchHistory(@Body e: Any): Observable<String>

  @POST("report_comment")
  fun reportCommentAbuse(@Body e: Any): Observable<String>

  data class TimeLineBody(val year: String, val season: String)


  data class SearchBody(var query: String)
}
