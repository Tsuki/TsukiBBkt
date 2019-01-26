package com.sukitsuki.tsukibb

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("DROP INDEX `index_cookies_name_domain_path`")
    database.execSQL("CREATE UNIQUE INDEX `index_cookies_name_domain_path_http_only` ON `cookies` (`name`, `domain`, `path`, `http_only`)")
  }
}
val MIGRATION_2_3 = object : Migration(2, 3) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("DROP INDEX `index_cookies_name_domain_path_http_only`")
    database.execSQL("CREATE UNIQUE INDEX `index_cookies_name_domain_path_secure_http_only` ON `cookies` (`name`, `domain`, `path`, `secure`, `http_only`)")
  }
}