package com.sukitsuki.tsukibb.service

import org.junit.Test

class TbbServiceKtTest {

  private val service: TbbService = TbbService.instance

  @Test
  fun test_fetchHPData() {
    val execute = service.fetchHPData().execute()
    val body: RespHpData? = execute.body()
    println(body)
  }

  @Test
  fun test_fetchUser() {
    val execute = service.fetchUser().execute()
    val body = execute.body()
    println(body)
  }

  @Test
  fun test_anime_list() {
    val execute = service.fetchAnimeList().execute()
    val body = execute.body()
    println(body?.joinToString("\n"))
  }

}

