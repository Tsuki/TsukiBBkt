package com.sukitsuki.tsukibb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.sukitsuki.tsukibb.entity.Favorite

class FavoriteAdapter(context: Context, private val resource: Int, items: List<Favorite>) :
  ArrayAdapter<Favorite>(context, resource, items) {

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
      mViewHolder = FavoriteAdapter.ViewHolder()
      inflate.tag = mViewHolder
    } else {
      inflate = convertView
      mViewHolder = convertView.tag as FavoriteAdapter.ViewHolder
    }
    return inflate
  }

  private data class ViewHolder constructor(
    val position: Int = 0
  )
}