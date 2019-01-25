package com.sukitsuki.tsukibb.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sukitsuki.tsukibb.AppDatabase
import com.sukitsuki.tsukibb.entity.Cookie
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class CookieDaoTest {
  private lateinit var appDatabase: AppDatabase
  private lateinit var cookieDao: CookieDao

  @Before
  fun initDb() {
    Timber.plant(Timber.DebugTree())
    appDatabase =
      Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, AppDatabase::class.java)
        .build()
    cookieDao = appDatabase.cookieDao()
  }

  @After
  fun closeDb() {
    appDatabase.close()
  }

  @Test
  fun testInsert() {
    cookieDao.insertCookie(Cookie(name = "test", domain = "https://www.google.com", path = "/"))
    cookieDao.insertCookie(Cookie(name = "test2", domain = "https://www.google.com", path = "/"))
    cookieDao.insertCookie(Cookie(name = "test3", domain = "https://www.google.com", path = "/"))
    cookieDao.insertCookie(Cookie(name = "test3", domain = "https://www.google.com", path = "/"))
    val list = cookieDao.getAll()
    Timber.d("testInsert: ${list.joinToString("\n")}")
    Assert.assertEquals(3, list.size)
  }

  @Test
  fun testGroup() {
    cookieDao.insertCookie(Cookie(name = "test", domain = "https://www.google.com", path = "/"))
    cookieDao.insertCookie(Cookie(name = "test2", domain = "https://www.google.com", path = "/"))
    cookieDao.insertCookie(Cookie(name = "test3", domain = "https://www.google.com", path = "/"))
    cookieDao.insertCookie(Cookie(name = "test3", domain = "https://www.google.com", path = "/"))
    val list = cookieDao.getAll()
    Timber.d("testGroup: ${list.groupBy { it.domain }}")
    Assert.assertEquals(3, list.size)
    Assert.assertEquals(1, list.groupBy { it.domain }.keys.size)
  }
}