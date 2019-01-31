package com.sukitsuki.tsukibb.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.activity.AnimeDetailActivity
import com.sukitsuki.tsukibb.adapter.AnimeListAdapter
import com.sukitsuki.tsukibb.utils.ViewModelFactory
import com.sukitsuki.tsukibb.viewmodel.AnimeListViewModel
import dagger.android.AndroidInjector
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<HomeFragment> {
    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<HomeFragment>()
  }

  @Inject
  lateinit var viewModeFactory: ViewModelFactory
  @BindView(R.id.home_refresh_layout)
  lateinit var swipeRefreshLayout: SwipeRefreshLayout

  private val animeListAdapter by lazy {
    AnimeListAdapter(requireContext()).apply {
      this.onItemClick = { it ->
        val intent = Intent(context, AnimeDetailActivity::class.java)
        intent.putExtra("animeList", it)
        startActivity(intent)
      }
    }
  }
  private val animeListViewModel by lazy {
    ViewModelProviders.of(this@HomeFragment, viewModeFactory).get(AnimeListViewModel::class.java).apply {
      this.animeList.observe(this@HomeFragment, Observer { animeListAdapter.loadDataSet(it) })
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // init animeListViewModel on create
    ::animeListViewModel.get()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
    inflater.inflate(R.layout.fragment_home, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    ButterKnife.bind(this, view)
    view.findViewById<RecyclerView>(R.id.anime_list_view).apply {
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(context, 3).apply { initialPrefetchItemCount = 6 }
      adapter = animeListAdapter
      this.addItemDecoration(ItemOffsetDecoration(requireContext(), R.dimen.item_offset))
    }
    swipeRefreshLayout.setOnRefreshListener { animeListViewModel.refresh(swipeRefreshLayout::setRefreshing) }
    super.onViewCreated(view, savedInstanceState)
  }

  class ItemOffsetDecoration(private val mItemOffset: Int) : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetId: Int) :
      this(context.resources.getDimensionPixelSize(itemOffsetId))

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) =
      outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset)
  }
}

