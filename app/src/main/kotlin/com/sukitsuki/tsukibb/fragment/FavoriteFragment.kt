package com.sukitsuki.tsukibb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.activity.AnimeDetailActivity
import com.sukitsuki.tsukibb.adapter.FavoriteAdapter
import com.sukitsuki.tsukibb.entity.Favorite
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
    FavoriteAdapter(requireContext(), R.layout.item_favorite).apply {
      this.onItemClick = { startActivity<AnimeDetailActivity>("animeList" to AnimeList(it)) }
    }
  }
  @Inject
  lateinit var viewModeFactory: ViewModelFactory

  @BindView(R.id.favoriteRecyclerView)
  lateinit var favoriteRecyclerView: RecyclerView

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
    inflater.inflate(R.layout.fragment_favorite, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    ButterKnife.bind(this, view)
    favoriteRecyclerView.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(requireContext())
      adapter = favoriteAdapter
    }
    viewModel.favorites.observe(viewLifecycleOwner, Observer { favoriteAdapter.loadDataSet(it.sortedByDescending(Favorite::lastUpdated)) })
  }

}
