package com.sukitsuki.tsukibb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.activity.AnimeDetailActivity
import com.sukitsuki.tsukibb.adapter.FavoriteAdapter
import com.sukitsuki.tsukibb.model.AnimeList
import com.sukitsuki.tsukibb.utils.ViewModelFactory
import com.sukitsuki.tsukibb.viewmodel.FavoriteViewModel
import dagger.android.AndroidInjector
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.support.v4.startActivity
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<FavoriteFragment> {
    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FavoriteFragment>()
  }

  private val viewModel by lazy { ViewModelProviders.of(this, viewModeFactory).get(FavoriteViewModel::class.java) }
  private val favoriteAdapter by lazy {
    FavoriteAdapter(requireContext(), R.layout.item_favorite, mutableListOf()).apply {
      this.onItemClick = { startActivity<AnimeDetailActivity>("animeList" to AnimeList(it)) }
    }
  }
  @Inject
  lateinit var viewModeFactory: ViewModelFactory

  @BindView(android.R.id.list)
  lateinit var listView: ListView

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_favorite, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    ButterKnife.bind(this, view)
    viewModel.favorites.observe(viewLifecycleOwner, Observer { favoriteAdapter.notifyDataSetChanged(it) })
    listView.adapter = favoriteAdapter
  }

}
