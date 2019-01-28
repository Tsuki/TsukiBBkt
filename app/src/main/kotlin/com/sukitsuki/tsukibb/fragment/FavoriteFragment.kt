package com.sukitsuki.tsukibb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sukitsuki.tsukibb.R
import dagger.android.AndroidInjector
import dagger.android.support.DaggerFragment

class FavoriteFragment : DaggerFragment() {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<FavoriteFragment> {
    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FavoriteFragment>()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

}