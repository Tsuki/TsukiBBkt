package com.sukitsuki.tsukibb.utils

import android.app.Notification
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class NotificationListener : PlayerNotificationManager.NotificationListener {
  override fun onNotificationCancelled(notificationId: Int) {
  }

  override fun onNotificationStarted(notificationId: Int, notification: Notification?) {
  }
}