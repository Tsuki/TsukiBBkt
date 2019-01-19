package com.sukitsuki.tsukibb.model

import androidx.lifecycle.MutableLiveData

interface Nullable {
  val isNull: Boolean
}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }