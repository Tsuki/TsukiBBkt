package com.sukitsuki.tsukibb.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.adapter.AnimeListAdapter


class HomeFragment : Fragment() {
  private lateinit var recyclerView: RecyclerView
  private lateinit var viewAdapter: RecyclerView.Adapter<*>
  private lateinit var viewManager: RecyclerView.LayoutManager

  override fun onAttach(context: Context?) {
    viewManager = LinearLayoutManager(context)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    viewAdapter = AnimeListAdapter(emptyArray())

    return inflater.inflate(R.layout.fragment_home, container, false)
//    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    recyclerView = view.findViewById<RecyclerView>(R.id.anime_list_view).apply {
      setHasFixedSize(true)
      layoutManager = viewManager
      adapter = viewAdapter
    }
    super.onViewCreated(view, savedInstanceState)
  }
}