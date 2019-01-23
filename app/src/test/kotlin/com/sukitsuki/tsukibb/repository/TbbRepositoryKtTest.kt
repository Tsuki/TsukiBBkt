package com.sukitsuki.tsukibb.repository

import org.junit.Assert
import org.junit.Test

class TbbRepositoryKtTest {

  private lateinit var repository: TbbRepository

  @Test
  fun test_fetchHPData() {
    val execute = repository.fetchHPData()
    val body = execute.blockingFirst()
    println(body)
    Assert.assertNotNull("fetchHPData", body)
  }

  @Test
  fun test_fetchUser() {
    val execute = repository.fetchUser()
    val body = execute.blockingGet()
    println(body)
    Assert.assertNull("fetchUser", body)
  }

  @Test
  fun test_anime_list() {
    val execute = repository.fetchAnimeList()
    val body = execute.blockingFirst()
    println(body.joinToString("\n"))
    Assert.assertTrue(body.isNotEmpty())
  }

}

