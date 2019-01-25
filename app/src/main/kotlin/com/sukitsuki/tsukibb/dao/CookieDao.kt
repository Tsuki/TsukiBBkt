package com.sukitsuki.tsukibb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sukitsuki.tsukibb.entity.Cookie

@Dao
interface CookieDao {

  @Query("SELECT * from cookies")
  fun getAll(): List<Cookie>

  @Query("SELECT * from cookies")
  fun getAllLive(): LiveData<List<Cookie>>

  @Query("SELECT * from cookies where name = :name and domain = :domain")
  fun findByNameDomain(name: String, domain: String): List<Cookie>

  @Query("SELECT * from cookies where name = :name and domain = :domain")
  fun findLiveByNameDomain(name: String, domain: String): LiveData<List<Cookie>>

  @Query("SELECT * from cookies where domain = :domain")
  fun findByDomain(domain: String): List<Cookie>

  @Query("SELECT * from cookies where domain = :domain")
  fun findLiveByDomain(domain: String): LiveData<List<Cookie>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCookie(cookie: Cookie): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCookie(vararg cookie: Cookie): List<Long>

  @Update
  fun updateCookie(vararg cookies: Cookie)

  @Query("DELETE from cookies")
  fun deleteAll()
}