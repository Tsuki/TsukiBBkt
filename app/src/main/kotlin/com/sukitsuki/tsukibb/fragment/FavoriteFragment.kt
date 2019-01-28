package com.sukitsuki.tsukibb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sukitsuki.tsukibb.R
import dagger.android.AndroidInjector

class FavoriteFragment : Fragment() {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<FavoriteFragment> {
    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FavoriteFragment>()
  }

  private lateinit var viewModel: FavoriteViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.favorite_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
  }

}
