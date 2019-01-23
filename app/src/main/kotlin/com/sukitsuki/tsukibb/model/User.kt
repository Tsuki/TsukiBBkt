package com.sukitsuki.tsukibb.model

import com.google.gson.annotations.SerializedName

data class User(

  @SerializedName("id")
  val id: String = "",

  @SerializedName("nickname")
  val nickname: String = "",

  @SerializedName("photo_url")
  val photoUrl: String = ""

)