package com.sukitsuki.tsukibb.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sukitsuki.tsukibb.MainActivity
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.adapter.AnimeListAdapter
import com.sukitsuki.tsukibb.model.AnimeList


class HomeFragment : Fragment() {
  private lateinit var recyclerView: RecyclerView
  private lateinit var viewAdapter: RecyclerView.Adapter<*>
  private lateinit var viewManager: RecyclerView.LayoutManager

  override fun onAttach(context: Context?) {
    viewManager = GridLayoutManager(context, 3)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val main: MainActivity = activity as MainActivity
    val strtext = arguments?.getParcelableArrayList("animeList") ?: emptyList<AnimeList>()
//    viewAdapter = AnimeListAdapter(main.animeList)
    viewAdapter = AnimeListAdapter(
      listOf(
        AnimeList(
          aired = 201810,
          animeId = 228,
          episodeTitle = "15",
          isEnded = 0,
          isHidden = 0,
          lastUpdated = "2019 - 01 - 18 T15 :35:22.000Z",
          nameChi = "魔法禁書目錄",
          nameJpn = "とある魔術の禁書目録",
          seasonId = 797,
          seasonTitle = "第三季"
        ),
        AnimeList(
          aired = 201810,
          animeId = 228,
          episodeTitle = "15",
          isEnded = 0,
          isHidden = 0,
          lastUpdated = "2019 - 01 - 18 T15 :35:22.000Z",
          nameChi = "魔法禁書目錄",
          nameJpn = "とある魔術の禁書目録",
          seasonId = 797,
          seasonTitle = "第三季"
        ),
        AnimeList(
          aired = 201810,
          animeId = 228,
          episodeTitle = "15",
          isEnded = 0,
          isHidden = 0,
          lastUpdated = "2019 - 01 - 18 T15 :35:22.000Z",
          nameChi = "魔法禁書目錄",
          nameJpn = "とある魔術の禁書目録",
          seasonId = 797,
          seasonTitle = "第三季"
        )
      )
    )

    return inflater.inflate(R.layout.fragment_home, container, false)
//    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    recyclerView = view.findViewById<RecyclerView>(R.id.anime_list_view).apply {
      setHasFixedSize(true)
      layoutManager = viewManager
      adapter = viewAdapter
    }
    val itemDecoration = ItemOffsetDecoration(context!!, R.dimen.item_offset)
    recyclerView.addItemDecoration(itemDecoration)
    super.onViewCreated(view, savedInstanceState)
  }
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