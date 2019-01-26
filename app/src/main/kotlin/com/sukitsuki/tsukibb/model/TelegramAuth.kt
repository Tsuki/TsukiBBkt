package com.sukitsuki.tsukibb.model


import com.google.gson.annotations.SerializedName


data class TelegramAuth(
  @SerializedName("origin")
  val origin: String = "",
  @SerializedName("html")
  val html: String = "",
  @SerializedName("user")
  val user: TelegramUser
) {
  data class TelegramUser(
    @SerializedName("id")
    val id: String = "0"
    , @SerializedName("first_name")
    val firstName: String = ""
    , @SerializedName("last_name")
    val lastName: String? = null
    , @SerializedName("username")
    val username: String? = null
    , @SerializedName("auth_date")
    val authDate: String = "0"
    , @SerializedName("hash")
    val hash: String = ""
  ) {
    fun toMap(): Map<String, String?> {
      return mapOf(
        "id" to id
        , "first_name" to firstName
        , "last_name" to lastName
        , "username" to username
        , "auth_date" to authDate
        , "hash" to hash
      ).filterValues { it != null }
    }
  }
}


