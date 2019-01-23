package com.sukitsuki.tsukibb.module

import android.app.Application
import com.google.gson.GsonBuilder
import com.sukitsuki.tsukibb.main.TbbEnum
import com.sukitsuki.tsukibb.main.TbbEnum.BASE_URL
import com.sukitsuki.tsukibb.repository.TbbRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rufus.lzstring4java.LZString
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [])
class AppModule(var app: Application) {

  @Singleton
  @Provides
  fun providesApplication(): Application {
    return app
  }

  @Singleton
  @Provides
  fun providesOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
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
    builder.addInterceptor(loggingInterceptor)
    return builder.build()
  }

  @Singleton
  @Provides
  fun providesRetrofit(httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      // Show primitive/String Type
      .addConverterFactory(ScalarsConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .baseUrl(BASE_URL)
      .client(httpClient)
      .build()
  }

  @Singleton
  @Provides
  fun providesTbbRepository(retrofit: Retrofit): TbbRepository = retrofit.create(TbbRepository::class.java)
}