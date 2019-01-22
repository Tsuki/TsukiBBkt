package com.sukitsuki.tsukibb.model


import com.google.gson.annotations.SerializedName

data class TelegramUser(
  @SerializedName("auth_date")
  val authDate: Int = 0,
  @SerializedName("id")
  val id: Int = 0,
  @SerializedName("first_name")
  val firstName: String = "",
  @SerializedName("hash")
  val hash: String = "",
  @SerializedName("username")
  val username: String = ""
)


data class TelegramAuth(
  @SerializedName("origin")
  val origin: String = "",
  @SerializedName("html")
  val html: String = "",
  @SerializedName("user")
  val user: TelegramUser
)


