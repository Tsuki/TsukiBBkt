package com.sukitsuki.tsukibb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cookies", indices = [Index(value = ["name", "domain", "path"], unique = true)])
data class Cookie(
//  FIXME id may overflow
  @PrimaryKey(autoGenerate = true) val id: Long? = null
  , @ColumnInfo(name = "name") val name: String = ""
  , @ColumnInfo(name = "value") val value: String = ""
  , @ColumnInfo(name = "expires_at") val expiresAt: Long = -1
  , @ColumnInfo(name = "domain") val domain: String = ""
  , @ColumnInfo(name = "path") val path: String = ""
  , @ColumnInfo(name = "secure") val secure: Boolean = false
  , @ColumnInfo(name = "http_only") val httpOnly: Boolean = false
  , @ColumnInfo(name = "persistent") val persistent: Boolean = false
  , @ColumnInfo(name = "host_only") val hostOnly: Boolean = false
) {
  constructor(cookie: okhttp3.Cookie) : this(
    null,
    cookie.name(),
    cookie.value(),
    cookie.expiresAt(),
    cookie.domain(),
    cookie.path(),
    cookie.secure(),
    cookie.httpOnly(),
    cookie.persistent(),
    cookie.hostOnly()
  )

  constructor(id: Long, cookie: okhttp3.Cookie) : this(
    id,
    cookie.name(),
    cookie.value(),
    cookie.expiresAt(),
    cookie.domain(),
    cookie.path(),
    cookie.secure(),
    cookie.httpOnly(),
    cookie.persistent(),
    cookie.hostOnly()
  )

  fun toOkHttpCookie(cookie: Cookie): okhttp3.Cookie {
    return okhttp3.Cookie.Builder().apply {
      this.name(cookie.name)
      this.value(cookie.value)
      this.expiresAt(cookie.expiresAt)
      this.domain(cookie.domain)
      this.path(cookie.path)
      if (cookie.secure) this.secure()
      if (cookie.httpOnly) this.httpOnly()
      if (cookie.hostOnly) this.hostOnlyDomain(cookie.domain)
      cookie.httpOnly
      cookie.persistent
      cookie.hostOnly
    }.build()
  }
}