package com.sukitsuki.tsukibb.dao

import androidx.room.*
import com.sukitsuki.tsukibb.entity.Cookie
import io.reactivex.Observable

@Dao
interface CookieDao {

  @Query("SELECT * from cookies")
  fun getAll(): Observable<List<Cookie>>

  @Query("SELECT * from cookies where name = :name and domain = :domain")
  fun findByNameDomain(name: String, domain: String): List<Cookie>

  @Query("SELECT * from cookies where domain = :domain")
  fun findByDomain(domain: String): List<Cookie>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCookie(cookie: Cookie): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCookie(vararg cookie: Cookie): List<Long>

  @Update
  fun updateCookie(vararg cookies: Cookie)

  @Query("DELETE from cookies")
  fun deleteAll()
}