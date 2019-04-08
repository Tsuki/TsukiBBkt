package com.sukitsuki.tsukibb.repository

import com.sukitsuki.tsukibb.module.RepositoryModule
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

class TbbRepositoryTest {

  @Singleton
  @dagger.Component(modules = [RepositoryModule::class])
  interface TestComponent {
    fun inject(test: TbbRepositoryTest)
  }

  @Inject
  lateinit var repository: TbbRepository

  @Before
  fun setUp() {
//    DaggerTbbRepositoryTest_TestComponent.create().inject(this)
  }

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

