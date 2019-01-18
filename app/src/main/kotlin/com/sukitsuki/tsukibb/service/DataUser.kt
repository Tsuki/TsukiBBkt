package com.sukitsuki.tsukibb.service

import com.google.gson.annotations.SerializedName

data class DataUser(
  @SerializedName("id")
  val id: String,
  @SerializedName("nickname")
  val nickname: String,
  @SerializedName("photo_url")
  val photoUrl: String
)