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
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.activity.AnimeDetailActivity
import com.sukitsuki.tsukibb.adapter.AnimeListAdapter
import com.sukitsuki.tsukibb.viewmodel.AnimeListViewModel
import com.sukitsuki.tsukibb.viewmodel.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : DaggerFragment() {
  private lateinit var recyclerView: RecyclerView
  private lateinit var viewAdapter: RecyclerView.Adapter<*>
  private lateinit var viewManager: RecyclerView.LayoutManager
  private lateinit var animeListViewModel: AnimeListViewModel
  private var animeListAdapter: AnimeListAdapter = AnimeListAdapter()

  @dagger.Subcomponent(modules = [])
  interface Component : AndroidInjector<HomeFragment> {
    @dagger.Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<HomeFragment>()
  }

  @Inject
  lateinit var viewModeFactory: ViewModelFactory


  @Inject
  fun logInjection() {
    Timber.d("Injecting ${this::class.java.simpleName}")
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    viewManager = GridLayoutManager(context, 3)
    animeListViewModel = ViewModelProviders.of(this@HomeFragment, viewModeFactory).get(AnimeListViewModel::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    viewAdapter = animeListAdapter
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    recyclerView = view.findViewById<RecyclerView>(R.id.anime_list_view).apply {
      setHasFixedSize(true)
      layoutManager = viewManager
      adapter = viewAdapter

    }
    animeListAdapter.onItemClick = { it ->
      val intent = Intent(context, AnimeDetailActivity::class.java)
      intent.putExtra("animeList", it)
      startActivity(intent)
    }
    animeListViewModel.animeList.observe(this, Observer {
      animeListAdapter.loadDataSet(it)
    })
    val itemDecoration = ItemOffsetDecoration(context!!, R.dimen.item_offset)
    recyclerView.addItemDecoration(itemDecoration)
    super.onViewCreated(view, savedInstanceState)
  }

  class ItemOffsetDecoration(private val mItemOffset: Int) : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetId: Int) : this(
      context.resources.getDimensionPixelSize(
        itemOffsetId
      )
    )

    override fun getItemOffsets(
      outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
      super.getItemOffsets(outRect, view, parent, state)
      outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset)
    }
  }
}

