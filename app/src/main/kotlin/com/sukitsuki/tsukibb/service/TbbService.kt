package com.sukitsuki.tsukibb.service

import com.google.gson.GsonBuilder
import com.sukitsuki.tsukibb.utils.LZString
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit
import okhttp3.ResponseBody


const val BASE_URL = "https://ebb.io/_/"


lateinit var retrofit: Retrofit

fun <S> create(serviceClass: Class<S>): S {
  val gson = GsonBuilder()
    .serializeNulls()
    .create()

  retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .client(httpBuilder.build())
    .build()

  return retrofit.create(serviceClass)
}

val httpBuilder: OkHttpClient.Builder
  get() {
    val httpClient = OkHttpClient.Builder()
      .addInterceptor(Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
          .method(original.method(), original.body())
          .build()
        val response = chain.proceed(request)
        val body = response.body()
        val newBody = ResponseBody.create(body?.contentType(), LZString.decompressFromUTF16(body?.string()))
        return@Interceptor response.newBuilder().body(newBody).build()
      })
      .readTimeout(30, TimeUnit.SECONDS)

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    httpClient.addInterceptor(loggingInterceptor)

    return httpClient
  }

interface TbbService {
  companion object {
    val instance by lazy {
      create(TbbService::class.java)
    }
  }

  @POST("logout")
  fun logout(): Call<Unit>

  @GET("user")
  fun fetchUser(): Call<Unit>

  @GET("seasons_watch_history")
  fun fetchSeasonsWatchHistory(e: Any): Call<Unit>

  @GET("hpdata")
  fun fetchHPData(): Call<RespHpData>

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

data class TimeLineBody(val year: String, val season: String)


data class SearchBody(var query: String)
