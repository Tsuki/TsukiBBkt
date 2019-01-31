package com.sukitsuki.tsukibb.adapter


import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sukitsuki.tsukibb.GlideApp
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.entity.Favorite

class FavoriteAdapter(context: Context, private val resource: Int) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

  var dataSet: List<Favorite> = emptyList()
  lateinit var onItemClick: ((Favorite) -> Unit)
  private val mImageLoader by lazy { GlideApp.with(context) }

  fun loadDataSet(newDataSet: List<Favorite>) {
    dataSet = newDataSet
    this.notifyDataSetChanged()
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(LayoutInflater.from(parent.context).inflate(resource, parent, false))

  override fun getItemCount() = dataSet.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = dataSet[position]
    mImageLoader.load("https://seaside.ebb.io/${item.animeId}x${item.seasonId}.jpg")
      .into(holder.animeCover)
    holder.animeName.text = item.nameChi
    holder.episodesTitle.text = "${item.seasonTitle} - ${item.episodeTitle}"
    holder.episodesTLastUpdate.text = "最後更新: " + DateUtils.getRelativeTimeSpanString(item.lastUpdated.time)
  }

  inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val animeCover: ImageView = v.findViewById(R.id.animeCover)
    val animeName: TextView = v.findViewById(R.id.animeName)
    val episodesTitle: TextView = v.findViewById(R.id.episodesTitle)
    val episodesTLastUpdate: TextView = v.findViewById(R.id.episodesTLastUpdate)

    init {
      v.setOnClickListener {
        onItemClick.invoke(dataSet[adapterPosition])
      }
    }
  }
}