package com.sukitsuki.tsukibb.repository

import com.sukitsuki.tsukibb.model.HpData
import com.sukitsuki.tsukibb.model.User
import org.junit.Test

class TbbRepositoryKtTest {

  private val repository: TbbRepository = TbbRepository.instance

  @Test
  fun test_fetchHPData() {
    val execute = repository.fetchHPData().toFuture()
    val body: HpData? = execute.get()
    println(body)
  }

  @Test
  fun test_fetchUser() {
    val execute = repository.fetchUser().defaultIfEmpty(User(isNull = true))
    val body = execute.toFuture().get()
    println(body)
  }

  @Test
  fun test_anime_list() {
    val execute = repository.fetchAnimeList().toFuture()
    val body = execute.get()
    println(body?.joinToString("\n"))
  }

}

