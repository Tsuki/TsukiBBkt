package com.sukitsuki.tsukibb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.sukitsuki.tsukibb.BuildConfig
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.AnimeList


class AnimeListAdapter(val context: Context) : RecyclerView.Adapter<AnimeListAdapter.ViewHolder>() {
  var onItemClick: ((AnimeList) -> Unit)? = null
  var dataSet: List<AnimeList> = emptyList()
  private val picasso by lazy {
    Picasso.Builder(context).downloader(OkHttp3Downloader(context, Long.MAX_VALUE)).build()
  }

  fun loadDataSet(newDataSet: List<AnimeList>) {
    dataSet = newDataSet
    this.notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val textView =
      LayoutInflater.from(parent.context).inflate(R.layout.view_anime_list_holder, parent, false)
    return ViewHolder(textView)
  }

  override fun getItemCount() = dataSet.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val anime = dataSet[position]
    picasso.setIndicatorsEnabled(BuildConfig.DEBUG)
    picasso.load("https://seaside.ebb.io/${anime.animeId}x${anime.seasonId}.jpg").into(holder.animeImage)
    holder.textView.text = anime.nameChi
  }

  inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val animeImage: ImageView = v.findViewById(R.id.anime_image)
    val textView: TextView = v.findViewById(R.id.anime_title)

    init {
      v.setOnClickListener {
        onItemClick?.invoke(dataSet[adapterPosition])
      }
    }
  }
}