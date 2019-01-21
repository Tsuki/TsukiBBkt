package com.sukitsuki.tsukibb.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.EpisodesItem


class EpisodesListAdapter(context: Context, resource: Int, items: List<EpisodesItem>) :
  ArrayAdapter<EpisodesItem>(context, resource, items) {
  private lateinit var mViewHolder: ViewHolder
  private lateinit var inflate: View
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val item = getItem(position)
    if (convertView == null) {
      inflate = LayoutInflater.from(context).inflate(R.layout.item_episode, parent, false)
      mViewHolder = ViewHolder(inflate.findViewById(R.id.episodesTitle), inflate.findViewById(R.id.episodesDuration))
      inflate.tag = mViewHolder
    } else {
      inflate = convertView
      mViewHolder = convertView.tag as ViewHolder
    }
    mViewHolder.episodesTitle.text = item?.title
    mViewHolder.episodesDuration.text = DateUtils.formatElapsedTime(item?.duration?.toLong() ?: 0)
    return inflate
  }

  private data class ViewHolder @JvmOverloads constructor(
    var episodesTitle: TextView,
    var episodesDuration: TextView,
    val position: Int = 0
  )

}