package com.sukitsuki.tsukibb.repository

import com.google.gson.GsonBuilder
import com.sukitsuki.tsukibb.main.TbbEnum
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.model.HpData
import com.sukitsuki.tsukibb.model.Season
import com.sukitsuki.tsukibb.model.User
import io.reactivex.Maybe
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rufus.lzstring4java.LZString
import java.util.concurrent.TimeUnit


const val BASE_URL = "https://ebb.io/_/"


lateinit var retrofit: Retrofit

fun <S> create(repositoryClass: Class<S>): S {
  val gson = GsonBuilder()
    .serializeNulls()
    .create()

  retrofit = Retrofit.Builder()
    // Show primitive/String Type
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .baseUrl(BASE_URL)
    .client(httpBuilder.build())
    .build()

  return retrofit.create(repositoryClass)
}
//login telegram https://oauth.telegram.org/auth?bot_id=639045451&origin=https%3A%2F%2Febb.io

val httpBuilder: OkHttpClient.Builder
  get() {
    val httpClient = OkHttpClient.Builder()
      .addInterceptor(Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
          .method(original.method(), original.body())
          // FIXME change to login method
          .addHeader("user-agent", TbbEnum.TbbUserAgent)
          .build()
        val response = chain.proceed(request)
        val body = response.body()
        val newBody = ResponseBody.create(body?.contentType(), LZString.decompressFromUTF16(body?.string()))
        return@Interceptor response.newBuilder().body(newBody).build()
      })
      .readTimeout(30, TimeUnit.SECONDS)

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    httpClient.addInterceptor(loggingInterceptor)

    return httpClient
  }

interface TbbRepository {
  companion object {
    val instance by lazy {
      create(TbbRepository::class.java)
    }
  }

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
}

data class TimeLineBody(val year: String, val season: String)


data class SearchBody(var query: String)
