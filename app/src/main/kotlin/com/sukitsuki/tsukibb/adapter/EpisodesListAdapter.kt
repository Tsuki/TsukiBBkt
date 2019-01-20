package com.sukitsuki.tsukibb.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.model.EpisodesItem


class EpisodesListAdapter(context: Context, users: ArrayList<EpisodesItem>) :
  ArrayAdapter<EpisodesItem>(context, R.layout.view_episodes_item, users) {
  private lateinit var text: TextView
  private lateinit var text2: TextView
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val item = getItem(position)
    text = convertView?.findViewById(R.id.textView2)!!
    text2 = convertView.findViewById(R.id.textView3)
    text.text = item?.title
    text2.text = item?.duration.toString()
    return super.getView(position, convertView, parent)
  }
}