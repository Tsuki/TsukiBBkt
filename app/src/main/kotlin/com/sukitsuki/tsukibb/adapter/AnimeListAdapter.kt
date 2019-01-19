package com.sukitsuki.tsukibb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.AnimeList


class AnimeListAdapter(private val dataSet: Array<AnimeList>) : RecyclerView.Adapter<AnimeListAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val textView =
      LayoutInflater.from(parent.context).inflate(R.layout.view_anime_list_holder, parent, false)
    return ViewHolder(textView)
  }

  override fun getItemCount() = dataSet.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
}