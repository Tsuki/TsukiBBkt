package com.sukitsuki.tsukibb.service

import org.junit.Test
import retrofit2.Response

class TbbServiceKtTest {

  private val service: TbbService = TbbService.instance
  @Test
  fun test_logout() {
    val execute: Response<RespHpData> = service.fetchHPData().execute()
    val body: RespHpData? = execute.body()
    println(body)
  }
}

