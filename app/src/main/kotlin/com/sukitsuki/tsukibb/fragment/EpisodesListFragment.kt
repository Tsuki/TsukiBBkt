package com.sukitsuki.tsukibb.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.adapter.EpisodesListAdapter
import com.sukitsuki.tsukibb.model.SeasonsItem


class EpisodesListFragment : ListFragment() {
  private lateinit var item: SeasonsItem
  override fun onCreate(savedInstanceState: Bundle?) {
    item = arguments?.get("SeasonsItem") as SeasonsItem
    listAdapter = EpisodesListAdapter(context!!, R.layout.item_episode, item.episodes.sortedByDescending { it.title })
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.view_episodes_item, container, false)
  }

  override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
    Log.d(tag, "onListItemClick" + position)
    super.onListItemClick(l, v, position, id)
  }
}

