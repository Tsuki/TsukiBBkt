package com.sukitsuki.tsukibb.utils

import android.content.Context
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.os.Environment
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.sukitsuki.tsukibb.R
import org.jetbrains.anko.doAsync
import permissions.dispatcher.PermissionRequest
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.util.*


fun Context.takeScreenshot(bitmap: Bitmap): String? {
  val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TsukiBB")
  val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
  val mediaFile = File("${mediaStorageDir.path}${File.separator}$timestamp.jpg")
  var compress = false
  doAsync {
    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
      return@doAsync
    }
    Timber.d("screenshot: file ${mediaStorageDir.path}${File.separator}$timestamp.jpg")
    val fos = FileOutputStream(mediaFile)
    compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
    fos.close()
    SingleMediaScanner(this@takeScreenshot, mediaStorageDir)
  }.get()
  return if (compress) mediaFile.toString() else null
}

fun Context.takeScreenshot(bitmap: Bitmap, mediaFile: File): String? {
  val compress: Boolean
  val fos = FileOutputStream(mediaFile)
  compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
  fos.close()
  return if (compress) mediaFile.toString() else null
}

fun Context.showRationale(request: PermissionRequest, message: String) {
  AlertDialog.Builder(applicationContext).setMessage(R.string.alert_sure_to_change)
    .setPositiveButton("Yes") { _, _ -> request.proceed() }
    .setNegativeButton("No") { _, _ -> request.cancel() }
    .setCancelable(false).setMessage(message).show()!!
}


fun Context.showRationale(request: PermissionRequest, @StringRes message: Int): AlertDialog? {
  return AlertDialog.Builder(applicationContext).setMessage(R.string.alert_sure_to_change)
    .setPositiveButton("Yes") { _, _ -> request.proceed() }
    .setNegativeButton("No") { _, _ -> request.cancel() }
    .setCancelable(false).setMessage(message).show()
}
