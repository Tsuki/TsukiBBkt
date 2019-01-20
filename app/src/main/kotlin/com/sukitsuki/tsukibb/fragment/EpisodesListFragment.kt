package com.sukitsuki.tsukibb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.SeasonsItem

class EpisodesListFragment : ListFragment() {
  private lateinit var item: SeasonsItem
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    item = arguments?.get("SeasonsItem") as SeasonsItem
    return inflater.inflate(R.layout.view_episodes_item, container, false)
  }
}

