package com.sukitsuki.tsukibb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.SeasonsItem


class EpisodesListFragment : ListFragment() {
  private lateinit var item: SeasonsItem
  private lateinit var data: List<String>
  override fun onCreate(savedInstanceState: Bundle?) {
    item = arguments?.get("SeasonsItem") as SeasonsItem
    data = item.episodes.map { it.title }.sortedDescending()
    listAdapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, data) as ListAdapter
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.view_episodes_item, container, false)
  }

  override fun getView(): View? {
    return super.getView()
  }

  override fun getListView(): ListView {
    return super.getListView()
  }
}

