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

  fun toOkHttpCookie(): okhttp3.Cookie {
    return okhttp3.Cookie.Builder().apply {
      this.name(this@Cookie.name)
      this.value(this@Cookie.value)
      this.expiresAt(this@Cookie.expiresAt)
      this.domain(this@Cookie.domain)
      this.path(this@Cookie.path)
      if (this@Cookie.secure) this.secure()
      if (this@Cookie.httpOnly) this.httpOnly()
      if (this@Cookie.hostOnly) this.hostOnlyDomain(this@Cookie.domain)
      this@Cookie.httpOnly
      this@Cookie.persistent
      this@Cookie.hostOnly
    }.build()
  }

}