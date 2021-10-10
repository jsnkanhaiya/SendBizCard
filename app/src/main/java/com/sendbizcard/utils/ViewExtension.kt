package com.sendbizcard.utils

import android.content.ContextWrapper
import android.graphics.Rect
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.badge.BadgeDrawable

fun View.getParentActivity(): AppCompatActivity? {
  var context = this.context
  while (context is ContextWrapper) {
    if (context is AppCompatActivity) {
      return context
    }
    context = context.baseContext
  }
  return null
}


fun View.visible() {
  this.visibility = View.VISIBLE
}

fun View.gone() {
  this.visibility = View.GONE
}

fun View.invisible() {
  this.visibility = View.INVISIBLE
}

fun ViewGroup.visible() {
  this.visibility = View.VISIBLE
}

fun ViewGroup.gone() {
  this.visibility = View.GONE
}

fun ViewGroup.invisible() {
  this.visibility = View.INVISIBLE
}


fun View.fadeIn(durationMillis: Long = 250) {
  this.startAnimation(AlphaAnimation(0F, 1F).apply {
    duration = durationMillis
    fillAfter = true
  })
}

fun View.fadeOut(durationMillis: Long = 250) {
  this.startAnimation(AlphaAnimation(1F, 0F).apply {
    duration = durationMillis
    fillAfter = true
  })
}

/**
 * rapid click Controll if user click multiple
 * time withing 1sec then only one click take
 */
fun View.setOnClickDelayListener(debounceTime: Long = 1000L, action: () -> Unit) {
  this.setOnClickListener(object : View.OnClickListener {
    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
      if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
      else action()

      lastClickTime = SystemClock.elapsedRealtime()
    }
  })
}

fun View.setMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
  val params = (layoutParams as? ViewGroup.MarginLayoutParams)
  params?.setMargins(
    left ?: params.leftMargin,
    top ?: params.topMargin,
    right ?: params.rightMargin,
    bottom ?: params.bottomMargin
  )
  layoutParams = params
}