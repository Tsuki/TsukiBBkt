package com.sukitsuki.tsukibb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.activity.AnimeDetailActivity
import com.sukitsuki.tsukibb.adapter.EpisodesListAdapter
import com.sukitsuki.tsukibb.model.Season.SeasonList.SeasonsItem
import com.sukitsuki.tsukibb.model.Season.SeasonList.SeasonsItem.EpisodesItem


class EpisodesListFragment : ListFragment() {
  private lateinit var mItems: List<EpisodesItem>
  private lateinit var mSeasonsItem: SeasonsItem
  override fun onCreate(savedInstanceState: Bundle?) {
    mSeasonsItem = arguments?.get("SeasonsItem") as SeasonsItem
    mItems = mSeasonsItem.episodes.sortedByDescending { it.title }
    listAdapter = EpisodesListAdapter(context!!, R.layout.item_episode, mItems)
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.view_episodes_item, container, false)
  }

  override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
    val animeDetailActivity = activity as AnimeDetailActivity
    animeDetailActivity.openEpisodesItem(mItems[position], mSeasonsItem.copy(episodes = emptyList()))
    super.onListItemClick(l, v, position, id)
  }
}

