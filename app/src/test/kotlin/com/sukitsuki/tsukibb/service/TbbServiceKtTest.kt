package com.sukitsuki.tsukibb.service

import com.sukitsuki.tsukibb.model.HpData
import com.sukitsuki.tsukibb.model.User
import org.junit.Test

class TbbServiceKtTest {

  private val service: TbbService = TbbService.instance

  @Test
  fun test_fetchHPData() {
    val execute = service.fetchHPData().toFuture()
    val body: HpData? = execute.get()
    println(body)
  }

  @Test
  fun test_fetchUser() {
    val execute = service.fetchUser().defaultIfEmpty(User(isNull = true))
    val body = execute.toFuture().get()
    println(body)
  }

  @Test
  fun test_anime_list() {
    val execute = service.fetchAnimeList().toFuture()
    val body = execute.get()
    println(body?.joinToString("\n"))
  }

}

