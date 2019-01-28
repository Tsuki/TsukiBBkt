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
val MIGRATION_3_4 = object : Migration(3, 4) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("CREATE TABLE IF NOT EXISTS favorites_new (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `anime_id` INTEGER NOT NULL, `aired` INTEGER NOT NULL, `name_chi` TEXT NOT NULL, `last_updated` INTEGER NOT NULL, `episode_title` TEXT NOT NULL)")
    database.execSQL("INSERT INTO favorites_new (id, anime_id, aired, name_chi) SELECT id, anime_id, aired, name_chi FROM favorites")
    database.execSQL("DROP TABLE favorites")
    database.execSQL("ALTER TABLE favorites_new RENAME TO favorites")
  }
}