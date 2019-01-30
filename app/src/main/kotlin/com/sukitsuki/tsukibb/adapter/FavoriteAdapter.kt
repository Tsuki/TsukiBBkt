package com.sukitsuki.tsukibb.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.sukitsuki.tsukibb.GlideApp
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.entity.Favorite
import org.jetbrains.anko.sdk27.coroutines.onClick

class FavoriteAdapter(context: Context, private val resource: Int, items: List<Favorite>) :
  ArrayAdapter<Favorite>(context, resource, items) {
  lateinit var onItemClick: ((Favorite) -> Unit)
  private val mImageLoader by lazy { GlideApp.with(context) }
  private lateinit var mViewHolder: FavoriteAdapter.ViewHolder
  private lateinit var inflate: View

  fun notifyDataSetChanged(items: List<Favorite>) {
    this.clear()
    this.addAll(items)
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val item = getItem(position)
    if (convertView == null) {
      inflate = LayoutInflater.from(context).inflate(resource, parent, false)
      mViewHolder = FavoriteAdapter.ViewHolder(
        inflate.findViewById(R.id.animeCover),
        inflate.findViewById(R.id.animeName),
        inflate.findViewById(R.id.episodesTitle),
        inflate.findViewById(R.id.episodesTLastUpdate)
      )
      inflate.tag = mViewHolder
    } else {
      inflate = convertView
      mViewHolder = convertView.tag as FavoriteAdapter.ViewHolder
    }
    item?.let {
      mImageLoader.load("https://seaside.ebb.io/${item.animeId}x${item.seasonId}.jpg")
        .into(mViewHolder.animeCover)
      mViewHolder.animeName.text = it.nameChi
      mViewHolder.episodesTitle.text = "${it.seasonTitle} - ${it.episodeTitle}"
      mViewHolder.episodesTLastUpdate.text = "最後更新: " + DateUtils.getRelativeTimeSpanString(it.lastUpdated.time)
      return@let
    }
    inflate.onClick { item?.let { onItemClick.invoke(it) } }
    return inflate
  }

  private data class ViewHolder constructor(
    val animeCover: ImageView,
    val animeName: TextView,
    val episodesTitle: TextView,
    val episodesTLastUpdate: TextView
  )
}