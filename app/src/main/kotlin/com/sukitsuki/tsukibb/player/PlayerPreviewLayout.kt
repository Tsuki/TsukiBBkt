package com.sukitsuki.tsukibb.player

import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.exoplayer2.ui.TimeBar
import com.sukitsuki.tsukibb.GlideApp
import com.sukitsuki.tsukibb.R
import com.sukitsuki.tsukibb.utils.GlideThumbnailTransformation

class PlayerPreviewLayout : FrameLayout, TimeBar.OnScrubListener {
  @BindView(R.id.timeline_preview_time)
  lateinit var mTimeTitle: TextView
  @BindView(R.id.timeline_preview_image)
  lateinit var mTimeImage: ImageView
  var thumbnailsUrl: String = ""
  var duration: Long = -1
  // private val mImageLoader by lazy { GlideApp.with(this) }
  private val mImageLoader by lazy { GlideApp.with(this) }

  constructor(context: Context) : this(context, null)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    LayoutInflater.from(context).inflate(R.layout.item_timeline_preview, this, true)
    ButterKnife.bind(this)
    visibility = View.GONE
  }


  override fun onScrubMove(timeBar: TimeBar?, position: Long) {
    if (duration < 0 || thumbnailsUrl == "") return
    mImageLoader.load(thumbnailsUrl).transform(GlideThumbnailTransformation(position, duration)).placeholder(mTimeImage.drawable).into(mTimeImage)
    mTimeTitle.text = DateUtils.formatElapsedTime(position / 1000)
  }

  override fun onScrubStart(timeBar: TimeBar?, position: Long) {
    mTimeTitle.text = DateUtils.formatElapsedTime(position / 1000)
    visibility = View.VISIBLE
  }

  override fun onScrubStop(timeBar: TimeBar?, position: Long, canceled: Boolean) {
    visibility = View.GONE
  }
}