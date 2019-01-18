package com.sukitsuki.tsukibb.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface TbbService {
    @POST("logout")
    fun logout(): Call<Unit>

    @GET("user")
    fun fetchUser(): Call<Unit>

    @GET("seasons_watch_history")
    fun fetchSeasonsWatchHistory(e: Any): Call<Unit>

    @GET("hpdata")
    fun fetchHPData(): Call<Unit>

    @GET("anime_list")
    fun fetchAnimeList(@Body e: Any): Call<Unit>

    @GET("season_list/{season_list}")
    fun fetchSeason(@Path("season_list") season_list: String): Call<Unit>

    @GET("anime_page_sp/{anime_page_sp}")
    fun fetchPageSpecials(@Path("anime_page_sp") anime_page_sp: String): Call<Unit>

    @POST("search")
    fun fetchSearchResults(@Body params: SearchBody): Call<Unit>

    @POST("timeline_anime_list")
    fun fetchTimelineAnimeList(@Body params: TimeLineBody): Call<Unit>

    @GET("article/{article}")
    fun fetchArticle(@Path("article") article: String): Call<Unit>

    @GET("watch_history")
    fun fetchWatchHistory(): Call<Unit>

    @POST("update_watch_history")
    fun updateWatchHistory(@Body e: Any): Call<Unit>

    @POST("remove_watch_history")
    fun removeWatchHistory(@Body e: Any): Call<Unit>

    @POST("report_comment")
    fun reportCommentAbuse(@Body e: Any): Call<Unit>
}

class TimeLineBody {
    lateinit var year: String
    lateinit var season: String
}

class SearchBody {
    lateinit var query: String
}
