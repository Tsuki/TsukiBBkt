package com.sukitsuki.tsukibb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.AnimeList


class AnimeListAdapter(private val dataSet: List<AnimeList>) : RecyclerView.Adapter<AnimeListAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val textView =
      LayoutInflater.from(parent.context).inflate(R.layout.view_anime_list_holder, parent, false)
    return ViewHolder(textView)
  }

  override fun getItemCount() = dataSet.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val anime = dataSet[position]
    Picasso.get().load("https://seaside.ebb.io/${anime.animeId}x${anime.seasonId}.jpg").into(holder.animeImage)
  }

  class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val animeImage: ImageView = v.findViewById(R.id.anime_image)

  }
}