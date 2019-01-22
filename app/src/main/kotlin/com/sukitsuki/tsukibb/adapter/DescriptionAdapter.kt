package com.sukitsuki.tsukibb.adapter

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.sukitsuki.tsukibb.activity.MainActivity


class DescriptionAdapter(private val app: Application) : PlayerNotificationManager.MediaDescriptionAdapter {
  lateinit var contentText: String
  lateinit var contentTitle: String
  var largeIcon: Bitmap? = null
  override fun createCurrentContentIntent(player: Player?): PendingIntent? {
    val intent = Intent(app.applicationContext, MainActivity::class.java)
    return PendingIntent.getActivity(app.applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
  }

  override fun getCurrentContentText(player: Player?): String? {
    return contentText
  }

  override fun getCurrentContentTitle(player: Player?): String {
    return contentTitle
  }

  override fun getCurrentLargeIcon(player: Player?, callback: PlayerNotificationManager.BitmapCallback?): Bitmap? {
    return largeIcon
  }

}