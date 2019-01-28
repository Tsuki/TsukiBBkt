package com.sukitsuki.tsukibb.dao

import androidx.room.*
import com.sukitsuki.tsukibb.entity.Cookie
import io.reactivex.Observable

@Dao
interface CookieDao : BaseDao<Cookie> {

  @Query("SELECT * from cookies")
  fun getAll(): Observable<List<Cookie>>

  @Query("SELECT * from cookies where name = :name and domain = :domain")
  fun findByNameDomain(name: String, domain: String): List<Cookie>

  @Query("SELECT * from cookies where domain = :domain")
  fun findByDomain(domain: String): List<Cookie>

}