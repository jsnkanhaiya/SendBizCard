package com.sendbizcard.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.use

@ColorInt
fun Context.getThemeColor(@AttrRes colorAttrId: Int): Int {
  return obtainStyledAttributes(intArrayOf(colorAttrId))
    .use { it.getColor(0, Color.BLUE) }
}

infix fun Context?.convertDpToPx(dps: Int): Int {

  var pixels = 2
  if (this != null) {
    val scale = resources.displayMetrics.density
    pixels = (dps * scale + 0.5f).toInt()
  }
  return pixels
}

fun Context.drawable(@DrawableRes id: Int): Drawable {
  return if (Build.VERSION.SDK_INT >= 21) {
    resources.getDrawable(id, null)
  } else {
    @Suppress("DEPRECATION")
    resources.getDrawable(id)
  }
}

fun Context?.vibratePhone() {
  val vibrator = this?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
  if (Build.VERSION.SDK_INT >= 26) {
    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
  } else {
    vibrator.vibrate(200)
  }
}