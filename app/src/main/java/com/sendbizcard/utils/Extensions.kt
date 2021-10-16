package com.sendbizcard.utils

import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.widget.AppCompatImageView
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