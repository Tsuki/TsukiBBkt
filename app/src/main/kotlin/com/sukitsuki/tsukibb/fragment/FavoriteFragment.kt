package com.sukitsuki.tsukibb.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.entity.Favorite
import com.sukitsuki.tsukibb.repository.FavoriteRepository
import com.sukitsuki.tsukibb.viewmodel.FavoriteViewModel
import com.sukitsuki.tsukibb.viewmodel.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import timber.log.Timber
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {
  private lateinit var mItems: List<Favorite>

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<FavoriteFragment> {
    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FavoriteFragment>()
  }

  private lateinit var viewModel: FavoriteViewModel

  @Inject
  lateinit var viewModeFactory: ViewModelFactory
  @Inject
  lateinit var mFavoriteRepository: FavoriteRepository

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    viewModel = ViewModelProviders.of(this, viewModeFactory).get(FavoriteViewModel::class.java)
    doAsync {
      mItems = mFavoriteRepository.getAllFavorite()
      Timber.d("onAttach: ${mItems.joinToString("\n")}")
      uiThread { }
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_favorite, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
  }

}
