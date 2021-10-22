package com.sendbizcard.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun withDelayOnMain(delay: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delay)
}
fun String.isValidEmail() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun AppCompatImageView.loadImages(url: String) {
    val options = RequestOptions()
    Glide.with(context).load(url).apply(options).into(this)
}


fun AppCompatImageView.loadCircleImages(url: String) {

    Glide.with(context).load(url) .apply(RequestOptions.circleCropTransform()).into(this)
}

fun AppCompatImageView.loadBitmap(bitmap: Bitmap) {
    Glide.with(context)
        .load(bitmap)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}
